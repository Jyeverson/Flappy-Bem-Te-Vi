package com.flappybemtevi;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class BemTeVi extends ApplicationAdapter {
	SpriteBatch batch;
	Texture obstaculoBaixo;
	Texture obstaculoCima;
	Texture passaro;
	Texture imagemFundo;
	Float posicaoX = 0f;

	@Override
	public void create () {
		batch = new SpriteBatch();
		obstaculoBaixo = new Texture("canobaixo.png");
        obstaculoCima = new Texture("canocima.png");
		imagemFundo = new Texture("imagemfundo.png");
        passaro = new Texture("passao1.png");
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
        batch.draw(imagemFundo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(obstaculoBaixo, posicaoX, 0);
        batch.draw(obstaculoCima, posicaoX, obstaculoBaixo.getHeight());
        batch.draw(passaro, 50, Gdx.graphics.getHeight() / 2);
        batch.draw(obstaculoBaixo, posicaoX, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		obstaculoBaixo.dispose();
	}
}
