module programacion.ipac.javaforms.javaforms {
    requires javafx.controls;
    requires javafx.fxml;


    opens programacion.ipac.javaforms.javaforms to javafx.fxml;
    exports programacion.ipac.javaforms.javaforms;
}