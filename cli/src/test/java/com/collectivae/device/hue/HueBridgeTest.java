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
import com.collectivae.device.huebridge.HueBridgeBaseConfig;
import com.collectivae.device.huebridge.HueBridgeFullConfig;
import com.collectivae.device.huebridge.HueBridgeLight;
import com.collectivae.device.huebridge.HueBridgeLightState;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * The JUnit tests for the Hue Bridge.
 * 
 * <p>
 *  If you want to enable these tests you need to tell the test the base URL of
 *  the bridge you want to use to validate against. If no base URL is specified
 *  the tests will be skipped.
 * </p>
 * 
 * <p>
 *  Note if you are running these tests for the first time you will need to setup
 *  a link to the bridge before you can access it. This involves pressing the
 *  sync button prior to executing the tests so the tests can setup a username,
 *  which will be stored in the same properties file that is used to specify the
 *  base URL.
 * </p>
 * 
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class HueBridgeTest {
    
    /**
     * Stores the logger.
     */
    private static final Logger LOGGER = Logger.getLogger(HueBridgeTest.class.getName());
    
    /**
     * Stores the base URL
     */
    private static String baseUrl;
    
    /**
     * Stores the Hue bridge.
     */
    private static HueBridge bridge;
    
    /**
     * Stores the username.
     */
    private String username;
    
    /**
     * Setup before testing.
     */
    @BeforeAll
    public static void beforeAll() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("HueBridge.properties"));
            baseUrl = properties.getProperty("baseUrl");
            LOGGER.log(Level.INFO, "HueBridge base URL: {0}", baseUrl);
            bridge = new HueBridge(baseUrl);
            bridge.setUsername(properties.getProperty("username"));
        } catch (IOException ioe) {
        }
    }

    /**
     * Test getBaseConfig method.
     */
    @Test
    public void testGetBaseConfig() {
        HueBridgeBaseConfig config = bridge.getBaseConfig();
        assertNotNull(config);
    }

    /**
     * Test getFullConfig method.
     */
    @Test
    public void testGetFullConfig() {
        HueBridgeFullConfig config = bridge.getFullConfig();
        assertNotNull(config);
    }

    /**
     * Test setLightState method - turn the light on.
     */
    @Test
    public void testSetLightStateOn() {
        HueBridgeLightState state = new HueBridgeLightState();
        state.setOn(true);
        bridge.setLightState("1", state);
        HueBridgeFullConfig config = bridge.getFullConfig();
        HueBridgeLight light = config.getLights().get("1");
        assertTrue(light.getState().isOn());
    }

    /**
     * Test setLightState method - turn the light off.
     */
    @Test
    public void testSetLightStateOff() {
        HueBridgeLightState state = new HueBridgeLightState();
        state.setOn(false);
        bridge.setLightState("1", state);
        HueBridgeFullConfig config = bridge.getFullConfig();
        HueBridgeLight light = config.getLights().get("1");
        assertFalse(light.getState().isOn());
    }
}
