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
package com.manorrock.sphynx.hue.lightstripplus;

import com.manorrock.sphynx.hue.core.HueLight;
import java.io.IOException;
import static java.lang.System.Logger.Level.WARNING;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import static java.net.http.HttpClient.Redirect.ALWAYS;
import static java.net.http.HttpClient.Version.HTTP_1_1;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * A Hue LightStrip Plus (model LST002).
 *
 * @author Manfred Riem (mriem@manorrock.com)
 * @see https://zigbee.blakadder.com/Philips_LST002.html
 */
public class HueLightstripPlus extends HueLight {
    
    /**
     * Stores the logger.
     */
    private static final System.Logger LOGGER = System.getLogger(HueLightstripPlus.class.getName());


    /**
     * Set the brightness.
     *
     * @param brightness the brightness.
     */
    public void setBrightness(int brightness) {
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
                            "{\"bri\":" + brightness + "}"))
                    .uri(new URI(bridgeUrl + "/lights/" + id + "/state"))
                    .header("Content-Type", "application/json")
                    .build();

            client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (URISyntaxException | InterruptedException | IOException e) {
            LOGGER.log(WARNING, "Unable to change brightness to: " + brightness, e);
        }        
    }
    
    /**
     * Set the XY.
     * 
     * @param xy the XY.
     */
    public void setXY(float[] xy) {
//        bridge.changeLightState(id, "xy", xy);
    }

    /**
     * Get the brightness.
     *
     * @return the brightness.
     */
    public int getBrightness() {
        int result = -1;
        try {
            HttpClient client = HttpClient
                    .newBuilder()
                    .version(HTTP_1_1)
                    .connectTimeout(Duration.ofSeconds(20))
                    .followRedirects(ALWAYS)
                    .build();

            HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(new URI(bridgeUrl + "/lights/" + id + "/brightness"))
                    .header("Content-Type", "application/json")
                    .build();

            result = Integer.valueOf(client.send(request, 
                    HttpResponse.BodyHandlers.ofString()).body().trim());
        } catch (URISyntaxException | InterruptedException | IOException e) {
            LOGGER.log(WARNING, "Unable to get brightness", e);
        }
        return result;
    }

    
    /**
     * Get the XY.
     * 
     * @return the XY.
     */
    public float[] getXy() {
        return null;
//        return bridge.getLightStateAsFloatArray(id, "xy");
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
                    .PUT(BodyPublishers.ofString(Boolean.toString(on)))
                    .uri(new URI(bridgeUrl + "/lights/" + id + "/on"))
                    .header("Content-Type", "application/json")
                    .build();

            client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (URISyntaxException | InterruptedException | IOException e) {
            LOGGER.log(WARNING, "Unable to change on to: " + on, e);
        }
    }

}
