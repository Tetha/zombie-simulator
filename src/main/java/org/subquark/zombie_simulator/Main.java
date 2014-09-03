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
        List<Entity> entities = new ArrayList<Entity>(100);
        while ( entities.size() < 100 ) {
            entities.add( new Entity( r.nextInt( 500 ), r.nextInt( 500 ), r.nextBoolean() ) );
        }
        while ( !Display.isCloseRequested() ) {
            GL11.glClear( GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT );
            entities.forEach( e -> drawEntity( e ) );
            Display.update();
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
        GL11.glVertex2f( e.x() - 2, e.y() - 2 );
        GL11.glVertex2f( e.x() + 2, e.y() - 2 );
        GL11.glVertex2f( e.x() + 2, e.y() + 2 );
        GL11.glVertex2f( e.x() - 2, e.y() + 2 );
        GL11.glEnd();
    }
}
