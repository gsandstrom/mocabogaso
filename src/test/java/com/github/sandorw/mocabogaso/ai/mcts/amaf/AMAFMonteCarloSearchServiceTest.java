package com.github.sandorw.mocabogaso.ai.mcts.amaf;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.sandorw.mocabogaso.Game;
import com.github.sandorw.mocabogaso.ai.AIService;
import com.github.sandorw.mocabogaso.ai.mcts.PlayoutPolicy;
import com.github.sandorw.mocabogaso.ai.mcts.policies.RandomMovePlayoutPolicy;
import com.github.sandorw.mocabogaso.games.GameResult;
import com.github.sandorw.mocabogaso.games.defaults.DefaultGameMove;
import com.github.sandorw.mocabogaso.games.hex.HexGameState;
import com.github.sandorw.mocabogaso.players.AIPlayer;

/**
 * Test cases for AMAFMonteCarloSearchService
 * 
 * @author sandorw
 */
public class AMAFMonteCarloSearchServiceTest {

    @Test
    public void ticTacToeAMAFTest() {
        HexGameState gameState = HexGameState.of(5);
        Game<DefaultGameMove, HexGameState> game = new Game<>(gameState);
        AMAFNodeResultsService nodeResultsService = new AMAFNodeResultsService();
        PlayoutPolicy policy = new RandomMovePlayoutPolicy();
        AIService<DefaultGameMove> oAIService = new AMAFMonteCarloSearchService<>(nodeResultsService, policy, gameState);
        AIService<DefaultGameMove> xAIService = new AMAFMonteCarloSearchService<>(nodeResultsService, policy, gameState);
        game.addPlayer("O", new AIPlayer<>(oAIService, 50));
        game.addPlayer("X", new AIPlayer<>(xAIService, 50));
        game.playGame();
        GameResult gameResult = game.getGameResult();
        assertFalse(gameResult.isTie());
    }
}