/*
 * Copyright (c) 2002-2024 Manorrock.com. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   1. Redistributions of source code must retain the above copyright notice,
 *      this list of conditions and the following disclaimer.
 *   2. Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 *   3. Neither the name of the copyright holder nor the names of its
 *      contributors may be used to endorse or promote products derived from
 *      this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.manorrock.sphynx.cli;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.System.Logger.Level.ERROR;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import picocli.CommandLine;

/**
 * The execute command.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@CommandLine.Command(name = "execute", mixinStandardHelpOptions = true)
public class ExecuteCommand implements Callable<Integer> {

    /**
     * Stores the logger.
     */
    private static final System.Logger LOGGER = System.getLogger(ExecuteCommand.class.getName());

    /**
     * Stores the base directory.
     */
    @CommandLine.Option(names = "--base-directory", description = "The base directory used for storage")
    protected File baseDirectory = new File(System.getProperty("user.home") + "/.manorrock/sphynx");

    /**
     * Stores the name.
     */
    @CommandLine.Option(names = "--name", description = "The name of the job", required = true)
    protected String name;

    @Override
    public Integer call() throws Exception {
        /*
         * Step 1 - Determine job directory.
         */
        File jobDirectory = new File(baseDirectory, "jobs" + File.separator + name);

        /*
         * Step 2 - Determine script filename.
         */
        File scriptFilename = new File(jobDirectory, "script" + File.separator + "run.sh");
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            scriptFilename = new File(jobDirectory, "script" + File.separator + "run.cmd");
        }

        /**
         * Step 4 - Create command array.
         */
        ArrayList<String> commands = new ArrayList<>();
        commands.add("sh");
        commands.add(scriptFilename.getAbsolutePath());

        /**
         * Step 5 - Determine work directory.
         */
        File workDirectory = new File(jobDirectory, "work");

        /*
         * Step 6 - Determine the log file.
         */
        File logFile = new File(jobDirectory, "logs" + File.separator + System.currentTimeMillis() + ".log");
        if (!logFile.getParentFile().exists()) {
            if (!logFile.getParentFile().mkdirs()) {
                LOGGER.log(ERROR, "Unable to create logs directory");
                return 1;
            }
        }

        /*
         * Step 7 - Create process.
         */
        Process process = new ProcessBuilder()
                .command(commands)
                .directory(workDirectory)
                .start();

        InputStream processOutput = process.getInputStream();
        try (DualOutputStream dualOutputStream = new DualOutputStream(
                System.out,
                new FileOutputStream(logFile))) {
            while (process.isAlive()) {
                int character = processOutput.read();
                if (character != -1) {
                    dualOutputStream.write(character);
                }
            }
        }

        return process.waitFor();
    }

    /**
     * Helper class to write out to both System.out and a file.
     */
    private static class DualOutputStream extends OutputStream {

        /**
         * Stores the reference to System.out.
         */
        private final OutputStream systemOut;

        /**
         * Stores the output stream to a log file.
         */
        private final OutputStream fileOut;

        /**
         * Constructor.
         *
         * @param systemOut the reference to System.out.
         * @param fileOut the output stream to a log file.
         */
        public DualOutputStream(OutputStream systemOut, OutputStream fileOut) {
            this.systemOut = systemOut;
            this.fileOut = fileOut;
        }

        @Override
        public void close() throws IOException {
            fileOut.close();
        }

        @Override
        public void flush() throws IOException {
            fileOut.flush();
        }

        @Override
        public void write(int b) throws IOException {
            systemOut.write(b);
            fileOut.write(b);
        }
    }
}
