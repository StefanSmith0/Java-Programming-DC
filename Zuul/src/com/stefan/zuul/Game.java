package com.stefan.zuul;

import java.util.ArrayList;

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
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (February 2002)
 */

class Game 
{
    private Parser parser;
    private Room currentRoom;
    Room mainBedroom, hallway, guestBedroom, bathroom, livingRoom, kitchen, stairs, garage, diningRoom, basement, storage, serverRoom, upstairsHallway, closet, masterBedroom, masterBathroom, brotherRoom;
    ArrayList<Item> inventory = new ArrayList<Item>();
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }
    
    public static void main(String[] args) {
    	Game mygame = new Game();
    	mygame.play();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {      
        // create the rooms
    	mainBedroom = new Room("in your friend's bedroom. They sleep like a brick");
    	hallway = new Room("in a nice carpeted hallway");
    	guestBedroom = new Room("in a nice guest bedroom. It's got a popcorn ceiling, though. Eurgh");
    	bathroom = new Room("in a very clean and very small bathroom");
    	livingRoom = new Room("in a classy living room. The couches could put you in a coma");
    	kitchen = new Room("in a suprisingly large kitchen. Wow, kitchens sure look different in the dark");
    	diningRoom = new Room("in an open dining room. In the center is a stained oak table and chairs");
    	basement = new Room("in a cold basement. Not nearly as big as you thought, though");
    	storage = new Room("in a small and cold storage space. An open box of winter clothes catches your eye");
    	serverRoom = new Room("in a cold room full of wires and computers");
    	garage = new Room("in a two-car garage. Only one car occupies it, the rest is full of junk");
    	stairs = new Room("on a wide, wooden staircase. The wall next to it is covered in family photos");
    	upstairsHallway = new Room("in a short, carpeted hallway. People are sleeping up here, gotta be extra quiet");
    	closet = new Room("looking into a messy closet. It's full of board games, sheets, and other stuff");
    	brotherRoom = new Room("in your friends' brothers room. He's kind of scary, so it's best not to stick around");
    	masterBedroom = new Room("in your friends' parents room. Wow, this would be really hard to explain");
    	masterBathroom = new Room("in a luxurious bathroom. You can hear snoring from the other room");
    	
    	//Initialize items
    	Item flashlight = new Item("flashlight");
        Item screwdriver = new Item("screwdriver");
        Item silentsocks = new Item("silentsocks");
        Item garagekey = new Item("garagekey");
    	
        // initialise room exits
    	mainBedroom.setExit("north", hallway);
    	
    	hallway.setExit("north", bathroom);
    	hallway.setExit("east", livingRoom);
    	hallway.setExit("south", mainBedroom);
    	hallway.setExit("west", guestBedroom);
    	
    	bathroom.setExit("south", hallway);
    	
    	guestBedroom.setExit("east", hallway);
    	
    	livingRoom.setExit("north", stairs);
    	livingRoom.setExit("east", diningRoom);
    	livingRoom.setExit("south", kitchen);
    	livingRoom.setExit("west", hallway);
    	
    	diningRoom.setExit("north", garage);
    	diningRoom.setExit("south", basement);
    	diningRoom.setExit("west", livingRoom);
    	
    	garage.setExit("south", diningRoom);
    	garage.setItemToEnter(garagekey);
    	
    	kitchen.setExit("north", livingRoom);
    	
    	basement.setExit("north", diningRoom);
    	basement.setExit("east", storage);
    	basement.setExit("south", serverRoom);
    	basement.setItemToEnter(flashlight);
    	
    	storage.setExit("west", basement);
    	
    	serverRoom.setExit("north", basement);
    	serverRoom.setItemToEnter(screwdriver);
    	
    	stairs.setExit("north", upstairsHallway);
    	stairs.setExit("south", livingRoom);
    	
    	upstairsHallway.setExit("north", masterBedroom);
    	upstairsHallway.setExit("east", closet);
    	upstairsHallway.setExit("south", stairs);
    	upstairsHallway.setExit("west", brotherRoom);
    	
    	masterBedroom.setExit("east", masterBathroom);
    	masterBedroom.setExit("south", upstairsHallway);
    	masterBedroom.setItemToEnter(silentsocks);
    	
    	masterBathroom.setExit("west", masterBedroom);
    	
    	closet.setExit("west", upstairsHallway);
    	
    	brotherRoom.setExit("east", upstairsHallway);
    	brotherRoom.setItemToEnter(silentsocks);
    	
    	//place items in rooms
        closet.setItem(flashlight);
        garage.setItem(screwdriver);
        storage.setItem(silentsocks);
        masterBathroom.setItem(garagekey);

        currentRoom = mainBedroom;  // start game in friend bedroom
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
        System.out.println("Welcome to Zuul!");
        System.out.println("It's 11am. You were drifting off to sleep after a great sleepover at your friend's house");
        System.out.println("when you realize you haven't turned in an assignment due at 12am. You turn on your laptop,");
        System.out.println("and the wifi is down. \n");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
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
            wantToQuit = goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("inventory")) {
        	printInventory();
        }
        else if (commandWord.equals("get")) {
        	getItem(command);
        }
        else if (commandWord.equals("drop")) {
        	dropItem(command);
        }
        return wantToQuit;
    }
    
    /**
     * Responds to the 'get' command. Adds the item from the room to inventory
     * and removes the item from the room.
     * @param command
     */
    private void getItem(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to get...
            System.out.println("Get what?");
            return;
        }

        String item = command.getSecondWord();

        Item newItem = currentRoom.getItem(item);

        if (newItem == null) {
            System.out.println("That item is not here!");
        }
        else {
        	inventory.add(newItem);
        	currentRoom.removeItem(item);
        	System.out.println("Picked up: " + item);
        	}
        }
    
    /**
     * Responds to the 'drop' command. Removes the item from inventory and adds it to the room.
     * @param command
     */
    private void dropItem(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to drop...
            System.out.println("Drop what?");
            return;
        }

        String item = command.getSecondWord();

        Item newItem = null;
        int index = 0;
        		for (int i = 0; i < inventory.size(); i++) {
					if(inventory.get(i).getDescription().equals(item)) {
						newItem = inventory.get(i);
						index = i;
					}
				}

        if (newItem == null) {
            System.out.println("That item is not in your inventory!");
        }
        else {
        	inventory.remove(index);
        	currentRoom.setItem(new Item(item));
        	System.out.println("Dropped: " + item);
        }
    }

    /**
     * Responds to the 'inventory' command. Prints what is in inventory.
     */
    private void printInventory() {
    	String output = "";
    	for (int i = 0; i < inventory.size(); i++) {
			output += inventory.get(i).getDescription() + " ";
		}
    	System.out.println("You are carrying:");
    	System.out.println(output);
	}

	// implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost and alone. You wander");
        System.out.println("around the house.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private boolean goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return false;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        // If room exists and isn't locked, go to room.
        if (nextRoom == null)
            System.out.println("There is no door!");
        else if(!nextRoom.isUnlocked(inventory)) {
        	System.out.println("You can't get in there, you need a " + nextRoom.getItemToEnter().getDescription());
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            if (currentRoom == serverRoom) {
            	System.out.println("After getting into the server room, you reboot the router.");
            	System.out.println("You sneak back to your room, submit the assignment, and sigh in relief.");
            	System.out.println("You realize you should probably put the garage key back now.");
            	return true;
            }
        }
        return false;
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game. Return true, if this command
     * quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else
            return true;  // signal that we want to quit
    }
}
