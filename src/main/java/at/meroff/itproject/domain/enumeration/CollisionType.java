package at.meroff.itproject.domain.enumeration;

/**
 * The CollisionType enumeration.
 * For every type of collision a specific factor is associated
 * Information for this factors can be found in the general documentation
 */
public enum CollisionType {
    INST_INST(100.0), WIN_WIN(80.0), WIN_EZK(70.0), EZK_EZK(60.0), WIN_ZK(30.0), EZK_ZK(20.0), ZK_ZK(10.0);

    /**
     * Factor for the calculation of the collision value
     */
    private final double val;

    CollisionType(double v) { val = v; }

    /**
     * return the value assigned to a collision type
     * @return
     */
    public double getVal() { return val; }
}
