package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.awt.*;

/**
 * Sun Halo represented by a game object which is linked to the sun
 * using component feature.
 */
public class SunHalo {
    /**
     * sun halo tag
     */
    public static final String SUN_HALO_TAG = "sunHalo";
    private static final Color HALO_COLOR = new Color(255, 255, 0, 20);
    private static final float HALO_SUN_RATIO = 1.5f;

    /**
     * creates a new Sun halo game object
     * @param sun game object to be linked to sun halo through component
     * @return new sun halo game object
     */
    public static GameObject create(GameObject sun) {
        Renderable sunHaloRenderable = new OvalRenderable(HALO_COLOR);
        GameObject sunHalo = new GameObject(Vector2.ZERO,
                sun.getDimensions().mult(HALO_SUN_RATIO),
                sunHaloRenderable);
        sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sunHalo.setTag(SUN_HALO_TAG);
        sunHalo.addComponent((deltaTime -> sunHalo.setCenter(sun.getCenter())));
        return sunHalo;
    }
}
