package martinfrancois;

import com.dlsc.workbenchfx.Workbench;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppStarter extends Application {

  private Workbench workbench;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    Scene myScene = new Scene(initWorkbench());

    primaryStage.setTitle("Simple WorkbenchFX Demo");
    primaryStage.setScene(myScene);
    primaryStage.setWidth(1000);
    primaryStage.setHeight(700);
    primaryStage.show();
    primaryStage.centerOnScreen();
  }

  private Workbench initWorkbench() {
    workbench = Workbench.builder(
    ).build();
    return workbench;
  }
}
