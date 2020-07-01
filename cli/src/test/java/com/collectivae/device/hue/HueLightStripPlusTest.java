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

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The JUnit tests for the HueLightStripPlus class.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class HueLightStripPlusTest {

    /**
     * Stores the light strip.
     */
    private HueLightStripPlus lightStrip;

    /**
     * Setup before testing.
     */
    @BeforeEach
    public void beforeEach() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("HueBridge.properties"));
            String id = properties.getProperty("lightStripPlusId");
            HueBridge bridge = new HueBridge();
            bridge.setBaseUrl(properties.getProperty("baseUrl"));
            bridge.setUsername(properties.getProperty("username"));
            lightStrip = new HueLightStripPlus();
            lightStrip.setParentDevice(bridge);
            lightStrip.setId(id);
        } catch (IOException ioe) {
        }
    }
    
    /**
     * Test getJson method.
     */
    @Test
    public void testGetJson() {
        assertNotNull(lightStrip.getJson());
    }

    /**
     * Test setBrightness method.
     */
    @Test
    public void testSetBrightness() {
        boolean on = lightStrip.isOn();
        lightStrip.setOn(true);
        int brightness = lightStrip.getBrightness();
        lightStrip.setBrightness(100);
        assertEquals(100, lightStrip.getBrightness());
        lightStrip.setBrightness(254);
        assertEquals(254, lightStrip.getBrightness());
        lightStrip.setBrightness(brightness);
        if (!on) {
            lightStrip.setOn(false);
        }
    }

    /**
     * Test setColorTemperature method.
     */
    @Test
    public void testSetColorTemperature() {
        boolean on = lightStrip.isOn();
        lightStrip.setOn(true);
        int colorTemperature = lightStrip.getColorTemperature();
        lightStrip.setColorTemperature(254);
        assertEquals(254, lightStrip.getColorTemperature());
        lightStrip.setColorTemperature(153);
        assertEquals(153, lightStrip.getColorTemperature());
        lightStrip.setColorTemperature(colorTemperature);
        if (!on) {
            lightStrip.setOn(false);
        }
    }

    /**
     * Test setHue method.
     */
    @Test
    public void testSetHue() {
        boolean on = lightStrip.isOn();
        lightStrip.setOn(true);
        int hue = lightStrip.getHue();
        lightStrip.setHue(254);
        assertEquals(254, lightStrip.getHue());
        lightStrip.setHue(2540);
        assertEquals(2540, lightStrip.getHue());
        lightStrip.setHue(hue);
        if (!on) {
            lightStrip.setOn(false);
        }
    }

    /**
     * Test setOn method.
     */
    @Test
    public void testSetOn() {
        boolean on = lightStrip.isOn();
        lightStrip.setOn(false);
        assertFalse(lightStrip.isOn());
        lightStrip.setOn(true);
        assertTrue(lightStrip.isOn());
        if (!on) {
            lightStrip.setOn(false);
        }
    }

    /**
     * Test setSaturation method.
     */
    @Test
    public void testSetSaturation() {
        boolean on = lightStrip.isOn();
        lightStrip.setOn(true);
        int saturation = lightStrip.getSaturation();
        lightStrip.setSaturation(254);
        assertEquals(254, lightStrip.getSaturation());
        lightStrip.setSaturation(100);
        assertEquals(100, lightStrip.getSaturation());
        lightStrip.setSaturation(saturation);
        if (!on) {
            lightStrip.setOn(false);
        }
    }
}
