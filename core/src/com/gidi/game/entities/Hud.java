package com.gidi.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gidi.game.BBGame;
import com.gidi.game.handlers.B2DVars;

import java.util.HashMap;

/**
 * Created by JustMac on 23/10/2016.
 */

public class Hud{
    private Player player;
    private TextureRegion[] blocks;

    public Hud(Player player) {
        this.player = player;

        Texture tex = BBGame.res.getTexture("hud");

        blocks = new TextureRegion[3];
        for (int i = 0; i < blocks.length; i++) {
            blocks[i] = new TextureRegion(tex, 32 + i * 16, 0, 16, 16);
        }

    }

    public void render(SpriteBatch sb) {
        short bits = player.getBody().getFixtureList().first().getFilterData().maskBits;
        sb.begin();
        if ((bits & B2DVars.BIT_RED) != 0) {
            sb.draw(blocks[0], 40, 200);
        }
        if ((bits & B2DVars.BIT_GREEN) != 0) {
            sb.draw(blocks[1], 40, 200);
        }
        if ((bits & B2DVars.BIT_BLUE) != 0) {
            sb.draw(blocks[2], 40, 200);
        }
        sb.end();
    }
}
