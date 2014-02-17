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
public class ReverseTalon extends Talon {

    public ReverseTalon(int channel) {
        super(channel);
    }

    public void set(double speed, byte syncGroup) {
        super.set(speed*-1, syncGroup);
    }
    
    public void set(double speed) {
        super.set(speed*-1);
    }
}
