/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team811.modes;

import edu.wpi.first.team811.subsystems.Arms;
import edu.wpi.first.team811.subsystems.Catapult;
import edu.wpi.first.team811.subsystems.Drive;
import edu.wpi.first.team811.subsystems.SubSystem;

/**
 *
 * @author saswat
 */
public class Teleop extends Mode {
    
    SubSystem drive;
    SubSystem arms;
    SubSystem catapult;
    
    public void robotStart() {
        super.robotStart();
        
        drive = new Drive();
        arms = new Arms();
        catapult = new Catapult();
    }

    public void init() {
        drive.init();
        arms.init();
        catapult.init();
    }

    public void periodic() {
        drive.run();
        arms.run();
        catapult.run();
    }

    public void disabled() {
        drive.disable();
        arms.disable();
        catapult.disable();
    }
    
}
