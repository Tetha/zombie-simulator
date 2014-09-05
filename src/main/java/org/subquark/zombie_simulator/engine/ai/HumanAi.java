package org.subquark.zombie_simulator.ai;

import org.subquark.zombie_simulator.engine.Level;
import org.subquark.zombie_simulator.engine.Entity;
import java.util.Random;

public class HumanAi implements Ai {
    private Entity controlled;
    private Entity closest;
    private final int velocityPerSecond = 50;
    private static final Random r = new Random();

    public HumanAi( Entity controlledEntity ) {
        this.controlled = controlledEntity;
    }

    @Override
    public void think( Level l, double timeDeltaSeconds ) {
        closest = null;
        l.entitiesAround( controlled.x(), controlled.y(), 50, (e2, d) -> {
            if ( controlled == e2 ) return;
            if ( closest != null ) {
                double dx = controlled.x() - closest.x();
                double dy = controlled.y() - closest.y();

                double minDSquared = dx * dx + dy * dy;
                if ( d < minDSquared ) {
                    closest = e2;
                }
            } else {
                closest = e2;
            }
        });
    }

    @Override
    public void act( double timeDeltaSeconds ) {
        if ( closest == null ) return;

        double coeff = 0;
        if ( controlled.isZombie() != closest.isZombie() ) {
            coeff = -1;
        }

        double xJitter = (r.nextInt( 10 ) - 5) * timeDeltaSeconds;
        double yJitter = (r.nextInt( 10 ) - 5) * timeDeltaSeconds;

        double dx = closest.x() - controlled.x();
        double dy = closest.y() - controlled.y();
        double distance = Math.sqrt( dx*dx + dy*dy );
        controlled.x( controlled.x() 
                        + dx/distance * velocityPerSecond * coeff * timeDeltaSeconds 
                        + xJitter );
        controlled.y( controlled.y() 
                      + dy/distance * velocityPerSecond * coeff * timeDeltaSeconds
                      + yJitter );

        if ( controlled.x() < 0 ) controlled.x( 0 );
        if ( 500 < controlled.x() ) controlled.x( 500 );
        if ( controlled.y() < 0 ) controlled.y( 0 );
        if ( 500 < controlled.y() ) controlled.y( 500 );
    }
}
