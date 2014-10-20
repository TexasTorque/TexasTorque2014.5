package org.texastorque.texastorque20145.feedback;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DigitalInput;
import org.texastorque.texastorque20145.constants.Constants;
import org.texastorque.texastorque20145.torquelib.TorqueCounter;
import org.texastorque.texastorque20145.torquelib.TorqueQuadrature;

public class SensorFeedback extends FeedbackSystem {
    
    //Drivebase
    private TorqueQuadrature leftDriveEncoder;
    private TorqueQuadrature rightDriveEncoder;
    
     //Intake
    private TorqueQuadrature frontIntakeEncoder;
    private TorqueQuadrature rearIntakeEncoder;
    private DigitalInput frontIntakeHallEffect;
    private DigitalInput rearIntakeHallEffect;
    
    public void resetFrontIntakeAngle()
    {
        frontIntakeEncoder.reset();
    }
    
    public void resetRearIntakeAngle()
    {
        rearIntakeEncoder.reset();
    }
    
    //Shooter
    private TorqueCounter flywheelcounter;
    
    public SensorFeedback()
    {
        //Drivebase
        leftDriveEncoder = new TorqueQuadrature(Constants.leftDriveEncoderAPort.getInt(),
                Constants.leftDriveEncoderBPort.getInt(), true, CounterBase.EncodingType.k2X);
        rightDriveEncoder = new TorqueQuadrature(Constants.rightDriveEncoderAPort.getInt(),
                Constants.rightDriveEncoderBPort.getInt(), true, CounterBase.EncodingType.k2X);
        
        leftDriveEncoder.start();
        rightDriveEncoder.start();
        
        //Intake
        frontIntakeEncoder = new TorqueQuadrature(Constants.frontIntakeEncoderAPort.getInt(),
                Constants.frontIntakeEncoderBPort.getInt(), true, CounterBase.EncodingType.k4X);
        rearIntakeEncoder = new TorqueQuadrature(Constants.rearIntakeEncoderAPort.getInt(),
                Constants.rearIntakeEncoderBPort.getInt(), true, CounterBase.EncodingType.k4X);
        frontIntakeHallEffect = new DigitalInput(Constants.frontIntakeHallEffect.getInt());
        rearIntakeHallEffect = new DigitalInput(Constants.rearIntakeHallEffect.getInt());
        
        frontIntakeEncoder.start();
        rearIntakeEncoder.start();
        
        //Shooter
        flywheelcounter = new TorqueCounter(Constants.shooterCounterPort.getInt());
        flywheelcounter.setFilterSize(10);
        
        flywheelcounter.start();
    }
    
    public void run()
    {
        //Drivebase
        leftDriveEncoder.calc();
        rightDriveEncoder.calc();
        
        //Temporary. Needs to be multiplied by scalars to get real units. (e.g ft/s)
        leftPosition = leftDriveEncoder.getPosition();
        leftVelocity = leftDriveEncoder.getRate();
        leftAcceleration = leftDriveEncoder.getAcceleration();
        
        rightPosition = rightDriveEncoder.getPosition();
        rightVelocity = rightDriveEncoder.getRate();
        rightAcceleration = rightDriveEncoder.getAcceleration();
        
        //Intake
        frontIntakeEncoder.calc();
        rearIntakeEncoder.calc();

        //Temporary. Will need to be multiplied by scalar and added to offset to get degrees.
        frontIntakeAngle = frontIntakeEncoder.getPosition();
        rearIntakeAngle = rearIntakeEncoder.getPosition();
        
        //Shooter
        flywheelcounter.calc();
        
        shooterRPM = flywheelcounter.getRate();
    }
}