/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team811.vars;

import edu.wpi.first.team811.devices.EncoderTalon;
import edu.wpi.first.team811.devices.UltrasonicRunner;
import edu.wpi.first.team811.devices.ReverseTalon;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive; 
import edu.wpi.first.wpilibj.Talon;

/**
 *
 * @author Matthew
 */
public class Devices implements Config {
   //Device declaration
    public final Joystick joy1;
    public final Joystick joy2;
    public final RobotDrive drive;
    public final Talon frontleft;
    public final Talon rearleft;
    public final Talon frontright;
    public final Talon rearright;
    public final Compressor compressor;
    public final DoubleSolenoid arms_piston;
    public final DoubleSolenoid catapult_piston;
    public final UltrasonicRunner ultra; 
    public final DigitalInput limitreloading;
    public final Talon arm;
    public final Talon winch;
    public final DigitalInput limitarmtop;
    public final DigitalInput limitarmbottom;
    public final Gyro gyro;
    
    
    private static Devices devices = null;
    
    private Devices(){
        joy1 = new Joystick(JOY_PORT_1);
        joy2 = new Joystick(JOY_PORT_2);
        //frontleft = new EncoderTalon(FRONT_LEFT_PORT, FRONTLEFT_ENCODER_PORT_1, FRONTLEFT_ENCODER_PORT_2, true);
        //rearleft = new EncoderTalon(REAR_LEFT_PORT, REARLEFT_ENCODER_PORT_1, REARLEFT_ENCODER_PORT_2, true);
        //frontright = new EncoderTalon(FRONT_RIGHT_PORT, FRONTRIGHT_ENCODER_PORT_1, FRONTRIGHT_ENCODER_PORT_2, false);
        //rearright = new EncoderTalon(REAR_RIGHT_PORT, REARRIGHT_ENCODER_PORT_1, REARRIGHT_ENCODER_PORT_2, false);
        frontleft = new Talon(FRONT_LEFT_PORT);
        rearleft = new Talon(REAR_LEFT_PORT);
        frontright = new Talon(FRONT_RIGHT_PORT);
        rearright = new Talon(REAR_RIGHT_PORT);
        drive = new RobotDrive(frontleft, rearleft, frontright, rearright);
        compressor = new Compressor(COMPRESSOR_PRESSURE_INPUT, COMPRESSOR_RELAY);
        arms_piston = new DoubleSolenoid(ARMS_CLOSE_CHANNEL, ARMS_OPEN_CHANNEL);
        ultra = new UltrasonicRunner();
        catapult_piston = new DoubleSolenoid(CATAPULT_LOCK_CHANNEL, CATAPULT_UNLOCK_CHANNEL);
        limitreloading = new DigitalInput(RELOADING_LIMITSWITCH_CHANNEL); 
        arm = new Talon(ARMS_ACTUATOR_PORT);
        winch = new ReverseTalon(WINCH_PORT);
        limitarmtop = new DigitalInput(ARM_TOP_LIMITSWITCH_CHANNEL);
        limitarmbottom = new DigitalInput(ARM_BOTTOM_LIMITSWITCH_CHANNEL);
        gyro = new Gyro(GYRO_CHANNEL);
        
        compressor.start();
        ultra.start();
        
        drive.setSafetyEnabled(false);
        
        if(frontleft instanceof EncoderTalon) {
            ((EncoderTalon)frontleft).setDistancePerPulse(.001);
            ((EncoderTalon)frontleft).setMaxSpeed(50);
            ((EncoderTalon)rearleft).setDistancePerPulse(.001);
            ((EncoderTalon)rearleft).setMaxSpeed(50);
            ((EncoderTalon)frontright).setDistancePerPulse(.001);
            ((EncoderTalon)frontright).setMaxSpeed(50);
            ((EncoderTalon)rearright).setDistancePerPulse(.001);
            ((EncoderTalon)rearright).setMaxSpeed(50);
            
        }
    }

    public static Devices getDefaultInstance() {
        if(devices == null) {
            devices = new Devices();
        }
        return devices;
    }
}