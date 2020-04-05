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

import com.collectivae.device.ChildDevice;

/**
 * A Hue sensor.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 * @param <T> the sub type.
 */
public class HueSensor<T> implements ChildDevice<HueBridge> {

    /**
     * Stores the parent device (aka the bridge).
     */
    protected HueBridge bridge;

    /**
     * Stores the device id.
     */
    protected String deviceId;

    /**
     * Stores the id.
     */
    protected String id;

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
     * Get the id.
     *
     * @return the id.
     */
    public String getId() {
        return id;
    }
    
    /**
     * Get the JSON.
     * 
     * @return the JSON.
     */
    public String getJson() {
        return bridge.getSensor(id);
    }

    /**
     * Get the Hue Bridge.
     * 
     * @return the Hue bridge.
     */
    @Override
    public HueBridge getParentDevice() {
        return bridge;
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
     * Set the id.
     *
     * @param id the id.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Set the parent device.
     * 
     * @param parentDevice the parent device.
     */
    @Override
    public void setParentDevice(HueBridge parentDevice) {
        this.bridge = parentDevice;
    }
}
