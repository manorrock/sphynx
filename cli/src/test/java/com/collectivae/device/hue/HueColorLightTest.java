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
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The JUnit tests for the HueWhiteLight class.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class HueColorLightTest {

    /**
     * Stores the light.
     */
    private HueColorLight light;

    /**
     * Setup before testing.
     */
    @BeforeEach
    public void beforeEach() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("HueBridge.properties"));
            String id = properties.getProperty("colorLightId");
            HueBridge bridge = new HueBridge();
            bridge.setBaseUrl(properties.getProperty("baseUrl"));
            bridge.setUsername(properties.getProperty("username"));
            light = new HueColorLight();
            light.setParentDevice(bridge);
            light.setId(id);
        } catch (IOException ioe) {
        }
    }

    /**
     * Test on method.
     */
    @Test
    public void testOn() {
        boolean on = light.isOn();
        light.setOn(true);
        assertTrue(light.isOn());
        if (!on) {
            light.setOn(false);
        }
    }

    /**
     * Test off method.
     */
    @Test
    public void testOff() {
        boolean on = light.isOn();
        light.setOn(false);
        assertFalse(light.isOn());
        if (on) {
            light.setOn(true);
        }
    }

    /**
     * Test brightness method.
     */
    @Test
    public void testXY() {
        boolean on = light.isOn();
        light.setOn(true);
        float[] xy = light.getXy();
        light.setXy(new float[]{0.7f, 0.5f});
        assertEquals(0.7f, light.getXy()[0]);
        light.setXy(xy);
        if (!on) {
            light.setOn(false);
        }
    }
}
