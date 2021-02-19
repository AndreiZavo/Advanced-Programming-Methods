package ViewFX.RunWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainWindow{

    public static void start() throws Exception {

        Parent root = FXMLLoader.load(MainWindow.class.getResource("main.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Visual Debugger");

        Scene scene = new Scene(root);
        String css = MainWindow.class.getResource("mainStyle.css").toExternalForm();
        scene.getStylesheets().add(css);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
