package com.flappybemtevi;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import java.util.Random;

public class BemTeVi extends ApplicationAdapter {
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;

    Texture obstaculoBaixo;
    Texture obstaculoCima;
    Texture[] passaros;
    Texture imagemFundo;

    float posicaoX = 0;
    float posicaoXpassaro = 0;
    float posicaoY;
    float velocidade;
    float altura;
    float vao;
    float contador = 0.0f;
    float gravidade = 2;
    int pontos = 0;
    boolean marcouPonto;
    int estadoJogo = 0;

    Random alturaRandom;

    BitmapFont textoPontuacao;
    BitmapFont textoGameOver;

    Circle circuloPassaro;
    Rectangle retanguloCima;
    Rectangle retanguloBaixo;

    Sound somHit;
    Sound somScore;

    @Override
    public void create () {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        alturaRandom = new Random();

        obstaculoBaixo = new Texture("canobaixo.png");
        obstaculoCima = new Texture("canocima.png");
        imagemFundo = new Texture("imagemfundo.png");


        posicaoX = Gdx.graphics.getWidth();
        posicaoY = Gdx.graphics.getHeight()/2;
        altura = Gdx.graphics.getHeight()/2;
        vao = 300;

        passaros = new Texture[2];
        for(int i = 0; i<passaros.length; i++) {
            passaros[i] = new Texture("passaro"+String.valueOf(i+1)+".png");
        }

        circuloPassaro = new Circle();
        retanguloBaixo = new Rectangle();
        retanguloCima = new Rectangle();

        textoPontuacao = new BitmapFont();
        textoPontuacao.setColor(Color.WHITE);
        textoPontuacao.getData().setScale(10);

        textoGameOver = new BitmapFont();
        textoGameOver.setColor(Color.GREEN);
        textoGameOver.getData().setScale(2);

        somHit = Gdx.audio.newSound(Gdx.files.internal("somHit.mp3"));
        somScore = Gdx.audio.newSound(Gdx.files.internal("somScore.mp3"));

    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(estadoJogo==0) {
            if (Gdx.input.justTouched()) {
                velocidade = -30;
                estadoJogo = 1;
            }
        }else if(estadoJogo==1){
            posicaoX-=Gdx.graphics.getDeltaTime()*500;

            if(posicaoX < -obstaculoBaixo.getWidth()){
                posicaoX = Gdx.graphics.getWidth();

                altura = Gdx.graphics.getHeight()/2;
                altura -= (alturaRandom.nextFloat() - 0.5f) * 150;
                marcouPonto=false;
            }

            if (Gdx.input.justTouched()) {
                velocidade = -30;
            }

            if (posicaoY > 0 || velocidade < 0)
            {
                velocidade =  velocidade + gravidade;
                posicaoY -= velocidade*Gdx.graphics.getDeltaTime()*30;

            }

        }else if(estadoJogo==2){
            posicaoXpassaro -= Gdx.graphics.getDeltaTime()*500;
            if (Gdx.input.justTouched()) {
                estadoJogo = 0;
                pontos = 0;
                velocidade = 0;
                posicaoXpassaro = 0;
                marcouPonto = false;
                posicaoX = Gdx.graphics.getWidth();
                posicaoY = Gdx.graphics.getHeight() / 2 - passaros[0].getHeight() / 2;
            }

        }

        if (posicaoX < 50) {
            if(!marcouPonto){
                pontos++;
                marcouPonto=true;
                somScore.play();
            }
        }

        contador += Gdx.graphics.getDeltaTime()*10;
        if(contador>2){contador=0;}

        batch.begin();
        batch.draw(imagemFundo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(obstaculoBaixo, posicaoX, altura - vao / 2 - obstaculoBaixo.getHeight());
        batch.draw(obstaculoCima, posicaoX, altura + vao / 2);
        batch.draw(passaros[((int) contador)], 50+posicaoXpassaro, posicaoY);

        textoPontuacao.draw(batch, String.valueOf(pontos), Gdx.graphics.getWidth() / 2-20, Gdx.graphics.getHeight() - 110);

        if (estadoJogo==2){
            textoGameOver.draw(batch, "Toque para reiniciar!", Gdx.graphics.getWidth()/2 - 110, Gdx.graphics.getHeight()/2);
        }

        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);

        retanguloCima = new Rectangle(posicaoX, altura + vao/2, obstaculoCima.getWidth(), obstaculoCima.getHeight());
        retanguloBaixo = new Rectangle(posicaoX, altura - obstaculoBaixo.getHeight() - vao/2, obstaculoBaixo.getWidth(), obstaculoBaixo.getHeight());

        circuloPassaro.set(50 + posicaoXpassaro + passaros[0].getWidth() / 2, posicaoY + passaros[0].getWidth() / 2, passaros[0].getHeight() / 2);

//        shapeRenderer.circle(circuloPassaro.x, circuloPassaro.y, circuloPassaro.radius);
//        shapeRenderer.rect(posicaoX, altura + vao/2, obstaculoCima.getWidth(), obstaculoCima.getHeight());
//        shapeRenderer.rect(posicaoX, altura - obstaculoBaixo.getHeight() - vao/2, obstaculoBaixo.getWidth(), obstaculoBaixo.getHeight());

        if (Intersector.overlaps(circuloPassaro, retanguloCima) || Intersector.overlaps(circuloPassaro, retanguloBaixo)) {
            Gdx.app.log("meuLog", "Colidiu!");

            if (estadoJogo==1) {
                somScore.stop();
                somHit.play();
                estadoJogo=2;
            }
        }
        shapeRenderer.end();
    }
}
