package com.kyugao.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;

public class FilterLightingKeyboard implements Screen, InputProcessor {

	private Mesh squareMesh;
	private PerspectiveCamera camera;
	private Texture[] textureArray;
	private int filter = 0;
	private float xrot = 0, yrot = 0;
	private float xspeed = 20, yspeed = 20;

	// indicator for controlling the light
	private boolean light;
	private boolean lp, fp;
	private float z = -5.0f;

	// Ambient Light
	float[] ambientLight = { 0.5f, 0.5f, 0.5f, 1.0f };
	// Diffuse Light
	float[] diffuseLight = { 1.0f, 1.0f, 1.0f, 1.0f };
	// Light Position
	float[] position = { 0.0f, 0.0f, 2.0f, 1.0f };

	@Override
	public void render(float delta) {

		camera.update();
		camera.apply(Gdx.graphics.getGL10());
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glEnable(GL10.GL_DEPTH_TEST);
		Gdx.gl.glEnable(GL10.GL_TEXTURE_2D);
		drawGLScene();
		xrot += xspeed * delta; // 每秒钟转20度，计算在每一次render的时间差里，要转的角度。比如0.5秒，那就应该转10度。
		xrot %= 360;
		yrot += yspeed * delta;
		yrot %= 360;
	}

	private boolean drawGLScene() {
		Gdx.gl10.glPushMatrix();
		Gdx.gl10.glLoadIdentity();
		Gdx.gl10.glTranslatef(0.0f, 0.0f, z);
		Gdx.gl10.glRotatef(xrot, 1.0f, 0.0f, 0.0f);
		Gdx.gl10.glRotatef(yrot, 0.0f, 1.0f, 0.0f);
		textureArray[filter].bind();
		for (int i = 0; i < squareMesh.getMaxVertices() / 4; i++) {
			squareMesh.render(GL10.GL_TRIANGLE_FAN, i * 4, 4); // 正方形的6个面分别画出.
		}
		Gdx.gl10.glPopMatrix();
		return true;
	}

	@Override
	public void show() {
		// init mesh
		squareMesh = new Mesh(true, 24, 24, new VertexAttribute(Usage.Position,
				3, "b_position"), new VertexAttribute(Usage.TextureCoordinates,
				2, "b_texture"));

		squareMesh.setVertices(new float[] {
				// Front face:
				-1f, 1.0f, 1.0f, 0f, 0f, // TL
				-1f, -1.0f, 1.0f, 0f, 1f, // BL
				1f, -1.0f, 1.0f, 1f, 1f, // BR
				1f, 1.0f, 1.0f, 1f, 0f, // TR
				// Top face:
				-1f, 1.0f, -1.0f, 0f, 0f, // LR
				-1f, 1.0f, 1.0f, 0f, 1f, // LN
				1f, 1.0f, 1.0f, 1f, 1f, // RN
				1f, 1.0f, -1.0f, 1f, 0f, // RR
				// Rear face
				1f, 1.0f, -1f, 0f, 0f, // TR
				1f, -1.0f, -1f, 0f, 1f, // BR
				-1f, -1.0f, -1f, 1f, 1f, // BL
				-1f, 1.0f, -1f, 1f, 0f, // TL
				// Bottom face
				-1f, -1.0f, 1f, 0f, 0f, // LN
				1f, -1.0f, 1f, 0f, 1f,// RN
				1f, -1.0f, -1f, 1f, 1f, // RR
				-1f, -1.0f, -1f, 1f, 0f, // LR
				// Left face
				-1f, 1.0f, -1f, 0f, 0f, // TR
				-1f, 1.0f, 1f, 0f, 1f, // TN
				-1f, -1.0f, 1f, 1f, 1f, // BN
				-1f, -1.0f, -1f, 1f, 0f, // BR
				// Right face
				1f, 1.0f, 1f, 0f, 0f, // TN
				1f, 1.0f, -1f, 1f, 0f, // TR
				1f, -1.0f, -1f, 1f, 1f, // BR
				1f, -1.0f, 1f, 0f, 1f // BN
				});
		squareMesh.setIndices(new short[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
				11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23 });

		// init texture
		FileHandle imageFileHandle = Gdx.files.internal("data/Crate.bmp");
		textureArray = new Texture[3];
		textureArray[0] = new Texture(imageFileHandle);
		textureArray[0].setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		textureArray[1] = new Texture(imageFileHandle);
		textureArray[1].setFilter(TextureFilter.Linear, TextureFilter.Linear);
		textureArray[2] = new Texture(imageFileHandle, true);
		textureArray[2].setFilter(TextureFilter.MipMap, TextureFilter.Linear);

		// init light
		Gdx.gl.glEnable(GL10.GL_LIGHT1);
		Gdx.gl10.glLightfv(GL10.GL_LIGHT1, GL10.GL_AMBIENT, ambientLight, 0);
		Gdx.gl10.glLightfv(GL10.GL_LIGHT1, GL10.GL_DIFFUSE, diffuseLight, 0);
		Gdx.gl10.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, position, 0);

		// Set input processor
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
		if (keycode == Input.Keys.L && !lp) {
			System.out.println("l key downing");
			lp = true;
			light = !light;
			if (light) {
				Gdx.gl.glEnable(GL10.GL_LIGHTING);
			} else {
				Gdx.gl.glDisable(GL10.GL_LIGHTING);
			}
		} else if (keycode == Input.Keys.F && !fp) {
			fp = true;
			filter = (filter + 1) % 3;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Input.Keys.L && lp) {
			System.out.println("l key up");
			lp = false;
		} else if (keycode == Input.Keys.F && fp) {
			fp = false;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		System.out.println(amount);
		z += amount;
		return false;
	}

}
