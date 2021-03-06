package game.menu;

import game.system.input.PlayerCommand;
import game.system.output.IPrintBuffer;

/**
 * Mediates post-game decisions Asks to save before returning to MainMenu Asks
 * player to load or restart after death/victory
 */
public class GameOverMenu extends Menu {

    public static final int LOSE = 0;
    public static final int WIN = 1;
    private static GameOverMenu instance;

    private String title;

    /**
     * Default GameOverMenu constructor
     */
    private GameOverMenu() {
        setMode(LOSE);
    }

    public static GameOverMenu getInstance() {
        if (instance == null) {
            instance = new GameOverMenu();
        }
        return instance;
    }

    /**
     * Process a {@link PlayerCommand} as receiveInput. This will set some
     * corresponding output to this menu's currently set {@link IPrintBuffer}.
     */
    public void processInput() {
        // TODO
    }

    /**
     * Create all valid stringCommands for this menu. Use addStringCommand().
     */
    protected void initializeCommands() {
        // TODO
    }

    /**
     * Input has already been validated. Do stuff before processing valid
     * input.
     */
    @Override
    protected void preProcessInput() {

    }

    /**
     * Retrieves information about the playerCommand after it has been process.
     * This may influence how future commands are processed. This is only ran if
     * isValidInput() is successful.
     */
    @Override
    protected void postProcessInput() {

    }

    public void printMainPrompt() {
//        printTitleln(title);
//
//        printPlayer("Restart");
//        print(" or ");
//        printPlayer("Load");
//        print(" a game save, or ");
//        printPlayer("Return");
//        print(" to the ");
//        printLocation(" Main Menu");
//        println(".");
    }

    /**
     * Prints a message signifying that the user can inputted invalid input.
     */
    @Override
    protected void printInvalidInput() {

    }

    /**
     * Mode determines printMainPrompt text and decisions???
     *
     * @param mode of menu
     */
    public void setMode(int mode) {
        switch (mode) {
            case LOSE:
                title = "Oh no, you died!";
                break;
            case WIN:
                title = "Hooray, you won!";
                break;
        }
    }

    public void setWin() {
        setMode(WIN);
    }

    public void setLose() {
        setMode(LOSE);
    }

}
