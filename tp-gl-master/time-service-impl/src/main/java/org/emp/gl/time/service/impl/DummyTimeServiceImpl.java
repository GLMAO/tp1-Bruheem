/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emp.gl.time.service.impl;

import java.beans.PropertyChangeSupport;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;

/**
 * Task (e): Updated to use PropertyChangeSupport
 * This fixes the ConcurrentModificationException when listeners unsubscribe
 * 
 * @author tina
 */
public class DummyTimeServiceImpl implements TimerService {

    int dixiemeDeSeconde;
    int minutes;
    int secondes;
    int heures;
    
    // TASK (e): Replace List with PropertyChangeSupport
    // This handles listener management safely!
    private final PropertyChangeSupport support;

    /**
     * Constructeur du DummyTimeServiceImpl: ici, 
     * nous nous avons utilisé un objet Timer, qui permet de
     * réaliser des tics à chaque N millisecondes
     */
    public DummyTimeServiceImpl() {
        // Initialize PropertyChangeSupport
        this.support = new PropertyChangeSupport(this);
        
        setTimeValues();
        // initialize schedular
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                timeChanged();
            }
        };
        timer.scheduleAtFixedRate(task, 100, 100);
    }

    private void setTimeValues() {
        LocalTime localTime = LocalTime.now();

        setSecondes(localTime.getSecond());
        setMinutes(localTime.getMinute());
        setHeures(localTime.getHour());
        setDixiemeDeSeconde(localTime.getNano() / 100000000);
    }

    @Override
    public void addTimeChangeListener(TimerChangeListener pl) {
        // Use PropertyChangeSupport instead of manual list
        support.addPropertyChangeListener(pl);
    }

    @Override
    public void removeTimeChangeListener(TimerChangeListener pl) {
        // Use PropertyChangeSupport - safe even during notification!
        support.removePropertyChangeListener(pl);
    }

    private void timeChanged() {
        setTimeValues();
    }

    public void setDixiemeDeSeconde(int newDixiemeDeSeconde) {
        if (dixiemeDeSeconde == newDixiemeDeSeconde)
            return;

        int oldValue = dixiemeDeSeconde;
        dixiemeDeSeconde = newDixiemeDeSeconde;

        // informer les listeners !
        dixiemeDeSecondesChanged(oldValue, dixiemeDeSeconde);
    }

    private void dixiemeDeSecondesChanged(int oldValue, int newValue) {
        // Use PropertyChangeSupport.firePropertyChange instead of manual loop
        // This is safe even if listeners unsubscribe during notification!
        support.firePropertyChange(
            TimerChangeListener.DIXEME_DE_SECONDE_PROP,
            oldValue, 
            newValue
        );
    }

    public void setSecondes(int newSecondes) {
        if (secondes == newSecondes)
            return;

        int oldValue = secondes;
        secondes = newSecondes;

        secondesChanged(oldValue, secondes);
    }

    private void secondesChanged(int oldValue, int newValue) {
        // Use PropertyChangeSupport instead of manual loop
        support.firePropertyChange(
            TimerChangeListener.SECONDE_PROP,
            oldValue, 
            newValue
        );
    }

    public void setMinutes(int newMinutes) {
        if (minutes == newMinutes)
            return;

        int oldValue = minutes;
        minutes = newMinutes;

        minutesChanged(oldValue, minutes);
    }

    private void minutesChanged(int oldValue, int newValue) {
        // Use PropertyChangeSupport instead of manual loop
        // BUG FIX: Was passing 'secondes' instead of 'newValue'!
        support.firePropertyChange(
            TimerChangeListener.MINUTE_PROP,
            oldValue, 
            newValue
        );
    }

    public void setHeures(int newHeures) {
        if (heures == newHeures)
            return;

        int oldValue = heures;
        heures = newHeures;

        heuresChanged(oldValue, heures);
    }

    private void heuresChanged(int oldValue, int newValue) {
        // Use PropertyChangeSupport instead of manual loop
        // BUG FIX: Was passing 'secondes' instead of 'newValue'!
        support.firePropertyChange(
            TimerChangeListener.HEURE_PROP,
            oldValue, 
            newValue
        );
    }

    @Override
    public int getDixiemeDeSeconde() {
        return dixiemeDeSeconde;
    }

    @Override
    public int getHeures() {
        return heures;
    }

    @Override
    public int getMinutes() {
        return minutes;
    }

    @Override
    public int getSecondes() {
        return secondes;
    }
}