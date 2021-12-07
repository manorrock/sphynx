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

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;

/**
 * The state of a light (in JSON format).
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
 @JsonbPropertyOrder({"brightness", "xy"})
public class HueLightStateInfo {

    /**
     * Stores the brightness.
     */
    @JsonbProperty("bri")
    private Integer brightness;

    /**
     * Stores the XY.
     */
    @JsonbProperty("xy")
    private float[] xy;

    /**
     * Constructor.
     */
    public HueLightStateInfo() {
    }

    /**
     * Get the brightness.
     *
     * @return the brightness.
     */
    public Integer getBrightness() {
        return brightness;
    }

    /**
     * Get the XY.
     *
     * @return the XY.
     */
    public float[] getXy() {
        return xy;
    }

    /**
     * Set the brightness.
     *
     * @param brightness the brightness.
     */
    public void setBrightness(Integer brightness) {
        this.brightness = brightness;
    }

    /**
     * Set the XY.
     *
     * @param xy he XY.
     */
    public void setXy(float[] xy) {
        this.xy = xy;
    }
}
