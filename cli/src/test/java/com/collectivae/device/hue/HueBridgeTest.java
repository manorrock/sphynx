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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The JUnit tests for the HueBridge class.
 * 
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class HueBridgeTest {
    
    /**
     * Stores the Hue bridge.
     */
    private HueBridge bridge;
    
    /**
     * Setup before testing.
     */
    @BeforeEach
    public void beforeEach() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("HueBridge.properties"));
            bridge = new HueBridge();
            bridge.setBaseUrl(properties.getProperty("baseUrl"));
            bridge.setUsername(properties.getProperty("username"));
        } catch (IOException ioe) {
        }
    }

    /**
     * Test getBaseConfig method.
     */
    @Test
    public void testGetBaseConfig() {
        String config = bridge.getBaseConfig();
        assertNotNull(config);
    }

    /**
     * Test getFullConfig method.
     */
    @Test
    public void testGetFullConfig() {
        String config = bridge.getFullConfig();
        assertNotNull(config);
    }

    /**
     * Test setLightState method - turn the light on.
     */
    @Test
    public void testSetLightStateOn() {
        JsonLightState state = new JsonLightState();
        state.setOn(true);
        bridge.setLightState("1", state);
        JsonLight light = bridge.getLightAsObject("1");
        assertTrue(light.getState().isOn());
    }

    /**
     * Test setLightState method - turn the light off.
     */
    @Test
    public void testSetLightStateOff() {
        JsonLightState state = new JsonLightState();
        state.setOn(false);
        bridge.setLightState("1", state);
        JsonLight light = bridge.getLightAsObject("1");
        assertFalse(light.getState().isOn());
    }
}
