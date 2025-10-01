module com.example.morse {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;

    opens com.example.morse to javafx.fxml;
    exports com.example.morse;
    exports com.example.morse.structure;
}
