package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import pepse.world.*;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.*;
import pepse.world.trees.fruit_strategies.DisappearReappearStrategy;
import pepse.world.trees.fruit_strategies.FruitStrategy;
import pepse.world.trees.fruit_strategies.RaiseEnergyStrategy;
import pepse.world.trees.tree_parts.Fruit;
import pepse.world.trees.tree_parts.Leaf;

import java.util.List;

/**
 * the main manager for the pepse game. responsible for creating
 * the game objects and running the game.
 */

public class PepseGameManager extends GameManager {
    /**
     * constant for the duration of the day-night cycle.
     */
    public static final int DAY_NIGHT_CYCLE_LENGTH = 30;
    private static final int SEED = 7;

    /**
     * creates all the game objects and gets the game ready to run
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     *                 See its documentation for help.
     * @param soundReader Contains a single method: readSound, which reads a wav file from
     *                    disk. See its documentation for help.
     * @param inputListener Contains a single method: isKeyPressed, which returns whether
     *                      a given key is currently pressed by the user or not. See its
     *                      documentation.
     * @param windowController Contains an array of helpful, self-explanatory methods
     *                         concerning the window.
     */
    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader,
                soundReader,
                inputListener,
                windowController);
        Vector2 windowDimensions = windowController.getWindowDimensions();
        // create sky
        GameObject sky = Sky.create(windowDimensions);
        gameObjects().addGameObject(sky, Layer.BACKGROUND);
        // create terrain
        Terrain terrain = new Terrain(windowDimensions, SEED);
        List<Block> blocks = terrain.createInRange(0, (int) windowDimensions.x());
        for (Block block : blocks) {
            gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
        }
        // create night
        GameObject night = Night.create(windowDimensions, DAY_NIGHT_CYCLE_LENGTH);
        gameObjects().addGameObject(night, Layer.FOREGROUND);
        // create sun
        GameObject sun = Sun.create(windowDimensions, DAY_NIGHT_CYCLE_LENGTH);
        gameObjects().addGameObject(sun, Layer.BACKGROUND);
        // create sunHalo
        GameObject sunHalo = SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo, Layer.BACKGROUND);
        // create flora
        Flora flora = new Flora(terrain::groundHeightAt);
        List<Tree> trees = flora.createInRange(0, (int) windowDimensions.x());
        addTreesObjects(trees);
        // create avatar
        float initialX = windowDimensions.x() * 0.5f;
        float initialY = terrain.groundHeightAt(initialX);
        Avatar avatar = new Avatar(new Vector2(initialX,initialY),inputListener,imageReader);
        gameObjects().addGameObject(avatar, Layer.DEFAULT);
        // create energyDisplay
        GameObject energyDisplay = EnergyDisplay.create(avatar);
        gameObjects().addGameObject(energyDisplay, Layer.UI);
        // set fruit strategies
        setFruitsStrategies(trees, avatar);
        // set avatar jump observers
        setJumpObservers(trees, avatar);
    }

    private void addTreesObjects(List<Tree> trees) {
        for (Tree tree : trees) {
            // add trunk
            gameObjects().addGameObject(tree.getTrunk(), Layer.DEFAULT);
            // add leaves
            for (Leaf leaf : tree.getLeaves()) {
                gameObjects().addGameObject(leaf, Layer.DEFAULT);
            }
            // add fruits
            for (Fruit fruit : tree.getFruits()) {
                gameObjects().addGameObject(fruit, Layer.DEFAULT);
            }
        }
    }

    private void setFruitsStrategies(List<Tree> trees, Avatar avatar) {
        for (Tree tree : trees) {
            for (Fruit fruit : tree.getFruits()) {
                fruit.addStrategy(createDissapearStrategy(fruit));
                fruit.addStrategy(createRaiseEnergyStrategy(avatar));
            }
        }
    }

    private FruitStrategy createRaiseEnergyStrategy(Avatar avatar) {
        return new RaiseEnergyStrategy(avatar::addEnergy);
    }

    private FruitStrategy createDissapearStrategy(Fruit fruit) {
        Runnable fruitRemover = () ->
                gameObjects().removeGameObject(fruit, Layer.DEFAULT);
        Runnable fruitAdder = () ->
                gameObjects().addGameObject(fruit, Layer.DEFAULT);
        return new DisappearReappearStrategy(fruitRemover, fruitAdder);
    }

    private void setJumpObservers(List<Tree> trees, Avatar avatar) {
        for (Tree tree : trees) {
            avatar.addObserver(tree.getTrunk());
            for (Fruit fruit : tree.getFruits()){
                avatar.addObserver(fruit);
            }
            for (Leaf leaf : tree.getLeaves()) {
                avatar.addObserver(leaf);
            }
        }
    }

    /**
     * program's main function
     * @param args user arguments
     */
    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}
