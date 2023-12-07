module com.example.testingprj {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens com.example.testingprj to javafx.fxml;
    exports com.example.testingprj;
}