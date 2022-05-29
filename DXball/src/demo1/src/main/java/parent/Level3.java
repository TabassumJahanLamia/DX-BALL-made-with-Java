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

public class Level3 implements Initializable {

    @FXML
    private AnchorPane scene;

    @FXML
    private Circle circle;

    @FXML
    private Rectangle paddle;

    @FXML
    private Rectangle bottomZone;

    @FXML
    private Button startButton;
    @FXML
    private Rectangle top_zone;
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
    private Button pause_game;
    @FXML
    private Button exitToMain;
    Robot robot = new Robot();
    Button btn = new Button();
    int score =0;
    int lives=3;
    private Stage stage;
    private Parent root;
    private Scene Scene;
    public boolean gameSoundOn=true;
    public boolean musicSoundOn=true;
    public void setSound(boolean val1,boolean val2){
        gameSoundOn=val1;
        musicSoundOn=val2;
    }
    public void inheritInfo(int num1,int num2)
    {
        this.score=num1;
        this.lives=num2;
    }
    AudioClip Bounce = new AudioClip("file:src/main/resources/parent/BallBounce1.wav");
    AudioClip dead = new AudioClip("file:src/main/resources/parent/DeadSound.wav");
    AudioClip musics = new AudioClip("file:src/main/resources/parent/Level3_Music.wav");
    AudioClip bottom = new AudioClip("file:src/main/resources/parent/BottomZone.wav");
    AudioClip win = new AudioClip("file:src/main/resources/parent/WinSound.wav");

    public int p=1;
    private ArrayList<Rectangle> bricks = new ArrayList<>();

    double deltaX = 1;
    double deltaY = 2.5;


    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
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
    public void checkCollisionTopzone(){

        if(circle.getBoundsInParent().intersects(top_zone.getBoundsInParent()))
        {
            deltaY*=-1;
            if(gameSoundOn==true)
                Bounce.play();
        }
    }
    public void next_level_funct(){
        circle.setLayoutX(300);
        circle.setLayoutY(300);
        paddle.setLayoutX(scene.getWidth()/2-paddle.getWidth()/2);
        timeline.stop();
        win.play();
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
        pause_game.setVisible(true);
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
        }}
    public void checkCollisionScene(Node node){

        Bounds bounds = node.getBoundsInLocal();
        boolean rightBorder = circle.getLayoutX() >= (bounds.getMaxX() - circle.getRadius());
        boolean leftBorder = circle.getLayoutX() <= (bounds.getMinX() + circle.getRadius());
        boolean bottomBorder = circle.getLayoutY() >= (bounds.getMaxY() - circle.getRadius());
        boolean topBorder = circle.getLayoutY() <= (bounds.getMinY() + circle.getRadius());

        if (rightBorder || leftBorder) {
            deltaX *= -1;
            Bounce.play();
        }
        if (bottomBorder || topBorder) {
            deltaY *= -1;
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
        double width = 600;
        double height = 200;

        int spaceCheck = 1;
        int k=1;

        for (double i = height; i >0; i = i - 50) {
            for (double j = 520; j >400; j = j - 25) {
                if(spaceCheck % 2== 0){
                    Rectangle rectangle = new Rectangle(j,i,30,20);
                    rectangle.setArcWidth(10);
                    rectangle.setArcHeight(7);


                        rectangle.setFill(Color.LIME);
                    scene.getChildren().add(rectangle);
                    bricks.add(rectangle);
                }
                spaceCheck++;
            }}

            for (double i = height; i >0; i = i - 50) {
                for (double j = 350; j >225 ; j = j - 25) {
                    if(spaceCheck % 2== 0){
                        Rectangle rectangle = new Rectangle(j,i,30,20);
                        rectangle.setArcWidth(10);
                        rectangle.setArcHeight(7);

                        rectangle.setFill(Color.AQUA);
                        scene.getChildren().add(rectangle);
                        bricks.add(rectangle);
                    }
                    spaceCheck++;
                }}
        for (double i = height; i >0; i = i - 50) {
            for (double j =175; j >50 ; j = j - 25) {
                if(spaceCheck % 2== 0){
                    Rectangle rectangle = new Rectangle(j,i,30,20);
                    rectangle.setArcWidth(10);
                    rectangle.setArcHeight(7);

                    rectangle.setFill(Color.YELLOW);

                    scene.getChildren().add(rectangle);
                    bricks.add(rectangle);
                }
                spaceCheck++;
            }}



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
        musics.stop();
        circle.setLayoutX(300);
        circle.setLayoutY(300);
        paddle.setLayoutX(scene.getWidth()/2-paddle.getWidth()/2);

        FXMLLoader loader= new FXMLLoader(getClass().getResource("GameOver.fxml"));
        root=loader.load();
        GameOver game_over=loader.getController();
        game_over.setScore(score);
        game_over.setMessage("CONGRATULATIONS");
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
                if(gameSoundOn==true)
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