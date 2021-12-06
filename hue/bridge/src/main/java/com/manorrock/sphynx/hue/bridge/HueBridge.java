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
import java.lang.System.Logger;
import static java.lang.System.Logger.Level.WARNING;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import static java.net.http.HttpClient.Redirect.ALWAYS;
import static java.net.http.HttpClient.Version.HTTP_1_1;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

/**
 * The implementation of the Philips Hue bridge.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class HueBridge {

    /**
     * Stores the logger.
     */
    private static final Logger LOGGER = System.getLogger(HueBridge.class.getName());

    /**
     * Stores the base URL.
     */
    private String baseUrl;

    /**
     * Stores the username.
     */
    private String username;

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

    public void changeLightState(int id, String name, boolean value) {
        try {
            HttpClient client = HttpClient
                    .newBuilder()
                    .version(HTTP_1_1)
                    .connectTimeout(Duration.ofSeconds(20))
                    .followRedirects(ALWAYS)
                    .build();

            HttpRequest request = HttpRequest
                    .newBuilder()
                    .PUT(BodyPublishers.ofString(
                            "{\"" + name + "\":" + value + "}"))
                    .uri(new URI(baseUrl + "/" + username + "/lights/" + id + "/state"))
                    .header("Content-Type", "application/json")
                    .build();

            client.send(request, BodyHandlers.discarding());
        } catch (URISyntaxException | InterruptedException | IOException e) {
            LOGGER.log(WARNING, "Unable to change light state for light #" + id, e);
        }
    }

    public void changeLightState(int id, String name, float[] value) {
        /*
        try {
            HttpClient client = HttpClient
                    .newBuilder()
                    .version(HTTP_1_1)
                    .connectTimeout(Duration.ofSeconds(20))
                    .followRedirects(ALWAYS)
                    .build();

            StringBuilder jsonValue = new StringBuilder();
            jsonValue.append("[");
            if (value != null) {
                for(int i=0; i<value.length; i++) {
                    jsonValue.append(value[i]);
                    if (i + 1 != value.length) {
                        jsonValue.append(",");
                    }
                }
            }
            jsonValue.append("]");
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .PUT(BodyPublishers.ofString(
                            "{\"" + name + "\":" + jsonValue.toString() + "}"))
                    .uri(new URI(baseUrl + "/" + username + "/lights/" + id + "/state"))
                    .header("Content-Type", "application/json")
                    .build();

            client.send(request, BodyHandlers.discarding());
        } catch (URISyntaxException | InterruptedException | IOException e) {
            LOGGER.log(WARNING, "Unable to change light state for light #" + id, e);
        }
        */
    }

    public void changeLightState(int id, String name, int value) {
        /*
        try {
            HttpClient client = HttpClient
                    .newBuilder()
                    .version(HTTP_1_1)
                    .connectTimeout(Duration.ofSeconds(20))
                    .followRedirects(ALWAYS)
                    .build();

            HttpRequest request = HttpRequest
                    .newBuilder()
                    .PUT(BodyPublishers.ofString(
                            "{\"" + name + "\":" + value + "}"))
                    .uri(new URI(baseUrl + "/" + username + "/lights/" + id + "/state"))
                    .header("Content-Type", "application/json")
                    .build();

            client.send(request, BodyHandlers.discarding());
        } catch (URISyntaxException | InterruptedException | IOException e) {
            LOGGER.log(WARNING, "Unable to change light state for light #" + id, e);
        }
        */
    }

    /**
     * Get the base configuration.
     *
     * @return the base configuration.
     */
    public String config() {
        String result = null;
        try {
            HttpClient client = HttpClient
                    .newBuilder()
                    .version(HTTP_1_1)
                    .connectTimeout(Duration.ofSeconds(20))
                    .followRedirects(ALWAYS)
                    .build();

            URI uri = new URI(baseUrl + "/config");
            if (username != null) {
                uri = new URI(baseUrl + "/" + username + "/config");
            }
            
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .GET()
                    .uri(uri)
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
     * Get all the information about a specific light.
     *
     * @param id the id of the light.
     * @return the information, or null if not found.
     */
    public String getLightInfo(int id) {
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
                    .uri(new URI(baseUrl + "/" + username + "/lights/" + id))
                    .build();

            result = client.send(request, BodyHandlers.ofString()).body();
        } catch (URISyntaxException | InterruptedException | IOException e) {
            LOGGER.log(WARNING, "Unable to get information for light #" + id, e);
        }

        return result;
    }

    public String getLightState(int id, String name) {
        String result = null;
        /*
        String info = getLightInfo(id);
        if (info != null) {
            Jsonb jsonb = JsonbBuilder.create();
            HueLightInfo json = jsonb.fromJson(info, HueLightInfo.class);
            switch(name) {
                case "bri" : result = json.getState().getBrightness().toString(); break;
            }
        }
        */
        return result;
    }

    public float[] getLightStateAsFloatArray(int id, String name) {
        float[] result = null;
        /*
        String info = getLightInfo(id);
        if (info != null) {
            Jsonb jsonb = JsonbBuilder.create();
            HueLightInfo json = jsonb.fromJson(info, HueLightInfo.class);
            switch(name) {
                case "xy": result = json.getState().getXy(); break;
            }
        }
        */
        return result;
    }

    /**
     * Get the username.
     *
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the base URL.
     *
     * @param baseUrl the base URL.
     */
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * Set the username.
     *
     * @param username the username.
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
