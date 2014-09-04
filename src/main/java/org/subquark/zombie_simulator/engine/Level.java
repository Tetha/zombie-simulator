package org.subquark.zombie_simulator.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import java.util.function.Consumer;

public final class Level {
    private final List<Entity> entities;

    public Level() {
        entities = new ArrayList<Entity>();
    }

    public void addEntity( Entity e ) {
        entities.add( Objects.requireNonNull( e ) );
    }

    public void forEachEntity( Consumer<Entity> operation ) {
        entities.forEach( operation::accept );
    }
}

