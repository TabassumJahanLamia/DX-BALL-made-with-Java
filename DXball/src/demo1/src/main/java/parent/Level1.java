package parent;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.robot.Robot;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.ResourceBundle;

public class Level1 implements Initializable {

    @FXML
    private AnchorPane scene;

    @FXML
    private Circle circle;
    @FXML
    private Button exitToMain;
    @FXML
    private Rectangle paddle;

    @FXML
    private Rectangle bottomZone;
    @FXML
    private Rectangle top_zone;
    @FXML
    private Button startButton;
    @FXML
    private Button resume;
    @FXML
    private Text text1;
    @FXML
    private Text text2;
    @FXML
    private Label gameOverLabel;
    @FXML
    private Button exitButton;
    @FXML
    private Button next_lev;
    @FXML
    private Label congo_msg;
    @FXML
    private Button pause_button;
    Robot robot = new Robot();
    Button btn = new Button();
    private Stage stage;
    private Parent root;
    private Scene Scene;
    public boolean gameSoundOn=true;
    public boolean musicSoundOn=true;
    public void setSound(boolean val1,boolean val2){
        gameSoundOn=val1;
        musicSoundOn=val2;
    }
    AudioClip Bounce = new AudioClip("file:src/main/resources/parent/BallBounce1.wav");
    AudioClip dead = new AudioClip("file:src/main/resources/parent/DeadSound.wav");
    AudioClip musics = new AudioClip("file:src/main/resources/parent/Level1_Music.wav");
    AudioClip bottom = new AudioClip("file:src/main/resources/parent/BottomZone.wav");
    AudioClip win = new AudioClip("file:src/main/resources/parent/WinSound.wav");

    public int p=1;
    private ArrayList<Rectangle> bricks = new ArrayList<>();
    int score =0;
    double deltaX = 1;
    double deltaY = 2;
    int lives=3;

    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(9), new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            movePaddle();

            text1.setText("Score: "+String.valueOf(score));
            text2.setText("Lives: "+String.valueOf(lives) );
            checkCollisionPaddle(paddle);
            circle.setLayoutX(circle.getLayoutX() + deltaX);
            circle.setLayoutY(circle.getLayoutY() + deltaY);

