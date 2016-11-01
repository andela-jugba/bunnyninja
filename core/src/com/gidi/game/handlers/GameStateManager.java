package com.gidi.game.handlers;

import com.gidi.game.BBGame;
import com.gidi.game.states.GameState;
import com.gidi.game.states.Play;

import java.util.Stack;

/**
 * Created by JustMac on 22/10/2016.
 */

public class GameStateManager {

    private BBGame game;

    private Stack<GameState> gameStates;

    public static final  int PLAY = 32344;

    public GameStateManager(BBGame game) {
        this.game = game;
        gameStates = new Stack<GameState>();
        pushState(PLAY);
    }

    public BBGame getGame() {
        return game;
    }

    public void update(float dt) {
        gameStates.peek().update(dt);

    }

    public void render() {
        gameStates.peek().render();
    }

    private GameState getState(int state){
         if (state == PLAY) return  new Play(this);
         return null;
    }

    public void setState(int state){
        popState();
        pushState(state);
    }

    private void popState() {
        GameState g = gameStates.pop();
        g.dispose();
    }

    private void pushState(int state) {
         gameStates.push(getState(state));
    }

}
