package pepse.world;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * represents a block of ground in the pepse game
 */
public class Block extends GameObject {
    /**
     * block size, also used for deciding the leaf size and avatar size.
     */
    public static final int SIZE = 30;

    /**
     * creates a new block object
     * @param topLeftCorner top left corner of the desired block
     * @param renderable block image
     */
    public Block(Vector2 topLeftCorner, Renderable renderable) {
        super(topLeftCorner, Vector2.ONES.mult(SIZE), renderable);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }
}

