package sneakylabs.workbenchFX;

import com.dlsc.workbenchfx.model.WorkbenchModule;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.Node;
import javafx.scene.control.Label;
import sneakylabs.util.ViewMixin;


public class SettingsModule extends WorkbenchModule implements ViewMixin {
  public SettingsModule() {
    super("Settings", MaterialDesignIcon.SETTINGS); // A name and an icon is required
  }
  @Override
  public Node activate() {
    return new Label("All settings belong here"); // return here the actual content to display
  }

  @Override
  public void initializeControls() {

  }

  @Override
  public void layoutControls() {

  }
}
