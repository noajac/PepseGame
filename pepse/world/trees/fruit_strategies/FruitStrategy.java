package pepse.world.trees.fruit_strategies;

import danogl.GameObject;
import danogl.collisions.Collision;

/**
 * handles fruit colliding with avatar
 */
public interface FruitStrategy {
    /**
     * to be called when a collision occurs between fruit and avatar
     * @param other avatar game object
     * @param collision collision object
     */
    public void onCollisionEnter(GameObject other, Collision collision);
}
