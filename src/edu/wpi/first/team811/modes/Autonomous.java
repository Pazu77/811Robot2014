/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team811.modes;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Grainne
 */
public class Autonomous extends Mode {

    long StartTime;
    boolean goal_is_lit_at_start = true;
    
    //normal auto
    boolean thrown = false;
    
    //active hotzone auto
    boolean done_turning = false;
    boolean leftLit = false;//TODO Smart Dashboard
    
    // For two ball auto
    boolean part1;
    boolean part2;
    boolean part3;
    boolean part3_5;
    boolean part4;
    boolean part5;
    boolean part6;
    boolean part7;
    boolean part8;
    boolean part9;

    public void robotStart() {
        super.robotStart();
        SmartDashboard.putNumber("autonomous", SmartDashboard.getNumber("autonomous", 1));
    }

    public void init() {
        StartTime = System.currentTimeMillis();
        done_turning = false;
        goal_is_lit_at_start = false;
        thrown = false;

        // For two ball auto
        part1 = true;
        part2 = false;
        part3 = false;
        part3_5 = false;
        part4 = false;
        part5 = false;
        part6 = false;
        part7 = false;
        part8 = false;
        part9 = false;

    }

    public void periodic() {
        int autoMode = (int) SmartDashboard.getNumber("autonomous", 1);
        if (autoMode == 1) {//shoot hot
            if (System.currentTimeMillis() > (StartTime + 1500)) {
                if (SmartDashboard.getBoolean("hotZone", true)) {
                    goal_is_lit_at_start = true;
                }
            }
            auto1(true);
        } else if (autoMode == 3) {//drive forward
            auto3();
        } else if (autoMode == 4) {//multiple ball
            auto4();
        } else if(autoMode == 5) {//multiple ball(better)
            auto5();
        } else if (autoMode == 6) {//turn toward hot
            auto2();
        } else {//shoot immediately
            auto1(false);
        }
    }

    public void disabled() {
    }

    //Drive forward, shoot hot or not
    private void auto1(boolean hot) {
        boolean goal_is_lit_at_start = this.goal_is_lit_at_start;
        if (!hot) {
            goal_is_lit_at_start = true;
        }

        long EndTime = StartTime + 1500;

        d.arms_piston.set(DoubleSolenoid.Value.kReverse);

        if (EndTime <= System.currentTimeMillis()) {
            d.drive.mecanumDrive_Cartesian(0, 0, 0, 0);
        } else {
            d.drive.mecanumDrive_Cartesian(0, .5, 0, 0);
        }

        if (thrown) {
        } else if ((EndTime <= System.currentTimeMillis() && goal_is_lit_at_start) || EndTime + 4500 <= System.currentTimeMillis()) {
            d.catapult_piston.set(DoubleSolenoid.Value.kReverse);
            d.winch.set(-1);
            thrown = true;
        }

        if (EndTime + (goal_is_lit_at_start ? 0 : 4500) + 1000 <= System.currentTimeMillis()) {
            d.winch.set(0);
        }
    }

    //Turn toward hot and shoot(untested)
    private void auto2() {
        double angle = d.gyro.getAngle();

        long EndTime = StartTime + 4000;

        if (!done_turning) {
            if (leftLit) {
                if (angle > -30) {
                    d.drive.mecanumDrive_Cartesian(0, 0, .5, 0);
                } else {
                    d.drive.mecanumDrive_Cartesian(0, 0, 0, 0);
                    done_turning = true;
                }

            } else {
                if (angle < 30) {
                    d.drive.mecanumDrive_Cartesian(0, 0, -.5, 0);
                } else {
                    d.drive.mecanumDrive_Cartesian(0, 0, 0, 0);
                    done_turning = true;
                }
            }
        }

        if (done_turning) {
            if (EndTime < System.currentTimeMillis()) {
                d.drive.mecanumDrive_Cartesian(0, 0, 0, 0);
                d.catapult_piston.set(DoubleSolenoid.Value.kForward);
            } else {
                d.drive.mecanumDrive_Cartesian(0, .5, 0, 0);
            }
        }
    }

    //Move forward
    private void auto3() {
        long EndTime = StartTime + 2000;
        if (EndTime <= System.currentTimeMillis()) {
            d.drive.mecanumDrive_Cartesian(0, 0, 0, 0);
        } else {
            d.drive.mecanumDrive_Cartesian(0, .5, 0, 0);
        }
    }

