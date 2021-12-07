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

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.System.Logger;
import static java.lang.System.Logger.Level.INFO;
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

    /**
     * Handle GET /config.
     *
     * @param response the response.
     * @throws IOException when an I/O error occurs.
     */
    private void getConfig(HttpServletResponse response) throws IOException {
        response.setContentType("text/json");
        try ( PrintWriter writer = response.getWriter()) {
            writer.println(bridge.config());
            writer.flush();
        }
    }

    /**
     * Handle GET /.
     *
     * @param response the response.
     * @throws IOException when an I/O error occurs.
     */
    private void getDefault(HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        try ( PrintWriter writer = response.getWriter()) {
            writer.println("Philips Hue Bridge container");
            writer.flush();
        }
    }

    /**
     * Handle GET /lights/&lt;id&gt;
     *
     * @param request the request.
     * @param response the response.
     * @throws IOException when an I/O error occurs.
     */
    private void getLight(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        String id = request.getServletPath().substring(
                request.getServletPath().indexOf("/lights/") + "/lights".length() + 1);
        try ( PrintWriter writer = response.getWriter()) {
            writer.println(bridge.getLightInfo(Integer.valueOf(id)));
            writer.flush();
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        bridge.setBaseUrl(config.getInitParameter("baseUrl"));
        if (config.getInitParameter("baseUrl") == null) {
            LOGGER.log(WARNING, "No baseUrl was set so nothing will work");
        } else {
            LOGGER.log(INFO, "Using " + config.getInitParameter("baseUrl") + " to communicate with Philips Hue Bridge");
        }
        bridge.setUsername(config.getInitParameter("username"));
        if (config.getInitParameter("username") == null) {
            LOGGER.log(WARNING, "No username was set so only basic operations will work");
        } else {
            LOGGER.log(INFO, "With a configured username");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getServletPath().startsWith("/config")) {
            getConfig(response);
        } else if (request.getServletPath().startsWith("/lights/")) {
            getLight(request, response);
        } else {
            getDefault(response);
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
        } else {
            getDefault(response);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getServletPath().startsWith("/lights/")
                && request.getServletPath().endsWith("/on")) {
            putLightOn(request, response);
        } else if (request.getServletPath().startsWith("/lights/")
                && request.getServletPath().endsWith("/state")) {
            putLightState(request, response);
        } else {
            getDefault(response);
        }
    }

    /**
     * Handle PUT /lights/&lt;id&gt;/brightness
     *
     * @param request the request.
     * @param response the response.
     * @throws IOException when an I/O error occurs.
     */
    private void putLightState(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        String id = request.getServletPath().substring(
                request.getServletPath().indexOf("/lights/") + "/lights".length() + 1);
        id = id.substring(0, id.indexOf("/"));
        try ( PrintWriter writer = response.getWriter()) {
            response.setStatus(200);
            Jsonb jsonb = JsonbBuilder.create();
            HueLightStateInfo info = jsonb.fromJson(request.getInputStream(), HueLightStateInfo.class);
            bridge.changeLightState(Integer.valueOf(id), "bri", info.getBrightness());
            writer.flush();
        }
    }

    /**
     * Handle PUT /lights/&lt;id&gt;/on
     *
     * @param request the request.
     * @param response the response.
     * @throws IOException when an I/O error occurs.
     */
    private void putLightOn(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        String id = request.getServletPath().substring(
                request.getServletPath().indexOf("/lights/") + "/lights".length() + 1);
        id = id.substring(0, id.indexOf("/"));
        try ( PrintWriter writer = response.getWriter()) {
            response.setStatus(200);
            Jsonb jsonb = JsonbBuilder.create();
            boolean on = jsonb.fromJson(request.getInputStream(), boolean.class);
            bridge.changeLightState(Integer.valueOf(id), "on", on);
            writer.flush();
        }
    }
}
