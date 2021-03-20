/*
 * Copyright (c) 2002-2021 Manorrock.com. All Rights Reserved.
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
package com.manorrock.sphynx.hue.bridge;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import static java.net.http.HttpClient.Redirect.ALWAYS;
import static java.net.http.HttpClient.Version.HTTP_1_1;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import static java.util.logging.Level.WARNING;
import java.util.logging.Logger;

/**
 * The Philips Hue bridge.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class HueBridge {

    /**
     * Stores the logger.
     */
    private static final Logger LOGGER = Logger.getLogger(HueBridge.class.getPackageName());

    /**
     * Stores the base URL.
     */
    private String baseUrl;

    /**
     * Authorize the given device/user.
     *
     * @param deviceType the device type/user.
     * @return the JSON response containing the generated username (token).
     */
    public String authorize(String deviceType) {
        String result = null;
        try {
            HttpClient client = HttpClient
                    .newBuilder()
                    .version(HTTP_1_1)
                    .connectTimeout(Duration.ofSeconds(20))
                    .followRedirects(ALWAYS)
                    .build();

            HttpRequest request = HttpRequest
                    .newBuilder()
                    .POST(BodyPublishers.ofString(
                            "{\"devicetype\":\"" + deviceType + "\"}"))
                    .uri(new URI(baseUrl))
                    .header("Content-Type", "application/json")
                    .build();

            result = client.send(request, BodyHandlers.ofString()).body();
        } catch (URISyntaxException | InterruptedException | IOException e) {
            LOGGER.log(WARNING, "Unable to authorize device/user: " + deviceType, e);
        }
        return result;
    }

    /**
     * Get the base configuration.
     *
     * @return the base configuration.
     */
    public String getBaseConfig() {
        String result = null;
        try {
            HttpClient client = HttpClient
                    .newBuilder()
                    .version(HTTP_1_1)
                    .connectTimeout(Duration.ofSeconds(20))
                    .followRedirects(ALWAYS)
                    .build();

            HttpRequest request = HttpRequest
                    .newBuilder()
                    .GET()
                    .uri(new URI(baseUrl + "/config"))
                    .build();

            result = client.send(request, BodyHandlers.ofString()).body();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            LOGGER.log(WARNING, "Unable to get base configuration", e);
        }
        return result;
    }

    /**
     * Get the base URL.
     *
     * @return the base URL.
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * Set the base URL.
     *
     * @param baseUrl the base URL.
     */
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
