package com.gidi.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gidi.game.BBGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = BBGame.TITLE;
		config.width = BBGame.V_WIDTH * BBGame.SCALE;
		config.height = BBGame.V_HEIGHT * BBGame.SCALE;
		new LwjglApplication(new BBGame(), config);
	}
}
