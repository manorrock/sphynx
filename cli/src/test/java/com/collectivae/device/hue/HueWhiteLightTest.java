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
public class HueWhiteLightTest {

    /**
     * Stores the base URL.
     */
    private String baseUrl;

    /**
     * Stores the ID of the white light.
     */
    private String id;

    /**
     * Stores the username.
     */
    private String username;

    /**
     * Setup before testing.
     */
    @BeforeEach
    public void beforeEach() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("HueBridge.properties"));
            baseUrl = properties.getProperty("baseUrl");
            username = properties.getProperty("username");
            id = properties.getProperty("whiteLightId");
        } catch (IOException ioe) {
        }
    }

    /**
     * Test on method.
     */
    @Test
    public void testOn() {
        HueWhiteLight light = new HueWhiteLight().
                baseUrl(baseUrl).username(username).id(id);
        boolean on = light.isOn();
        light.on();
        assertTrue(light.isOn());
        if (!on) {
            light.off();
        }
    }

    /**
     * Test off method.
     */
    @Test
    public void testOff() {
        HueWhiteLight light = new HueWhiteLight().
                baseUrl(baseUrl).username(username).id(id);
        boolean on = light.isOn();
        light.off();
        assertFalse(light.isOn());
        if (on) {
            light.on();
        }
    }

    /**
     * Test brightness method.
     */
    @Test
    public void testBrightness() {
        HueWhiteLight light = new HueWhiteLight().
                baseUrl(baseUrl).username(username).id(id);
        boolean on = light.isOn();
        light.on();
        light.brightness(100);
        assertEquals(100, light.getBrightness());
        light.brightness(254);
        assertEquals(254, light.getBrightness());
        if (!on) {
            light.off();
        }
    }
}
