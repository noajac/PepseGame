package pepse.world.trees;
import danogl.util.Vector2;
import pepse.world.Block;
import pepse.world.trees.tree_parts.*;
import java.util.ArrayList;
import java.util.List;

/**
 * tree represented by a trunk, a set of leaves and a set of fruits
 */
public class Tree {
    private final Trunk trunk;
    private final List<Leaf> leaves;
    private final List<Fruit> fruits;

    /**
     * creates a tree in a given position
     * @param pos tree base position
     */
    public Tree(Vector2 pos) {
        trunk = new Trunk(pos);
        int treeTopSize = (int) trunk.getDimensions().y() / Block.SIZE;
        Vector2 trunkTopMiddle = new Vector2(trunk.getCenter().x(), trunk.getTopLeftCorner().y());
        List<Vector2> treeTopPositions = treeTopPositions(trunkTopMiddle, treeTopSize);
        leaves = Leaves.create(treeTopPositions);
        fruits = Fruits.create(treeTopPositions);
    }

    /**
     * getter for the trunk of the tree
     * @return trunk
     */
    public Trunk getTrunk() {
        return trunk;
    }

    /**
     * getter for the set of leaves
     * @return leaves
     */
    public List<Leaf> getLeaves() {
        return leaves;
    }

    /**
     * getter for the set of fruits of the tree
     * @return the fruits
     */
    public List<Fruit> getFruits() {
        return fruits;
    }

    private static List<Vector2> treeTopPositions(Vector2 trunkTopMiddle, int treeTopSize) {
        ArrayList<Vector2> positions = new ArrayList<>();
        for (int row = -treeTopSize/2; row < treeTopSize/2 ; row++) {
            for (int col = -treeTopSize/2; col < treeTopSize/2 ; col++) {
                positions.add(trunkTopMiddle.add(new Vector2(row, col).mult(Block.SIZE)));
            }
        }
        return positions;
    }
}
