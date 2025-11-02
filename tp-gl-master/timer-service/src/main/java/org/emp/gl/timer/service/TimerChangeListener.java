/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emp.gl.timer.service;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

/**
 * Task (e): TimerChangeListener now extends PropertyChangeListener
 * This allows us to use PropertyChangeSupport in TimerService implementation
 * to fix the ConcurrentModificationException bug
 * 
 * @author tina
 */
public interface TimerChangeListener extends PropertyChangeListener {
    
    final static String DIXEME_DE_SECONDE_PROP = "dixième";
    final static String SECONDE_PROP = "seconde";
    final static String MINUTE_PROP = "minute";
    final static String HEURE_PROP = "heure";
    
    // cette méthode est appelé du TimeChangeProvider à chaque 
    // fois qu'il y a un changement sur l'une des propriété de l'heure    
    void propertyChange(String prop, Object oldValue, Object newValue);
    
    /**
     * Implementation of PropertyChangeListener's propertyChange method
     * This bridges the PropertyChangeEvent from PropertyChangeSupport
     * to our custom propertyChange method
     * 
     * @param evt The PropertyChangeEvent from PropertyChangeSupport
     */
    @Override
    default void propertyChange(PropertyChangeEvent evt) {
        // Delegate to our existing propertyChange method
        propertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
    }
}