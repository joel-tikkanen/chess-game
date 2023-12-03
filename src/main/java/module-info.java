module com.chessgame {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.chessgame to javafx.fxml;
    exports com.chessgame;
}
