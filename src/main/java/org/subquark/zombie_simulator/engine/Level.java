package org.subquark.zombie_simulator.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import java.util.function.Consumer;
import java.util.function.BiConsumer;

public final class Level {
    private final List<Entity> entities;

    public Level() {
        entities = new ArrayList<Entity>();
    }

    public int entityCount() {
        return entities.size();
    }

    public void addEntity( Entity e ) {
        entities.add( Objects.requireNonNull( e ) );
    }

    public void forEachEntity( Consumer<Entity> operation ) {
        entities.forEach( operation::accept );
    }

    /**
     * @param x x of center of the target circle
     * @param y y of center of the target circle
     * @param r circle radius
     * @param handler parameters are entity and squared distance
     */
    public void entitiesAround( double x, double y, double r, BiConsumer<Entity,Double> handler ) {
        for ( Entity e : entities ) {
            double dX = e.x() - x;
            double dY = e.y() - y;
            // sqrt( dX*dX + dY*dY) < r squared
            double d = dX*dX + dY*dY;
            if ( d <= r*r ) {
                handler.accept( e, d );
            }
        }        
    }
}

