import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.shape.Path;
import javafx.scene.image.*;
import javafx.scene.layout.HBox;
import javafx.scene.control.*;
import javafx.scene.text.*;
import java.util.HashMap;
import java.util.Random;             
import javafx.geometry.Rectangle2D;

public class Display extends Application {
    private Game game = new Game();  
    private Canvas canvas = new Canvas(900, 600); //900x600 pixel canvas -- can be changed just for testing
    private GraphicsContext g = canvas.getGraphicsContext2D(); 
    private Group root = new Group(canvas); 
    private Scene scene = new Scene(root); 
    private int grd[][] = new int[10][15]; //the grid of 150 cells -- GRID SIZE
    private Image humans = new Image("test1.png");//Player
    private Image aliens = new Image("test2.png"); //Enemy Invaders
    private Image selectoutline = new Image("selectoutline.png");  //select outline image import
    private Image redsquare = new Image("redsquare.png"); //red square movement shadow image import
    private int playersturn = 1;  //a counter for which player's turn it currently is (1 is Humans's turn, 2 is Alien's turn)
    private int somethingselected=0; //a flag to indicate whether the user has selected something or not (not a boolean as we'll need expansion for multiple select actions)
    private int savedx = 0;
    private int savedy = 0;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        int i=0;
        Path path = new Path();        
        primaryStage.setScene(scene);
        grd=game.playgame(game); //sets up a new game
        drawGRID(g, grd);
        scene.setOnMouseClicked(mouseHandler);
        root.getChildren().add(path);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    // Movement Testing -- works more or less
    EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
 
        @Override
        public void handle(MouseEvent mouseEvent) {
            if(mouseEvent.getEventType()==MouseEvent.MOUSE_CLICKED){
                double x=mouseEvent.getSceneX();
                double y=mouseEvent.getSceneY();
                int coordy=(int)((x-(x%60))/60);
                int coordx=(int)((y-(y%60))/60);
                //default: square clicked is not one of your humans while somethingselected = 0
                if(grd[coordx][coordy]!=playersturn+1 && grd[coordx][coordy]!=4){
                    undodrawMOVEBUBBLE (g, grd);
                    somethingselected=0;
                }
                // if the square that has been chosen is a human and somethingselected = 0
                if(grd[coordx][coordy]==playersturn+1 && somethingselected==0){ //i.e. if the selected item is on the player's team
                    savedx=coordx;
                    savedy=coordy;
                    drawSELECT(g, coordx, coordy, grd);
                    somethingselected=1;
                }
                // if the square that has been chosen is an applicable movement space with ss as 1
                if(grd[coordx][coordy]==4 && somethingselected==1){
                    grd[savedx][savedy]=0;
                    grd[coordx][coordy]=playersturn+1;
                    undodrawMOVEBUBBLE (g, grd);
                    somethingselected=0;
                    if(playersturn==2){
                        playersturn=1;

                    }
                    else{
                        playersturn=2;
                    }
                }
            }
        } 
    };

        
    //a method to draw the grid
    void drawGRID(GraphicsContext g, int rack[][]){
        int i, j, temp=0;
        for(i=0; i<10; i++){
            for(j=0; j<15; j++){
                temp=rack[i][j];
                if(temp==0 || temp==1 || temp==2 || temp==3){
                    drawTERRAIN(g, i, j, temp);
                }
            }
        }
        for(i=0; i<10; i++){
            for(j=0; j<15; j++){
                temp=rack[i][j];
                if(temp==2||temp==3){
                    drawPERSON(g, i, j, temp);
                }  
            }
        }

      for(i=0; i<10; i++){
         for(j=0; j<15; j++){
             System.out.print(rack[i][j] + " ");
         }
         System.out.print("\n");
      }
    }
    

    //draw the background of the grid in blue and green - random color variations on set sizes of squares
    void drawTERRAIN(GraphicsContext g, int i, int j, int temp) {
        int x=0, y=0, a=0, b=0, c=0, limit=0, blue=0, green=0, random=0;
        if(temp==1){
            limit=10;
            blue=0;
            green=195;
            random=60; //color of the water background
        }
        else{
            limit=5;
            blue=215;
            green=20;
            random=40; //color of the grass background
        }
        y=(60*i);
        x=(60*j);
        for(int k=0; k<limit; k++){
            for(int m=0; m<limit; m++){
                Random randa = new Random(); 
                Random randb = new Random(); 
                Random randc = new Random(); 
                a=randa.nextInt(random);
                b=randb.nextInt(random);
                c=randc.nextInt(random);
                g.setFill(Color.rgb(a,blue+b,green+c));   //random color
                g.fillRect(x+((60/limit)*k), y+((60/limit)*m), (60/limit), (60/limit));

              
            }
        }
    }

    
    
    //a method to draw the player sprite
    void drawPERSON (GraphicsContext g, int i, int j, int allegiance){
        int x=0, y=0;
        y=(60*i);
        x=(60*j);
        if(allegiance==2){
            g.drawImage(humans, x-20, y-20, 100, 100); //makes it so the image fits a square
        }
        if(allegiance==3){
            g.drawImage(aliens, x-20, y-20, 100, 100); //same as above
        }
       
    }

    //a method to draw the areas that a user can move a selected human to
    void drawSELECT (GraphicsContext g, int i, int j, int grd[][]){
        int x=0, y=0;
        y=(60*i);
        x=(60*j);
        g.drawImage(selectoutline, x-70, y-47, 280, 175); //makes it so the image fits a square as above
        drawMOVEBUBBLE(g, i, j, grd);
    }

    //removes the yellow select brackets initialized above in the drawSELECT method
    void removeSELECT (GraphicsContext g, int grd[][]){
        drawGRID(g, grd);
    }

    //a method to highlight the movement range of a player controlled human
    void drawMOVEBUBBLE(GraphicsContext g, int i, int j, int grd[][]){
        int k=0, l=0, x=0, y=0, temp=0;
        Movement mv = new Movement();
        grd=mv.calculateMovement(i, j, grd);  
        for(k=0; k<10; k++){
            for(l=0; l<15; l++){
                temp=grd[k][l];
                if(temp==4){
                    y=(60*k);
                    x=(60*l);
                    g.drawImage(redsquare, x-4, y-4, 68, 68);
                }
            }
        }     
    }
    
    
    //a method to remove the graphics showing the selected areas
    void undodrawMOVEBUBBLE (GraphicsContext g, int grd[][]){
        int l=0, k=0, temp=0;
        for(k=0; k<10; k++){
            for(l=0; l<15; l++){
                temp=grd[k][l];
                if(temp==4){
                    grd[k][l]=0;
                }
            }
        }
        drawGRID(g, grd);  
    }
    
 
}






