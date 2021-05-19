import java.io.File;
        import javafx.application.Application;
        import javafx.scene.Scene;
        import javafx.scene.layout.BorderPane;
        import javafx.stage.FileChooser;
        import javafx.stage.Stage;
        import javafx.scene.control.Button;


public class Game extends Application {
    Food food;
    MyBotPlayer player;
    Map map;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button chooseFile = new Button("Choose File");
        BorderPane pane = new BorderPane();
        pane.setCenter(chooseFile);
        Scene scene = new Scene(pane, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Eater");
        primaryStage.show();
        chooseFile.setOnAction(e ->{
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(primaryStage);
            if(!file.getName().isEmpty()){
                map = new Map(file.getName());
                player = new MyBotPlayer(map);
                food = new Food(map, player);
                pane.setCenter(map);
            }

        });
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case RIGHT:
                    player.moveRight();
                    break;
                case LEFT:
                    player.moveLeft();
                    break;
                case UP:
                    player.moveUp();
                    break;
                case DOWN:
                    player.moveDown();
                    break;
                case E:
                    player.feed(food);
                    player.eat();
                    break;
                case F:
                    player.feed(food);
                    player.find();
                    break;
            }
        });

    }
}
