/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team811.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 *
 * @author Matthew
 */
public class Catapult extends SubSystem {

    boolean reloading = false;
    boolean shot = false;
    long StartTime;

    public void init() {
        reloading = false;
        shot = false;
    }

    public void run() {
        //Catapult shoot and lock
        if (d.joy2.getRawButton(CATAPULT_RELEASE_BUTTON)) { //shoot
            long StartTime = System.currentTimeMillis();
            d.catapult_piston.set(DoubleSolenoid.Value.kForward);
            shot = true;
        } else if (d.joy2.getRawButton(CATAPULT_LOCK_BUTTON)) { //lock
            d.catapult_piston.set(DoubleSolenoid.Value.kReverse);
        } else { //turn off
            d.catapult_piston.set(DoubleSolenoid.Value.kOff);
        }
        
        if ((System.currentTimeMillis() > StartTime + 1000) && (shot)) {
                d.catapult_piston.set(DoubleSolenoid.Value.kReverse);
                reloading = true;
        }
        
        //auto reloading
        if(d.joy2.getRawButton(CATAPULT_AUTO_RELOAD_BUTTON)) {
            d.catapult_piston.set(DoubleSolenoid.Value.kForward);//lock
            reloading = true;//reloading 
        }
        if(d.joy2.getRawButton(CATAPULT_AUTO_RELOAD_CANCEL_BUTTON)) {
            reloading = false;
        }
        if (reloading) {
            if (!d.limitreloading.get()) {
                reloading = false;
                d.winch.set(0);
            } else {
                d.winch.set(1);
            }
        }
        
        //manual winch override
        if(Math.abs(d.joy2.getRawAxis(CATAPULT_WINCH_OVERRIDE_AXIS)) > .2) {
            d.winch.set(d.joy2.getRawAxis(CATAPULT_WINCH_OVERRIDE_AXIS));
            reloading = false;
        } else if (!reloading) {
            d.winch.set(0);
        }
    }

    public void disable() {
        reloading = false;
    }
}
