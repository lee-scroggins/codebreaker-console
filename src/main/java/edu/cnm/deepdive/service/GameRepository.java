package edu.cnm.deepdive.service;

import edu.cnm.deepdive.model.Game;
import edu.cnm.deepdive.model.Guess;
import java.io.IOException;
import retrofit2.Response;

public class GameRepository {

  private final CodebreakerServiceProxy proxy;

  public GameRepository() {
    proxy = CodebreakerServiceProxy.getInstance();
  }

  public Game newGame(String pool, int length) throws IOException {
    Game gameStub = new Game();
    gameStub.setPool(pool);
    gameStub.setLength(length);
    // Uses a Retrofit Call object to execute the HTTP request and obtain the response.
    Response<Game> response = proxy.startGame(gameStub).execute();
    if (!response.isSuccessful()) {
      throw new IllegalArgumentException();
    } // end if
    return response.body();
  }

  public Guess newGuess(Game game, String text) throws IOException {
    Guess guess = new Guess();
    guess.setText(text);
    Response<Guess> response = proxy.submitGuess(game.getId(), guess).execute();
    if (!response.isSuccessful()) {
      throw new IllegalArgumentException();
    }
    return response.body();
  }

  // TODO Define methods for obtaining a single existing game, the list of guesses in a game, and
  //  submitting a new guess.

}

