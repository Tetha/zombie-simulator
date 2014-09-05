package org.subquark.zombie_simulator.ai;

import org.subquark.zombie_simulator.engine.Level;
import org.subquark.zombie_simulator.engine.Entity;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class ZombieAi implements Ai {
    private Entity controlled;
    private List<Entity> nearbyHumans = new ArrayList<Entity>();
    private final int velocityPerSecond = 30;
    private static final Random r = new Random();

    public ZombieAi( Entity controlledEntity ) {
        this.controlled = controlledEntity;
    }

    @Override
    public void think( Level l, double timeDeltaSeconds ) {
        nearbyHumans.clear();
        l.entitiesAround( controlled.x(), controlled.y(), 50, (e2, d) -> {
            if ( e2.isHuman() ) {
                nearbyHumans.add( e2 );
            }
        });
    }

    @Override
    public void act( double timeDeltaSeconds ) {
        if ( nearbyHumans.isEmpty() ) {
            double xJitter = (r.nextInt( 20 ) - 10) * timeDeltaSeconds;
            double yJitter = (r.nextInt( 20 ) - 10) * timeDeltaSeconds;
            controlled.x( controlled.x() + xJitter );
            controlled.y( controlled.y() + yJitter );
            return;
        } else {
            int i = r.nextInt( nearbyHumans.size() );
            Entity target = nearbyHumans.get( i );

            double coeff = 1;

            double xJitter = (r.nextInt( 20 ) - 10) * timeDeltaSeconds;
            double yJitter = (r.nextInt( 20 ) - 10) * timeDeltaSeconds;

            double dx, dy;
            if ( target != null ) {
                dx = target.x() - controlled.x();
                dy = target.y() - controlled.y();
            } else {
                dx = 0;
                dy = 0;
            }
            double distance = Math.sqrt( dx*dx + dy*dy );
            if ( distance < 3 ) {
                target.isZombie( true );
            }
            controlled.x( controlled.x() 
                            + dx/distance * velocityPerSecond * timeDeltaSeconds );
            controlled.y( controlled.y() 
                          + dy/distance * velocityPerSecond * timeDeltaSeconds );

            if ( controlled.x() < 0 ) controlled.x( 0 );
            if ( 500 < controlled.x() ) controlled.x( 500 );
            if ( controlled.y() < 0 ) controlled.y( 0 );
            if ( 500 < controlled.y() ) controlled.y( 500 );
        }
    }
}
