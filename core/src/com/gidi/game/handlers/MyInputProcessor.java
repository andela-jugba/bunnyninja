package com.gidi.game.handlers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

/**
 * Created by JustMac on 22/10/2016.
 */

public class MyInputProcessor extends InputAdapter {
    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.Z) {
            MyInput.setKey(MyInput.BUTTON1, true);
        }else if (keycode == Input.Keys.X) {
            MyInput.setKey(MyInput.BUTTON2, true);
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.Z) {
            MyInput.setKey(MyInput.BUTTON1, false);
        }else if (keycode == Input.Keys.X) {
            MyInput.setKey(MyInput.BUTTON2, false);
        }
        return super.keyUp(keycode);
    }
}
