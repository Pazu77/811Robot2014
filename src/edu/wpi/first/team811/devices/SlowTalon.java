/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team811.devices;

import edu.wpi.first.wpilibj.Talon;

/**
 *
 * @author Matthew
 */
public class SlowTalon extends Talon {
    
    double speed_multipler = 1;
    
    public SlowTalon(int channel, double speed_multipler) {
        super(channel);
        this.speed_multipler = speed_multipler;
    }

    public void set(double speed, byte syncGroup) {
        super.set(speed*speed_multipler, syncGroup);
    }
    
    public void set(double speed) {
        super.set(speed*speed_multipler);
    }
}
