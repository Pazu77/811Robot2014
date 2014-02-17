/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team811.modes;

import edu.wpi.first.team811.vars.Config;
import edu.wpi.first.team811.vars.Devices;

/**
 *
 * @author saswat
 */
public abstract class Mode implements Config{
    
    Devices d;
    
    public void robotStart() {
        d = Devices.getDefaultInstance();
    }
    
    public abstract void init();
    public abstract void periodic();
    public abstract void disabled();
}
