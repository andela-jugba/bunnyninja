package com.gidi.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gidi.game.BBGame;
import com.gidi.game.handlers.GameStateManager;

/**
 * Created by JustMac on 22/10/2016.
 */

public abstract class GameState {
    protected  GameStateManager gsm;
    protected BBGame game;

    protected SpriteBatch sb;
    protected OrthographicCamera cam;
    protected OrthographicCamera hudCam;

    protected GameState(GameStateManager gsm){
        this.gsm = gsm;
        game = gsm.getGame();
        sb = game.getSpriteBatch();
        cam = game.getCam();
        hudCam = game.getHudCam();

    }

    public abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render();
    public abstract void dispose();
}
