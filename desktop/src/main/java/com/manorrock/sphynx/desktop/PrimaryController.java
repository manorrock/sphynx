package com.manorrock.sphynx.desktop;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        DesktopApplication.setRoot("secondary");
    }
}
