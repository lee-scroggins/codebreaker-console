package edu.cnm.deepdive;

import edu.cnm.deepdive.model.Game;
import edu.cnm.deepdive.service.GameRepository;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Main {

  private static final String POOL = "AEIOUY";
  private static final String BUNDLE_NAME = "strings";
  private static final int CODE_LENGTH = 7;
  private static final String REPLAY_PROMPT_KEY = "replay_prompt";
  private static final char NEGATIVE_RESPONSE = 'n';

  public static void main(String[] args) throws IOException {
    Scanner scanner = new Scanner(System.in);
    ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME);
    GameRepository repository = new GameRepository();
    // TODO Create proxy class for service.
    do {
      // TODO Play a single game.
      Game game = repository.newGame(POOL, CODE_LENGTH);
      System.out.printf("Secret code of length %d generated from the pool \"%s\".%n",
          game.getLength(), game.getPool());
    } while (queryReplay(scanner, bundle));      repository.newGame(POOL, CODE_LENGTH);

  }

  private static boolean queryReplay(Scanner scanner, ResourceBundle bundle) {
    System.out.println(bundle.getString(REPLAY_PROMPT_KEY));
    String input = scanner.nextLine().trim().toLowerCase();
    return (input.isEmpty() || input.charAt(0) != NEGATIVE_RESPONSE);
  }
}
