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

import cloud.piranha.http.impl.DefaultHttpServer;
import cloud.piranha.nano.NanoPiranha;
import cloud.piranha.nano.NanoPiranhaBuilder;

/**
 * The Hue Bridge bootstrap.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class HueBridgeBootstrap {

    /**
     * Main method.
     * 
     * @param arguments the command-line arguments.
     * @throws Exception when an error occurs.
     */
    public static void main(String[] arguments) throws Exception {
        String baseUrl = null;
        String username = null;
        for(int i=0; i<arguments.length; i++) {
            if (arguments[i].equals("--baseUrl")) {
                baseUrl = arguments[i + 1];
            }
            if (arguments[i].equals("--username")) {
                baseUrl = arguments[i + 1];
            }
        }
        
        HueBridgeServlet servlet = new HueBridgeServlet();
        NanoPiranha piranha = new NanoPiranhaBuilder()
                .servlet("Hue Bridge", servlet)
                .servletInitParam("Hue Bridge", "baseUrl", baseUrl)
                .servletInitParam("Hue Bridge", "username", username)
                .build();
        
        DefaultHttpServer server = new DefaultHttpServer(8080, 
                new HueBridgeHttpProcessor(piranha), false);
        
        server.start();
    }
}
