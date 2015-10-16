package com.github.sandorw.mocabogaso;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.sandorw.mocabogaso.games.defaults.DefaultGameMove;
import com.github.sandorw.mocabogaso.games.hex.HexGameState;
import com.github.sandorw.mocabogaso.players.AIPlayerFactory;
import com.github.sandorw.mocabogaso.players.HumanPlayer;

public class Mocabogaso {
    private static Logger LOGGER = LoggerFactory.getLogger(Mocabogaso.class);

    public static void main(String args[]) {
        LOGGER.info("Starting game of Hex");
        
        HexGameState gameState = HexGameState.of(9);
        Game<DefaultGameMove, HexGameState> game = new Game<>(gameState);
        game.addPlayer("X", AIPlayerFactory.getNewMultiThreadedAMAFAIPlayer(gameState, 3000, 2));
        game.addPlayer("O", new HumanPlayer<>());
        game.playGame();
    }
}
