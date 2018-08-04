package game.menu;

import game.system.input.PlayerCommand;

/**
 * Prompts user if they want to save
 */
public class AskToSaveMenu extends Menu {

    private static AskToSaveMenu instance;

    private AskToSaveMenu() {

    }
    public static AskToSaveMenu getInstance() {
        if (instance == null) {
            instance = new AskToSaveMenu();
        }
        return instance;
    }

    public void printMainPrompt() {
//        println("Would you like to save the game first?");
//        print("(");
//        printPlayer("Yes");
//        print("/");
//        printPlayer("No");
//        print("/");
//        printPlayer("Return");
//        println(")");
    }


    @Override
    public void processInput(PlayerCommand playerCommand) {
        // TODO
//        if (inputEquals(yes)) {
//            changeToSaveMenu();
//        } else if (inputEquals(no)) {
//            changeToMainMenu();
//        } else if (inputEquals(returnToPreviousMenu)) {
//            changeToPreviousMenu();
//        } else {
//            printMainPrompt();
//        }
    }

    @Override
    public void initializeCommands() {
        // TODO
    }
}
