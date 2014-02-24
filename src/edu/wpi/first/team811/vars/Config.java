 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team811.vars;

/**
 *
 * @author Matthew
 */
public interface Config {
    //Joysticks
    int JOY_PORT_1 = 1;
    int JOY_PORT_2 = 2;
    
    //Compressor Ports
    int COMPRESSOR_RELAY = 1;
    int COMPRESSOR_PRESSURE_INPUT = 4;
    
    //Arms Ports
    int ARMS_CLOSE_CHANNEL = 3;
    int ARMS_OPEN_CHANNEL = 4;
    int ARMS_ACTUATOR_PORT = 5;
    
    //Catapult Ports
    int CATAPULT_LOCK_CHANNEL = 2;
    int CATAPULT_UNLOCK_CHANNEL = 1;
    int WINCH_PORT = 6;
    
    //Drive Ports
    int FRONT_LEFT_PORT = 3;
    int REAR_LEFT_PORT = 4;
    int FRONT_RIGHT_PORT = 2;
    int REAR_RIGHT_PORT = 1;
    int GYRO_CHANNEL = 1;
    int FRONTLEFT_ENCODER_PORT_1 = 9;
    int FRONTLEFT_ENCODER_PORT_2 = 10;
    int REARLEFT_ENCODER_PORT_1 = 11;
    int REARLEFT_ENCODER_PORT_2 = 12;
    int FRONTRIGHT_ENCODER_PORT_1 = 7;
    int FRONTRIGHT_ENCODER_PORT_2 = 8;
    int REARRIGHT_ENCODER_PORT_1 = 5;
    int REARRIGHT_ENCODER_PORT_2 = 6;
    
    
    //Limit Switch Ports
    int RELOADING_LIMITSWITCH_CHANNEL = 1;
    int ARM_TOP_LIMITSWITCH_CHANNEL = 3;
    int ARM_BOTTOM_LIMITSWITCH_CHANNEL = 2;
    
    
    //Drive Vars
    double JOYSTICK_DRIVE_TOLERANCE = .3;
    double DEFAULT_SPEED_SCALE = 1;
    double SLOW_SPEED_SCALE = .75;
    
    
    //Drive Controls
    int DRIVE_X_JOYSTICK_AXIS = 3;
    int DRIVE_Y_JOYSTICK_AXIS = 2;
    int DRIVE_TURNING_JOYSTICK_AXIS = 4;
    int FIELD_CENTRIC_BUTTON = 1;
    int ROBOT_CENTIC_BUTTON = 2;
    int SLOW_BUTTON = 6;
    int GYRO_RESET_BUTTON = 7;
    
    //Arms Controls
    int ARMS_CLOSE_BUTTON = 1;
    int ARMS_OPEN_BUTTON = 2;
    int AUTO_CATCH_BUTTON = 6;
    int ARMS_MOVEMENT_JOYSTICK_AXIS = 2;
    
    //Catapult Controls
    int CATAPULT_RELEASE_BUTTON = 4;//this will SHOOT the ball
    int CATAPULT_LOCK_BUTTON = 3;
    int CATAPULT_AUTO_RELOAD_BUTTON = 8;
    int CATAPULT_AUTO_RELOAD_CANCEL_BUTTON = 7;
    int CATAPULT_WINCH_OVERRIDE_AXIS = 5;
    
    //Smartdashboard variables
    String strRobotOrientation = "Robot Orientation";
    String dblRobotSpeedScale = "Robot Speed Scale";
    
    
}
