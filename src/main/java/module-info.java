module com.lipin {
//     org.example
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires java.prefs;
    requires java.xml.bind;
    requires java.activation;


    opens com.lipin.controller to javafx.fxml;
    exports com.lipin;
}