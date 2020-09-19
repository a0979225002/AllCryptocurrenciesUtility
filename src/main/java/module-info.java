module com.lipin {
//     org.example
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires java.prefs;
    requires jakarta.activation;
    requires jakarta.xml.bind;

    opens com.lipin.controller to javafx.fxml;
    opens com.lipin.model to jakarta.xml.bind;
    exports com.lipin;
}