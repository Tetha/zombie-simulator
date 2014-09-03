package org.subquark.zombie_simulator.engine;

public final class Entity {
    private final boolean isRed;
    private final int x;
    private final int y;

    public Entity( int x, int y, boolean isRed ) {
        this.x = x;
        this.y = y;
        this.isRed = isRed;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ( result << 31 ) + x;
        result = ( result << 31 ) + y;
        if ( isRed ) result = result * 2;
        return result;
    }

    @Override
    public boolean equals( Object o ) {
        if ( o == this ) return true;
        if ( o == null ) return false;
        if ( o.getClass() != Entity.class ) return false;

        Entity co = ( Entity ) o;
        return co.x == x && co.y == y && co.isRed == isRed;
    }

    @Override
    public String toString() {
        return String.format("Entity[x=%d,y=%d,isRed=%b]", x, y, isRed );
    }
}
