package com.elevator.interfaces;

import com.elevator.enums.Direction;
import com.elevator.enums.ElevatorState;

/**
 * Observer interface for elevator state changes
 * ISP: Small, focused interface for observation only
 */
public interface IElevatorObserver {
    /**
     * Receive updates about elevator state changes
     * 
     * @param elevatorId The ID of the elevator
     * @param floor Current floor
     * @param direction Current direction
     * @param state Current state
     */
    void update(int elevatorId, int floor, Direction direction, ElevatorState state);
}
