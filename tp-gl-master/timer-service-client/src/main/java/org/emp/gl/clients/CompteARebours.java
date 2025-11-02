package org.emp.gl.clients;

import org.emp.gl.timer.service.TimerService;
import org.emp.gl.timer.service.TimerChangeListener;

/**
 * CompteARebours (Countdown) class that observes a TimerService
 * and counts down from an initial value to 0
 */
public class CompteARebours implements TimerChangeListener {
    
    private final TimerService timerService;
    private final String name;
    private int compteur;
    private final int valeurInitiale;
    
    /**
     * Constructor with TimerService dependency injection
     * @param timerService The timer service to observe
     * @param valeurInitiale The initial countdown value
     * @param name Name identifier for this countdown instance
     */
    public CompteARebours(TimerService timerService, int valeurInitiale, String name) {
        this.timerService = timerService;
        this.valeurInitiale = valeurInitiale;
        this.compteur = valeurInitiale;
        this.name = name;
    
        // Subscribe as observer to the TimerService
        this.timerService.addTimeChangeListener(this);
        
        System.out.println(name + " - Démarrage du compte à rebours depuis " + valeurInitiale);
    }
    
    /**
     * Constructor without name (generates default name)
     */
    public CompteARebours(TimerService timerService, int valeurInitiale) {
        this(timerService, valeurInitiale, "CompteARebours");
    }
    
    /**
     * Called when any time property changes
     * Decrements the counter every second
     * 
     * @param prop The property that changed
     * @param oldValue The old value of the property
     * @param newValue The new value of the property
     */
    @Override
    public void propertyChange(String prop, Object oldValue, Object newValue) {
        // Only decrement when a second passes
        if (prop.equals(SECONDE_PROP)) {
            if (compteur > 0) {
                compteur--;
                afficherCompteur();
                
                // Task d.2: Unsubscribe when countdown reaches 0
                if (compteur == 0) {
                    System.out.println(name + " - ⏰ TERMINÉ! Se désinscrit du TimerService.");
                    timerService.removeTimeChangeListener(this);
                }
            }
        }
    }
    
    /**
     * Display the current countdown value
     */
    private void afficherCompteur() {
        System.out.printf("%s - Compteur: %d%n", name, compteur);
    }
    
    /**
     * Get current counter value
     */
    public int getCompteur() {
        return compteur;
    }
}