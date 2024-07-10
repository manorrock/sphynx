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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import static java.lang.System.Logger.Level.ERROR;
import java.util.concurrent.Callable;
import picocli.CommandLine;

/**
 * The log delete command.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@CommandLine.Command(name = "delete", mixinStandardHelpOptions = true)
public class LogDeleteCommand implements Callable<Integer> {

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
    
    /**
     * Stores the log name.
     */
    @CommandLine.Option(names = "--log", description = "The name of the log", required = true)
    protected String log;

    @Override
    public Integer call() throws Exception {
        File logFile = new File(baseDirectory,
                "jobs"
                + File.separator
                + name
                + File.separator
                + "logs"
                + File.separator
                + log
                + ".log");

        if (!logFile.exists()) {
            LOGGER.log(ERROR, "Log file does not exist");
            return 1;
        }

        if (!logFile.delete()) {
            LOGGER.log(ERROR, "Log file cannot be deleted");
            return 2;
        }
        
        return 0;
    }
}
