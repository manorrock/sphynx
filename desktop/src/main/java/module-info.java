module com.manorrock.sphynx.desktop {
    
    opens com.manorrock.sphynx.desktop to javafx.fxml;
    exports com.manorrock.sphynx.desktop;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.manorrock.sphynx.shared;
}
