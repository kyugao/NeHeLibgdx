package com.kyugao.nehe;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.FPSLogger;
import com.kyugao.screen.Transform;

public class NeHeLibgdx extends Game {

	private FPSLogger fpsLogger;

	@Override
	public void create() {
		// TODO Auto-generated method stub
		setScreen(new Transform());
		fpsLogger = new FPSLogger();
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		super.render();
		fpsLogger.log();
	}

}
