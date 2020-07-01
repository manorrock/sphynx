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
package com.collectivae.cli.device.huebridge;

import com.collectivae.device.hue.JsonLightState;
import com.collectivae.cli.CliExecutor;
import com.collectivae.device.hue.HueBridge;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import javax.json.JsonArray;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * The executor that sets the state of a light.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
class SetLightStateExecutor implements CliExecutor {

    /**
     * Execute.
     *
     * @param arguments the arguments.
     * @return the result.
     */
    @Override
    public String execute(List<String> arguments) {
        String result;
        Options options = new Options();
        options.addOption(null, "help", false, "Print this message");
        options.addRequiredOption(null, "base-url", true, "The URL of the bridge");
        options.addRequiredOption(null, "id", true, "The ID of the light");
        options.addOption(null, "on", true, "The on state (true/false)");
        options.addRequiredOption(null, "username", true, "The username to authenticate with");
        options.addOption(null, "color-temperature", true, "The color temperature");
        options.addOption(null, "brightness", true, "The brightness");
        options.addOption(null, "alert", true, "The alert mode");
        options.addOption(null, "xy", true, "The XY value");
        DefaultParser parser = new DefaultParser();
        CommandLine commandLine = null;
        try {
            commandLine = parser.parse(options, arguments.toArray(new String[0]));
        } catch (ParseException pe) {
        }
        if (commandLine == null || commandLine.hasOption("help")) {
            HelpFormatter formatter = new HelpFormatter();
            StringWriter stringWriter = new StringWriter();
            formatter.printUsage(new PrintWriter(stringWriter), 80,
                    "co device huebridge set-light-state", options);
            formatter.printOptions(new PrintWriter(stringWriter), 80, options, 1, 1);
            result = stringWriter.toString();
        } else {
            JsonbConfig config = new JsonbConfig();
            config.withFormatting(true);
            Jsonb jsonb = JsonbBuilder.create(config);
            HueBridge bridge = new HueBridge();
            bridge.setBaseUrl(commandLine.getOptionValue("base-url"));
            bridge.setUsername(commandLine.getOptionValue("username"));
            JsonLightState state = new JsonLightState();
            if (commandLine.hasOption("on")) {
                state.setOn(Boolean.parseBoolean(commandLine.getOptionValue("on")));
            }
            if (commandLine.hasOption("brightness")) {
                state.setBrightness(Integer.parseInt(commandLine.getOptionValue("brightness")));
            }
            if (commandLine.hasOption("color-temperature")) {
                state.setColorTemperature(Integer.parseInt(commandLine.getOptionValue("color-temperature")));
            }
            if (commandLine.hasOption("alert")) {
                state.setAlert(commandLine.getOptionValue("alert"));
            }
            if (commandLine.hasOption("xy")) {
                String xyString = commandLine.getOptionValue("xy");
                JsonArray jsonArray = jsonb.fromJson(xyString, JsonArray.class);
                state.setXy(new float[]{
                    Float.parseFloat(jsonArray.getJsonNumber(0).toString()),
                    Float.parseFloat(jsonArray.getJsonNumber(1).toString())
                });
            }
            result = JsonUtil.prettyPrint(
                    bridge.setLightState(commandLine.getOptionValue("id"), state));
        }
        return result;
    }
}
