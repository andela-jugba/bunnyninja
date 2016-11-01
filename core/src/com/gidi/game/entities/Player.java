package com.gidi.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.gidi.game.BBGame;

/**
 * Created by JustMac on 23/10/2016.
 */

public class Player extends B2DSprite {

    private int numCrystals;
    private int totalCrystals;

    public Player(Body body) {
        super(body);

        Texture tex = BBGame.res.getTexture("bunny");
        TextureRegion[] sprites = TextureRegion.split(tex, 32, 32)[0];
        setAnimation(sprites, 1 / 12f);
    }

    public int getNumCrystals() {
        return numCrystals;
    }

    public int getTotalCrystals() {
        return totalCrystals;
    }

    public void setTotalCrystals(int i) {
        totalCrystals = i;
    }
    public void collectCrystal() {
        numCrystals++;
    }
}
