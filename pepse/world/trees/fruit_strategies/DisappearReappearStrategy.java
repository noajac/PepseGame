package pepse.world.trees.fruit_strategies;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import pepse.PepseGameManager;

/**
 * Strategy for when fruit collides with avatar
 * removes the fruit and returns the fruit to the game after one day cycle.
 */
public class DisappearReappearStrategy implements FruitStrategy {
    private final Runnable fruitRemover;
    private final Runnable fruitAdder;

    /**
     * creates a new DissapearReappearStrategy instance
     * @param fruitRemover Runnable to remove the fruit from the game objects
     * @param fruitAdder Runnable to add the fruit to the game objects
     */
    public DisappearReappearStrategy(Runnable fruitRemover, Runnable fruitAdder) {
        this.fruitRemover = fruitRemover;
        this.fruitAdder = fruitAdder;
    }

    /**
     * removes the fruit and adds it after a day cycle
     * @param other colliding object
     * @param collision collision object
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        fruitRemover.run();
        new ScheduledTask(other,
                PepseGameManager.DAY_NIGHT_CYCLE_LENGTH,
                false,
                fruitAdder);
    }
}
