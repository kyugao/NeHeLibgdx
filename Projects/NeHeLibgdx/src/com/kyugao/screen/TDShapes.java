package com.kyugao.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;

public class TDShapes implements Screen {

	private Mesh triangleMesh, squareMesh;
	private PerspectiveCamera camera;
	private float rquad = 0;

	@Override
	public void render(float delta) {

		camera.update();
		camera.apply(Gdx.graphics.getGL10());
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glEnable(GL10.GL_DEPTH_TEST);

		drawGLScene();
		rquad += 20f * delta; // 每秒钟转20度，计算在每一次render的时间差里，要转的角度。比如0.5秒，那就应该转10度。
		rquad %= 360;
	}

	private boolean drawGLScene() {
		Gdx.gl10.glLoadIdentity();
		Gdx.gl10.glTranslatef(-1.5f, 0.0f, -6.0f);
		Gdx.gl10.glRotatef(rquad, 0.0f, 1.0f, 0.0f);
		Gdx.gl10.glTranslatef(1.5f, 0.0f, 6.0f);
		triangleMesh.render(GL10.GL_TRIANGLES, 0, 12);

		Gdx.gl10.glLoadIdentity();
		Gdx.gl10.glTranslatef(1.5f, 0.0f, -7.0f);
		Gdx.gl10.glRotatef(rquad, 1.0f, 1.0f, 1.0f);
		Gdx.gl10.glTranslatef(-1.5f, 0.0f, 7.0f);
		for (int i = 0; i < squareMesh.getMaxVertices() / 4; i ++) {
			squareMesh.render(GL10.GL_TRIANGLE_FAN, i * 4, 4); // 正方形的6个面分别画出.
		}
		return true;
	}

	@Override
	public void show() {
		triangleMesh = new Mesh(true, 12, 12, new VertexAttribute(
				Usage.Position, 3, "a_position"), new VertexAttribute(
				Usage.ColorPacked, 4, "a_color"));
		squareMesh = new Mesh(true, 24, 24, new VertexAttribute(Usage.Position,
				3, "b_position"), new VertexAttribute(Usage.ColorPacked, 4,
				"b_color"));
		triangleMesh.setVertices(new float[] {
				//
				-1.5f, 1.0f, -6.0f, Color.toFloatBits(1f, 0f, 0f, 1f), // point-1
				-2.5f, -1.0f, -5.0f, Color.toFloatBits(0f, 1f, 0f, 1f), // point-2
				-0.5f, -1.0f, -5.0f, Color.toFloatBits(0f, 0f, 1f, 1f),// point-3
				-1.5f, 1.0f, -6.0f, Color.toFloatBits(1f, 0f, 0f, 1f),// point-4
				-0.5f, -1.0f, -5.0f, Color.toFloatBits(0f, 0f, 1f, 1f),// point-5
				-0.5f, -1.0f, -7.0f, Color.toFloatBits(0f, 1f, 0f, 1f),// point-6
				-1.5f, 1.0f, -6.0f, Color.toFloatBits(1f, 0f, 0f, 1f),// point-7
				-0.5f, -1.0f, -7.0f, Color.toFloatBits(0f, 1f, 0f, 1f),// point-8
				-2.5f, -1.0f, -7.0f, Color.toFloatBits(0f, 0f, 1f, 1f),// point-9
				-1.5f, 1.0f, -6.0f, Color.toFloatBits(1f, 0f, 0f, 1f),// point-10
				-2.5f, -1.0f, -7.0f, Color.toFloatBits(0f, 0f, 1f, 1f), // point-11
				-2.5f, -1.0f, -5.0f, Color.toFloatBits(0f, 1f, 0f, 1f) // point-12
				});
		triangleMesh.setIndices(new short[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
				11 });

		squareMesh.setVertices(new float[] {
				// Front face:
				0.5f, 1.0f, -6.0f, Color.toFloatBits(0f, 0f, 1f, 1f), // TL
				0.5f, -1.0f, -6.0f, Color.toFloatBits(0f, 0f, 1f, 1f), // BL
				2.5f, -1.0f, -6.0f, Color.toFloatBits(0f, 0f, 1f, 1f), // BR
				2.5f, 1.0f, -6.0f, Color.toFloatBits(0f, 0f, 1f, 1f), // TR
				// Top face:
				0.5f, 1.0f, -8.0f, Color.toFloatBits(1f, 0f, 1f, 1f), // LR
				0.5f, 1.0f, -6.0f, Color.toFloatBits(1f, 0f, 1f, 1f), // LN
				2.5f, 1.0f, -6.0f, Color.toFloatBits(1f, 0f, 1f, 1f), // RN
				2.5f, 1.0f, -8.0f, Color.toFloatBits(1f, 0f, 1f, 1f), // RR
				// Rear face
				2.5f, 1.0f, -8.0f, Color.toFloatBits(0f, 1f, 0f, 1f), // TR
				2.5f, -1.0f, -8.0f, Color.toFloatBits(0f, 1f, 0f, 1f), // BR
				0.5f, -1.0f, -8.0f, Color.toFloatBits(0f, 1f, 0f, 1f), // BL
				0.5f, 1.0f, -8.0f, Color.toFloatBits(0f, 1f, 0f, 1f), // TL
				// Bottom face
				0.5f, -1.0f, -6.0f, Color.toFloatBits(0.5f, 0.5f, 0.5f, 1f), // LN
				2.5f, -1.0f, -6.0f, Color.toFloatBits(0.5f, 0.5f, 0.5f, 1f), // RN
				2.5f, -1.0f, -8.0f, Color.toFloatBits(0.5f, 0.5f, 0.5f, 1f), // RR
				0.5f, -1.0f, -8.0f, Color.toFloatBits(0.5f, 0.5f, 0.5f, 1f), // LR
				// Left face
				0.5f, 1.0f, -8.0f, Color.toFloatBits(1f, 1f, 0f, 1f), // TR
				0.5f, 1.0f, -6.0f, Color.toFloatBits(1f, 1f, 0f, 1f), // TN
				0.5f, -1.0f, -6.0f, Color.toFloatBits(1f, 1f, 0f, 1f), // BN
				0.5f, -1.0f, -8.0f, Color.toFloatBits(1f, 1f, 0f, 1f), // BR
				// Right face
				2.5f, 1.0f, -6.0f, Color.toFloatBits(0f, 1f, 1f, 1f), // TN
				2.5f, 1.0f, -8.0f, Color.toFloatBits(0f, 1f, 1f, 1f), // TR
				2.5f, -1.0f, -8.0f, Color.toFloatBits(0f, 1f, 1f, 1f), // BR
				2.5f, -1.0f, -6.0f, Color.toFloatBits(0f, 1f, 1f, 1f), // BN
		});
		squareMesh.setIndices(new short[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
				11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23 });
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
