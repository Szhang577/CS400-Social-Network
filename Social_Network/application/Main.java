/**
 * 
 */
package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Siyuan
 *
 */
public class Main extends Application {
  
  private static final double WINDOW_WIDTH = 1200;
  private static final double WINDOW_HEIGHT = 800;
  private static final String APP_TITLE = "Weibo Social Network";
  
  public SocialNetwork socialNetwork;
  public VBox graphBox;
  public VBox statusBox;
  public VBox basicMenuBox;
  public VBox advanceMenuBox;
  
  private void setUpgraphBox() {
    
  }
  
  private void setUpstatusBox() {
    
  }
  
  private void setUpBsicMenuBox() {
    
  }
  
  private void setUpAdvanceMenuBox() {
    
  }
  
  private void drawGraph(GraphicsContext graph) {
    
  }
  
  private void drawNode(GraphicsContext graph, String name, double x, double y) {
    
  }
  
  private void drawEdge(GraphicsContext graph, double x1, double y1, double x2, double y2) {
    
  }
  
  private String getNameFromCoordinates(double x1, double x2) {
    return null;
    
  }
  
  private void setSelectedUser(String person) {
    
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    // TODO Auto-generated method stub
    BorderPane pane = new BorderPane();
    Scene scene = new Scene(pane, WINDOW_WIDTH, WINDOW_HEIGHT);
    
    primaryStage.setTitle(APP_TITLE);
    primaryStage.setScene(scene);
    primaryStage.show();
    
  }

  
  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    launch(args);
  }

}