    //Multiple ball autonomous(untested)
    private void auto4() {
        long EndTime = StartTime + 2000;
        double angle = d.gyro.getAngle();
        boolean has_ball = false;
        boolean part1done = false;
        boolean part2done = false;

        if (!part1done) {
            if (EndTime <= System.currentTimeMillis()) {
                d.drive.mecanumDrive_Cartesian(0, 0, 0, 0);
            } else {
                d.drive.mecanumDrive_Cartesian(0, .5, 0, 0);
            }

            boolean goal_is_lit = SmartDashboard.getBoolean("Goal On", true);

            if (EndTime <= System.currentTimeMillis() && goal_is_lit) {
                d.catapult_piston.set(DoubleSolenoid.Value.kForward);
                part1done = true;
            }

        } else {
            if (!part2done) {
                if (angle < 180) {
                    d.drive.mecanumDrive_Cartesian(0, 0, 1, 0);
                } else {
                    //find ball and go for it
                    /*if (d.ultra.getAverageReading() < 250 && d.ultra.getSlope() > -75) {
                     d.arms_piston.set(DoubleSolenoid.Value.kForward);
                     part2done = true;
                     } else {
                     do {
                     d.drive.mecanumDrive_Cartesian(0, 0, 1, 0);
                     } while (d.ultra.getAverageReading() > 250 && d.ultra.getSlope() < -75);
                     }*/
                }

            } else {
                if (!d.limitarmtop.get()) {
                    d.arm.set(0);
                    d.arms_piston.set(DoubleSolenoid.Value.kReverse);
                } else {
                    d.arm.set(1);
                }
                has_ball = true;
            }

            if (has_ball) {
                if (angle > -180) {
                    d.drive.mecanumDrive_Cartesian(0, 0, -1, 0);
                } else {
                    d.drive.mecanumDrive_Cartesian(0, 0, 0, 0);
                    d.catapult_piston.set(DoubleSolenoid.Value.kForward);
                }
            }
        }
    }

    private void auto5() {
        if (part1) { // Open arms
            d.arms_piston.set(DoubleSolenoid.Value.kReverse);
            part1 = false;
            part2 = true;
        } else if (part2) { // Put arms all the way down until limitswitch
            if ((d.limitarmtop.get())) {//limitswitch is inversed
                d.arm.set(1);
            } else {
                d.arm.set(0);
                part2 = false;
                part3 = true;
            }
        } else if (part3) { // Close arms
            d.arms_piston.set(DoubleSolenoid.Value.kForward);
            part3 = false;
            part3_5 = true;
            StartTime = System.currentTimeMillis();
        } else if(part3_5) {
            d.arm.set(-1);
            if(StartTime + 500 < System.currentTimeMillis()) {
                d.arm.set(0);
                part3_5 = false;
                part4 = true;
            }
        } else if (part4) { // Move forward for 2 seconds / encoder time
            d.drive.mecanumDrive_Cartesian(0, .5, 0, 0);
            if(StartTime + 3000 < System.currentTimeMillis()) {
                d.drive.mecanumDrive_Cartesian(0, 0, 0, 0);
                part4 = false;
                part5 = true;
            }
        } else if (part5) { // Shoot pre-loaded ball
            d.catapult_piston.set(DoubleSolenoid.Value.kReverse);
            d.winch.set(-1);
            if(StartTime + 3250 < System.currentTimeMillis()) {
                d.winch.set(0);
                part5 = false;
                part6 = true;
            }
        } else if (part6) { // Reload winch
            d.catapult_piston.set(DoubleSolenoid.Value.kForward);
            if (!d.limitreloading.get()) {
                d.winch.set(0);
                part6 = false;
                part7 = true;
            } else {
                d.winch.set(1);
            }
        } else if (part7) { // Move arm up until limitswitch
             if ((d.limitarmbottom.get())) {//limitswitch is inversed
                d.arm.set(-1);
            } else {
                d.arm.set(0);
                part7 = false;
                part8 = true;
            }
        } else if (part8) { // Open arms
            d.arms_piston.set(DoubleSolenoid.Value.kReverse);
            part8 = false;
            part9 = true;
        } else if (part9) { // Shoot ball
            d.catapult_piston.set(DoubleSolenoid.Value.kReverse);
            part9 = false;
        }
    }
}
