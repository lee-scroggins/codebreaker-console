package edu.cnm.deepdive;

import java.util.ResourceBundle;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    ResourceBundle bundle = ResourceBundle.getBundle("strings");
    // TODO Create proxy class for service.
    do {
      // TODO Play a single game.
    } while (queryReplay(scanner, bundle));

  }

  private static boolean queryReplay(Scanner scanner, ResourceBundle bundle) {
    System.out.println(bundle.getString("replay_prompt"));
    String input = scanner.nextLine().trim().toLowerCase();
    return (input.isEmpty() || input.charAt(0) != 'n');
  }
}
