package pepse.world;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.NoiseGenerator;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * responsible for creating the ground of the pepse game.
 */
public class Terrain {
    /**
     * ground tag.
     */
    public static final String GROUND_BLOCK_TAG = "ground";
    /**
     * constant representing the ratio between the ground height
     * at x0 and the window height.
     */
    public static final float HEIGHT_WINDOW_RATIO = 2f/3;

    private static final int GROUND_HEIGHT_FACTOR = 7;
    private static final int TERRAIN_DEPTH = 20;
    private static final Color BASE_GROUND_COLOR =
            new Color(212, 123, 74);


    private final int groundHeightAtX0;
    private final NoiseGenerator noiseGenerator;

    /**
     * creates a new Terrain object, to be later used for creating
     * ground blocks.
     * @param windowDimensions window dimensions
     * @param seed in order to create the ground heights randomly.
     */
    public Terrain(Vector2 windowDimensions, int seed) {
        groundHeightAtX0 = (int) (windowDimensions.y() * HEIGHT_WINDOW_RATIO);
        noiseGenerator = new NoiseGenerator(seed, groundHeightAtX0);
    }

    /**
     * creates terrain made of ground blocks with random but continuous
     * heights at different x values.
     * @param minX leftmost bound to create terrain.
     * @param maxX rightmost bound to create terrain
     * @return list of ground block to be added to the game objects.
     */
    public List<Block> createInRange(int minX, int maxX) {
        List<Block> blocks = new ArrayList<>();
        int maxXColumn = roundBlockSizeDown(maxX);
        int minXColumn = roundBlockSizeDown(minX);
        for (int x = minXColumn; x <= maxXColumn; x+=Block.SIZE) {
            int columnHeight = roundBlockSizeDown(groundHeightAt(x));
            for (int depth = 0; depth < TERRAIN_DEPTH; depth++) {
                Renderable blockRenderable = new RectangleRenderable(
                        ColorSupplier.approximateColor(BASE_GROUND_COLOR));
                Vector2 coordinates = new Vector2(x, columnHeight+depth*Block.SIZE);
                Block block = createBlock(coordinates, blockRenderable);
                 blocks.add(block);
            }
        }
        return blocks;
    }

    /**
     * calculates the ground height at a given x-value,
     * chosen randomly using NoiseGenerator.
     * use roundBlockSizeDown to get the exact height of the ground.
     * @param x x-axis value
     * @return the ground y-value
     */
    public float groundHeightAt(float x) {
        float noise = (float) noiseGenerator.noise(x,
                Block.SIZE * GROUND_HEIGHT_FACTOR);
        return groundHeightAtX0 + noise;
    }

    /**
     * rounds a value down so that the rounded number will
     * divide by block size.
     * @param coordinate value to round down
     * @return int value rounded down.
     */
    public static int roundBlockSizeDown(float coordinate) {
        return (int) Math.floor(coordinate/Block.SIZE) * Block.SIZE;
    }

    private Block createBlock(Vector2 topLeftCorner, Renderable blockRenderable) {
        Block block = new Block(topLeftCorner, blockRenderable);
        block.setTag(GROUND_BLOCK_TAG);
        return block;
    }
}
