package com.kyugao.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.loaders.ModelLoaderRegistry;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;

public class Blend implements Screen, InputProcessor {

	private PerspectiveCamera camera;
	private Texture texture;
	private float rquad = 0;

	// indicator of light and blend
	private boolean blend;

	private StillModel model;

	@Override
	public void render(float delta) {

		camera.update();
		camera.apply(Gdx.graphics.getGL10());
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glEnable(GL10.GL_DEPTH_TEST);
		Gdx.gl.glEnable(GL10.GL_TEXTURE_2D);

		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);

		drawGLScene();
		rquad += 20f * delta; // 每秒钟转20度，计算在每一次render的时间差里，要转的角度。比如0.5秒，那就应该转10度。
		rquad %= 360;
	}

	private boolean drawGLScene() {

		Gdx.gl10.glLoadIdentity();
		Gdx.gl10.glTranslatef(0.0f, 0.0f, -7.0f);
		Gdx.gl10.glRotatef(rquad, 1.0f, 1.0f, 1.0f);
		texture.bind();
		model.render();
		return true;
	}

	@Override
	public void show() {
		FileHandle modelFile = Gdx.files.internal("g3d/cube.obj");
		model = ModelLoaderRegistry.loadStillModel(modelFile);
		texture = new Texture(Gdx.files.internal("data/textureglass.png"));
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

	@Override
	public void resize(int width, int height) {
		float aspectRatio = (float) width / (float) height;
		camera = new PerspectiveCamera(45, 2f * aspectRatio, 2f);
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.B) {
			System.out.println("B down");
			blend = !blend;
			if (blend) {
				Gdx.gl.glEnable(GL10.GL_BLEND);
				Gdx.gl.glDisable(GL10.GL_DEPTH_TEST);
			} else {
				Gdx.gl.glEnable(GL10.GL_DEPTH_TEST);
				Gdx.gl.glDisable(GL10.GL_BLEND);
			}
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
