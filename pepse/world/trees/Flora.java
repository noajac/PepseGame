package pepse.world.trees;
import danogl.util.Vector2;
import pepse.util.CoinToss;
import pepse.world.Block;
import pepse.world.Terrain;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * class for creating a list of trees in random places
 */
public class Flora {
    private static final float TREES_FREQUENCY = 0.1f;
    private final Function<Float, Float> treeBaseHeightAt;

    /**
     * creates a new flora instance
     * @param treeBaseHeightAt method to determine the wanted
     * height of tree root according to its x value
     */
    public Flora(Function<Float, Float> treeBaseHeightAt) {
        this.treeBaseHeightAt = treeBaseHeightAt;
    }

    /**
     * creates trees in random places in a given range
     * @param minX left bound
     * @param maxX right bound
     * @return a list of trees
     */
    public List<Tree> createInRange(int minX, int maxX) {
        ArrayList<Tree> trees = new ArrayList<Tree>();
        int maxXColumn = Terrain.roundBlockSizeDown(maxX);
        int minXColumn = Terrain.roundBlockSizeDown(minX);
        boolean isLastColTreed = false;
        for (int x = minXColumn; x <= maxXColumn; x+= Block.SIZE) {
            if (!CoinToss.toss(TREES_FREQUENCY) || isLastColTreed) {
                isLastColTreed = false;
                continue;
            }
            isLastColTreed = true;
            int baseHeight = Terrain.roundBlockSizeDown(
                    treeBaseHeightAt.apply((float) x));
            Vector2 position = new Vector2(x, baseHeight);
            Tree tree = new Tree(position);
            trees.add(tree);
        }
    return trees;
    }
}
