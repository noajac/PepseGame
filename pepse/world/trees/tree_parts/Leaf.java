package pepse.world.trees.tree_parts;
import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.world.AvatarJumpObserver;
import java.util.Random;
import java.util.function.Consumer;

/**
 * leaf which moves and grows according to wind
 * and turns 90 degrees when avatar jumps
 */
public class Leaf extends GameObject implements AvatarJumpObserver {
    private static final float WIDTH_GROWTH_FACTOR = 1.3f;
    private static final float WIND_ANGLE_CHANGE = 20f;
    private static final int WIND_TRANSITION_TIME = 2;
    private static final int WAIT_TIME_INT_BOUND = 3;
    private static final float ANGLE_CHANGE_ON_JUMP = 90f;
    private static final float JUMP_TRANSITION_TIME = 0.3f;
    private static final int FULL_CIRCLE = 360;
    private float angleChangeFromJump = 0f;

    /**
     * Construct a leaf instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Leaf(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        addDelayedWidthTransition();
        addDelayedAngleTransition();
    }

    /**
     * turns 90 degrees when the avatar jumps
     */
    @Override
    public void onAvatarJump() {
        new Transition<>(this,
                this::addAngle,
                0f,
                ANGLE_CHANGE_ON_JUMP,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                JUMP_TRANSITION_TIME,
                Transition.TransitionType.TRANSITION_ONCE,
                () -> angleChangeFromJump += ANGLE_CHANGE_ON_JUMP);
    }

    private void addAngle(float angle) {
        float newAngle = (renderer().getRenderableAngle() + angle) % FULL_CIRCLE;
        renderer().setRenderableAngle(newAngle);
    }

    private void addDelayedWidthTransition() {
        Random random = new Random();
        float widthWaitTime = random.nextFloat() + random.nextInt(WAIT_TIME_INT_BOUND);
        new ScheduledTask(this,
                widthWaitTime,
                false,
                this::createWidthTransition);
    }

    private void addDelayedAngleTransition() {
        Random random = new Random();
        float angleWaitTime = random.nextFloat() + random.nextInt(WAIT_TIME_INT_BOUND);
        new ScheduledTask(this,
                angleWaitTime,
                false,
                this::createAngleTransition);
    }

    private void createWidthTransition() {
        Consumer<Float> changeLeafWidth = width ->
                setDimensions(new Vector2(width, getDimensions().y()));
        new Transition<>(this,
                changeLeafWidth,
                getDimensions().x(),
                getDimensions().x() * WIDTH_GROWTH_FACTOR,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                WIND_TRANSITION_TIME,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);
    }
    private void createAngleTransition() {
        Consumer<Float> changeAngle = angle ->
                renderer().setRenderableAngle(angle + angleChangeFromJump% FULL_CIRCLE);
        new Transition<>(this,
                changeAngle,
                0f,
                WIND_ANGLE_CHANGE,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                WIND_TRANSITION_TIME,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);
    }
}
