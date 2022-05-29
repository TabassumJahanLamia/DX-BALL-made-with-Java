package parent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.util.Objects;

public class MainMenu {
private Stage stage;
private Parent root;
private Scene scene;
@FXML
private AnchorPane scenePane;

public boolean soundON=true;
public boolean musicON=true;
public void setMusic(boolean send)
{

     this.musicON=send;
}
public void setSound(boolean send)
{

     this.soundON=send;
}


public void switchtoCredits(ActionEvent event) throws IOException {

     FXMLLoader loader= new FXMLLoader(getClass().getResource("Credits.fxml"));
     root=loader.load();

     stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
     scene=new Scene(root);
     stage.setScene(scene);
     stage.show();

     }
public void switchToGame(ActionEvent event)throws IOException{
     FXMLLoader loader= new FXMLLoader(getClass().getResource("Level1.fxml"));
     root=loader.load();
     Level1 level=loader.getController();
     level.setSound(soundON,musicON);
     stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
     scene=new Scene(root);
     stage.setScene(scene);
     stage.show();

}
public void switchToSettings(ActionEvent event)throws IOException{

     root = FXMLLoader.load(getClass().getResource("Settings.fxml"));
     stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
     scene =new Scene(root);
     stage.setScene(scene);
     stage.show();
}
     public void switchToHighScore(ActionEvent event)throws IOException{

          root = FXMLLoader.load(getClass().getResource("HighScore.fxml"));
          stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
          scene =new Scene(root);
          stage.setScene(scene);
          stage.show();
     }

public void exit(ActionEvent event )throws IOException{
     Alert alert =new Alert (Alert.AlertType.CONFIRMATION);
     alert.setTitle("EXIT");
     alert.setHeaderText("Your About to Exit the Game!!");
     alert.setContentText("Press Ok to exit");
      if(alert.showAndWait().get()== ButtonType.OK) {
       stage=(Stage) scenePane.getScene().getWindow();
      stage.close();
      }



}





}
