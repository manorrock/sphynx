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
package com.manorrock.sphynx.hue.lcb001;

import com.manorrock.sphynx.hue.bridge.DefaultHueBridge;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * The JUnit test for the HueLCB001Light class.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class HueLCB001LightTest {
    
    /**
     * Test setBrightness method.
     */
    @Test
    public void testSetBrightness() {
        if (!System.getProperty("sphynx.hue.lcb001.id").equals("")) {
            DefaultHueBridge bridge = new DefaultHueBridge();
            bridge.setBaseUrl(System.getProperty("sphynx.hue.bridge.baseUrl"));
            bridge.setUsername(System.getProperty("sphynx.hue.bridge.username"));
            HueLCB001Light light = new HueLCB001Light();
            light.setId(Integer.valueOf(System.getProperty("sphynx.hue.lcb001.id")));
            light.setBridge(bridge);
            light.setOn(true);
            light.setBrightness(50);
            int brightness = light.getBrightness();
            light.setOn(false);
            assertEquals(50, brightness);
        }
    }
    
    /**
     * Test setXY method.
     */
    @Test
    public void testSetXY() {
        if (!System.getProperty("sphynx.hue.lcb001.id").equals("")) {
            DefaultHueBridge bridge = new DefaultHueBridge();
            bridge.setBaseUrl(System.getProperty("sphynx.hue.bridge.baseUrl"));
            bridge.setUsername(System.getProperty("sphynx.hue.bridge.username"));
            HueLCB001Light light = new HueLCB001Light();
            light.setId(Integer.valueOf(System.getProperty("sphynx.hue.lcb001.id")));
            light.setBridge(bridge);
            light.setOn(true);
            light.setXY(new float[] { 0.45f, 0.40f });
            float[] xy = light.getXy();
            light.setOn(false);
            assertEquals(0.45f, xy[0], 0.1);
            assertEquals(0.40f, xy[1], 0.1);
        }
    }
    
    /**
     * Test getId method.
     */
    @Test
    public void testGetId() {
        if (!System.getProperty("sphynx.hue.lcb001.id").equals("")) {
            HueLCB001Light light = new HueLCB001Light();
            light.setId(Integer.valueOf(System.getProperty("sphynx.hue.lcb001.id")));
            assertEquals((int) Integer.valueOf(System.getProperty("sphynx.hue.lcb001.id")), light.getId());
        }
    }
    
    /**
     * Test getBridge method.
     */
    @Test
    public void testGetBridge() {
        if (!System.getProperty("sphynx.hue.lcb001.id").equals("")) {
            DefaultHueBridge bridge = new DefaultHueBridge();
            HueLCB001Light light = new HueLCB001Light();
            light.setBridge(bridge);
            assertEquals(bridge, light.getBridge());
        }
    }
    
    /**
     * Test setOn method.
     */
    @Test
    public void testSetOn() {
        if (!System.getProperty("sphynx.hue.lcb001.id").equals("")) {
            DefaultHueBridge bridge = new DefaultHueBridge();
            bridge.setBaseUrl(System.getProperty("sphynx.hue.bridge.baseUrl"));
            bridge.setUsername(System.getProperty("sphynx.hue.bridge.username"));
            HueLCB001Light light = new HueLCB001Light();
            light.setBridge(bridge);
            light.setId(Integer.valueOf(System.getProperty("sphynx.hue.lcb001.id")));
            light.setOn(true);
        }
    }
    
    /**
     * Test setOn method.
     */
    @Test
    public void testSetOn2() {
        if (!System.getProperty("sphynx.hue.lcb001.id").equals("")) {
            DefaultHueBridge bridge = new DefaultHueBridge();
            bridge.setBaseUrl(System.getProperty("sphynx.hue.bridge.baseUrl"));
            bridge.setUsername(System.getProperty("sphynx.hue.bridge.username"));
            HueLCB001Light light = new HueLCB001Light();
            light.setBridge(bridge);
            light.setId(Integer.valueOf(System.getProperty("sphynx.hue.lcb001.id")));
            light.setOn(false);
        }
    }
}
