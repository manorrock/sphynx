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

/**
 * A Hue color light.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 * @param <T> the type of color light.
 */
public class HueColorLight<T> extends HueLight<T> {

    /**
     * Get the color temperature.
     *
     * @return the color temperature.
     */
    public int getColorTemperature() {
        JsonLight light = bridge.getLightAsObject(id);
        return light.getState().getColorTemperature();
    }

    /**
     * Get the hue.
     *
     * @return the hue.
     */
    public int getHue() {
        JsonLight light = bridge.getLightAsObject(id);
        return light.getState().getHue();
    }

    /**
     * Get the saturation.
     *
     * @return saturation.
     */
    public int getSaturation() {
        JsonLight light = bridge.getLightAsObject(id);
        return light.getState().getSaturation();
    }
    
    /**
     * Get the XY.
     * 
     * @return the XY.
     */
    public float[] getXy() {
        JsonLight light = bridge.getLightAsObject(id);
        return light.getState().getXy();
    }

    /**
     * Set the color temperature.
     *
     * @param colorTemperature.
     */
    public void setColorTemperature(int colorTemperature) {
        JsonLightState state = new JsonLightState();
        state.setColorTemperature(colorTemperature);
        bridge.setLightState(id, state);
    }

    /**
     * Set the hue.
     *
     * @param hue the hue.
     */
    public void setHue(int hue) {
        JsonLightState state = new JsonLightState();
        state.setHue(hue);
        bridge.setLightState(id, state);
    }

    /**
     * Set the saturation.
     *
     * @param saturation the saturation.
     */
    public void setSaturation(int saturation) {
        JsonLightState state = new JsonLightState();
        state.setSaturation(saturation);
        bridge.setLightState(id, state);
    }
    
    /**
     * Set the XY.
     * 
     * @param xy the XY.
     */
    public void setXy(float[] xy) {
        JsonLightState state = new JsonLightState();
        state.setXy(xy);
        bridge.setLightState(id, state);
    }
}
