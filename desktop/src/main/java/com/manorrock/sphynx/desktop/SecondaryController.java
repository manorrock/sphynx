package com.manorrock.sphynx.desktop;

import java.io.IOException;
import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        DesktopApplication.setRoot("primary");
    }
}