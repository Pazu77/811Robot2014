/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team811.devices;

import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.I2C;

/**
 *
 * @author Developer
 */
public class Ultrasonic {

    private static final int SensorAddress = 0xe0;
    private static final int RangeCommand = 81;
    private final I2C ultra;
    
    /**
     * Creates a new Ultrasonic sensor (I2CXL-MaxSonar-EZ Series) at its default shipping address on the default module
     */
    public Ultrasonic() {
            ultra = DigitalModule.getInstance(DigitalModule.getDefaultDigitalModule()).getI2C(SensorAddress);
    }
    
    
    /**
     * Creates a new Ultrasonic sensor (I2CXL-MaxSonar-EZ Series) at specified address on the specified module
     * @param module The Digital Module the sensor is on
     * @param address The sensor's I2C address
     */
    public Ultrasonic(int module, int address) {
        
          // ultra = DigitalModule.getInstance(module).getI2C(SensorAddress);
           ultra = DigitalModule.getInstance(module).getI2C(SensorAddress);
    }
    
    /**
     * Attempts to address the ultrasonic sensor on the I2C bus
     * @return true if the command was aborted
     */
    public boolean address() {
        return ultra.addressOnly();
    }
    
    /**
     * Requests the sensor to take a range reading
     * @return true if the command was aborted
     */
    public boolean takeRangeReadingOnSensor() {
        return i2cWrite(RangeCommand);
    }
    
    /**
     * Get the range from the sensor AFTER the sensor has been requested to take a reading in takeRangeReadingOnSensor()
     * This will return 20cm for objects closer than 20cm
     * @return the range the sensor measures in centimeters, -1 if the command was aborted
     */
    public short requestRange() {
        byte buffer[] = new byte[2];
        if(i2cRead(2, buffer)) {
            return -1;
        }
        return bytes2short(buffer[0], buffer[1]);
    }

    /**
     * Converts a high byte and a low byte to a short
     * @param hibyte the high byte
     * @param lowbyte the low byte
     * @return the resulting short
     */
    private short bytes2short(byte hibyte, byte lobyte) {
        int i1, i2;
        i1 = hibyte & 0xFF; // stores b1 as an unsigned value
        i2 = lobyte & 0xFF; // stores b2 as an unsigned value
        return (short)(i1 << 8| i2);
    }
    
    /**
     * Sends data to the I2C bus
     * @param data the bytes to send
     * @return true if the command was aborted
     */
    private boolean i2cWrite(int data) {
        byte[] buffer = new byte[1];
        buffer[0] = (byte) data;
        return ultra.transaction(buffer, buffer.length, null, 0);
    }
    
    /**
     * Reads data from the I2C bus
     * @param count the number of bytes to read
     * @param buffer the array to store the bytes in
     * @return true if the command was aborted
     */
    private boolean i2cRead(int count, byte[] buffer) {
        return ultra.transaction(null, 0, buffer, count);
    }
    
}
