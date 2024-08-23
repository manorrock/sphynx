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

import jakarta.ws.rs.Path;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import static java.lang.System.Logger.Level.ERROR;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Log resource class.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Path("job/{jobName}/log")
@RequestScoped
public class LogResource {

    /**
     * Stores the logger.
     */
    private static final System.Logger LOGGER = System.getLogger(LogResource.class.getName());

    /**
     * Stores the base directory.
     */
    protected File baseDirectory = new File(System.getProperty("user.home") + "/.manorrock/sphynx");

    /**
     * Delete the specific log.
     * 
     * @param jobName the job name.
     * @param logName the log name.
     */
    @DELETE
    @Path("{logName}")
    public void delete(
            @PathParam("jobName") String jobName,
            @PathParam("logName") String logName) {
        
        File logFile = new File(baseDirectory,
                "jobs"
                + File.separator
                + jobName
                + File.separator
                + "logs"
                + File.separator
                + logName
                + ".log");

        if (!logFile.exists()) {
            LOGGER.log(ERROR, "Log file does not exist");
            throw new WebApplicationException(NOT_FOUND);
        }

        if (!logFile.delete()) {
            LOGGER.log(ERROR, "Log file cannot be deleted");
            throw new WebApplicationException(INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Get the specific log.
     *
     * @param jobName the job name.
     * @param logName the log name.
     * @return the log.
     */
    @GET
    @Path("{logName}")
    public String get(
            @PathParam("jobName") String jobName,
            @PathParam("logName") String logName) {

        StringBuilder result = new StringBuilder();
        
        File logFile = new File(baseDirectory,
                "jobs"
                + File.separator
                + jobName
                + File.separator
                + "logs"
                + File.separator
                + logName
                + ".log");
        if (!logFile.exists()) {
            LOGGER.log(ERROR, "Log file does not exist");
            throw new WebApplicationException(NOT_FOUND);
        }

        try (BufferedInputStream input = new BufferedInputStream(
                new FileInputStream(logFile))) {
            while (input.available() > 0) {
                int character = input.read();
                if (character == -1) {
                    break;
                }
                result.append((char) character);
            }
        } catch (IOException ex) {
            LOGGER.log(ERROR, "I/O error occured", ex);
            throw new WebApplicationException(INTERNAL_SERVER_ERROR);
        }
        
        return result.toString();
    }

    /**
     * List the logs.
     *
     * @param jobName the job name.
     * @return the list of logs.
     */
    @GET
    @Produces(APPLICATION_JSON)
    public List<String> list(@PathParam("jobName") String jobName) {
        File logsDirectory = new File(baseDirectory,
                "jobs"
                + File.separator
                + jobName
                + File.separator
                + "logs");

        if (logsDirectory.exists()) {
            ArrayList<String> result = new ArrayList<>();
            String[] names = logsDirectory.list();
            if (names.length > 0) {
                result.addAll(Arrays.asList(names));
            }
            return result;
        } else {
            LOGGER.log(ERROR, "Logs directory does not exist");
            throw new WebApplicationException(INTERNAL_SERVER_ERROR);
        }
    }
}
