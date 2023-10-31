/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {

        // create Ground-floor Rooms
        Room starter = new Room("its the starter place innit");
        Room room1 = new Room("its room 1 innit");
        Room room2 = new Room("its room 2 innit");
        Room room3 = new Room("its room 3 innit");
        Room room4 = new Room("its room 4 innit");
        Room room5 = new Room("its room 5 innit");
        Room room6 = new Room("its room 6 innit");
        Room room7 = new Room("its room 7 innit");
        Room room8 = new Room("its room 8 innit");
        Room room9 = new Room("its room 9 innit");
        Room room10 = new Room("its room 10 innit");
        Room room11 = new Room("its room 11 innit");
        Room room12 = new Room("its room 12 innit");
        Room room13 = new Room("its room 13 innit");
        Room room14 = new Room("its room 14 innit");
        Room room15 = new Room("its room 15 innit");

        // Create Basement-floor Rooms
        Room baseroom1 = new Room("its a basement-room 1 innit");

        // Create First level Rooms


        // initialise Ground-floor room exits | North | East | South | West | Up | Down |

        starter.setExit("North",room3);
        starter.setExit("West",room1);
        starter.setExit("East",room4);

        room1.setExit("East",starter);
        room1.setExit("south",room2);

        room2.setExit("North",room1);

        room3.setExit("East",room10);
        room3.setExit("South",starter);

        room4.setExit("East",room7);
        room4.setExit("South",room5);
        room4.setExit("West",starter);

        room5.setExit("East",room6);
        room5.setExit("North",room4);

        room6.setExit("West",room5);

        room7.setExit("North",room9);
        room7.setExit("East",room8);
        room7.setExit("West",room4);

        room8.setExit("West",room7);

        room9.setExit("North",room12);
        room9.setExit("West",room10);

        room10.setExit("North",room11);
        room10.setExit("East",room9);
        room10.setExit("West",room3);
        baseroom1.setExit("Down", baseroom1);

        room11.setExit("North",room13);
        room11.setExit("South",room10);

        room12.setExit("North",room14);
        room12.setExit("South",room9);

        room13.setExit("East",room14);
        room13.setExit("South",room11);

        room14.setExit("East",room15);
        room14.setExit("South",room12);
        room14.setExit("West",room13);

        room15.setExit("West",room14);

        // initialise Basement-floor room exits | North | East | South | West | Up | Down |
        baseroom1.setExit("Up",room10);

        // initialise First level floor room exits | North | East | South | West | Up | Down |


        currentRoom = starter;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        roomExits();
    }
    private void roomExits(){

        System.out.println("You are " + currentRoom.getDescription());
        System.out.print("Exits: ");
        /** not working atm
        Room.getExitString();
        Room nextRoom = currentRoom.getExit(Room.returnString);
        System.out.println(Room.exits);
         */
        System.out.println();

    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println("   Go | Quit | Help");
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);


        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            roomExits();
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
