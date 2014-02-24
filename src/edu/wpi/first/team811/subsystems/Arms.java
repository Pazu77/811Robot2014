/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team811.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Saswat
 */
public class Arms extends SubSystem {

    public void init() {
        SmartDashboard.putNumber("autocatch", 150);
    }

    public void run() {//On Joystick 2
        //Pneumatics
        if (d.joy2.getRawButton(ARMS_CLOSE_BUTTON)) { //closes arms
            d.arms_piston.set(DoubleSolenoid.Value.kForward);
        } else if (d.joy2.getRawButton(ARMS_OPEN_BUTTON)) { //opens arms
            d.arms_piston.set(DoubleSolenoid.Value.kReverse);
        } else {
            //ultrasonic auto catch
            if (d.joy2.getRawButton(AUTO_CATCH_BUTTON)) {
                SmartDashboard.putBoolean("auto catch on", true);
                if (d.ultra.getAverageReading() < SmartDashboard.getNumber("autocatch", 150)) {
                    d.arms_piston.set(DoubleSolenoid.Value.kForward);
                    SmartDashboard.putBoolean("auto catch on", false);
                }
            } else {
                SmartDashboard.putBoolean("auto catch on", false);
                //d.arms_piston.set(DoubleSolenoid.Value.kOff);
            }
        }
        
        double armsInput = d.joy2.getRawAxis(ARMS_MOVEMENT_JOYSTICK_AXIS) * -1;
        
        //Actuation of the arms, they won't move in a certain direction if a limit switch is hit
        if ((!d.limitarmtop.get() && armsInput > 0) || (!d.limitarmbottom.get() && armsInput < 0)) {
            d.arm.set(0);
        } else {
            if(Math.abs(armsInput) < .2){
                d.arm.set(0);
            } else {
                d.arm.set(armsInput);
            }
        }
        SmartDashboard.putBoolean("limit arm bottom", d.limitarmbottom.get());
        SmartDashboard.putBoolean("limit arm top", d.limitarmtop.get());

        SmartDashboard.putNumber("range", d.ultra.getLastReading());
        SmartDashboard.putNumber("range avg", d.ultra.getAverageReading());
        SmartDashboard.putNumber("range slope", d.ultra.getSlope());
    }

    public void disable() {

    }

}
