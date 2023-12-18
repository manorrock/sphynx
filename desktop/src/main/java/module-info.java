module com.manorrock.sphynx.desktop {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.manorrock.sphynx.desktop to javafx.fxml;
    exports com.manorrock.sphynx.desktop;
}
