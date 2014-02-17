/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.team811.devices;


/**
 * A runner class that polls the Ultrasonic Sensor in the background
 * @author Developer
 */
public class UltrasonicRunner {

    private Ultrasonic ultra;    //The sensor
    private int reading = 0;     //The last reading
    private int avg_reading = 0; //The average of the last three readings
    private double slope = 0;    //The slope of the last two readings
    private Ultra_Reader runner; //The background thread

    public UltrasonicRunner() {
        ultra = new Ultrasonic();
    }
    
    /**
     * Starts the background thread that polls the sensor
     */
    public void start() {
        if(runner != null) {
            runner.stop();
        }
        runner = new Ultra_Reader();
        runner.start();
    }

    /**
     * Stops the background thread that polls the sensor
     */
    public void stop() {
        runner.stop();
    }
    
    /**
     * Says whether the background thread is running
     * @return true if the thread is running, false otherwise
     */
    public boolean isRunning() {
        if(runner != null) {
            return runner.isRunning();
        }
        return false;
    }
    
    /**
     * Gives the average of the last three readings
     * @return the average of the last three readings in centimeters
     */
    public int getAverageReading() {
        return avg_reading;
    }

    /**
     * Gives the last reading
     * @return the last reading taken in centimeters
     */
    public int getLastReading() {
        return reading;
    }

    /**
     * Gives the difference of the last 
     * @return 
     */
    public double getSlope() {
        return slope;
    }

    /**
     * Get the Ultrasonic sensor instance
     *
     * @return the Ultrasonic sensor
     */
    public Ultrasonic getUltrasonic() {
        return ultra;
    }

    /**
     * Constantly running thread that reports range multiple times every second
     */
    private class Ultra_Reader extends Thread {
        
        private boolean run = true;
        private short avg[] = new short[3];
        private long last_time = 0;
        
        
        public Ultra_Reader() {
        }

        public void run() {
            while (run) {
                ultra.takeRangeReadingOnSensor();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                short r = ultra.requestRange();
                if (r != -1) {
                    //set reading
                    reading = r;

                    //set average reading
                    avg[0] = avg[1];
                    avg[1] = avg[2];
                    avg[2] = r;
                    avg_reading = (avg[0] + avg[1] + avg[2]) / 3;
                    
                    //set slope
                    if(last_time != 0) {
                        slope = (double)(avg[2]-avg[1])/(double)(System.currentTimeMillis()-last_time);
                    }
                    last_time = System.currentTimeMillis();
                }
            }
        }
        
        public void stop() {
            run = false;
        }
        
        public boolean isRunning() {
            return run;
        }
        
    }
}
