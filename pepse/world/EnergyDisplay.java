package pepse.world;
import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

/**
 * displays the energy of the avatar on the screen. to be added to UI layer.
 */
public class EnergyDisplay {
    /**
     * energy display tag.
     */
    public static final String ENERGY_DISPLAY_TAG = "energyDisplay";
    private static final String DISPLAY_TEXT_FORMAT = "Energy: %.1f";
    private static final Vector2 DIMENSIONS = new Vector2(200, 30);
    private static final Vector2 TOP_LEFT_CORNER = Vector2.ZERO;
    private static final String FONT = "Courier New";

    /**
     * creates a new energy display GameObject.
     * @param avatar avatar to display its energy.
     * @return GameObject object
     */
    public static GameObject create(Avatar avatar) {
        TextRenderable textRenderable = new TextRenderable("", FONT);

        GameObject energyDisplay =
                new GameObject(TOP_LEFT_CORNER,
                DIMENSIONS,
                textRenderable);
        energyDisplay.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        energyDisplay.setTag(ENERGY_DISPLAY_TAG);
        energyDisplay.addComponent(deltaTime -> updateDisplay(textRenderable,
                avatar.getEnergy()));
        return energyDisplay;
    }
    private static void updateDisplay(TextRenderable textRenderable, Float energy) {
        textRenderable.setString(String.format(DISPLAY_TEXT_FORMAT, energy));
    }
}
