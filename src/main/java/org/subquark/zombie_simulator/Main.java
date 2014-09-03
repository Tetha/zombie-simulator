package org.subquark.zombie_simulator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Main {
    private static final Logger log = LogManager.getLogger();
    public static final int main( String[] args ) {
        log.info( "Logging initialized" );
        return 0;
    }
}
