package pepse.world.trees.tree_parts;
import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.AvatarJumpObserver;
import pepse.world.Block;
import java.awt.*;
import java.util.Random;

/**
 * creates trunk for a tree
 */
public class Trunk extends GameObject implements AvatarJumpObserver {
    /**
     * trunk tag
     */
    public static final String TRUNK_TAG = "trunk";
    private static final int MIN_HEIGHT = 5;
    private static final int MAX_HEIGHT = 12;

    /**
     * creates trunk in a given position
     * @param pos position of the bottom of the trunk
     */
    public Trunk(Vector2 pos) {
        super(Vector2.ZERO, randomHeightDimensions(), createRenderable());
        setTopLeftCorner(new Vector2(pos.x(), pos.y() - getDimensions().y()));
        setTag(TRUNK_TAG);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }

    /**
     * change color when avatar jumps
     */
    @Override
    public void onAvatarJump() {
        renderer().setRenderable(createRenderable());
    }

    private static Renderable createRenderable(){
        Color randomBrownColor = ColorSupplier.approximateColor(new Color(100, 50, 20));
        return new RectangleRenderable(randomBrownColor);
    }

    private static Vector2 randomHeightDimensions(){
        Random rand = new Random();
        int trunkHeight = Block.SIZE *
                (rand.nextInt(MAX_HEIGHT - MIN_HEIGHT) + MIN_HEIGHT);
        return new Vector2(Block.SIZE, trunkHeight);
    }
}
