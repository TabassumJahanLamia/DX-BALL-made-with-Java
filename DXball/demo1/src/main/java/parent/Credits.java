package parent;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Credits {



    private Stage stage;
    private Parent root;
    private Scene scene;






    public void swtichToMenu(ActionEvent event) throws IOException {

        boolean musicON2,soundON2;

        FXMLLoader loader= new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        root=loader.load();
        MainMenu mainMenu=loader.getController();

        stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
