package parent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.io.FileWriter;
import java.io.IOException;

public class GameOver {

    private Stage stage;
    private Parent root;
    private Scene Scene;



    @FXML
    private Label score_label;
    @FXML
    private Button exit_without_save;
    @FXML
    private TextField name_inp;
    @FXML
    private Label congo_msg;

    private int score;

    public void setMessage(String s){
        congo_msg.setText(s);


    }
    public void setScore(int num)
    {   this.score=num;
        score_label.setText(""+score);


    }

    public void submitToFile(ActionEvent event)throws IOException {
     String name=name_inp.getText();
     String score=score_label.getText();
        FileWriter writer = new FileWriter("C:\\Users\\Omie\\IdeaProjects\\demo1\\src\\main\\resources\\parent\\high_score.txt",true);
        BufferedWriter b =new BufferedWriter(writer);
        b.write(name);
        b.newLine();
        b.write(score);
        b.newLine();
        b.close();
        writer.close();

        FXMLLoader loader= new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        root=loader.load();
        MainMenu mainMenu=loader.getController();

        stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene=new Scene(root);
        stage.setScene(Scene);
        stage.show();

    }
    public void swtichToMenu(ActionEvent event) throws IOException{



        FXMLLoader loader= new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        root=loader.load();
        MainMenu mainMenu=loader.getController();

        stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene=new Scene(root);
        stage.setScene(Scene);
        stage.show();
    }






    }




