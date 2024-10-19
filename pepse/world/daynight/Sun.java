package pepse.world.daynight;
import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.world.Terrain;
import java.awt.*;
import java.util.function.Consumer;

/**
 * Sun is represented by s game object
 * which moves around the middle of the horizon in a circle
 */
public class Sun {
    /**
     * sun tag
     */
    public static final String SUN_TAG = "sun";
    private static final Vector2 SUN_DIMENSIONS = new Vector2(80, 80);
    private static final float INITIAL_ANGLE = 0f;
    private static final float FINAL_ANGLE = 360f;

    /**
     *
     * @param windowDimensions the dimensions of the window
     * @param cycleLength the length of the circle movment cycle
     * @return game object representing Sun;
     */
    public static GameObject create(Vector2 windowDimensions,
                                    float cycleLength) {
        Renderable sunRenderable = new OvalRenderable(Color.YELLOW);
        GameObject sun = new GameObject(Vector2.ZERO, SUN_DIMENSIONS, sunRenderable);

        sun.setTag(SUN_TAG);
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        Vector2 initialSunCenter = skyCenter(windowDimensions);
        sun.setCenter(initialSunCenter);
        Vector2 cycleCenter = centerOfHorizon(windowDimensions);

        Consumer<Float> relocateSunAroundHorizon = (Float angle) ->
                sun.setCenter(initialSunCenter.subtract(cycleCenter).rotated(angle).add(cycleCenter));
        new Transition<Float>(sun,
                relocateSunAroundHorizon,
                INITIAL_ANGLE,
                FINAL_ANGLE,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                cycleLength,
                Transition.TransitionType.TRANSITION_LOOP,
                null);
        return sun;
    }

    private static Vector2 centerOfHorizon(Vector2 windowDimensions) {
        return new Vector2(windowDimensions.x()*0.5f,
                windowDimensions.y()*Terrain.HEIGHT_WINDOW_RATIO);
    }

    private static Vector2 skyCenter(Vector2 windowDimensions) {
        return new Vector2(windowDimensions.x()*0.5f,
                windowDimensions.y()*Terrain.HEIGHT_WINDOW_RATIO *0.5f);

    }
}
