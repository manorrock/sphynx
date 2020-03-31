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

import com.collectivae.device.huebridge.HueBridge;
import com.collectivae.device.huebridge.HueBridgeLight;
import com.collectivae.device.huebridge.HueBridgeLightState;

/**
 * A Hue light.
 * 
 * @author Manfred Riem (mriem@manorrock.com)
 * @param <T> the sub type.
 */
public class HueLight<T> {
    
    /**
     * Stores the base URL.
     */
    protected String baseUrl;
    
    /**
     * Stores the id.
     */
    protected String id;

    /**
     * Stores the username.
     */
    protected String username;

    /**
     * Set the base URL.
     *
     * @param baseUrl the base URL.
     * @return the light.
     */
    public T baseUrl(String baseUrl) {
        return (T) this;
    }

    /**
     * Set the brightness.
     *
     * @param brightness the brightness.
     * @return the light.
     */
    public T brightness(int brightness) {
        HueBridgeLightState state = new HueBridgeLightState();
        state.setBrightness(brightness);
        HueBridge bridge = new HueBridge(baseUrl, username);
        bridge.setLightState(id, state);
        return (T) this;
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
     * Get the brightness.
     *
     * @return the brightness.
     */
    public int getBrightness() {
        HueBridge bridge = new HueBridge(baseUrl, username);
        HueBridgeLight light = bridge.getLightAsObject(id);
        return light.getState().getBrightness();
    }

    /**
     * Get the id.
     *
     * @return the id.
     */
    public String getId() {
        return id;
    }

    /**
     * Set the id.
     *
     * @param id the id.
     * @return the light.
     */
    public T id(String id) {
        this.id = id;
        return (T) this;
    }

    /**
     * Is the light on.
     *
     * @return true if it is, false otherwise.
     */
    public boolean isOn() {
        HueBridge bridge = new HueBridge(baseUrl, username);
        HueBridgeLight light = bridge.getLightAsObject(id);
        return light.getState().isOn();
    }

    /**
     * Turn off.
     *
     * @return the light.
     */
    public T off() {
        HueBridgeLightState state = new HueBridgeLightState();
        state.setOn(false);
        HueBridge bridge = new HueBridge(baseUrl, username);
        bridge.setLightState(id, state);
        return (T) this;
    }

    /**
     * Turn on.
     *
     * @return the light.
     */
    public T on() {
        HueBridgeLightState state = new HueBridgeLightState();
        state.setOn(true);
        HueBridge bridge = new HueBridge(baseUrl, username);
        bridge.setLightState(id, state);
        return (T) this;
    }

    /**
     * Set the base url.
     *
     * @param baseUrl the base URL.
     */
    public void setBaseUrl(String baseUrl) {
        baseUrl(baseUrl);
    }
    
    /**
     * Set the brightness.
     * 
     * @param brightness the brightness.
     */
    public void setBrightness(int brightness) {
        brightness(brightness);
    }

    /**
     * Set the id.
     * 
     * @param id the id.
     */
    public void setId(String id) {
        id(id);
    }
    
    /**
     * Set the light on or off.
     * 
     * @param on true for on, false for off.
     */
    public void setOn(boolean on) {
        if (on) {
            on();
        } else {
            off();
        }
    }

    /**
     * Set the username.
     * 
     * @param username the username.
     */
    public void setUsername(String username) {
        username(username);
    }
    
    /**
     * Set the username.
     * 
     * @param username the username.
     * @return the light.
     */
    public T username(String username) {
        this.username = username;
        return (T) this;
    }
}
