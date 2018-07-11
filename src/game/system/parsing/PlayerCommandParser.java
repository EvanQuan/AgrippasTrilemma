package game.system.parsing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import game.object.item.background.character.Player;
import game.system.parsing.words.Article;
import game.system.parsing.words.DirectObjectPhrase;
import game.system.parsing.words.IndirectObjectPhrase;
import game.system.parsing.words.ObjectPhrase;
import game.system.parsing.words.Preposition;
import game.system.parsing.words.Verb;
import util.ArrayUtils;

/**
 * Parses a string into a {@link PlayerCommand}. The parser abides by the
 * following grammar rules:
 * <p>
 * 1. The dictionary of all possible verbs, adjectives, direct objects, and
 * indirect objects is not known.<br>
 * - The game handles the validity of these words, not the parser.<br>
 * 2. The first word of the input is always a verb.<br>
 * - Player commands are 2nd person imperative statements.<br>
 * 3. Indirect object phrases are always preceded by a preposition.<br>
 * 4. Direct object phrases are always positioned before indirect object
 * phrases.<br>
 * 5. The dictionary of all possible prepositions is known.<br>
 * 6. The dictionary of all possible articles is known.<br>
 *
 * @author Evan Quan
 *
 */
public abstract class PlayerCommandParser {

    // NOTE: For now, only "," as end punctuation will count, as quotes are causing problems with syntactical analysis
    /**
     * Defines the type of punctuation that can exist at the start of a word that will split and count as its own token.
     */
//    public static final char[] START_PUNCTUATION = { '\'', '"' };
    public static final char[] START_PUNCTUATION = {};
    /**
     * Defines the type of punctuation that can exist at the end of a word that will split and cont as its own token.
     */
//    public static final char[] END_PUNCTUATION = { '\'', '"', ',' };
    public static final char[] END_PUNCTUATION = {','};

    public static final String[] VALID_PREPOSITIONS = {};
    /**
     * Splits token by punctuation and adds punctuation components to tokens.<br>
     * - Double and single quotes at the start or end of words<br>
     * - Commas after a word<br>
     * - Other punctuation and symbols are stripped and ignored.
     *
     * @param tokens
     * @param token
     */
    private static void addToken(ArrayList<String> tokens, String token) {

        char firstChar = token.charAt(0);
        if (ArrayUtils.contains(START_PUNCTUATION, firstChar)) {
            tokens.add(Character.toString(firstChar));
            token = token.substring(1, token.length());
        }

        boolean changedLastChar = false;
        String endQuote = "";
        char lastChar = token.charAt(token.length() - 1);
        if (ArrayUtils.contains(END_PUNCTUATION, lastChar)) {
            endQuote = Character.toString(lastChar);
            token = token.substring(0, token.length() - 1);
            changedLastChar = true;
        }

        tokens.add(token);
        // End quote is added after word to preserve token order
        if (changedLastChar) {
            tokens.add(endQuote);
        }
    }

    /**
     * Find an objective phrase. Can either be a {@link DirectObjectPhrase} or an
     * {@link IndirectObjectPhrase}.
     *
     * @param tokens
     * @return
     */
    private static ObjectPhrase findObjectPhrase(ArrayList<String> tokens) {
        // Scan for an article. If one is found, remove it and parse the rest of the
        // input.
        // The last word in the input is the object. Remove it and parse the rest of the input
        // If any input remains, they are adjectives which modify the object.
        return null;
    }

    /**
     * <b>Step 1: Lexical Analysis</b>
     * <p>
     * Splits the input string into tokens, each representing a word of the command.
     * The tokens are in the same order as they appear in the input string. Each
     * character of punctuation counts as its own token only if it is a single or
     * double quote around a word, or a comma after a word.
     *
     * @param input
     *            - input String
     * @return list of all tokens.
     */
    public static ArrayList<String> lexicalAnalysis(String input) {
        // NOTE: There's probably a better way to do this that doesn't use Scanner.
        // aka. Split by spaces, then map reduce.
        Scanner in = new Scanner(input);
        ArrayList<String> tokens = new ArrayList<String>();

        // Add all tokens
        while (in.hasNext()) {
            String token = in.next();
            addToken(tokens, token);
        }
        in.close();
        return tokens;
        // Right, now just using basic split by spaces. May need to change this when things get more complicated
//        return new ArrayList<>(Arrays.asList(input.split(" ")));
    }

