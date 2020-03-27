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
package com.collectivae.device.huebridge;

import java.util.List;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

/**
 * The executor that sets the state of a light.
 *
 * <pre>
 *
 * co device huebridge set-light-state --base-url BASE_URL --username USERNAME
 * --id ID [--on ON] [--brightness BRIGHTNESS]
 * </pre>
 * <p>
 * where BASE_URL is the URL of the Hue Bridge API endpoint, USERNAME is the
 * username to be used for authentication, ID is the ID of the light, ON is the
 * boolean 'on' state for the light and BRIGHTNESS is the brightness of the
 * light.
 * </p>
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
class SetLightStateExecutor extends AbstractAuthenticatedExecutor {

    /**
     * Stores the brightness.
     */
    private Integer brightness;

    /**
     * Stores the light id.
     */
    private String id;

    /**
     * Stores the on state.
     */
    private Boolean on;

    /**
     * Execute.
     *
     * @param arguments the arguments.
     * @return the result.
     */
    @Override
    public String execute(List<String> arguments) {
        parseArguments(arguments);
        JsonbConfig config = new JsonbConfig();
        config.withFormatting(true);
        Jsonb jsonb = JsonbBuilder.create(config);
        HueBridgeLightState state = new HueBridgeLightState();
        if (on != null) {
            state.setOn(on);
        }
        if (brightness != null) {
            state.setBrightness(brightness);
        }
        HueBridge bridge = new HueBridge(baseUrl);
        bridge.setUsername(username);
        return jsonb.toJson(bridge.setLightState(id, state));
    }

    /**
     * Parse the arguments.
     *
     * @param arguments the arguments.
     */
    @Override
    protected void parseArguments(List<String> arguments) {
        super.parseArguments(arguments);
        for (int i = 0; i < arguments.size(); i++) {
            if (arguments.get(i).equals("--id")) {
                id = arguments.get(i + 1);
            }
            if (arguments.get(i).equals("--on")) {
                on = Boolean.parseBoolean(arguments.get(i + 1));
            }
            if (arguments.get(i).equals("--brightness")) {
                brightness = Integer.parseInt(arguments.get(i + 1));
            }
        }
    }
}
