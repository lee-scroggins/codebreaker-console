package edu.cnm.deepdive;

import edu.cnm.deepdive.service.GameRepository;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) throws IOException {
    Scanner scanner = new Scanner(System.in);
    ResourceBundle bundle = ResourceBundle.getBundle("strings");
    GameRepository repository = new GameRepository();
    // TODO Create proxy class for service.
    do {
      // TODO Play a single game.
      repository.newGame("0123456789", 3);
    } while (queryReplay(scanner, bundle));
  }

  private static boolean queryReplay(Scanner scanner, ResourceBundle bundle) {
    System.out.println(bundle.getString("replay_prompt"));
    String input = scanner.nextLine().trim().toLowerCase();
    return (input.isEmpty() || input.charAt(0) != 'n');
  }
}
