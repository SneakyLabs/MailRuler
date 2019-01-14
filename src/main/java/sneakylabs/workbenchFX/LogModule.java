package sneakylabs.workbenchFX;

import com.dlsc.workbenchfx.model.WorkbenchModule;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.Node;
import javafx.scene.control.Label;
import sneakylabs.util.ViewMixin;


public class LogModule extends WorkbenchModule implements ViewMixin {
  public LogModule() {
    super("Logs", MaterialDesignIcon.SCRIPT); // A name and an icon is required
  }
  @Override
  public Node activate() {
    return new Label("All logs appear here"); // return here the actual content to display
  }

  @Override
  public void initializeControls() {

  }

  @Override
  public void layoutControls() {

  }
}
