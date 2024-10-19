package pepse.world.trees.fruit_strategies;
import danogl.GameObject;
import danogl.collisions.Collision;
import java.util.function.Consumer;

/**
 * strategy to raise the energy of avatar upon colliding with the fruit
 */
public class RaiseEnergyStrategy implements FruitStrategy {
    private static final float ENERGY_ADD = 10;
    private final Consumer<Float> energyAdder;

    /**
     * creates a RaiseEnergyStrategy
     * @param energyAdder avatar adding energy method
     */
    public RaiseEnergyStrategy(Consumer<Float> energyAdder) {
        this.energyAdder = energyAdder;
    }

    /**
     * adds to the avatar energy
     * @param other avatar game object
     * @param collision collision object
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        energyAdder.accept(ENERGY_ADD);
    }
}
