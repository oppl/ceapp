package at.meroff.itproject.domain.enumeration;

/**
 * The CollisionType enumeration.
 */
public enum CollisionType {
    INST_INST(100.0), WIN_WIN(80.0), WIN_EZK(70.0), EZK_EZK(60.0), WIN_ZK(30.0), EZK_ZK(20.0), ZK_ZK(10.0);

    private final double val;
    private CollisionType(double v) { val = v; }
    public double getVal() { return val; }
}
