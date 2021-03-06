package sneakylabs.workbenchFX;

import com.dlsc.workbenchfx.model.WorkbenchModule;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.Node;
import javafx.scene.control.Label;
import sneakylabs.util.ViewMixin;

public class RuleModule extends WorkbenchModule implements ViewMixin {
  public RuleModule() {
    super("Rules", MaterialDesignIcon.VIEW_LIST); // A name and an icon is required
  }
  @Override
  public Node activate() {
    return new Label("Here we will define all the rules 👍"); // return here the actual content to display
  }

  @Override
  public void initializeControls() {

  }

  @Override
  public void layoutControls() {

  }
}
