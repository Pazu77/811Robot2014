/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.team811;


import edu.wpi.first.team811.modes.Autonomous;
import edu.wpi.first.team811.modes.Mode;
import edu.wpi.first.team811.modes.Teleop;
import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * Practice Bot Code
 * 
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Team811Robot extends IterativeRobot {
    
    private final Mode tele = new Teleop();
    private final Mode auto = new Autonomous();
    
    private boolean is_teleop = false;
    private boolean is_autonomous = false;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        tele.robotStart();
        auto.robotStart();
    }

    /**
     * This function is called on the start of autonomous
     */
    public void autonomousInit() {
        is_autonomous = true;
        auto.init();
    }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        //waitForData();
        System.out.println("auto periodic 1");
        auto.periodic();
        System.out.println("auto periodic 2");
    }

    /**
     * This function is called on the start of operator control
     */
    public void teleopInit() {
        is_teleop = true;
        tele.init();
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        //waitForData();
        System.out.println("tele periodic 1");
        tele.periodic();
        System.out.println("tele periodic 2");
    }

    /**
     * This function is called on the start of disabled
     */
    public void disabledInit() {
        if(is_autonomous) {
            is_autonomous = false;
            //auto.disabled();
        }
        if(is_teleop) {
            is_teleop = false;
            tele.disabled();
        }
    }

    /**
     * This function is called periodically during disabled
     */
    public void disabledPeriodic() {
        waitForData();
    }

    /**
     * This function is called on the start of practice mode
     */
    public void testInit() {
    }
    
    /**
     * This function is called periodically during practice mode
     */
    public void testPeriodic() {
        waitForData();
    }
    
    /**
     * Method to fix Iterative Robot bug - 
     * robot does not wait for driver station packets
     */
    private void waitForData() {
        getWatchdog().feed();
        m_ds.waitForData();
    }
}
