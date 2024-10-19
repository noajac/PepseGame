package pepse.world.trees.tree_parts;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;
import pepse.util.CoinToss;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * creates multiple leaves in random places in the tree top
 */
public class Leaves {
    /**
     * leaf tag
     */
    public static final String LEAF_TAG = "leaf";
    private static final float LEAVES_FREQUENCY = 0.6f;
    private static final Color LEAF_COLOR = new Color(50, 200, 30);

    /**
     * creates leaves in random places from the given list of tree top positions
     * @param treeTopPositions given all possible tree top positions
     * @return a list of leaves in the tree top
     */
    public static List<Leaf> create(List<Vector2> treeTopPositions) {
        ArrayList<Leaf> leaves = new ArrayList<>();
        for (Vector2 pos : treeTopPositions) {
            if (!CoinToss.toss(LEAVES_FREQUENCY)) {
                continue;
            }
            Color Color = ColorSupplier.approximateColor(LEAF_COLOR);
            RectangleRenderable leafRenderable = new RectangleRenderable(Color);
            Leaf leaf = new Leaf(pos,
                    new Vector2(Block.SIZE,Block.SIZE),
                    leafRenderable);
            leaf.setTag(LEAF_TAG);
            leaves.add(leaf);
        }
        return leaves;
    }
}
