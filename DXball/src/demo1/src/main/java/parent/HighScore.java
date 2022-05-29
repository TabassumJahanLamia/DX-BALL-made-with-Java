package parent;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.AccessibleAction;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class HighScore implements Initializable {

    @FXML
    private Label player1_name;
    @FXML
    private Label player1_score;
    @FXML
    private Label player2_name;
    @FXML
    private Label player2_score;
    @FXML
    private Label player3_name;
    @FXML
    private Label player3_score;
    @FXML
    private Label player4_name;
    @FXML
    private Label player4_score;
    @FXML
    private Label player5_name;
    @FXML
    private Label player5_score;
    @FXML
    private Button exit_button;
    private Stage stage;
    private Parent root;
    private Scene Scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        File file = new File("C:\\Users\\Omie\\IdeaProjects\\demo1\\src\\main\\resources\\parent\\high_score.txt");
        Map<String,Integer> highScoreMap = new HashMap<>();

        try {
            Scanner scan =new Scanner(file);
            while(scan.hasNext()){
            String name =scan.nextLine();
            String temp= scan.nextLine();
            int score=Integer.parseInt(temp);
            if(highScoreMap.containsKey(name)){

            int temp1= highScoreMap.get(name);
            if(score>temp1)
                highScoreMap.put(name,score);


            }   else
                highScoreMap.put(name,score);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
       // highScoreMap.forEach((k,v)-> System.out.println(k+" "+v));
        Set<Map.Entry<String,Integer>> entrySet=highScoreMap.entrySet();
        List<Map.Entry<String,Integer>> list =new ArrayList<>(entrySet);
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public  int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (-1)*(o1.getValue().compareTo(o2.getValue()));

            }
        });

        ArrayList<String> names = new ArrayList<String>();
        ArrayList<Integer> scores=new ArrayList<Integer>();

        for (Map.Entry<String, Integer> s : list) {

             System.out.println(s.getKey() + " " + s.getValue());
             names.add(s.getKey());
             scores.add(s.getValue());
        }
        for(int i=0;i<5;i++)
        { if(i==0)
        {
         player1_name.setText(names.get(i));
         player1_score.setText(""+scores.get(i));
        }
            if(i==1)
            {
                player2_name.setText(names.get(i));
                player2_score.setText(""+scores.get(i));
            }
            if(i==2)
            {
                player3_name.setText(names.get(i));
                player3_score.setText(""+scores.get(i));
            }
            if(i==3)
            {
                player4_name.setText(names.get(i));
                player4_score.setText(""+scores.get(i));
            }
            if(i==4)
            {
                player5_name.setText(names.get(i));
                player5_score.setText(""+scores.get(i));
            }


    }

}


    public void exit_button_func(javafx.event.ActionEvent event) throws IOException {

        FXMLLoader loader= new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        root=loader.load();
        MainMenu mainMenu=loader.getController();

        stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene=new Scene(root);
        stage.setScene(Scene);
        stage.show();
    }
}




