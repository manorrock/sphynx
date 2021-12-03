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

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.System.Logger;
import static java.lang.System.Logger.Level.WARNING;

/**
 * The Hue Bridge.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class HueBridgeServlet extends HttpServlet {

    /**
     * Stores the logger.
     */
    private static final Logger LOGGER = System.getLogger(HueBridgeServlet.class.getName());

    /**
     * Stores the Hue Bridge.
     */
    private final HueBridge bridge = new HueBridge();

    @Override
    public void init(ServletConfig config) throws ServletException {
        bridge.setBaseUrl(config.getInitParameter("baseUrl"));
        if (config.getInitParameter("baseUrl") == null) {
            LOGGER.log(WARNING, "No baseUrl was set so nothing will work");
        }
        bridge.setUsername(config.getInitParameter("username"));
        if (config.getInitParameter("username") == null) {
            LOGGER.log(WARNING, "No username was set so only basic operations will work");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getServletPath().startsWith("/config")) {
            response.setContentType("text/json");
            try ( PrintWriter writer = response.getWriter()) {
                writer.println(bridge.config());
                writer.flush();
            }
        } else {
            response.setContentType("text/plain");
            try ( PrintWriter writer = response.getWriter()) {
                writer.println("Hue Bridge container");
                writer.flush();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getServletPath().startsWith("/authorize")) {
            response.setContentType("text/json");
            try ( PrintWriter writer = response.getWriter()) {
                writer.println(bridge.authorize(request.getParameter("username")));
                writer.flush();
            }
        }
    }
}
