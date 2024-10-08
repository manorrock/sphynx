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
package com.manorrock.sphynx.shared;

import java.io.File;
import java.io.IOException;
import static java.lang.System.Logger.Level.WARNING;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

/**
 * A utility class dealing with jobs.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class JobUtils {

    /**
     * Stores the logger.
     */
    private static final System.Logger LOGGER = System.getLogger(JobUtils.class.getName());

    /**
     * Delete a job.
     *
     * @param baseDirectory the base directory.
     * @param name the name of the job.
     * @return 0 if deleted, 1 if the job directory does not exist, 2 when an
     * I/O error occured.
     */
    public static int deleteJob(File baseDirectory, String name) {
        File jobDirectory = new File(baseDirectory, "jobs" + File.separator + name);
        if (!jobDirectory.exists()) {
            return 1;
        } else {
            try {
                Path jobPath = jobDirectory.toPath();
                Files.walk(jobPath)
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            } catch (IOException ex) {
                LOGGER.log(WARNING, "I/O error occurred while deleting job: " + name);
                return 2;
            }
        }
        return 0;
    }
}
