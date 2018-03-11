package FollowObject;

import edu.cmu.ri.createlab.terk.robot.finch.Finch;
import java.util.Scanner;

public class Test {
	static Finch myF = new Finch();
    static int BuzzFeq = 300; 						//Buzz frequency
    static int Duration = 1250; 					//Buzz duration for buzzer and wheels 
    static int Turn = -50; 							//Sets turning speed
    static int NormSpeed = 100;
    static int TurnSpeed;
    static double AverageSpeed = 0;
    static double SpeedAddedStops = 0;
    static double TravelTime= 0;
    static int StopCount = 0;
    static double StopTime = 0;
    static double TimeSeconds;
    static double SleepTime;
    static int Sleep = 1000;
    static int TapCount= 2; 						//Counts how many times finch is tapped
    
    public static void main(String[] args) 
    {
    	TimeSeconds = ((Duration + Sleep)/1000);
    	SleepTime =  Sleep / 1000;
/**/    myF.setLED(255,168,0); 						//Test
/**/	System.out.println("Connected"); 			//Test 
    	TurnSpeed = (((NormSpeed - Turn) / 2) + Turn);
    	
    	while (myF.isFinchUpsideDown() == false){
    		if (TapCount== 0) {
    			myF.setLED(1,1,1);					//Check if finch is connected
    			if (myF.isTapped() == true) {
    				if (myF.isObstacleLeftSide() == true && myF.isObstacleRightSide() == true) {
    					TapCount= TapCount+ 1;
/**/    				System.out.print("Test"); 	//Test
    		}}}    		
    		if (TapCount> 0){
    			if (myF.isTapped() == true) {
    				TapCount= TapCount+ 1;
    		}}
/**/			System.out.println(myF.isTapped());	//Test
/**/			System.out.println(TapCount);		//Test
			if (TapCount== 1 || TapCount== 2) { 	
/**/		            myF.setLED(0,0,255);		//Test				 
		            Move();
			}else if (TapCount== 3 || TapCount> 3){
				myF.quit();							//Disconnects the finch
				Stats();
			}
        }
    }
    
    public static void Stats() 
    {
        System.out.println("Would you like to view the statistical results?");
        System.out.println("y or n");
        Scanner input = new Scanner(System.in);
        String YorN = input.nextLine();
        switch (YorN) { 							//allows the user to choose to view statistical results or not
            case "y":
            	
            	double StopTimeRound = Math.round(StopTime * 100)/100.0;
            	double Sped = Math.round((AverageSpeed / SpeedAddedStops)* 100)/100.0;
            	
                System.out.println("The available statistical results are average speed(1), total travel time(2), number of stops(3), and total time stopped(4).");
                System.out.println("Please enter the indicated number in brackets to the statistic result, or enter 'all' to view all of them");
                Scanner userChoice = new Scanner(System.in);
                String StatSelect = input.nextLine();
                switch (StatSelect) { 				//User picks stats to view
                    case "1": //AverageSpeed
                        System.out.println("The average speed was " + Sped + ".");
                        break;
                    case "2": 
                        System.out.println("The total travel time was " + TravelTime+ " seconds.");
                        break;
                    case "3": 
                        System.out.println("The number of stops was " + StopCount + ".");
                        break;
                    case "4": 
                        System.out.println("The total time stopped was " + StopTime + " seconds.");
                        break;
                    case "all": 
                        System.out.println("The average speed was " + Sped + ".");
                        System.out.println("The total travel time was " + TravelTime+ " seconds.");
                        System.out.println("The number of stops was " + StopCount + ".");
                        System.out.println("The total time stopped was " + StopTimeRound + " seconds.");
                        break;						//exit
                    default:
                        break; 						//exit
                }
                break;
            case "n":
            	System.out.print("Thank you for using the finch, the program has now ended.");
                System.exit(0); 					//exit
            default:
                break; 								//exit
        }
    }

    public static void Move() 
    {
    	//Sensor checks
        if (myF.isObstacleRightSide() == false && myF.isObstacleLeftSide() == false) { 
            ONWARDS();
        } else if (myF.isObstacleRightSide() == false && myF.isObstacleLeftSide() == true) { 
            Hidari();
        } else if (myF.isObstacleRightSide() == true && myF.isObstacleLeftSide() == false) { 
            Migi();
        } else if (myF.isObstacleRightSide() == true && myF.isObstacleLeftSide() == true) { 
           CEASE();
        }
    }

    public static void ONWARDS() 
    {
        myF.setLED(0,255,0); 						//Green
        myF.buzz(BuzzFeq, Duration); 
        myF.setWheelVelocities(NormSpeed, NormSpeed, Duration); 
        myF.sleep(Sleep);
        SpeedAddedStops = SpeedAddedStops + 1.0; 	//keeps track of the speed calculation divider
        AverageSpeed = AverageSpeed + NormSpeed; 	//adds to the average speed
        TravelTime = TravelTime + TimeSeconds; 		//adds to the time travelled counter
    }

    public static void Hidari() 					//JPN L
    {
        myF.setLED(0,255,0); 						//Green
        myF.buzz(BuzzFeq, Duration); 
        myF.setWheelVelocities(Turn, NormSpeed, Duration); 
        myF.sleep(Sleep);
        SpeedAddedStops = SpeedAddedStops + 1.0; 	//keeps track of the speed calculation divider
        AverageSpeed = AverageSpeed + TurnSpeed; 	//adds to the average speed
        TravelTime = TravelTime + TimeSeconds; 		//adds one second to the time travelled counter
    }

    public static void Migi() 						//JPN R
    {
        myF.setLED(0,255,0); 						//Green
        myF.buzz(BuzzFeq, Duration); 
        myF.setWheelVelocities(NormSpeed, Turn, Duration); 
        myF.sleep(Sleep);
        SpeedAddedStops = SpeedAddedStops + 1.0; 	//keeps track of the speed calculation divider
        AverageSpeed = AverageSpeed + TurnSpeed; 	//adds to the average speed
        TravelTime = TravelTime + TimeSeconds; 		//adds to the time travelled counter
    }

    public static void CEASE() 
    {
        myF.setLED(255,0,0); 						//Red
        myF.buzz(0, Duration); 
        myF.stopWheels(); 
        myF.sleep(Sleep);
        SpeedAddedStops = SpeedAddedStops + 1.0; 	//keeps track of the speed calculation divider
        StopTime = StopTime + 0.1;		 			//adds one second to the time stopped counter
        StopCount = StopCount +1; 					//adds one to the amount of times stopped counter
    }
	
}
