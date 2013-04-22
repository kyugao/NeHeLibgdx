package com.kyugao.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;

public class TextureMapping implements Screen {

	private Mesh squareMesh;
	private PerspectiveCamera camera;
	private Texture texture;
	private float rquad = 0;

	@Override
	public void render(float delta) {

		camera.update();
		camera.apply(Gdx.graphics.getGL10());
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glEnable(GL10.GL_DEPTH_TEST);
		Gdx.gl.glEnable(GL10.GL_TEXTURE_2D);

		drawGLScene();
		rquad += 20f * delta; // 每秒钟转20度，计算在每一次render的时间差里，要转的角度。比如0.5秒，那就应该转10度。
		rquad %= 360;
	}

	private boolean drawGLScene() {

		Gdx.gl10.glLoadIdentity();
		Gdx.gl10.glTranslatef(0.0f, 0.0f, -7.0f);
		Gdx.gl10.glRotatef(rquad, 1.0f, 1.0f, 1.0f);
		Gdx.gl10.glTranslatef(0.0f, 0.0f, 7.0f);
		texture.bind();
		for (int i = 0; i < squareMesh.getMaxVertices() / 4; i++) {
			squareMesh.render(GL10.GL_TRIANGLE_FAN, i * 4, 4); // 正方形的6个面分别画出.
		}
		return true;
	}

	@Override
	public void show() {
		squareMesh = new Mesh(true, 24, 24, new VertexAttribute(Usage.Position,
				3, "b_position"), new VertexAttribute(Usage.TextureCoordinates,
				2, "b_texture"));

		squareMesh.setVertices(new float[] {
				// Front face:
				-1f, 1.0f, -6.0f, 0f, 0f, // TL
				-1f, -1.0f, -6.0f, 0f, 1f, // BL
				1f, -1.0f, -6.0f, 1f, 1f, // BR
				1f, 1.0f, -6.0f, 1f, 0f, // TR
				// Top face:
				-1f, 1.0f, -8.0f, 0f, 0f, // LR
				-1f, 1.0f, -6.0f, 0f, 1f, // LN
				1f, 1.0f, -6.0f, 1f, 1f, // RN
				1f, 1.0f, -8.0f, 1f, 0f, // RR
				// Rear face
				1f, 1.0f, -8.0f, 0f, 0f, // TR
				1f, -1.0f, -8.0f, 0f, 1f, // BR
				-1f, -1.0f, -8.0f, 1f, 1f, // BL
				-1f, 1.0f, -8.0f, 1f, 0f, // TL
				// Bottom face
				-1f, -1.0f, -6.0f, 0f, 0f, // LN
				1f, -1.0f, -6.0f, 0f, 1f,// RN
				1f, -1.0f, -8.0f, 1f, 1f, // RR
				-1f, -1.0f, -8.0f, 1f, 0f, // LR
				// Left face
				-1f, 1.0f, -8.0f, 0f, 0f, // TR
				-1f, 1.0f, -6.0f, 0f, 1f, // TN
				-1f, -1.0f, -6.0f, 1f, 1f, // BN
				-1f, -1.0f, -8.0f, 1f, 0f, // BR
				// Right face
				1f, 1.0f, -6.0f, 0f, 0f, // TN
				1f, 1.0f, -8.0f, 1f, 0f, // TR
				1f, -1.0f, -8.0f, 1f, 1f, // BR
				1f, -1.0f, -6.0f, 0f, 1f // BN
				});
		squareMesh.setIndices(new short[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
				11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23 });
		FileHandle imageFileHandle = Gdx.files.internal("data/NeHe.bmp");
		texture = new Texture(imageFileHandle);
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

}
