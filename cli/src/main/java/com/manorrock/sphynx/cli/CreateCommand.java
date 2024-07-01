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
import java.io.IOException;
import java.lang.System.Logger;
import static java.lang.System.Logger.Level.DEBUG;
import static java.lang.System.Logger.Level.ERROR;
import static java.lang.System.Logger.Level.INFO;
import static java.lang.System.Logger.Level.TRACE;
import java.nio.file.Files;
import java.util.UUID;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Option;

/**
 * The create command.
 *
 * <p>
 * This command will create the required directory structure to run a job. Note
 * that if no script is passed it will create an empty script.
 * </p>
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@CommandLine.Command(name = "create", mixinStandardHelpOptions = true)
public class CreateCommand implements Callable<Integer> {

    /**
     * Stores the logger.
     */
    private static final Logger LOGGER = System.getLogger(CreateCommand.class.getName());

    /**
     * Stores the base directory.
     */
    @Option(names = "--base-directory", description = "The base directory used for storage")
    protected File baseDirectory = new File(System.getProperty("user.home") + "/.manorrock/sphynx");
    
    /**
     * Stores the name.
     */
    @Option(names = "--name", description = "The name of the job")
    protected String name;
    
    /**
     * Stores the script.
     */
    @Option(names = "--script", description = "The script to use")
    protected String script;

    @Override
    public Integer call() throws Exception {
        /*
         * Step 1 - determine name.
        */
        LOGGER.log(DEBUG, "Determining name of the job");
        if (name == null || name.trim().equals("")) {
            LOGGER.log(TRACE, "No name specified");
            LOGGER.log(TRACE, "Generating name");
            name = UUID.randomUUID().toString();
        }
        LOGGER.log(INFO, "Using name: " + name);
        
        /*
         * Step 2 - determine script.
         */
        LOGGER.log(DEBUG, "Determining script content of the job");
        if (script == null) {
            LOGGER.log(TRACE, "No script specified");
            LOGGER.log(TRACE, "Generating empty script");
            script = "";
        }
        LOGGER.log(INFO, "Using script: " + script);
        
        /*
         * Step 3 - create directory structure.
         */
        LOGGER.log(DEBUG, "Creating directory structure");
        if (!baseDirectory.exists()) {
            if (!baseDirectory.mkdirs()) {
                LOGGER.log(ERROR, "Unable to create base directory");
                return 1;
            }
        }
        File jobDirectory = new File(baseDirectory, "jobs" + File.separator + name);
        if (jobDirectory.exists()) {
            LOGGER.log(ERROR, "Unable to create job directory as it already exists");
            return 2;
        } else {
            if (!jobDirectory.mkdirs()) {
                LOGGER.log(ERROR, "Unable to create job directory");
                return 3;
            }
        }
        File jobWorkDirectory = new File(jobDirectory, "work");
        if (jobWorkDirectory.exists()) {
            LOGGER.log(ERROR, "Unable to create job work directory as it already exists");
            return 4;
        } else {
            if (!jobWorkDirectory.mkdirs()) {
                LOGGER.log(ERROR, "Unable to create job work directory");
                return 5;
            }
        }
        File executionsDirectory = new File (jobDirectory, "executions");
        if (executionsDirectory.exists()) {
            LOGGER.log(ERROR, "Unable to create job executions directory as it already exists");
            return 6;
        } else {
            if (!executionsDirectory.mkdirs()) {
                LOGGER.log(ERROR, "Unable to create job executions directory");
                return 7;
            }
        }
        LOGGER.log(INFO, "Created directory structure at: " + jobDirectory);
        
        /*
         * Step 4 - copy script into directory structure.
        */
        File scriptFilename = new File(jobDirectory, "script" + File.separator + "run.sh");
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            scriptFilename = new File(jobDirectory, "script" + File.separator + "run.cmd");
        }
        if (!scriptFilename.getParentFile().exists()) {
            if (!scriptFilename.getParentFile().mkdirs()) {
                LOGGER.log(ERROR, "Unable to create job script directory");
                return 8;
            }
        }
        try {
            Files.write(scriptFilename.toPath(), script.getBytes());
        }
        catch (IOException ioe) {
            LOGGER.log(ERROR, "Unable to write script to file");
            return 9;
        }
        LOGGER.log(INFO, "Script written to: " + scriptFilename);
        LOGGER.log(INFO, "Successfully created job: " + name);
        return 0;
    }
}
