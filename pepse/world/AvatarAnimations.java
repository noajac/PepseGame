package pepse.world;
import danogl.gui.ImageReader;
import danogl.gui.rendering.AnimationRenderable;

/**
 * AvatarAnimation creates and hold animation renderables for each
 * avatar state: run, idle and jump. animations are loaded when
 * constructor is called, then can be quickly accessed in every
 * change of state using the animation getter.
 */
public class AvatarAnimations {
    private static final double TIME_BETWEEN_CLIPS = 0.1;

    private static final String[] IDLE_IMAGES =
            {"assets/idle_0.png",
                    "assets/idle_1.png",
                    "assets/idle_2.png",
                    "assets/idle_3.png"};
    private static final String[] JUMP_IMAGES =
            {"assets/jump_0.png",
                    "assets/jump_1.png",
                    "assets/jump_2.png",
                    "assets/jump_3.png"};
    private static final String[] RUN_IMAGES =
            {"assets/run_0.png",
                    "assets/run_1.png",
                    "assets/run_2.png",
                    "assets/run_3.png",
                    "assets/run_4.png",
                    "assets/run_5.png"};

    private final AnimationRenderable idleAnimation;
    private final AnimationRenderable runAnimation;
    private final AnimationRenderable jumpAnimation;

    /**
     * creates a new AvatarAnimations instance, loading all avatar animations
     * and getting them ready to be used efficiently.
     * @param imageReader image reader to load the animations
     */
    AvatarAnimations(ImageReader imageReader) {
        idleAnimation = new AnimationRenderable(IDLE_IMAGES,
                imageReader,
                true,
                TIME_BETWEEN_CLIPS);
        runAnimation = new AnimationRenderable(RUN_IMAGES,
                imageReader,
                true,
                TIME_BETWEEN_CLIPS);
        jumpAnimation = new AnimationRenderable(JUMP_IMAGES,
                imageReader,
                true,
                TIME_BETWEEN_CLIPS);
    }

    /**
     * gets an already-loaded animation renderable object according to the
     * given avatar state.
     * @param state: IDLE, RUN or JUMP
     * @return animation renderable.
     */
    public AnimationRenderable get(AvatarMotionState state) {
        switch (state) {
            case IDLE:
                return idleAnimation;
            case RUN:
                return runAnimation;
            case JUMP:
                return jumpAnimation;
        }
        return null;
    }
}
