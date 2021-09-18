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

    Texture lowObstacacle;
    Texture upObstacle;
    Texture[] birds;
    Texture backgroundImage;

    float positionX = 0;
    float birdX = 0;
    float positionY;
    float speed;
    float height;
    float space;
    float counter = 0.0f;
    float gravity = 2;
    int spots = 0;
    boolean scoredPoint;
    int gameState = 0;

    Random heightRandom;

    BitmapFont txtPunctuation;
    BitmapFont txtGameOver;

    Circle birdCircle;
    Rectangle upRectangle;
    Rectangle lowRectangle;

    Sound somHit;
    Sound somScore;

    @Override
    public void create () {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        heightRandom = new Random();

        lowObstacacle = new Texture("canobaixo.png");
        upObstacle = new Texture("canocima.png");
        backgroundImage = new Texture("imagemfundo.png");

        positionX = Gdx.graphics.getWidth();
        positionY = Gdx.graphics.getHeight()/2;
        height = Gdx.graphics.getHeight()/2;
        space = 300;

        birds = new Texture[2];
        for(int i = 0; i < birds.length; i++) {
            birds[i] = new Texture("passaro"+String.valueOf(i+1)+".png");
        }

        birdCircle = new Circle();
        lowRectangle = new Rectangle();
        upRectangle = new Rectangle();

        txtPunctuation = new BitmapFont();
        txtPunctuation.setColor(Color.WHITE);
        txtPunctuation.getData().setScale(10);

        txtGameOver = new BitmapFont();
        txtGameOver.setColor(Color.GREEN);
        txtGameOver.getData().setScale(2);

        somHit = Gdx.audio.newSound(Gdx.files.internal("somHit.mp3"));
        somScore = Gdx.audio.newSound(Gdx.files.internal("somScore.mp3"));

    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(gameState == 0) {
            if (Gdx.input.justTouched()) {
                speed = - 30;
                gameState = 1;
            }
        }else if(gameState==1){
            positionX-=Gdx.graphics.getDeltaTime()*500;

            if(positionX < - lowObstacacle.getWidth()){
                positionX = Gdx.graphics.getWidth();

                height = Gdx.graphics.getHeight() / 2;
                height -= (heightRandom.nextFloat() - 0.5f) * 150;
                scoredPoint = false;
            }

            if (Gdx.input.justTouched()) {
                speed = - 30;
            }

            if (positionY > 0 || speed < 0)
            {
                speed =  speed + gravity;
                positionY -= speed * Gdx.graphics.getDeltaTime() * 30;

            }

        }else if(gameState == 2){
            birdX -= Gdx.graphics.getDeltaTime() * 500;
            if (Gdx.input.justTouched()) {
                gameState = 0;
                spots = 0;
                speed = 0;
                birdX = 0;
                scoredPoint = false;
                positionY = Gdx.graphics.getWidth();
                positionY = Gdx.graphics.getHeight() / 2 - birds[0].getHeight() / 2;
            }

        }

        if (positionX < 50) {
            if(!scoredPoint){
                spots++;
                scoredPoint = true;
                somScore.play();
            }
        }

        counter += Gdx.graphics.getDeltaTime()*10;
        if(counter>2){counter=0;}

        batch.begin();
        batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(lowObstacacle, positionX, height - space / 2 - lowObstacacle.getHeight());
        batch.draw(upObstacle, positionX, height + space / 2);
        batch.draw(birds[((int) counter)], 50 + birdX, positionY);

        txtPunctuation.draw(batch, String.valueOf(spots), Gdx.graphics.getWidth() / 2-20, Gdx.graphics.getHeight() - 110);

        if (gameState == 2){
            txtGameOver.draw(batch, "Toque para reiniciar!", Gdx.graphics.getWidth() / 2 - 110, Gdx.graphics.getHeight()/2);
        }

        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);

        upRectangle = new Rectangle(positionX, height + space / 2, upObstacle.getWidth(), upObstacle.getHeight());
        lowRectangle = new Rectangle(positionX, height - lowObstacacle.getHeight() - space / 2, lowObstacacle.getWidth(), lowObstacacle.getHeight());

        birdCircle.set(50 + birdX + birds[0].getWidth() / 2, positionY + birds[0].getWidth() / 2, birds[0].getHeight() / 2);

        shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);
        shapeRenderer.rect(positionX, height + space / 2, upObstacle.getWidth(), upObstacle.getHeight());
        shapeRenderer.rect(positionX, height - lowObstacacle.getHeight() - space / 2, lowObstacacle.getWidth(), lowObstacacle.getHeight());

        if (Intersector.overlaps(birdCircle, upRectangle) || Intersector.overlaps(birdCircle, lowRectangle)) {
            Gdx.app.log("meuLog", "Colidiu!");

            if (gameState == 1) {
                somScore.stop();
                somHit.play();
                gameState = 2;
            }
        }

        shapeRenderer.end();

    }
}
