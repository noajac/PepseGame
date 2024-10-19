package pepse.world;

/**
 * interface to be implemented by classes which are affected by
 * the avatar jumping.
 */
public interface AvatarJumpObserver {
    /**
     * implement this method to decide what happens to the object
     * when the avatar jumps.
     * add the implementing object to avatar's jump observers list
     * in order for this method to be activated and called every
     * time the avatar jumps.
     */
    public void onAvatarJump();
}
