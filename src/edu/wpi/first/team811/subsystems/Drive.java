/*

 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team811.subsystems;

import edu.wpi.first.team811.devices.EncoderTalon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Matthew
 */
public class Drive extends SubSystem {

    boolean isFieldCentric = false;
    boolean isGyroStableOn = true;
    boolean isTurning = false;
    double angle = d.gyro.getAngle();

    public void init() {
    }

    public void run() {
        //Inputs
        double input1x = d.joy1.getRawAxis(DRIVE_X_JOYSTICK_AXIS);//left and right
        double input1y = -1 * d.joy1.getRawAxis(DRIVE_Y_JOYSTICK_AXIS);//forward and backwards
        double input2x = -1 * d.joy1.getRawAxis(DRIVE_TURNING_JOYSTICK_AXIS);//turning

        //Joystick Tolerance
        if (Math.abs(input1x) < JOYSTICK_DRIVE_TOLERANCE) {
            input1x = 0;
        }
        if (Math.abs(input1y) < JOYSTICK_DRIVE_TOLERANCE) {
            input1y = 0;
        }
        if (Math.abs(input2x) < JOYSTICK_DRIVE_TOLERANCE) {
            input2x = 0;
        }

        //Robot Centric vs Field Centric
        if (d.joy1.getRawButton(FIELD_CENTRIC_BUTTON)) {//Toggle to field centric
            isFieldCentric = true;
        } else if (d.joy1.getRawButton(ROBOT_CENTIC_BUTTON)) {//Toggle to robot centric
            isFieldCentric = false;
        }

        if (d.joy1.getRawButton(ENCODER_RESET_BUTTON)) {
            if (d.rearleft instanceof EncoderTalon) {
                ((EncoderTalon) d.rearleft).reset();
                ((EncoderTalon) d.rearright).reset();
                ((EncoderTalon) d.frontleft).reset();
                ((EncoderTalon) d.frontright).reset();
            }
        }

        if (Math.abs(input2x) > 0.001) {
            isTurning = true;
        } else {
            if (isTurning) {
                isTurning = false;
                angle = d.gyro.getAngle();
            }
        }

        //Speed Scale
        boolean slow = d.joy1.getRawButton(SLOW_BUTTON);//Slow! only while holding the button
        double speedScale = DEFAULT_SPEED_SCALE;
        if (slow) {
            speedScale = SLOW_SPEED_SCALE;
        }

        if (d.joy1.getRawButton(GYRO_RESET_BUTTON)) {
            d.gyro.reset();
        }

        if (d.joy1.getRawButton(GYRO_STABLE_ON_BUTTON)) {
            isGyroStableOn = true;
        }

        if (d.joy1.getRawButton(GYRO_STABLE_OFF_BUTTON)) {
            isGyroStableOn = false;
        }

        if (isGyroStableOn) {
            if (Math.abs(input2x) < 0.001) {
                input2x = (d.gyro.getAngle() - angle) * Kp;
            }
        }

        //Smartdashboard print outs
        SmartDashboard.putNumber(dblGyro, d.gyro.getAngle());
        SmartDashboard.putString(strRobotOrientation, isFieldCentric ? "Field Centric" : "Robot Centric");
        SmartDashboard.putNumber(dblRobotSpeedScale, speedScale);
        SmartDashboard.putBoolean(blnGyroStable, isGyroStableOn);

        //Drive!
        d.drive.mecanumDrive_Cartesian(input1x * speedScale, input1y * speedScale, input2x * speedScale, isFieldCentric ? d.gyro.getAngle() : 0);
    }

    public void disable() {
    }
}
