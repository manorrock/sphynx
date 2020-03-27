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

import com.collectivae.cli.CliExecutor;
import java.util.List;

/**
 * The executor for the Hue Bridge (aka "huebridge").
 * 
 * <p>
 *  Commands for the Hue Bridge:
 * </p>
 * <ul>
 *  <li>get-base-config implemented by {@link GetBaseConfigExecutor}</li>
 *  <li>get-full-config implemented by {@link GetFullConfigExecutor}</li>
 *  <li>link implemented by {@link LinkExecutor}</li>
 * </ul>
 * 
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class HueBridgeExecutor implements CliExecutor {
    
    /**
     * Stores the base URL.
     */
    private String baseUrl;
    
    /**
     * Stores the Hue bridge.
     */
    private HueBridge bridge;
    
    /**
     * Stores the device type.
     */
    private String deviceType;
    
    /**
     * Stores the id.
     */
    private String id;
    
    /**
     * Stores the on flag.
     */
    private boolean on;
    
    /**
     * Stores the username.
     */
    private String username;
    
    /**
     * Execute.
     * 
     * @param arguments the arguments.
     * @return the result.
     */
    @Override
    public String execute(List<String> arguments) {
        String result = "";
        if (arguments.size() > 0) {
            parseArguments(arguments);
            bridge = new HueBridge(baseUrl);
            switch(arguments.get(0)) {
                case "get-base-config": {
                    GetBaseConfigExecutor executor = new GetBaseConfigExecutor();
                    arguments.remove(0);
                    result = executor.execute(arguments);
                } break;
                case "get-full-config": {
                    GetFullConfigExecutor executor = new GetFullConfigExecutor();
                    arguments.remove(0);
                    result = executor.execute(arguments);
                } break;
                case "link" : {
                    LinkExecutor executor = new LinkExecutor();
                    arguments.remove(0);
                    result = executor.execute(arguments);
                } break;
                case "set-light-state": {                    
                    SetLightStateExecutor executor = new SetLightStateExecutor();
                    arguments.remove(0);
                    result = executor.execute(arguments);
                    
                } break;
            }
        }
        return result;
    }
    
    /**
     * Parse the arguments.
     * 
     * @param arguments the arguments.
     */
    public void parseArguments(List<String> arguments) {
        for(int i=0; i<arguments.size(); i++) {
            if (arguments.get(i).equals("--base-url")) {
                baseUrl = arguments.get(i+1);
            }
            if (arguments.get(i).equals("--username")) {
                username = arguments.get(i+1);
            }
        }
    }
}
