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
package com.manorrock.sphynx.desktop;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

/**
 * The Automations Window.
 * 
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class AutomationListWindow extends BorderPane {
    
    /**
     * Stores the automation TableView.
     */
    @FXML
    private TableView automationTableView;

    /**
     * Constructor.
     */
    public AutomationListWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setRoot(this);
            loader.setController(this);
            loader.load(getClass().getResourceAsStream(
                    "/com/manorrock/sphynx/desktop/AutomationListWindow.fxml"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    /**
     * Initialize the component.
     */
    @FXML
    public void initialize() {
        TableColumn idTableColumn = new TableColumn("Id");
        idTableColumn.setCellValueFactory(
                new PropertyValueFactory<Automation, Long>("id")
        );
        automationTableView.getColumns().add(idTableColumn);
        TableColumn nameTableColumn = new TableColumn("Name");
        nameTableColumn.setCellValueFactory(
                new PropertyValueFactory<Automation, String>("name")
        );
        automationTableView.getColumns().add(nameTableColumn);
    }
    
    /**
     * Handle 'Add' button click.
     * 
     * @param event the event.
     */
    @FXML
    public void onAdd(ActionEvent event) {
        Automation automation = new Automation();
        automation.setId(System.currentTimeMillis());
        automation.setName("Untitled - " + System.currentTimeMillis());
        automationTableView.getItems().add(automation);
    }
    
    /**
     * Handle 'Delete' button click.
     * 
     * @param event the event.
     */
    @FXML
    public void onDelete(ActionEvent event) {
        if (!automationTableView.getSelectionModel().isEmpty()) {
            Automation automation = (Automation) automationTableView
                    .getSelectionModel().getSelectedItem();
            automationTableView.getItems().remove(automation);
        }
    }
}