            if(!bricks.isEmpty()){
                bricks.removeIf(brick -> checkCollisionBrick(brick));
            } else {
                timeline.stop();
            }
            if(bricks.isEmpty())
            {
                next_level_funct();

            }
            checkKeyPressed();
            checkCollisionScene(scene);
            checkCollisionBottomZone();
            checkCollisionTopzone();

        }
    }));
   public void checkKeyPressed(){

       scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
           @Override
           public void handle(KeyEvent keyEvent) {
               next_level_funct();

               System.out.println(keyEvent.getCode());
           }
       });



   }
    public void swtichToMenu(ActionEvent event) throws IOException{
         timeline.pause();
         resume.setVisible(true);
        Alert alert =new Alert (Alert.AlertType.CONFIRMATION);
        alert.setTitle("EXIT TO MENU");
        alert.setHeaderText("Your About to Exit the Game!! PROGRESS WILL NOT BE SAVED!!");
        alert.setContentText("Press Ok to exit");
        if(alert.showAndWait().get()== ButtonType.OK) {
            FXMLLoader loader= new FXMLLoader(getClass().getResource("MainMenu.fxml"));
            root=loader.load();
            MainMenu mainMenu=loader.getController();

            stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
            Scene=new Scene(root);
            stage.setScene(Scene);
            stage.show();
            musics.stop();
        }






    }
   public void next_level_funct(){

       circle.setLayoutX(300);
       circle.setLayoutY(300);
       paddle.setLayoutX(scene.getWidth()/2-paddle.getWidth()/2);

       timeline.stop();
       if(musicSoundOn)
           win.play();
       musics.stop();
       congo_msg.setVisible(true);
       next_lev.setVisible(true);
       bricks.forEach(brick -> scene.getChildren().remove(brick));
       bricks.clear();
       resume.setVisible(false);
   }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    @FXML
    void startGameButtonAction(ActionEvent event) {

       if(musicSoundOn==true)
       musics.play();
       pause_button.setVisible(true);
        startButton.setVisible(false);
        startGame();
    }

    public void startGame(){

        createBricks();
        timeline.play();
    }
    public void resumeGame()
    {

        timeline.play();
        resume.setVisible(false);
    }
    public void nextLevel(ActionEvent event ) throws IOException {

        FXMLLoader loader= new FXMLLoader(getClass().getResource("Level2.fxml"));
        root=loader.load();
        Level2 level2=loader.getController();
        level2.setSound(gameSoundOn,musicSoundOn);
        level2.inheritInfo(score,lives);
        stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene=new Scene(root);
        stage.setScene(Scene);
        stage.show();
   }


    public void checkCollisionScene(Node node){

        Bounds bounds = node.getBoundsInLocal();
        boolean rightBorder = circle.getLayoutX() >= (bounds.getMaxX() - circle.getRadius());
        boolean leftBorder = circle.getLayoutX() <= (bounds.getMinX() + circle.getRadius());
        boolean bottomBorder = circle.getLayoutY() >= (bounds.getMaxY() - circle.getRadius());
        boolean topBorder = circle.getLayoutY() <= (bounds.getMinY() + circle.getRadius());

        if (rightBorder || leftBorder) {
            deltaX *= -1;
           if(gameSoundOn)
            Bounce.play();
        }
        if (bottomBorder || topBorder) {
            deltaY *= -1;
            if(gameSoundOn)
            Bounce.play();
        }
    }
    public void checkCollisionTopzone(){

        if(circle.getBoundsInParent().intersects(top_zone.getBoundsInParent()))
        {
            deltaY*=-1;
            if(gameSoundOn==true)
                Bounce.play();
        }
    }

    public boolean checkCollisionBrick(Rectangle brick)  {

        if(circle.getBoundsInParent().intersects(brick.getBoundsInParent())){
            boolean rightBorder = circle.getLayoutX() >= ((brick.getX() + brick.getWidth()) - circle.getRadius());
            boolean leftBorder = circle.getLayoutX() <= (brick.getX() + circle.getRadius());
            boolean bottomBorder = circle.getLayoutY() >= ((brick.getY() + brick.getHeight()) - circle.getRadius());
            boolean topBorder = circle.getLayoutY() <= (brick.getY() + circle.getRadius());
            if(gameSoundOn==true)
            Bounce.play();
            if (rightBorder || leftBorder) {
                deltaX *= -1;
            }
            if (bottomBorder || topBorder) {
                deltaY *= -1;
            }
            scene.getChildren().remove(brick);
           score+=20;




            return true;

        }
        return false;
    }

    public void pause_game(ActionEvent event) throws IOException{

       timeline.stop();
        resume.setVisible(true);

    }
    public void createBricks(){
        double width = 560;
        double height = 200;

        int spaceCheck = 1;

        for (double i = height; i > 50; i = i - 50) {
            for (double j = width; j > 0 ; j = j - 25) {
                if(spaceCheck % 2 == 0){
                    Rectangle rectangle = new Rectangle(j,i,30,20);
                    rectangle.setArcWidth(10);
                    rectangle.setArcHeight(7);
                    int num=(int) ((Math.random() * (5 - 1)) + 1);
                   if(num==1)
                       rectalngle.setFill(Color.AQUA);
                   else  if( num==2)
                        rectangle.setFill(Color.FORESTGREEN);
                   else if(num==3)
                        rectangle.setFill(Color.CRIMSON);
                   else if(num==4)
                       rectangle.setFill(Color.PEACHPUFF);
                    scene.getChildren().add(rectangle);
                    bricks.add(rectangle);
                }
                spaceCheck++;
            }
        }
    }

    public void movePaddle(){
        Bounds bounds = scene.localToScreen(scene.getBoundsInLocal());
        double sceneXPos = bounds.getMinX();

        double xPos = robot.getMouseX();
        double paddleWidth = paddle.getWidth();

        if(xPos >= sceneXPos + (paddleWidth/2) && xPos <= (sceneXPos + scene.getWidth()) - (paddleWidth/2)){
            paddle.setLayoutX(xPos - sceneXPos - (paddleWidth/2));
        } else if (xPos < sceneXPos + (paddleWidth/2)){
            paddle.setLayoutX(0);
        } else if (xPos > (sceneXPos + scene.getWidth()) - (paddleWidth/2)){
            paddle.setLayoutX(scene.getWidth() - paddleWidth);
        }
    }

    public void checkCollisionPaddle(Rectangle paddle){

        if(circle.getBoundsInParent().intersects(paddle.getBoundsInParent())){
            if(gameSoundOn==true)
               Bounce.play();
            boolean leftBorder = circle.getLayoutX() >= ((paddle.getLayoutX() + paddle.getWidth()) - circle.getRadius());
            boolean rightBorder = circle.getLayoutX() <= (paddle.getLayoutX() + circle.getRadius());
            boolean bottomBorder = circle.getLayoutY() >= ((paddle.getLayoutY() + paddle.getHeight()) - circle.getRadius());
            boolean topBorder = circle.getLayoutY() <= (paddle.getLayoutY() + circle.getRadius());

            if (rightBorder || leftBorder) {
                deltaX *= -1;
            }
            if (bottomBorder|| topBorder) {
                deltaY *= -1;
            }
        }
    }
    public void gameOver(ActionEvent event) throws IOException {

        circle.setLayoutX(300);
        circle.setLayoutY(300);
        paddle.setLayoutX(scene.getWidth()/2-paddle.getWidth()/2);
        FXMLLoader loader= new FXMLLoader(getClass().getResource("GameOver.fxml"));
        root=loader.load();
        GameOver game_over=loader.getController();
        game_over.setScore(score);
        stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene=new Scene(root);
        stage.setScene(Scene);
        stage.show();





    }

    public void checkCollisionBottomZone(){
        if(circle.getBoundsInParent().intersects(bottomZone.getBoundsInParent())){
            if(gameSoundOn==true)
            bottom.play();

            lives--;
            text2.setText("Lives: "+String.valueOf(lives) );
            if(lives==0) {
                musics.stop();
                timeline.stop();
                bricks.forEach(brick -> scene.getChildren().remove(brick));
                bricks.clear();
                //startButton.setVisible(true);

                deltaX = -1;
                deltaY = -3;

                circle.setLayoutX(300);
                circle.setLayoutY(300);
                lives=3;
                text2.setText("Lives: "+String.valueOf(lives) );
                gameOverLabel.setVisible(true);
                exitButton.setVisible(true);
                if(gameSoundOn)
                dead.play();

            }
            else{

                timeline.pause();
                deltaX = -1;
                deltaY = -3;

                resume.setVisible(true);
                circle.setLayoutX(300);
                circle.setLayoutY(300);
                paddle.setLayoutX(scene.getWidth()/2-paddle.getWidth()/2);


            }
            System.out.println("Game over!");
        }
    }
}