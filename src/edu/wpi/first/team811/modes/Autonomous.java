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
    boolean leftLit = false;//TODO Smart Dashboard
    boolean done_turning = false;
    boolean goal_is_lit_at_start = true;
    boolean thrown = false;

    public void robotStart() {
        super.robotStart();
        SmartDashboard.putNumber("autonomous", SmartDashboard.getNumber("autonomous", 1));
    }

    public void init() {
        StartTime = System.currentTimeMillis();
        done_turning = false;

        thrown = false;
    }

    public void periodic() {
        int autoMode = (int) SmartDashboard.getNumber("autonomous", 1);
        if (autoMode == 1) {//shoot hot
            if (System.currentTimeMillis() > (StartTime + 500)) {
                goal_is_lit_at_start = SmartDashboard.getBoolean("hotZone", true);
            }
            auto1(true);
        } else if (autoMode == 3) {//drive forward
            auto3();
        } else if (autoMode == 4) {//multiple ball
            auto4();
        } else if (autoMode == 5) {//turn toward hot
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

        d.arms_piston.set(DoubleSolenoid.Value.kForward);

        if (EndTime <= System.currentTimeMillis()) {
            d.drive.mecanumDrive_Cartesian(0, 0, 0, 0);
        } else {
            d.drive.mecanumDrive_Cartesian(0, -.5, 0, 0);
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
                d.drive.mecanumDrive_Cartesian(0, -.5, 0, 0);
            }
        }
    }

    //Move forward
    private void auto3() {
        long EndTime = StartTime + 2000;

        if (EndTime <= System.currentTimeMillis()) {
            d.drive.mecanumDrive_Cartesian(0, 0, 0, 0);
        } else {
            d.drive.mecanumDrive_Cartesian(0, -.5, 0, 0);
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
                d.drive.mecanumDrive_Cartesian(0, -.5, 0, 0);
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
}
