package pepse.world;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.util.Vector2;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * represents the pepse avatar
 */
public class Avatar extends GameObject {
    /**
     * avatar tag
     */
    public static final String AVATAR_TAG = "avatar";
    private static final Vector2 DIMENSIONS = new Vector2(60, 90);
    private static final float GRAVITY = 600;
    private static final float VELOCITY_X = 400;
    private static final float VELOCITY_Y = -650;
    private static final float MIN_ENERGY = 0f;
    private static final float MAX_ENERGY = 100f;
    private static final float INITIAL_ENERGY = MAX_ENERGY;
    private static final float RUN_ENERGY_LOSS = 0.5f;
    private static final float JUMP_ENERGY_LOSS = 10f;
    private static final float IDLE_ENERGY_GAIN = 1f;

    private final UserInputListener inputListener;
    private float energy;
    private AvatarMotionState state = AvatarMotionState.IDLE;
    private final AvatarAnimations animations;
    private final ArrayList<AvatarJumpObserver> jumpObservers;

    /**
     * creates a new avatar object
     * @param pos wanted position
     * @param inputListener inputListener
     * @param imageReader imageReader
     */
    public Avatar(Vector2 pos,
                  UserInputListener inputListener,
                  ImageReader imageReader) {
        super(pos.subtract(DIMENSIONS),
                DIMENSIONS,
                null);
        this.animations = new AvatarAnimations(imageReader);
        this.inputListener = inputListener;
        this.energy = INITIAL_ENERGY;
        this.jumpObservers = new ArrayList<>();
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        setTag(AVATAR_TAG);
    }

    /**
     * checks pressed key and runs or jumps accordingly.
     * updates avatar state, animation and energy according to state.
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        runIfKeyPressed();
        jumpIfKeyPressed();
        updateState();
        updateEnergy();
        updateAnimation();
    }

    /**
     * adds energy to the avatar, not exceeding 0 or 100
     * @param energyAdd energy to add
     */
    public void addEnergy(float energyAdd) {
        float newEnergy = energyAdd + energy;
        energy = Math.max(Math.min(newEnergy, MAX_ENERGY),MIN_ENERGY);
    }

    /**
     * getter for avatar's energy
     * @return energy
     */
    public float getEnergy() {
        return energy;
    }

    /**
     * adds a new jump observer which will be activated whenever the
     * avatar jumps.
     * @param observer observer to add
     */
    public void addObserver(AvatarJumpObserver observer) {
        jumpObservers.add(observer);
    }

    private void runIfKeyPressed() {
        float xVel = 0;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT) &&
                energy >= RUN_ENERGY_LOSS) {
            xVel -= VELOCITY_X;
        }
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT) &&
                energy >= RUN_ENERGY_LOSS) {
            xVel += VELOCITY_X;
        }
        transform().setVelocityX(xVel);
    }

    private void jumpIfKeyPressed() {
        if(inputListener.isKeyPressed(KeyEvent.VK_SPACE) &&
                getVelocity().y() == 0 &&
                energy >= JUMP_ENERGY_LOSS) {
            transform().setVelocityY(VELOCITY_Y);
            addEnergy(-1*JUMP_ENERGY_LOSS);
            activateJumpObservers();
        }
    }

    private void activateJumpObservers() {
        for (AvatarJumpObserver observer : jumpObservers) {
            observer.onAvatarJump();
        }
    }

    private void updateState() {
        //check idle
        if (getVelocity().y() == 0) {
            state = AvatarMotionState.IDLE;
        }
        //check jump
        else {
            state = AvatarMotionState.JUMP;
        }
        //check run
        if (getVelocity().x() != 0) {
            state = AvatarMotionState.RUN;
        }
    }

    private void updateEnergy() {
        if (state == AvatarMotionState.IDLE){
            addEnergy(IDLE_ENERGY_GAIN);
        }
        else if (state == AvatarMotionState.RUN){
            addEnergy(-1*RUN_ENERGY_LOSS);
        }
    }

    private void updateAnimation() {
        renderer().setRenderable(animations.get(state));
        renderer().setIsFlippedHorizontally(false);
        if (getVelocity().x() < 0) {
            renderer().setIsFlippedHorizontally(true);
        }
    }
}