    /**
     * Parse input text into words and apply their appropriate meanings and
     * relationships. Accepts only imperative statements.
     *
     * @param input
     *            - String to parse into words
     * @return command that represents the player {@link PlayerCommand}
     */
    public static PlayerCommand parse(String input) {
        // Add unaltered input to PlayerCommand
        PlayerCommand playerCommand = new PlayerCommand(input);
        // https://groups.google.com/forum/#!topic/rec.arts.int-fiction/VpsWZdWRnlA
        ArrayList<String> tokens = lexicalAnalysis(input);
        syntacticalAnalysis(playerCommand, tokens);

        return playerCommand;
    }

    /**
     * <b>Step 2: Syntactical Analysis</b>
     * <p>
     * Takes a sequence of tokens and sees whether the sequences matches a known
     * correct sentence structure.
     * <p>
     * <b>Grammar Rules</b><br>
     * 0. The dictionary of all possible verbs, adjectives, direct objects, and
     * indirect objects is <b>not</b> known.<br>
     * 1. The first world of the input is a always a {@link Verb}.<br>
     * 2. Indirect object phrases are always preceded by a preposition.<br>
     * 3. Direct object phrases are always positioned before indirect object
     * phrases.<br>
     * 4. The dictionary of all possible {@link Preposition}s is known.<br>
     * 5. The dictionary of all possible {@link Article}s is known.
     *
     * @param playerCommand
     * @param tokens
     * @return
     */
    private static void syntacticalAnalysis(PlayerCommand playerCommand, ArrayList<String> tokens) {
        // 0. The first word is a verb. Remove it and parse the rest of the input.
        // No adverbs are allowed as it would not be possible to distinguish between the
        // end of the verb phrase and the start of the proceeding indirect/direct object
        // phrase without a dictionary of all possible verbs.
        playerCommand.setVerbPhrase(new Verb(tokens.remove(0)));
        // 1. Scan for a preposition. If one is found, remove it. Parse the input
        // preceding the preposition as a direct object phrase. Parse the input
        // following the preposition as an indirect object phrase.
        // For the sake of how the PlayerCommand will be parsed in the game, the preposition
        // is added to the indirect object phrase.

        // Add first tokens before preposition (if any) to direct tokens.
        // If there is a preposition, store it by itself.
        ArrayList<String> directTokens = new ArrayList<>();
        Preposition preposition;
        int i;
        for (i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);
            if (Preposition.isValid(token)) {
                preposition = new Preposition(token);
                break;
            } else {
                directTokens.add(token);
            }
        }
        // Add remaining tokens after preposition (if any) to indirect tokens.
        ArrayList<String> indirectTokens = new ArrayList<>();
        for (; i < tokens.size(); i++) {
            indirectTokens.add(tokens.get(i));
        }
        int indirectObjectIndex = 0;
        int directObjectIndex = 0;
//        while (Prepositions tokens.get(0))
    }

//    /**
//     * <b>Part 3: Translation</b>
//     * <p>
//     * TODO Creates a player command from a list of words. Depending on the relation
//     * between words, the action {@link Verb} and object {@link Noun} are
//     * determined.
//     *
//     * @param input
//     *            - original input string
//     * @param statement
//     * @return
//     */
//    private static PlayerCommand translation(String input, Sentence statement) {
//        // Index tracking
//        int actionIndex = 0;
//        int objectIndex = 0;
//        // 1. The first word of the command should either be a verb, or a shortcut
//        // represents some action
//
//        // return new PlayerCommand(command, action, object);
//        return null;
//    }
}
