package FollowObject;

import edu.cmu.ri.createlab.terk.robot.finch.Finch;
import java.util.Scanner;

public class Test {
	static Finch myF = new Finch();
    static int buzz = 300; //sets buzz frequency
    static int Duration = 1250; //sets buzz duration as 100 milliseconds
    static int Turn = -50; //sets left turning speed
    static double averageSpeed = 0; //average speed is set to 0 to start
    static double speedAddedStops = 0; //used to calculate how many numbers were added to the average speed variable
    static double travelTime = 0; //keeps track of the travel time in seconds
    static int stopCount = 0; //keeps track of the amount of times the finch stops
    static double TotalStopStime = 0; //keeps track of the total time the finch was stopped for
    static int tapcount = 0; //Counts how many times finch is tapped
    
    public static void main(String[] args) {
    	//myF.setLED(255,168,0); //test LED
        //if Finch is tapped, and an object is in front of it, the beak turns red
        //System.out.println("Connected"); 		//Test 
    	while (myF.isFinchUpsideDown() == false)
		{
    		if (tapcount == 0) {
    			myF.setLED(1,1,1);	//Check if finch is connected
    			if (myF.isTapped() == true) {
    				if (myF.isObstacleLeftSide() == true && myF.isObstacleRightSide() == true) {
    					tapcount = tapcount + 1;
    					//System.out.print("Test"); 	//Test
    				}
    			}
    		}
    		
    		if (tapcount > 0){
    			if (myF.isTapped() == true) {
    				tapcount = tapcount + 1;
    			}
    		}
			//System.out.println(myF.isTapped());		//Test
			//System.out.println(tapcount);				//Test
			if (tapcount == 1 || tapcount == 2) {
				//if (//myF.isTapped() == true && myF.isObstacleLeftSide() == true && myF.isObstacleRightSide() == true) { 	
		            myF.setLED(0,0,255); //turns beak colour to red
		            Move();
			}else if (tapcount == 3 || tapcount >3 ){
				myF.quit();
				Stats();
			}
        } 
    }
    
    public static void Stats() {
        System.out.println("Would you like to view the statistical results of the execution?");
        System.out.println("y or n");
        Scanner input = new Scanner(System.in);
        String userResponse = input.nextLine();
        switch (userResponse) { //allows the user to choose to view statistical results or not
            case "y":
                System.out.println("The available statistical results are average speed(1), total travel time(2), number of stops(3), and total time stopped(4).");
                System.out.println("Please enter the indicated number in brackets to the statistic result, or enter 'all' to view all of them");
                Scanner userChoice = new Scanner(System.in);
                String resultChoice = input.nextLine();
                switch (resultChoice) { //allows user to pick a statistical result to view
                    case "1": //first choice is average speed
                        System.out.println("The average speed was " + (averageSpeed / speedAddedStops) + ".");
                        break;
                    case "2": //second choice is total travel time
                        System.out.println("The total travel time was " + travelTime + " seconds.");
                        break;
                    case "3": //third choice is number of stops
                        System.out.println("The number of stops was " + stopCount + ".");
                        break;
                    case "4": //fourth choice is total time stopped
                        System.out.println("The total time stopped was " + TotalStopStime + " seconds.");
                        break;
                    case "all": //displays all the statistical results
                        System.out.println("The average speed was " + (averageSpeed / speedAddedStops) + ".");
                        System.out.println("The total travel time was " + travelTime + " seconds.");
                        System.out.println("The number of stops was " + stopCount + ".");
                        System.out.println("The total time stopped was " + TotalStopStime + " seconds.");
                        break;
                    default:
                        break; //exits program
                }
                break;
            case "n":
            	System.out.print("Thank you for using the finch, the program has now ended.");
                System.exit(0);; //exits program
            default:
                break; //exits program
        }
    }

    public static void Move() {
        if (myF.isObstacleRightSide() == false && myF.isObstacleLeftSide() == false) { //neither sensors detect an object
            MoveStraight();
        } else if (myF.isObstacleRightSide() == false && myF.isObstacleLeftSide() == true) { //one of obstacle sensors detect an object
            MoveLeft();
        } else if (myF.isObstacleRightSide() == true && myF.isObstacleLeftSide() == false) { //one of obstacle sensors detect an object
            MoveRight();
        } else if (myF.isObstacleRightSide() == true && myF.isObstacleLeftSide() == true) { //both sensors detect an object not moving
            StopMoving();
        }
    }

    public static void MoveStraight() {
        myF.setLED(0,255,0); //sets beak to green
        myF.buzz(buzz, Duration); //creates a buzzing noise for 100 milliseconds
        myF.setWheelVelocities(100, 100, Duration); //moves finch forward
        myF.sleep(1000);
        speedAddedStops = speedAddedStops + 1.0; //keeps track of the speed calculation divider
        averageSpeed = averageSpeed + 100; //adds 100 to the average speed
        travelTime = travelTime + 0.1; //adds one second to the time travelled counter
    }

    public static void MoveLeft() {
        myF.setLED(0,255,0); //sets beak to green
        myF.buzz(buzz, Duration); //creates a buzzing noise for 100 milliseconds
        myF.setWheelVelocities(Turn, 100, Duration); //moves finch to the left
        myF.sleep(1000);
        speedAddedStops = speedAddedStops + 1.0; //keeps track of the speed calculation divider
        averageSpeed = averageSpeed + 100; //adds 100 to the average speed
        travelTime = travelTime + 0.1; //adds one second to the time travelled counter
    }

    public static void MoveRight() {
        myF.setLED(0,255,0); //sets beak to green
        myF.buzz(buzz, Duration); //creates a buzzing noise for 100 milliseconds
        myF.setWheelVelocities(100, Turn, Duration); //moves finch to the right
        myF.sleep(1000);
        speedAddedStops = speedAddedStops + 1.0; //keeps track of the speed calculation divider
        averageSpeed = averageSpeed + 100; //adds 100 to the average speed
        travelTime = travelTime + 0.1; //adds one second to the time travelled counter
    }

    public static void StopMoving() {
        myF.setLED(255,0,0); //sets beak to red
        myF.buzz(0, Duration); //creates a buzzing noise with 0 hertz (it's silent)
        myF.stopWheels(); //stops the robots wheels from moving
        myF.sleep(1000);
        speedAddedStops = speedAddedStops + 1.0; //keeps track of the speed calculation divider
        TotalStopStime = TotalStopStime + 0.1; //adds one second to the time stopped counter
        stopCount++; //adds one to the amount of times stopped counter
    }
	
}
