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
package com.manorrock.sphynx.rest;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.Response.Status.CONFLICT;
import static jakarta.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import java.io.File;
import java.io.IOException;
import static java.lang.System.Logger.Level.DEBUG;
import static java.lang.System.Logger.Level.ERROR;
import static java.lang.System.Logger.Level.INFO;
import static java.lang.System.Logger.Level.TRACE;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * The Job resource class.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Path("job")
@RequestScoped
public class JobResource {

    /**
     * Stores the logger.
     */
    private static final System.Logger LOGGER = System.getLogger(JobResource.class.getName());

    /**
     * Stores the base directory.
     */
    protected File baseDirectory = new File(System.getProperty("user.home") + "/.manorrock/sphynx");

    /**
     * Create a job.
     *
     * @param job the job.
     * @return the job.
     */
    @PUT
    @Consumes(APPLICATION_JSON)
    public Job create(Job job) {

        /*
         * Step 1 - determine name.
         */
        LOGGER.log(DEBUG, "Determining name of the job");
        if (job.getName() == null || job.getName().trim().equals("")) {
            LOGGER.log(TRACE, "No name specified");
            LOGGER.log(TRACE, "Generating name");
            job.setName(UUID.randomUUID().toString());
        }
        LOGGER.log(INFO, "Using name: " + job.getName());

        /*
         * Step 2 - determine script.
         */
        LOGGER.log(DEBUG, "Determining script content of the job");
        if (job.getScript() == null) {
            LOGGER.log(TRACE, "No script specified");
            LOGGER.log(TRACE, "Generating empty script");
            job.setScript("");
        }
        LOGGER.log(INFO, "Using script: " + job.getScript());

        /*
         * Step 3 - create directory structure.
         */
        LOGGER.log(DEBUG, "Creating directory structure");
        if (!baseDirectory.exists()) {
            if (!baseDirectory.mkdirs()) {
                LOGGER.log(ERROR, "Unable to create base directory");
                throw new WebApplicationException(INTERNAL_SERVER_ERROR);
            }
        }
        File jobDirectory = new File(baseDirectory, "jobs" + File.separator + job.getName());
        if (jobDirectory.exists()) {
            LOGGER.log(ERROR, "Unable to create job directory as it already exists");
            throw new WebApplicationException(CONFLICT);
        } else {
            if (!jobDirectory.mkdirs()) {
                LOGGER.log(ERROR, "Unable to create job directory");
                throw new WebApplicationException(INTERNAL_SERVER_ERROR);
            }
        }
        File jobWorkDirectory = new File(jobDirectory, "work");
        if (jobWorkDirectory.exists()) {
            LOGGER.log(ERROR, "Unable to create job work directory as it already exists");
            throw new WebApplicationException(INTERNAL_SERVER_ERROR);
        } else {
            if (!jobWorkDirectory.mkdirs()) {
                LOGGER.log(ERROR, "Unable to create job work directory");
                throw new WebApplicationException(INTERNAL_SERVER_ERROR);
            }
        }
        File executionsDirectory = new File(jobDirectory, "executions");
        if (executionsDirectory.exists()) {
            LOGGER.log(ERROR, "Unable to create job executions directory as it already exists");
            throw new WebApplicationException(INTERNAL_SERVER_ERROR);
        } else {
            if (!executionsDirectory.mkdirs()) {
                LOGGER.log(ERROR, "Unable to create job executions directory");
                throw new WebApplicationException(INTERNAL_SERVER_ERROR);
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
                throw new WebApplicationException(INTERNAL_SERVER_ERROR);
            }
        }
        try {
            Files.write(scriptFilename.toPath(), job.getScript().getBytes());
        } catch (IOException ioe) {
            LOGGER.log(ERROR, "Unable to write script to file");
            throw new WebApplicationException(INTERNAL_SERVER_ERROR);
        }
        LOGGER.log(INFO, "Script written to: " + scriptFilename);
        LOGGER.log(INFO, "Successfully created job: " + job.getName());

        return job;
    }

    /**
     * List jobs.
     *
     * @return the list of jobs.
     */
    @GET
    @Produces(APPLICATION_JSON)
    public List<String> list() {
        File jobsDirectory = new File(baseDirectory, "jobs");
        if (jobsDirectory.exists()) {
            ArrayList<String> result = new ArrayList<>();
            String[] names = jobsDirectory.list();
            if (names.length > 0) {
                result.addAll(Arrays.asList(names));
            }
            return result;
        } else {
            throw new WebApplicationException(INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Execute a job.
     *
     * @param name the name of the job to execute.
     * @return the log name.
     */
    @Path("{name}/execute")
    @POST
    public String execute(@PathParam("name") String name) {
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
                throw new WebApplicationException(INTERNAL_SERVER_ERROR);
            }
        }

        try {
            /*
            * Step 7 - Create process.
             */
            new ProcessBuilder()
                    .command(commands)
                    .directory(workDirectory)
                    .start();
        } catch (IOException ex) {
            LOGGER.log(ERROR, "I/O error occurred: ", ex);
            throw new WebApplicationException(INTERNAL_SERVER_ERROR);
        }

        return logFile.getName().substring(0, logFile.getName().indexOf(".log"));
    }
}
