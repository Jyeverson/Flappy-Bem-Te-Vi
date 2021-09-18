package com.flappybemtevi;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class BemTeVi extends ApplicationAdapter {
	SpriteBatch batch;
	Texture obstaculoBaixo;


	Float posicaoX = 0f;

	@Override
	public void create () {
		batch = new SpriteBatch();
		obstaculoBaixo = new Texture("canobaixo.png");
		posicaoX = (float) Gdx.graphics.getWidth();
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();

		posicaoX -= Gdx.graphics.getDeltaTime()*500;

		if(posicaoX <- obstaculoBaixo.getWidth()) {
			posicaoX = (float) Gdx.graphics.getWidth();
		}

		batch.draw(obstaculoBaixo, posicaoX, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		obstaculoBaixo.dispose();
	}
}
