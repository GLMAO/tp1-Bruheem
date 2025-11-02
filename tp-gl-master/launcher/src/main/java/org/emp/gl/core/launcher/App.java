package org.emp.gl.core.launcher;

import org.emp.gl.clients.Horloge;
import org.emp.gl.time.service.impl.DummyTimeServiceImpl;
import org.emp.gl.clients.CompteARebours;
import org.emp.gl.timer.service.TimerService;
import java.util.Random;

/**
 * Launcher application to test the Observer Pattern
 */
public class App {

    public static void main(String[] args) {
        // Uncomment the test you want to run:

        // testDuTimeService(); // Task (c) - Test Horloge
        // testCompteARebours_Simple(); // Task (d.1) - Test with value 5
        testCompteARebours_Multiple(); // Task (d.3) - Test with 10 random countdowns
    }

    /**
     * Task (c): Test multiple Horloge instances
     */
    private static void testDuTimeService() {
        System.out.println("=== Task (c): Test Horloge ===\n");

        TimerService timerService = new DummyTimeServiceImpl();

        Horloge horloge1 = new Horloge(timerService, "Horloge 1");
        Horloge horloge2 = new Horloge(timerService, "Horloge 2");
        Horloge horloge3 = new Horloge(timerService, "Horloge 3");
    }

    /**
     * Task (d.1): Test CompteARebours with initial value of 5
     */
    private static void testCompteARebours_Simple() {
        System.out.println("=== Task (d.1): Test CompteARebours avec valeur 5 ===\n");

        TimerService timerService = new DummyTimeServiceImpl();

        // Create two countdown instances with value 5
        CompteARebours compte1 = new CompteARebours(timerService, 5, "Compte 1");
        CompteARebours compte2 = new CompteARebours(timerService, 5, "Compte 2");

    }

    /**
     * Task (d.3): Test 10 CompteARebours with random initial values (10-20)
     * WARNING: This will likely cause bugs due to ConcurrentModificationException!
     */
    private static void testCompteARebours_Multiple() {
        System.out.println("=== Task (d.3): Test 10 CompteARebours avec valeurs aléatoires ===\n");

        TimerService timerService = new DummyTimeServiceImpl();
        Random random = new Random();

        // Create 10 countdown instances with random values between 10 and 20
        for (int i = 1; i <= 10; i++) {
            int valeurAleatoire = 10 + random.nextInt(11); // 10 to 20 inclusive
            new CompteARebours(timerService, valeurAleatoire, "Compte " + i);
        }

        System.out.println("\n⚠️  ATTENTION: Des bogues peuvent survenir...\n");
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}