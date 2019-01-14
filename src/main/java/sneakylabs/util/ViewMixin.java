package sneakylabs.util;

public interface ViewMixin {

  /**
   * Calls method to initialize the view.
   */
  default void init() {
    initializeControls();
    layoutControls();
    addEventHandlers();
    addValueChangedListeners();
    addBindings();
  }

  /**
   * Initializes all the controls used in this view (labels, buttons, ...).
   */
  void initializeControls();

  /**
   * Defines how the controls are put together, in which order and with what attributes.
   */
  void layoutControls();

  /**
   * Ensures the reaction for an action.
   */
  default void addEventHandlers() {
  }

  /**
   * Ensures that components get updated, when a value changes.
   */
  default void addValueChangedListeners() {
  }

  /**
   * Binds properties to the presentation model.
   */
  default void addBindings() {
  }
}
