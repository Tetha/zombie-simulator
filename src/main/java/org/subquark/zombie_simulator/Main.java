package org.subquark.zombie_simulator;

import java.util.ArrayList;
import java.util.List;
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
        final int velocityPerSecond = 500;
        while ( !Display.isCloseRequested() ) {
            long lastTime = time;
            time = System.nanoTime();
            long timeDeltaNano = time - lastTime;
            double timeDeltaSeconds = timeDeltaNano / 1_000_000_000d;

            GL11.glClear( GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT );
            l.forEachEntity( e -> drawEntity( e ) );
            Display.update();

            l.forEachEntity( e -> {
                e.x( e.x() + ( r.nextInt( velocityPerSecond ) - velocityPerSecond/2 ) * timeDeltaSeconds );
                e.y( e.y() + ( r.nextInt( velocityPerSecond ) - velocityPerSecond/2 ) * timeDeltaSeconds );
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
