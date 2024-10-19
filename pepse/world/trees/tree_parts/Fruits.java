package pepse.world.trees.tree_parts;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;
import pepse.util.CoinToss;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * class for creating fruits in random places
 */
public class Fruits {
    /**
     * fruit tag
     */
    public static final String FRUIT_TAG = "fruit";
    private static final float FRUITS_FREQUENCY = 0.03f;
    private static final Color INITIAL_FRUIT_COLOR = new Color(245, 3, 35);


    /**
     * creates fruits on random positions from tree top positions
     * @param treeTopPositions all possible fruit positions
     * @return list of fruits in the tree top
     */
    public static List<Fruit> create(List<Vector2> treeTopPositions) {
        ArrayList<Fruit> fruits = new ArrayList<>();
        for (Vector2 pos : treeTopPositions) {
            if (!CoinToss.toss(FRUITS_FREQUENCY)) {
                continue;
            }
            Color redColor = ColorSupplier.approximateColor(INITIAL_FRUIT_COLOR);
            OvalRenderable fruitRenderable = new OvalRenderable(redColor);
            Fruit fruit = new Fruit(pos,
                    new Vector2(Block.SIZE,Block.SIZE),
                    fruitRenderable);
            fruit.setTag(FRUIT_TAG);
            fruits.add(fruit);
        }
        return fruits;
    }
}
