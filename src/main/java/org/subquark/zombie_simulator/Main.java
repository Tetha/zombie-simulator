package org.subquark.zombie_simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import org.subquark.zombie_simulator.engine.Entity;
import org.subquark.zombie_simulator.engine.Level;

public final class Main {
    private static final Logger log = LogManager.getLogger();
    public static final int main( String[] args ) {
        log.info( "Logging initialized" );

        // TODO: move somewhere else
        try {
            Display.setDisplayMode( new DisplayMode( 500, 500 ) );
            Display.create();
        } catch ( LWJGLException e ) {
            log.fatal( e );
            System.exit( 1 );
        }

        GL11.glMatrixMode( GL11.GL_PROJECTION );
        GL11.glLoadIdentity();
        GL11.glOrtho( 0, 500, 0, 500, 1, -1 );
        GL11.glMatrixMode( GL11.GL_MODELVIEW );

        Random r = new Random();
        Level l = new Level();
        for ( int i = 0; i < 100; i++ ) {
            l.addEntity( new Entity( r.nextDouble() * 500, r.nextDouble() * 500, r.nextBoolean() ) );
        }
        long time = System.nanoTime();
        final int velocityPerSecond = 50;
        while ( !Display.isCloseRequested() ) {
            long lastTime = time;
            time = System.nanoTime();
            long timeDeltaNano = time - lastTime;
            double timeDeltaSeconds = timeDeltaNano / 1_000_000_000d;

            GL11.glClear( GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT );
            l.forEachEntity( e -> drawEntity( e ) );
            Display.update();

            log.trace( l.entityCount() );
            Map<Entity, Entity> closestEntity = new HashMap<Entity, Entity>();
            l.forEachEntity( e -> {
                l.entitiesAround( e.x(), e.y(), 50, (e2, d) -> {
                    if ( e == e2 ) return;
                    if ( closestEntity.containsKey( e ) ) {
                        Entity closest = closestEntity.get( e );
                        double dx = e.x() - closest.x();
                        double dy = e.y() - closest.y();

                        double minDSquared = dx * dx + dy * dy;
                        if ( d < minDSquared ) {
                            closestEntity.put( e, e2 );
                        }
                    } else {
                        closestEntity.put( e, e2 );
                    }
                });
            });

            closestEntity.forEach( (e, e2) -> {
                double coeff = 0;
                if ( e.isZombie() != e2.isZombie() ) {
                    if ( e.isZombie() ) {
                        coeff = 1;
                    } else {
                        coeff = -1;
                    }
                }

                double xJitter = (r.nextInt( 10 ) - 5) * timeDeltaSeconds;
                double yJitter = (r.nextInt( 10 ) - 5) * timeDeltaSeconds;

                double dx = e2.x() - e.x();
                double dy = e2.y() - e.y();
                double distance = Math.sqrt( dx*dx + dy*dy );
                if ( distance < 1 ) {
                    e.isZombie( true );
                    e2.isZombie( true );
                }
                e.x( e.x() + dx/distance * velocityPerSecond * coeff * timeDeltaSeconds + xJitter );
                e.y( e.y() + dy/distance * velocityPerSecond * coeff * timeDeltaSeconds + yJitter );

                if ( e.x() < 0 ) e.x( 0 );
                if ( 500 < e.x() ) e.x( 500 );
                if ( e.y() < 0 ) e.y( 0 );
                if ( 500 < e.y() ) e.y( 500 );
            });
        }

        return 0;
    }

    private static void drawEntity( Entity e ) {
        if ( e.isZombie() ) {
            GL11.glColor3f( 0.0f, 1.0f, 0.0f );
        } else {
            GL11.glColor3f( 0.0f, 0.0f, 1.0f );
        }

        GL11.glBegin( GL11.GL_QUADS );
        GL11.glVertex2d( e.x() - 2, e.y() - 2 );
        GL11.glVertex2d( e.x() + 2, e.y() - 2 );
        GL11.glVertex2d( e.x() + 2, e.y() + 2 );
        GL11.glVertex2d( e.x() - 2, e.y() + 2 );
        GL11.glEnd();
    }
}
