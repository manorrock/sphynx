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
 * The executor that establishes a link with the Hue Bridge.
 *
 * <pre>
 * 
 * co device huebridge link --base-url BASE_URL --device-type MY_DEVICE
 * </pre>
 * <p>
 * where BASE_URL is the URL of the Hue Bridge API endpoint and MY_DEVICE is the
 * identifier to use for your registration.
 * </p>
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
class LinkExecutor extends AbstractBaseUrlExecutor {

    /**
     * Stores the device type.
     */
    private String deviceType;

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
        HueBridgeLinkRequest linkRequest = new HueBridgeLinkRequest();
        linkRequest.setDeviceType(deviceType);
        HueBridge bridge = new HueBridge(baseUrl);
        return jsonb.toJson(bridge.link(linkRequest));
    }

    /**
     * Parse the arguments.
     *
     * @param arguments the arguments.
     */
    @Override
    protected void parseArguments(List<String> arguments) {
        for (int i = 0; i < arguments.size(); i++) {
            if (arguments.get(i).equals("--base-url")) {
                baseUrl = arguments.get(i + 1);
            }
            if (arguments.get(i).equals("--device-type")) {
                deviceType = arguments.get(i + 1);
            }
        }
    }
}
