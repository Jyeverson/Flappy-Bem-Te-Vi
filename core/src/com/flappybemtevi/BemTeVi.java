package com.flappybemtevi;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.Random;

public class BemTeVi extends ApplicationAdapter {
	SpriteBatch batch;
	Texture obstaculoBaixo;
	Texture obstaculoCima;
	Texture[] passaros;
	Texture imagemFundo;

	float posicaoX = 0;
    float posicaoY;
    float velocidade;
	float altura;
    float vao;
    float contador = 0.0f;
    float gravidade = 2;

    Random alturaRandom;

	@Override
	public void create () {
		batch = new SpriteBatch();

		alturaRandom = new Random();

		obstaculoBaixo = new Texture("canobaixo.png");
        obstaculoCima = new Texture("canocima.png");
		imagemFundo = new Texture("imagemfundo.png");

        posicaoX = Gdx.graphics.getWidth();
        posicaoY = Gdx.graphics.getHeight() / 2;
        altura = Gdx.graphics.getHeight() / 2;
        vao = 300;

        passaros = new Texture[6];
        for (int i = 0; i < passaros.length; i++) {
            passaros[i] = new Texture("passaro" + String.valueOf(i + 1) + ".png");
        }

	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();

		posicaoX -= Gdx.graphics.getDeltaTime()*500;

		if(posicaoX <- obstaculoBaixo.getWidth()) {
			posicaoX = (float) Gdx.graphics.getWidth();

			altura += (alturaRandom.nextFloat() - 0.5f) * 150;

		}

		if (posicaoY > 0 || velocidade < 0){
		    velocidade = velocidade + gravidade;
		    posicaoY -= velocidade * Gdx.graphics.getDeltaTime() * 30;
        }

		if(Gdx.input.justTouched()){
			velocidade = -30;

		}

		contador += Gdx.graphics.getDeltaTime() * 10;
		if (contador > 6) {contador = 0;}

        batch.draw(imagemFundo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(obstaculoBaixo, posicaoX, altura - vao / 2 - obstaculoBaixo.getHeight());
        batch.draw(obstaculoCima, posicaoX, altura + vao / 2 );
        batch.draw(obstaculoBaixo, posicaoX, 0);
		batch.draw(passaros[(int) contador], 50, posicaoY);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		obstaculoBaixo.dispose();
	}
}
