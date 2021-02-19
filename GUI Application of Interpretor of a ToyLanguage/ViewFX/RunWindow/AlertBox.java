package ViewFX.RunWindow;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

    public static void display(String title, String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(200);

        Label label = new Label();
        label.setText(message);
        label.setStyle("-fx-font-family: 'Agency FB'");
        label.setStyle("-fx-font-size: 12pt");

        Button closeButton = new Button("OK");
        closeButton.setPadding(new Insets(10,30, 10,30));
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox();
        layout.getChildren().addAll(label, closeButton);
        layout.setPadding(new Insets(20, 10, 10, 10));
        layout.setSpacing(100);
        layout.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);

        window.showAndWait();
    }
}
