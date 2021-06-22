package edu.cnm.deepdive.service;

import edu.cnm.deepdive.model.Error;
import edu.cnm.deepdive.model.Game;
import edu.cnm.deepdive.model.Guess;
import java.io.IOException;
import retrofit2.Response;

public class GameRepository {

  private final CodebreakerServiceProxy proxy;

  public GameRepository() {
    proxy = CodebreakerServiceProxy.getInstance();
  }

  public Game newGame(String pool, int length) throws IOException, IllegalArgumentException {
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

  public Guess newGuess(Game game, String text) throws IOException, ValidationException {
    Guess guess = new Guess();
    guess.setText(text);
    Response<Guess> response = proxy.submitGuess(game.getId(), guess).execute();
    if (!response.isSuccessful()) {
      //noinspection ConstantConditions
      Error error = CodebreakerServiceProxy.getGsonInstance()
          .fromJson(response.errorBody().string(), Error.class);
      throw new ValidationException(error);
    }
    return response.body();
  }

  public static class ValidationException extends IllegalArgumentException {

    private final Error error;

    public ValidationException(Error error) {
      this.error = error;
    }

    public Error getError() {
      return error;
    }

  }

}


