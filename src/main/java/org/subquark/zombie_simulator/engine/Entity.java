package org.subquark.zombie_simulator.engine;

public final class Entity {
    private final boolean isZombie;
    private final int x;
    private final int y;

    public Entity( int x, int y, boolean isZombie ) {
        this.x = x;
        this.y = y;
        this.isZombie = isZombie;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public boolean isZombie() {
        return isZombie;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ( result << 31 ) + x;
        result = ( result << 31 ) + y;
        if ( isZombie ) result = result * 2;
        return result;
    }

    @Override
    public boolean equals( Object o ) {
        if ( o == this ) return true;
        if ( o == null ) return false;
        if ( o.getClass() != Entity.class ) return false;

        Entity co = ( Entity ) o;
        return co.x == x && co.y == y && co.isZombie == isZombie;
    }

    @Override
    public String toString() {
        return String.format("Entity[x=%d,y=%d,isZombie=%b]", x, y, isZombie );
    }
}
