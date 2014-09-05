package org.subquark.zombie_simulator.ai;

import org.subquark.zombie_simulator.engine.Level;

public interface Ai {
    void think( Level l, double timeDeltaSeconds );
    void act( double timeDeltaSeconds );
}
