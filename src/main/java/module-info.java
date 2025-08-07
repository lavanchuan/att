module demo.attackweb {
    requires javafx.controls;
    requires javafx.fxml;
    requires playwright;


    opens demo.attackweb to javafx.fxml;
    exports demo.attackweb;
}