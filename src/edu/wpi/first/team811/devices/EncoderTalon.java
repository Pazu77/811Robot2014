/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team811.devices;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Talon;

/**
 *
 * @author Pazu
 */
public class EncoderTalon extends Talon {
    
    private Encoder encoder;
    private PIDController pid;
    
    private double MAX_SPEED = 2000;
    
    private static final double PID_P = .000001;
    private static final double PID_I = .000001;
    private static final double PID_D = .000001;
    
    /**
     * Creates a new encoder talon
     * This talon controls its speed based on the input from an attached encoder
     * It is mostly battery independent!
     * @param channel the talon's port
     * @param left_encoder_a the first port of the encoder
     * @param left_encoder_b the second port of the encoder
     * @param reversed whether the readings of the encoder should be reversed
     */
    public EncoderTalon(int channel, int left_encoder_a, int left_encoder_b, boolean reversed) {  
        this(channel, new Encoder(left_encoder_a, left_encoder_b, reversed));
    }
    
    /**
     * Creates a new encoder talon
     * This talon controls its speed based on the input from an attached encoder
     * It is mostly battery independent!
     * @param channel the talon's port
     * @param encoder the encoder
     */
    public EncoderTalon(int channel, Encoder encoder) {
        super(channel);
        this.encoder = encoder;
        
        encoder.setPIDSourceParameter(PIDSource.PIDSourceParameter.kRate);
        
        pid = new PIDController(PID_P, PID_I, PID_D, encoder, new PIDOutput() {

            public void pidWrite(double output) {
                EncoderTalon.super.set(output);
            }
        });
        
        setMaxSpeed(MAX_SPEED);
        
        pid.setAbsoluteTolerance(.5);
        pid.setSetpoint(0);
        
        encoder.start();
        pid.enable();
    }
    
    /**
     * Gets the current distance recorded from the encoder
     * @return The distance the encoder has recorded since its last reset (in encoder units)
     */
    public double getDistance() {
        return encoder.getDistance();
    }
    
    /**
     * Gets the current speed recorded from the encoder
     * @return The speed the encoder is spinning at (in encoder units per second)
     */
    public double getSpeed() {
        return encoder.getRate();
    }
    
    /**
     * Resets the encoder's distance counter
     */
    public void reset() {
        encoder.reset();
    }
    
    /**
     * Sets the scale of each encoder reading
     * This controls the encoder units
     * @param distance The amount each encoder reading counts as
     */
    public void setDistancePerPulse(double distance) {
        encoder.setDistancePerPulse(distance);
    }
    
    /**
     * Sets the maximum speed for the motors to reach (in encoder units)
     * @param speed The max speed in encoder units
     */
    public void setMaxSpeed(double speed) {
        MAX_SPEED = speed;
        
        pid.setInputRange(-speed, speed);
        pid.setOutputRange(-1, 1);
        
    }
    
    /**
     * Sets the tolerance for a speed to be considered "close enough"
     * @param speed The bound to consider as on target (in encoder units)
     */
    public void setTolerance(double speed) {
        pid.setAbsoluteTolerance(speed);
    }
    
    /**
     * Sets the tolerance for a speed to be considered "close enough"
     * @param percent The percent bounds to consider as on target (between 0 and 1)
     */
    public void setPercentTolerance(double percent) {
        pid.setPercentTolerance(percent*100);
    }
    
    /**
     * Sets the speed for the talon based on encoder input
     * @param speed The speed the motor should reach (in encoder units)
     */
    public void setActualSpeed(double speed) {
        pid.setSetpoint(speed);
    }
    
    /**
     * Sets the speed for the talon based on encoder input
     * @param speed The percent speed the motor should reach with the defined maximum speed = 100% (enter a value from -1 to 1)
     */
    public void set(double speed) {
        if(Math.abs(speed) < .1) {
            pid.setSetpoint(0);
            pid.disable();
            super.set(0);
        } else {
            pid.setSetpoint(speed*MAX_SPEED);
            pid.enable();
        }
    }
}
