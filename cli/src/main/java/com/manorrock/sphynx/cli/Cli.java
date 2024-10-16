/*
 * Copyright (c) 2002-2024 Manorrock.com. All Rights Reserved.
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

import picocli.CommandLine;
import picocli.CommandLine.Command;

/**
 * The Manorrock Sphynx CLI.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Command(name = "si", mixinStandardHelpOptions = true,
        subcommands = {
            CreateCommand.class,
            DeleteCommand.class,
            ExecuteCommand.class,
            ListCommand.class,
            LogCommand.class
        },
        versionProvider = CliVersionProvider.class)
public class Cli implements Runnable {
    
    /**
     * Stores the arguments.
     */
    private String[] arguments;
    
    /**
     * Stores the exit code.
     */
    private int exitCode;

    /**
     * Get the exit code.
     * 
     * @return the exit code.
     */
    public int getExitCode() {
        return exitCode;
    }
    
    /**
     * Main entry point.
     *
     * @param arguments the command-line arguments.
     */
    public static void main(String[] arguments) {
        Cli cli = new Cli();
        cli.arguments = arguments;
        cli.run();
        System.exit(cli.exitCode);
    }

    /**
     * Run the CLI.
     */
    @Override
    public void run() {
        exitCode = new CommandLine(new Cli()).execute(arguments);
    }
    
    /**
     * Set the arguments.
     * 
     * @param arguments the arguments. 
     */
    public void setArguments(String[] arguments) {
        this.arguments = arguments;
    }
}
