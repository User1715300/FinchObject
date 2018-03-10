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
    static double averageSpeed = 0;
    static double speedAddedStops = 0;
    static double travelTime = 0;
    static int stopCount = 0;
    static double TotalStopStime = 0;
    static int tapcount = 0; 						//Counts how many times finch is tapped
    static double TimeSeconds;
    static int Sleep = 100;
    
    public static void main(String[] args) 
    {
    	TimeSeconds = Duration/1000;
    	//myF.setLED(255,168,0); 					//Test
/**/	System.out.println("Connected"); 			//Test 
    	TurnSpeed = (((NormSpeed - Turn) / 2) + Turn) ;
    	//System.out.println(TurnSpeed);
    	
    	while (myF.isFinchUpsideDown() == false){
    		if (tapcount == 0) {
    			myF.setLED(1,1,1);					//Check if finch is connected
    			if (myF.isTapped() == true) {
    				if (myF.isObstacleLeftSide() == true && myF.isObstacleRightSide() == true) {
    					tapcount = tapcount + 1;
/**/    					System.out.print("Test"); //Test
    				}
    			}
    		}
    		if (tapcount > 0){
    			if (myF.isTapped() == true) {
    				tapcount = tapcount + 1;
    			}
    		}
/**/			System.out.println(myF.isTapped());		//Test
/**/			System.out.println(tapcount);			//Test
			if (tapcount == 1 || tapcount == 2) { 	
/**/		            myF.setLED(0,0,255);				//Test				 
		            Move();
			}else if (tapcount == 3 || tapcount > 3){
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
        switch (YorN) { //allows the user to choose to view statistical results or not
            case "y":
                System.out.println("The available statistical results are average speed(1), total travel time(2), number of stops(3), and total time stopped(4).");
                System.out.println("Please enter the indicated number in brackets to the statistic result, or enter 'all' to view all of them");
                Scanner userChoice = new Scanner(System.in);
                String StatSelect = input.nextLine();
                switch (StatSelect) { //User picks stats to view
                    case "1": 
                        System.out.println("The average speed was " + (averageSpeed / speedAddedStops) + ".");
                        break;
                    case "2": 
                        System.out.println("The total travel time was " + travelTime + " seconds.");
                        break;
                    case "3": 
                        System.out.println("The number of stops was " + stopCount + ".");
                        break;
                    case "4": 
                        System.out.println("The total time stopped was " + TotalStopStime + " seconds.");
                        break;
                    case "all": 
                        System.out.println("The average speed was " + (averageSpeed / speedAddedStops) + ".");
                        System.out.println("The total travel time was " + travelTime + " seconds.");
                        System.out.println("The number of stops was " + stopCount + ".");
                        System.out.println("The total time stopped was " + TotalStopStime + " seconds.");
                        break;
                    default:
                        break; //exit
                }
                break;
            case "n":
            	System.out.print("Thank you for using the finch, the program has now ended.");
                System.exit(0);; //exit
            default:
                break; //exit
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
        myF.setLED(0,255,0); 	//Green
        myF.buzz(BuzzFeq, Duration); 
        myF.setWheelVelocities(NormSpeed, NormSpeed, Duration); 
        myF.sleep(Sleep);
        speedAddedStops = speedAddedStops + 1.0; //keeps track of the speed calculation divider
        averageSpeed = averageSpeed + NormSpeed; //adds to the average speed
        travelTime = travelTime + 0.1; //adds to the time travelled counter
    }

    public static void Hidari() //JPN L
    {
        myF.setLED(0,255,0); 	//Green
        myF.buzz(BuzzFeq, Duration); 
        myF.setWheelVelocities(Turn, NormSpeed, Duration); 
        myF.sleep(Sleep);
        speedAddedStops = speedAddedStops + 1.0; //keeps track of the speed calculation divider
        averageSpeed = averageSpeed + TurnSpeed; //adds to the average speed
        travelTime = travelTime + 0.1; //adds one second to the time travelled counter
    }

    public static void Migi() //JPN R
    {
        myF.setLED(0,255,0); 	//Green
        myF.buzz(BuzzFeq, Duration); 
        myF.setWheelVelocities(NormSpeed, Turn, Duration); 
        myF.sleep(Sleep);
        speedAddedStops = speedAddedStops + 1.0; //keeps track of the speed calculation divider
        averageSpeed = averageSpeed + TurnSpeed; //adds to the average speed
        travelTime = travelTime + 0.1; //adds to the time travelled counter
    }

    public static void CEASE() 
    {
        myF.setLED(255,0,0); 	//Red
        myF.buzz(0, Duration); 
        myF.stopWheels(); 
        myF.sleep(Sleep);
        speedAddedStops = speedAddedStops + 1.0; //keeps track of the speed calculation divider
        TotalStopStime = TotalStopStime + 0.1; //adds one second to the time stopped counter
        stopCount++; //adds one to the amount of times stopped counter
    }
	
}
