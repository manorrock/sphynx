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
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import java.io.File;
import static java.lang.System.Logger.Level.ERROR;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Log resource class.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Path("job/{name}")
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
     * List the logs.
     *
     * @param jobName the job name.
     * @return the list of logs.
     */
    @GET
    @Produces(APPLICATION_JSON)
    public List<String> list(@PathParam("name") String jobName) {
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
