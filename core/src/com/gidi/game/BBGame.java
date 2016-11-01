package com.gidi.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gidi.game.handlers.GameStateManager;
import com.gidi.game.handlers.MyInput;
import com.gidi.game.handlers.MyInputProcessor;
import com.gidi.game.resources.Content;

public class BBGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	public static final String TITLE = "Bunny Hop";
	public static final int V_WIDTH = 320;
	public static final int V_HEIGHT = 240;
	public static final int SCALE = 2;

    public static final float STEP = 1 / 60f;
    private float accum;

	private SpriteBatch sb;
	private OrthographicCamera cam;
	private  OrthographicCamera hudCam;

    private GameStateManager gsm;
    public static Content res;

	@Override
	public void create () {
        // sets input Adapter
        Gdx.input.setInputProcessor( new MyInputProcessor());

        res = new Content();
        res.loadTexture("images/bunny.png", "bunny");
        res.loadTexture("images/crystal.png", "crystal");
        res.loadTexture("images/hud.png", "hud");

        sb = new SpriteBatch();
        cam = new OrthographicCamera();
        hudCam = new OrthographicCamera();

        cam.setToOrtho(false, V_WIDTH, V_HEIGHT) ;
        hudCam.setToOrtho(false, V_WIDTH, V_HEIGHT);
        gsm = new GameStateManager(this);

	}

    public SpriteBatch getSpriteBatch() {
        return sb;
    }

    public OrthographicCamera getCam() {
        return cam;
    }

    public OrthographicCamera getHudCam() {
        return hudCam;
    }

    @Override
	public void render () {
		gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render();
        MyInput.uodate();
	}
	
	@Override
	public void dispose () {
        sb.dispose();
        res.disposeTexure("bunny");
	}

	@Override
	public void pause() {

	}


}
