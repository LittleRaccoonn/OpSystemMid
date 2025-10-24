module org.example.memorycards {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.memorycards to javafx.fxml;
    exports org.example.memorycards;
}