/*
 * Copyright (c) 2002-2023 Manorrock.com. All Rights Reserved.
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
package com.manorrock.sphynx.cli;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Bash CLI.
 * 
 * @author Manfred Riem (mriem@manorrock.com)
 */
class BashCli {
    
    /**
     * Stores the script file.
     */
    private String bashFile;
    
    /**
     * Stores the run arguments.
     */
    private String runArguments;
    
    /**
     * Execute.
     * 
     * @param arguments the arguments.
     */
    void execute(String[] arguments) {
        parseArguments(arguments);
        run();
    }

    /**
     * Parse the arguments.
     * 
     * @param arguments the arguments.
     */
    private void parseArguments(String[] arguments) {
        for(int i=1; i<arguments.length; i++) {
            if (arguments[i].equals("--bash-file")) {
                bashFile = arguments[i + 1];
            }
            if (arguments[i].equals("--run-arguments")) {
                runArguments = arguments[i];
            }
        }
    }
    
    /**
     * Run the Java file.
     */
    private void run() {
        try {
            ArrayList<String> command = new ArrayList<>();
            command.add("bash");
            command.add(runArguments);
            Process process = new ProcessBuilder()
                .command(command)
                .inheritIO()
                .start();
            System.exit(process.waitFor());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
