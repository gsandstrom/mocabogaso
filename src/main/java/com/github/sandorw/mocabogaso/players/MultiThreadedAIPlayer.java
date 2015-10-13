package com.github.sandorw.mocabogaso.players;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.github.sandorw.mocabogaso.ai.AIService;
import com.github.sandorw.mocabogaso.games.GameMove;
import com.github.sandorw.mocabogaso.games.GameResult;
import com.github.sandorw.mocabogaso.games.GameState;

/**
 * Multithreaded AI Player implementation.
 * 
 * @author sandorw
 */
public class MultiThreadedAIPlayer<GM extends GameMove> implements Player<GM> {
    private final AIService<GM> aiService;
    private final int allottedTimeMs;
    private final int numThreads;
    private final ExecutorService executor;
    
    public MultiThreadedAIPlayer(AIService<GM> aiService, int allottedTimeMs, int numThreads) {
        this.aiService = aiService;
        this.allottedTimeMs = allottedTimeMs;
        this.numThreads = numThreads;
        executor = Executors.newFixedThreadPool(numThreads);
    }
    
    @Override
    public <GS extends GameState<GM, ? extends GameResult>> GM chooseNextMove(GS currentGameState) {
        for (int i=0; i < numThreads; ++i) {
            executor.submit(() -> {
                aiService.searchMoves(currentGameState, allottedTimeMs);
            });
        }
        try {
            Thread.sleep(allottedTimeMs);
        } catch (InterruptedException e) {
            return aiService.selectMove();
        }
        return aiService.selectMove();
    }

    @Override
    public <GS extends GameState<GM, ? extends GameResult>> 
            void informOfMoveMade(GM move, GS resultingGameState) {
        aiService.applyMove(move, resultingGameState);
    }

    @Override
    public void shutdown() {
        executor.shutdownNow();
    }
}