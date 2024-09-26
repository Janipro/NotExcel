package sheep.ui;

import java.util.Optional;

/**
 * An interface to get the UI to display a variety of prompts
 * @provided
 */
public interface Prompt {
    /**
     * A prompt that requests text input
     *
     * @param prompt the text displayed to the user.
     * @return the text the user types in.
     */
    Optional<String> ask(String prompt);

    /**
     * A prompt that requests a series of text inputs
     *
     * @param prompts the text associated with each input
     * @return the responses the user typed in.
     */
    Optional<String[]> askMany(String[] prompts);

    /**
     * A prompt that requests a true or false response.
     *
     * @param prompt the text displayed to the user.
     * @return whether the user agreed.
     */
    boolean askYesNo(String prompt);

    /**
     * A prompt that just displays text to the user.
     *
     * @param prompt the text displayed to the user.
     */
    void message(String prompt);

    /**
     * A prompt that requests the user select a file to open.
     *
     * @return the absolute filepath chosen or null if they cancelled out.
     */
    String openFile();

    /**
     * A prompt that requests the user select a location to save a file.
     *
     * @return the absolute filepath chosen or null if they cancelled out.
     */
    String saveFile();
}
