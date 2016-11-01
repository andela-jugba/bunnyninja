package com.gidi.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.gidi.game.BBGame;

/**
 * Created by JustMac on 23/10/2016.
 */

public class Crystal extends B2DSprite {
    public Crystal(Body body) {
        super(body);

        Texture tex = BBGame.res.getTexture("crystal");
        TextureRegion[] sprites = TextureRegion.split(tex, 16, 16)[0];

        setAnimation(sprites, 1 / 12f);
    }
}
