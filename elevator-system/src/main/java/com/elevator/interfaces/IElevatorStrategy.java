package com.elevator.interfaces;

import com.elevator.models.Elevator;
import java.util.List;

/**
 * Strategy interface for elevator selection algorithms
 * ISP: Focused interface for selection logic only
 * OCP: Open for extension - new strategies can be added without modifying existing code
 */
public interface IElevatorStrategy {
    /**
     * Select the best elevator for a request
     * 
     * @param elevators List of available elevators
     * @param requestedFloor The floor where elevator is requested
     * @return Selected elevator or null if none available
     */
    Elevator selectElevator(List<Elevator> elevators, int requestedFloor);
}
