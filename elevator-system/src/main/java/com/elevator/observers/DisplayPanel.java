package com.elevator.observers;

import com.elevator.enums.Direction;
import com.elevator.enums.ElevatorState;
import com.elevator.interfaces.IElevatorObserver;

/**
 * Display panel that observes elevator state
 * SRP: Single responsibility - display updates only
 * LSP: Can be substituted for any IElevatorObserver without breaking functionality
 * ISP: Implements only the methods it needs
 */
public class DisplayPanel implements IElevatorObserver {

    @Override
    public void update(int elevatorId, int floor, Direction direction, ElevatorState state) {
        System.out.printf("[DISPLAY] Elevator %d: Floor %d, %s, %s%n",
                elevatorId, floor, direction, state);
    }
}
