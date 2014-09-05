package org.subquark.zombie_simulator.engine;

import org.subquark.zombie_simulator.ai.Ai;
import org.subquark.zombie_simulator.ai.ZombieAi;
import org.subquark.zombie_simulator.ai.HumanAi;

public final class Entity {
    private boolean isZombie;
    private double x;
    private double y;
    private Ai ai;

    public Entity( double x, double y, boolean isZombie ) {
        this.x = x;
        this.y = y;
        this.isZombie = isZombie;
        if ( isZombie ) {
            this.ai = new ZombieAi( this );
        } else {
            this.ai = new HumanAi( this );
        }
    }

    public void think( Level l, double timeDeltaSeconds ) {
        this.ai.think( l, timeDeltaSeconds );
    }

    public void act( double timeDeltaSeconds ) {
        this.ai.act( timeDeltaSeconds );
    }

    public double x() {
        return x;
    }

    public void x( double newX ) {
        this.x = newX;
    }

    public double y() {
        return y;
    }

    public void y( double newY ) {
        this.y = newY;
    }

    public boolean isZombie() {
        return isZombie;
    }

    public void isZombie( boolean zombie ) {
        this.isZombie = zombie;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ( result << 31 ) + (int) x;
        result = ( result << 31 ) + (int) y;
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
