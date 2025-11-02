package org.emp.gl.core.launcher;

import org.emp.gl.clients.Horloge;
import org.emp.gl.timer.service.TimerService;
import org.emp.gl.time.service.impl.DummyTimeServiceImpl;

/**
 * Launcher application to test the Observer Pattern
 */
public class App {

    public static void main(String[] args) {
        System.out.println("=== Task (c): Testing Horloge with Observer Pattern ===\n");
        
        testDuTimeService();
    }

    private static void testDuTimeService() {
        // 1. Instantiate the TimerService first (using abstraction)
        TimerService timerService = new DummyTimeServiceImpl();
        
        // 2. Create multiple Horloge instances and inject the TimerService
        Horloge horloge1 = new Horloge(timerService, "Horloge 1");
        Horloge horloge2 = new Horloge(timerService, "Horloge 2");
        Horloge horloge3 = new Horloge(timerService, "Horloge 3");
        
        System.out.println("Three clocks registered to TimerService.\n");
        System.out.println("Starting timer... Each clock will display time every second:\n");
        
        // 3. Start the timer service
        timerService.notify();
        
        // Keep the program running
        try {
            // Run for 15 seconds to see the clocks in action
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\n=== End of Test ===");
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}