package blockfighter.server.entities;

/**
 * This is the interface of projectiles and attacks.
 * <p>
 * All projectiles/attacks must implement this interface so that the server will update these on each logic cycle.
 * </p>
 *
 * @author Ken
 */
public interface Projectile {

    /**
     * Set the owner of this projectile.
     *
     * @param owner The owning Player entity
     */
    public abstract void setOwner(Player owner);

    /**
     * Return the owner of this projectile.
     *
     * @return Owning Player entity
     */
    public abstract Player getOwner();

    /**
     * Update logic of this projectile.
     */
    public abstract void update();

    /**
     * Return the x position of this projectile.
     *
     * @return current x location in double
     */
    public abstract double getX();

    /**
     * Return the key of this projectile.
     *
     * @return Key value
     */
    public abstract int getKey();

    /**
     * Return the y position of this projectile.
     *
     * @return current y location in double
     */
    public abstract double getY();

    /**
     * Check if projectile expired(duration=0).
     *
     * @return True if duration <= 0
     */
    public abstract boolean isExpired();

    /**
     * Process any effects to be applied to players hit by this projectile.
     */
    public abstract void processQueue();
}
