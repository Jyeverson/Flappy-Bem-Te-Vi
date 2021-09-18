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


	float posicaoX = 0;
    float altura;
    float vao;

	@Override
	public void create () {
		batch = new SpriteBatch();
		obstaculoBaixo = new Texture("canobaixo.png");
        obstaculoCima = new Texture("canocima.png");
		imagemFundo = new Texture("imagemfundo.png");
        passaro = new Texture("passao1.png");

        posicaoX = (float) Gdx.graphics.getWidth();
        altura = Gdx.graphics.getHeight() / 2;
        vao = 300;
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
		batch.draw(obstaculoBaixo, posicaoX, altura - vao / 2 - obstaculoBaixo.getHeight());
        batch.draw(obstaculoCima, posicaoX, altura + vao / 2 );
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
