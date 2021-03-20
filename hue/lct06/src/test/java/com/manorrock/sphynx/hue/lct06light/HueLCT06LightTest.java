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
package com.manorrock.sphynx.hue.lct06light;

import com.manorrock.sphynx.hue.bridge.HueBridge;
import org.junit.Test;

/**
 * The JUnit test for the HueLCT06Light class.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class HueLCT06LightTest {
    
    /**
     * Test on method.
     */
    @Test
    public void testOn() {
        if (!System.getProperty("sphynx.hue.lct06.id").equals("")) {
            HueBridge bridge = new HueBridge();
            bridge.setBaseUrl(System.getProperty("sphynx.hue.bridge.baseUrl"));
            bridge.setUsername(System.getProperty("sphynx.hue.bridge.username"));
            HueLCT06Light light = new HueLCT06Light();
            light.setBridge(bridge);
            light.setId(Integer.valueOf(System.getProperty("sphynx.hue.lct06.id")));
            light.on();
        }
    }
    
    /**
     * Test off method.
     */
    @Test
    public void testOff() {
        if (!System.getProperty("sphynx.hue.lct06.id").equals("")) {
            HueBridge bridge = new HueBridge();
            bridge.setBaseUrl(System.getProperty("sphynx.hue.bridge.baseUrl"));
            bridge.setUsername(System.getProperty("sphynx.hue.bridge.username"));
            HueLCT06Light light = new HueLCT06Light();
            light.setBridge(bridge);
            light.setId(Integer.valueOf(System.getProperty("sphynx.hue.lct06.id")));
            light.off();
        }
    }
}
