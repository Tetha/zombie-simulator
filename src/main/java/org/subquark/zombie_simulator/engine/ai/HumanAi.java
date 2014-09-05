package org.subquark.zombie_simulator.ai;

import org.subquark.zombie_simulator.engine.Level;
import org.subquark.zombie_simulator.engine.Entity;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class HumanAi implements Ai {
    private Entity controlled;
    private List<Entity> nearbyZombies = new ArrayList<Entity>();
    private final int velocityPerSecond = 50;
    private static final Random r = new Random();

    public HumanAi( Entity controlledEntity ) {
        this.controlled = controlledEntity;
    }

    @Override
    public void think( Level l, double timeDeltaSeconds ) {
        nearbyZombies.clear();
        l.entitiesAround( controlled.x(), controlled.y(), 50, (e2, d) -> {
            if ( e2.isZombie() ) {
                nearbyZombies.add( e2 );
            }
        });
    }

    @Override
    public void act( double timeDeltaSeconds ) {
        double dx = nearbyZombies.stream().mapToDouble( e -> e.x() - controlled.x() ).sum();
        double dy = nearbyZombies.stream().mapToDouble( e -> e.y() - controlled.y() ).sum();

        if ( dx > 0 || dy > 0 ) {
            double distance = Math.sqrt( dx*dx + dy*dy );
            controlled.x( controlled.x() 
                            - dx/distance * velocityPerSecond * timeDeltaSeconds );
            controlled.y( controlled.y() 
                          - dy/distance * velocityPerSecond * timeDeltaSeconds );
        }
        if ( controlled.x() < 0 ) controlled.x( 0 );
        if ( 500 < controlled.x() ) controlled.x( 500 );
        if ( controlled.y() < 0 ) controlled.y( 0 );
        if ( 500 < controlled.y() ) controlled.y( 500 );
    }
}
