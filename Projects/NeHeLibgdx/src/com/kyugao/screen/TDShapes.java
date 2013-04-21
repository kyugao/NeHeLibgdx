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
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		drawGLScene();
		rquad += 20f * delta; // 每秒钟转20度，计算在每一次render的时间差里，要转的角度。比如0.5秒，那就应该转10度。
		rquad %= 360;
	}

	private boolean drawGLScene() {
		Gdx.gl10.glLoadIdentity();
		Gdx.gl10.glTranslatef(-1.5f, 0.0f, -6.0f);
		Gdx.gl10.glRotatef(rquad, 0.0f, 1.0f, 0.0f);
		Gdx.gl10.glTranslatef(1.5f, 0.0f, 6.0f);
		triangleMesh.render(GL10.GL_TRIANGLES, 0, 3);

		Gdx.gl10.glLoadIdentity();
		Gdx.gl10.glTranslatef(1.5f, 0.0f, -6.0f);
		Gdx.gl10.glRotatef(rquad, 1.0f, 0.0f, 0.0f);
		Gdx.gl10.glTranslatef(-1.5f, 0.0f, 6.0f);
		squareMesh.render(GL10.GL_TRIANGLE_STRIP, 0, 4);
		return true;
	}

	@Override
	public void show() {
		triangleMesh = new Mesh(true, 3, 3, new VertexAttribute(Usage.Position,
				3, "a_position"), new VertexAttribute(Usage.ColorPacked, 4,
				"a_color"));
		squareMesh = new Mesh(true, 4, 4, new VertexAttribute(Usage.Position,
				3, "b_position"), new VertexAttribute(Usage.ColorPacked, 4,
				"b_color"));
		triangleMesh.setVertices(new float[] { //
				-1.5f, 1.0f, -6.0f, // point 1
						Color.toFloatBits(1f, 0f, 0f, 1f), // color 1
						-2.5f, -1.0f, -6.0f, // point 2
						Color.toFloatBits(0f, 1f, 0f, 1f), // color 2
						-0.5f, -1.0f, -6.0f,// point 3
						Color.toFloatBits(0f, 0f, 1f, 1f) // color 3
				});
		triangleMesh.setIndices(new short[] { 0, 1, 2 });

		squareMesh.setVertices(new float[] { 0.5f, -1.0f, -6.0f,// point BL
				Color.toFloatBits(0.5f, 0.5f, 1f, 1f), // color 1
				2.5f, -1.0f, -6.0f,// point BR
				Color.toFloatBits(0.5f, 0.5f, 1f, 1f), // color 1
				0.5f, 1.0f, -6.0f, // point TL
				Color.toFloatBits(0.5f, 0.5f, 1f, 1f), // color 1
				2.5f, 1.0f, -6.0f, // point TR
				Color.toFloatBits(0.5f, 0.5f, 1f, 1f) // color 1
				});
		squareMesh.setIndices(new short[] { 0, 1, 2, 3 });
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
