package martinfrancois.workbenchFX;

import com.dlsc.workbenchfx.model.WorkbenchModule;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.Node;
import javafx.scene.control.Label;


public class SettingsModule extends WorkbenchModule {
  public SettingsModule() {
    super("Settings", MaterialDesignIcon.SETTINGS); // A name and an icon is required
  }
  @Override
  public Node activate() {
    return new Label("All settings belong here"); // return here the actual content to display
  }
}