package parent;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
    ;
      Parent root = FXMLLoader.load(getClass().getResource("Level3.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
       /// Parent root = FXMLLoader.load(getClass().getResource("Settings.fxml"));
        primaryStage.setTitle("DxBall");
        primaryStage.setResizable(false);

        primaryStage.setScene(new Scene(root));

        primaryStage.show();
    }


}