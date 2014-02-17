/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team811.subsystems;
import edu.wpi.first.team811.vars.Config;
import edu.wpi.first.team811.vars.Devices;

/**
 *
 * @author Matthew
 */
public abstract class SubSystem implements Config {
    final Devices d;

    public SubSystem() {
        d = Devices.getDefaultInstance();
    }   
    
    public abstract void init();
    
    public abstract void run();
    
    public abstract void disable();
    
    
}
