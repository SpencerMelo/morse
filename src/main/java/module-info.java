module com.morse {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;

    opens com.morse to javafx.fxml;

    exports com.morse;
    exports com.morse.structure;
    exports com.morse.utils;
}
