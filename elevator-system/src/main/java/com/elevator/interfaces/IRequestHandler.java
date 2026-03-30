package com.elevator.interfaces;

import com.elevator.enums.Direction;
import com.elevator.models.Elevator;
import java.util.List;

/**
 * Interface for handling elevator requests
 * ISP: Focused interface for request handling only
 */
public interface IRequestHandler {
    /**
     * Process an elevator request
     * 
     * @param elevators List of available elevators
     * @param floor Requested floor
     * @param direction Desired direction
     * @return Selected elevator or null if none available
     */
    Elevator handleRequest(List<Elevator> elevators, int floor, Direction direction);
}
