package parent;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Settings {

    private Stage stage;
    private Parent root;
    private Scene scene;
    public  boolean musicON=true;
    public boolean soundON=true;
public void setMusicON(ActionEvent event) throws IOException{

    this.musicON=true;
}
public void setSoundON(ActionEvent event) throws IOException{

        this.soundON=true;

}
public void setSoundOFF(ActionEvent event)throws IOException{

    this.soundON=false;
}
public void setMusicOFF(ActionEvent event)throws IOException{

    this.musicON=false;
}








public void swtichToMenu(ActionEvent event) throws IOException{

    boolean musicON2,soundON2;
    musicON2=this.musicON;
    soundON2=this.soundON;
    FXMLLoader loader= new FXMLLoader(getClass().getResource("MainMenu.fxml"));
    root=loader.load();
    MainMenu mainMenu=loader.getController();
    mainMenu.setMusic(musicON2);
    mainMenu.setSound(soundON2);
    stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
    scene=new Scene(root);
    stage.setScene(scene);
    stage.show();
}







}
