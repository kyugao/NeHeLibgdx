package com.kyugao.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;

public class FirstPolygon implements Screen {

	private Mesh triangleMesh, squareMesh;
	private PerspectiveCamera camera;

	@Override
	public void render(float delta) {

		camera.update();
		camera.apply(Gdx.graphics.getGL10());
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		drawGLScene();
	}

	private boolean drawGLScene() {
		triangleMesh.render(GL10.GL_TRIANGLES, 0, 3);
		squareMesh.render(GL10.GL_TRIANGLE_STRIP, 0, 4);
		return true;
	}

	@Override
	public void show() {
		triangleMesh = new Mesh(true, 3, 3, new VertexAttribute(Usage.Position,
				3, "a_position"));
		squareMesh = new Mesh(true, 4, 4, new VertexAttribute(Usage.Position,
				3, "b_position"));
		triangleMesh.setVertices(new float[] { //
				-1.5f, 1.0f, -6.0f, // point 1
						-2.5f, -1.0f, -6.0f, // point 2
						-0.5f, -1.0f, -6.0f }); // point 3
		triangleMesh.setIndices(new short[] { 0, 1, 2 });

		squareMesh.setVertices(new float[] { 0.5f, -1.0f, -6.0f,// point BL
				2.5f, -1.0f, -6.0f,// point BR
				0.5f, 1.0f, -6.0f, // point TL
				2.5f, 1.0f, -6.0f // point TR
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
