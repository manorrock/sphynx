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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;

/**
 * The Manorrock Sphynx CLI.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class Cli {

    /**
     * Add a workflow.
     */
    private void addWorkflow(String[] arguments) {
        String name = null;
        String filename = null;
        for(int i=1; i<arguments.length; i++) {
            if (arguments[i].equals("--name")) {
                name = arguments[i+1];
            }
            if (arguments[i].equals("--filename")) {
                filename = arguments[i+1];
            }
        }
        if (name == null) {
            System.out.println("Missing --name parameter");
            System.exit(100);
        }
        if (filename == null) {
            System.out.println("Missing --filename parameter");
        }
        File baseDir = new File(System.getProperty("user.home"), ".manorrock/sphynx");
        if (!baseDir.exists()) {
            baseDir.mkdirs();
        }
        File workflowDir = new File(baseDir, name);
        if (!workflowDir.exists()) {
            workflowDir.mkdir();
        }
        File workflowConfigFile = new File(workflowDir, "workflow.json");
        File workflowFile = new File(workflowDir, "workflow.jshell");
        try (PrintStream printStream = new PrintStream(new FileOutputStream(workflowConfigFile))) {
            printStream.println("{name:\"" + name + "\"}");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            Files.copy(new File(filename).toPath(), workflowFile.toPath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Execute.
     *
     * @param arguments the arguments.
     */
    private void execute(String[] arguments) {
        if (arguments.length > 0) {
            switch (arguments[0]) {
                case "add" -> addWorkflow(arguments);
                case "bash" -> new BashCli().execute(arguments);
                case "execute" -> executeWorkflow(arguments);
                case "java" -> new JavaCli().execute(arguments);
                case "list" -> listWorkflows();
                case "create" -> createJob();
                case "delete" -> deleteJob();
                case "version" -> showVersion();
            }
        }
    }
    
    /**
     * Execute the workflow.
     * 
     * @param arguments the arguments.
     */
    private void executeWorkflow(String[] arguments) {
        String name = null;
        for(int i=1; i<arguments.length; i++) {
            if (arguments[i].equals("--name")) {
                name = arguments[i+1];
            }
        }
        if (name == null) {
            System.out.println("Missing --name parameter");
            System.exit(100);
        }
        ProcessBuilder builder = new ProcessBuilder();
        
        File baseDir = new File(System.getProperty("user.home"), ".manorrock/sphynx");
        File workflowDir = new File(baseDir, name);
        File tmpDir = new File(workflowDir, "temp");
        if (!tmpDir.exists()) {
            tmpDir.mkdir();
        }
        File workflowFile = new File(workflowDir, "workflow.jshell");
        builder.directory(tmpDir)
                .command("jshell", workflowFile.getAbsolutePath())
                .inheritIO();
        Process process;
        try {
            process = builder.start();
            System.exit(process.waitFor());
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Main entry point.
     *
     * @param arguments the command-line arguments.
     */
    public static void main(String[] arguments) {
        Cli cli = new Cli();
        cli.execute(arguments);
    }

    /**
     * List the workflows.
     */
    private void listWorkflows() {
        System.out.println("NONE");
    }

    /**
     * Show the version information.
     */
    private void showVersion() {
        System.out.println("TODO - show version");
    }

    private void createJob() {
        System.out.println("TODO - create job");
    }

    private void deleteJob() {
        System.out.println("TODO - delete job");
    }
}
