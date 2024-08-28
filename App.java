import java.util.*;
import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.*;  
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
  
public class App extends Application {  

    static Random rand = new Random();
    static Stage window;
    static GridPane table;
    static Scene scene;
    static int len = 4;
    static int grid[][];
    static Label label; 
    static String text;

    public static void main(String[] args) {  
        grid = new int[len][len];
        init_grid();
        spawn_num();
        launch(args); // note: do not make a different constructor - that messes it up 
    }

    static void init_grid(){
        for(int r = 0; r < grid.length; r++){
            for(int c = 0; c < grid[r].length; c++){
                grid[r][c] = 0;
            }
        }
    }

    static void print_grid(){
        for(int r = 0; r < grid.length; r++){
            for(int c = 0; c < grid[r].length; c++){
                if(grid[r][c] != 0) System.out.printf("[%d]\t", grid[r][c]);
                else System.out.print("[ ]\t");
            }
            System.out.println();
        }
    }

    static void spawn_num(){
        int x, y, left = 2;
        while(left != 0){
            x = rand.nextInt(grid.length); y = rand.nextInt(grid.length);
            if(grid[x][y] == 0){
                if(rand.nextInt(11) == 1) grid[x][y] = 4;
                else grid[x][y] = 2;
                left--;
            }
        }
    }

    static void move_up(){
        for(int c = 0; c < grid.length; c++){
            for(int r = 0; r < grid[c].length; r++){
                for(int i = r; i > 0; i--){
                    if(grid[i-1][c] == 0){
                        grid[i-1][c] = grid[i][c];
                        grid[i][c] = 0;
                    }
                    if(grid[i-1][c] == grid[i][c] && grid[i][c] != 0){
                        grid[i-1][c] = 2*grid[i][c];
                        grid[i][c] = 0;
                        break;
                    }
                }
            }
        }
    }

    static void move_down(){
        for(int c = 0; c < grid.length; c++){
            for(int r = grid[c].length-1; r > -1; r--){
                for(int i = r; i < grid[c].length-1; i++){
                    if(grid[i+1][c] == 0){
                        grid[i+1][c] = grid[i][c];
                        grid[i][c] = 0;
                    }
                    if(grid[i+1][c] == grid[i][c] && grid[i][c] != 0){
                        grid[i+1][c] = 2*grid[i][c];
                        grid[i][c] = 0;
                        break;
                    }
                }
            }
        }
    }
    
    static void move_left(){
        for(int r = 0; r < grid.length; r++){
            for(int c = 0; c < grid[r].length; c++){
                for(int i = c; i > 0; i--){
                    if(grid[r][i-1] == 0){
                        grid[r][i-1] = grid[r][i];
                        grid[r][i] = 0;
                    }
                    if(grid[r][i-1] == grid[r][i] && grid[r][i] != 0){
                        grid[r][i-1] = 2*grid[r][i];
                        grid[r][i] = 0;
                        break;
                    }
                }
            }
        }
    }

    static void move_right(){
        for(int r = 0; r < grid.length; r++){
            for(int c = grid[r].length-1; c > -1; c--){
                for(int i = c; i < grid[r].length-1; i++){
                    if(grid[r][i+1] == 0){
                        grid[r][i+1] = grid[r][i];
                        grid[r][i] = 0;
                    }
                    if(grid[r][i+1] == grid[r][i] && grid[r][i] != 0){
                        grid[r][i+1] = 2*grid[r][i];
                        grid[r][i] = 0;
                        break;
                    }
                }
            }
        }
    }

    void make_grid() {
        table.getChildren().clear();
        for (int r = 0; r < len; r++) {
            for (int c = 0; c < len; c++) {
                text = grid[r][c] != 0 ? Integer.toString(grid[r][c]) : "";
                label = new Label(text);
                label.getStyleClass().add("label");
    
                // Apply specific class based on the value of the cell
                label.getStyleClass().add("num-" + grid[r][c]);
    
                GridPane.setConstraints(label, c, r);
                table.getChildren().add(label);
            }
        }
    }
    
  
    @Override  
    public void start(Stage primaryStage) throws Exception { 
        window = primaryStage;
        window.setTitle("Too Oh For Ate");

        table = new GridPane();
        table.setPadding(new Insets(50, 50, 50, 50));
        table.setVgap(5); table.setHgap(5);

        scene = new Scene(table);
        scene.getStylesheets().add("stylesheet.css");

        make_grid();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch(event.getCode()){
                    case W: move_up(); spawn_num(); make_grid(); break; 
                    case S: move_down(); spawn_num(); make_grid(); break;
                    case D: move_right(); spawn_num(); make_grid(); break;
                    case A: move_left(); spawn_num(); make_grid(); break;
                    default: break;
                }
            } 
        });

        window.setScene(scene);    
        window.show();  
          
    }  
}
