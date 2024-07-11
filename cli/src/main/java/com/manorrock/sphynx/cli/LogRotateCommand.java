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
import static java.lang.System.Logger.Level.ERROR;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.Callable;
import picocli.CommandLine;

/**
 * The log rotate command.
 * 
 * <p>
 *  This command will rotate the logs, by default only 10 logs will be kept.
 * </p>
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@CommandLine.Command(name = "rotate", mixinStandardHelpOptions = true)
public class LogRotateCommand implements Callable<Integer> {

    /**
     * Stores the logger.
     */
    private static final System.Logger LOGGER = System.getLogger(CreateCommand.class.getName());

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
        File logsDirectory = new File(baseDirectory,
                "jobs"
                + File.separator
                + name
                + File.separator
                + "logs");

        if (!logsDirectory.exists()) {
            LOGGER.log(ERROR, "Logs directory does not exist");
            return 1;
        }

        File[] logFiles = logsDirectory.listFiles();
        Arrays.sort(logFiles, Comparator.comparingLong(File::lastModified).reversed());
        for(int i=10; i<logFiles.length; i++) {
            System.out.println("Deleting " + logFiles[i].getAbsolutePath());
            logFiles[i].delete();
        }

        return 0;
    }
}
