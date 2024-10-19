package pepse.world.trees.tree_parts;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Avatar;
import pepse.world.AvatarJumpObserver;
import pepse.world.trees.fruit_strategies.FruitStrategy;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * fruit has strategies to handle collisions with the avatar
 * and changes color according to the avatar's jumping
 */
public class Fruit extends GameObject implements AvatarJumpObserver {
    private final ArrayList<FruitStrategy> strategies;
    private static final Color[] COLOR_LIST = new Color[]
            {new Color(229, 3, 245),
                    new Color(7, 3, 245),
                    new Color(245, 3, 35)};

    /**
     * Construct a new fruit instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Fruit(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        strategies = new ArrayList<>();
    }

    /**
     * adds a strategy to the fruit strategy collection
     * strategy will be activated when the fruit collides with the avatar
     * @param strategy to add
     */
    public void addStrategy(FruitStrategy strategy) {
        strategies.add(strategy);
    }

    /**
     * if other is avatar applies all strategies
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (!other.getTag().equals(Avatar.AVATAR_TAG)) {
            return;
        }
        for (FruitStrategy strategy : strategies) {
            strategy.onCollisionEnter(other, collision);
        }
    }

    /**
     * randomly chooses a color from the color list
     * and changes the fruit color.
     * to be called when the avatar jumps.
     */
    @Override
    public void onAvatarJump() {
        Random random = new Random();
        int colorIndex = random.nextInt(COLOR_LIST.length);
        Color color = ColorSupplier.approximateColor(COLOR_LIST[colorIndex]);
        OvalRenderable renderable = new OvalRenderable(color);
        renderer().setRenderable(renderable);
    }
}
