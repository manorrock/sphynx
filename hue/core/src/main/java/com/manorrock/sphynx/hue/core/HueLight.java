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
package com.manorrock.sphynx.hue.core;

import java.io.IOException;
import java.lang.System.Logger;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import static java.net.http.HttpClient.Redirect.ALWAYS;
import static java.net.http.HttpClient.Version.HTTP_1_1;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * A Hue light.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class HueLight {

    /**
     * Stores the logger.
     */
    private static final Logger LOGGER = System.getLogger(HueLight.class.getName());

    /**
     * Stores the bridge URL.
     */
    protected String bridgeUrl;

    /**
     * Stores the id.
     */
    protected int id;

    /**
     * Get the bridge URL.
     *
     * @return the bridge URL.
     */
    public String getBridgeUrl() {
        return bridgeUrl;
    }

    /**
     * Get the id.
     *
     * @return the id.
     */
    public int getId() {
        return id;
    }

    /**
     * Set the bridge URL.
     *
     * @param bridgeUrl the bridge URL.
     */
    public void setBridgeUrl(String bridgeUrl) {
        this.bridgeUrl = bridgeUrl;
    }

    /**
     * Set the id.
     *
     * @param id the id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Turn the light on/off.
     *
     * @param on the on/off flag.
     */
    public void setOn(boolean on) {
        try {
            HttpClient client = HttpClient
                    .newBuilder()
                    .version(HTTP_1_1)
                    .connectTimeout(Duration.ofSeconds(20))
                    .followRedirects(ALWAYS)
                    .build();
            
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .PUT(HttpRequest.BodyPublishers.ofString(Boolean.toString(on)))
                    .uri(new URI(bridgeUrl + "/lights/" + id + "/on"))
                    .header("Content-Type", "application/json")
                    .build();
            
            client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (URISyntaxException | InterruptedException | IOException e) {
            LOGGER.log(System.Logger.Level.WARNING, "Unable to change on to: " + on, e);
        }
    }
}
