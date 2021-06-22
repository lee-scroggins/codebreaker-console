package edu.cnm.deepdive;

import edu.cnm.deepdive.model.Game;
import edu.cnm.deepdive.model.Guess;
import edu.cnm.deepdive.service.GameRepository;
import edu.cnm.deepdive.service.GameRepository.ValidationException;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Main {

  private static final String POOL = "AEIOUY";
  private static final String BUNDLE_NAME = "strings";
  private static final int CODE_LENGTH = 4;
  private static final String REPLAY_PROMPT_KEY = "replay_prompt";
  private static final char NEGATIVE_RESPONSE = 'n';
  private static final String INITIAL_STATUS_KEY = "initial_status_format";
  private static final String GUESS_PROMPT_KEY = "guess_prompt_format";
  private static final String GUESS_RESULTS_KEY = "guess_results_format";
  private static final String VALIDATION_ERROR_KEY = "validation_error_format";

  public static void main(String[] args) throws IOException {
    Scanner scanner = new Scanner(System.in);
    ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME);
    GameRepository repository = new GameRepository();
    do {
      play(scanner, bundle, repository);
    } while (queryReplay(scanner, bundle));  // End of do-while
  }

  private static void play(Scanner scanner, ResourceBundle bundle, GameRepository repository)
      throws IOException {
    String initialStatusFormat = bundle.getString(INITIAL_STATUS_KEY);
    String guessPromptFormat = bundle.getString(GUESS_PROMPT_KEY);
    String guessResultsFormat = bundle.getString(GUESS_RESULTS_KEY);
    String validationErrorFormat = bundle.getString(VALIDATION_ERROR_KEY);
    Game game = repository.newGame(POOL, CODE_LENGTH);
    System.out.printf(initialStatusFormat, game.getLength(), game.getPool());
    Guess guess =null;
    boolean validationFailed;
    do {
      System.out.printf(guessPromptFormat, game.getLength(), game.getPool());
      String input = scanner.nextLine().trim().toUpperCase();
      try {
      guess = repository.newGuess(game, input);
      validationFailed = false;
        System.out.printf(guessResultsFormat,
            guess.getText(), guess.getExactMatches(), guess.getNearMatches());
      } catch (ValidationException e) {
        validationFailed = true;
        System.out.printf(validationErrorFormat, input,
            e.getError().getDetails().getText());
      }
    } while (validationFailed || !guess.isSolution());
  }

  private static boolean queryReplay(Scanner scanner, ResourceBundle bundle) {
    System.out.println(bundle.getString(REPLAY_PROMPT_KEY));
    String input = scanner.nextLine().trim().toLowerCase();
    return (input.isEmpty() || input.charAt(0) != NEGATIVE_RESPONSE);
  }
}
