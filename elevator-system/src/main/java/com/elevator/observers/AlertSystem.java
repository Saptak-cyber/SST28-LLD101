package com.elevator.observers;

import com.elevator.enums.Direction;
import com.elevator.enums.ElevatorState;
import com.elevator.interfaces.IElevatorObserver;

/**
 * Alert system that notifies on critical events
 * SRP: Single responsibility - alert notifications only
 * LSP: Another IElevatorObserver implementation demonstrating substitutability
 * OCP: Can be extended with different alert mechanisms
 */
public class AlertSystem implements IElevatorObserver {
    private int maintenanceAlertCount = 0;

    @Override
    public void update(int elevatorId, int floor, Direction direction, ElevatorState state) {
        // Alert on maintenance state
        if (state == ElevatorState.MAINTENANCE) {
            maintenanceAlertCount++;
            System.out.printf("[ALERT] ⚠️  Elevator %d entered MAINTENANCE mode at floor %d%n",
                    elevatorId, floor);
        }

        // Alert on specific conditions (example: top floor)
        if (floor == 10 && state == ElevatorState.STOPPED) {
            System.out.printf("[ALERT] 🔔 Elevator %d reached top floor%n", elevatorId);
        }
    }

    public int getMaintenanceAlertCount() {
        return maintenanceAlertCount;
    }
}
