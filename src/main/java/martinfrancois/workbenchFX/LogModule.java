package martinfrancois.workbenchFX;

import com.dlsc.workbenchfx.model.WorkbenchModule;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.Node;
import javafx.scene.control.Label;


public class LogModule extends WorkbenchModule {
  public LogModule() {
    super("Logs", MaterialDesignIcon.SCRIPT); // A name and an icon is required
  }
  @Override
  public Node activate() {
    return new Label("All logs appear here"); // return here the actual content to display
  }
}
