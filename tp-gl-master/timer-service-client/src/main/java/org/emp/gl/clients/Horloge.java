package org.emp.gl.clients;

import org.emp.gl.timer.service.TimerService;
import org.emp.gl.timer.service.TimerChangeListener;

/**
 * Horloge (Clock) class that observes a TimerService
 * and displays the current time every second
 */
public class Horloge implements TimerChangeListener {
    
    private final TimerService timerService;
    private final String name;
    
    // Store current time values
    private int heures = 0;
    private int minutes = 0;
    private int secondes = 0;
    private int dixiemes = 0;
    
    /**
     * Constructor with TimerService dependency injection
     * @param timerService The timer service to observe (abstraction, not implementation)
     * @param name Name identifier for this clock instance
     */
    public Horloge(TimerService timerService, String name) {
        this.timerService = timerService;
        this.name = name;
        
        // Subscribe as observer to the TimerService
        this.timerService.addTimeChangeListener(this);
    }
    
    /**
     * Called when any time property changes (Observer pattern callback)
     * This method is called by the TimerService for each property change
     * 
     * @param prop The property that changed (HEURE_PROP, MINUTE_PROP, SECONDE_PROP, DIXEME_DE_SECONDE_PROP)
     * @param oldValue The old value of the property
     * @param newValue The new value of the property
     */
    @Override
    public void propertyChange(String prop, Object oldValue, Object newValue) {
        // Update the corresponding time component
        switch (prop) {
            case HEURE_PROP:
                heures = (Integer) newValue;
                break;
            case MINUTE_PROP:
                minutes = (Integer) newValue;
                break;
            case SECONDE_PROP:
                secondes = (Integer) newValue;
                break;
            case DIXEME_DE_SECONDE_PROP:
                dixiemes = (Integer) newValue;
                break;
        }
        
        // Display the time only when seconds change (to avoid too much output)
        if (prop.equals(SECONDE_PROP)) {
            afficherHeure();
        }
    }
    
    /**
     * Display the current time in format HH:MM:SS
     */
    private void afficherHeure() {
        System.out.printf("%s - %02d:%02d:%02d%n", name, heures, minutes, secondes);
    }
}