/*
 * Copyright (c) 2002-2020 Manorrock.com. All Rights Reserved.
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
package com.collectivae.device.hue;

import com.collectivae.device.Device;
import java.io.IOException;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ContentType;

/**
 * A Philips Hue Bridge.
 *
 * @author Manfred Riem (mriem@manorock.com)
 */
public class HueBridge implements Device {

    /**
     * Stores the base URL.
     */
    private String baseUrl;

    /**
     * Storesd the device id.
     */
    private String deviceId;

    /**
     * Stores the username.
     */
    private String username;

    /**
     * Constructor.
     */
    public HueBridge() {
    }

    /**
     * Constructor.
     *
     * @param baseUrl the base URL.
     */
    public HueBridge(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * Constructor.
     *
     * @param baseUrl the base URL.
     * @param username the username.
     */
    public HueBridge(String baseUrl, String username) {
        this.baseUrl = baseUrl;
        this.username = username;
    }

    /**
     * Get the base configuration.
     *
     * <p>
     * This method does not require you to be logged in as a user.
     * </p>
     *
     * @return the base configuration.
     */
    public String getBaseConfig() {
        String result = null;
        try {
            result = Request.get(baseUrl + "/config")
                    .execute().returnContent().asString();
        } catch (IOException ioe) {
        }
        return result;
    }

    /**
     * Get the device id.
     *
     * @return the device id.
     */
    @Override
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * Get the full configuration.
     *
     * <p>
     * This method requires you to be logged in as a user.
     * </p>
     *
     * @return the full configuration.
     */
    public String getFullConfig() {
        String result = null;
        try {
            result = Request.get(baseUrl + "/" + username)
                    .execute().returnContent().asString();
        } catch (IOException ioe) {
        }
        return result;
    }

    /**
     * Get the light.
     *
     * @param id the ID of the light.
     * @return the JSON.
     */
    public String getLight(String id) {
        String result = null;
        try {
            result = Request.get(baseUrl + "/" + username + "/lights/"
                    + id).execute().returnContent().asString();
        } catch (IOException ioe) {
        }
        return result;
    }

    /**
     * Get the light (as an object).
     *
     * @param id the ID of the light.
     * @return the light.
     */
    public JsonLight getLightAsObject(String id) {
        JsonLight result;
        Jsonb jsonb = JsonbBuilder.create();
        String body = getLight(id);
        result = jsonb.fromJson(body, JsonLight.class);
        return result;
    }

    /**
     * Get the sensor.
     *
     * @param id the id.
     * @return the JSON.
     */
    public String getSensor(String id) {
        String result = null;
        try {
            result = Request.get(baseUrl + "/" + username + "/sensors/"
                    + id).execute().returnContent().asString();
        } catch (IOException ioe) {
        }
        return result;
    }

    /**
     * Get the sensor (as an object).
     *
     * @param id the ID of the light.
     * @return the sensor.
     */
    public JsonSensor getSensorAsObject(String id) {
        Jsonb jsonb = JsonbBuilder.create();
        String body = getSensor(id);
        return jsonb.fromJson(body, JsonSensor.class);
    }

    /**
     * Establish link with the bridge.
     *
     * @param linkRequest the link request.
     * @return the response.
     */
    public String link(JsonLinkRequest linkRequest) {
        String result = "";
        try {
            Jsonb jsonb = JsonbBuilder.create();
            String body = Request.post(baseUrl).bodyString(
                    jsonb.toJson(linkRequest), ContentType.APPLICATION_JSON)
                    .execute().returnContent().asString();
            result = body;
        } catch (IOException ioe) {
        }
        return result;
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
     * Set the device id.
     *
     * @param deviceId the device id.
     */
    @Override
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * Set the light state.
     *
     * @param id the id of the light.
     * @param state the light state.
     * @return the response.
     */
    public String setLightState(String id, JsonLightState state) {
        String result = "";
        try {
            Jsonb jsonb = JsonbBuilder.create();
            String body = Request.put(baseUrl + "/" + username + "/lights/"
                    + id + "/state").bodyString(jsonb.toJson(state),
                            ContentType.APPLICATION_JSON)
                    .execute().returnContent().asString();
            result = body;
        } catch (IOException ioe) {
        }
        return result;
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
