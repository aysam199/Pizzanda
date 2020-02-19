package com.badlogic.pizzanda;

import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;


public class PizzaNda extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public BitmapFont font2;
    public static Preferences prefs;
    long startTime;
	Texture welcome;
	Texture Welcome_background;
	OrthographicCamera camera;
    
    
    public void create() {
    	startTime = TimeUtils.millis();
		welcome = new Texture(Gdx.files.internal("Welcome.png"));
		Welcome_background = new Texture(Gdx.files.internal("Welcome_background.png"));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

        batch = new SpriteBatch();
        //Use LibGDX's default Arial font.
        font = new BitmapFont();
        font2 = new BitmapFont();
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        font.getData().setScale(1.2f);
        font2.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        font2.getData().setScale(2.5f);
        prefs = Gdx.app.getPreferences("DropGame");
        if (!prefs.contains("highScore")) {
            prefs.putInteger("highScore", 0);
        }
        this.setScreen(new MainMenuScreen(this));
    }

    public void render() {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(Welcome_background, 0, 0,800, 565);
		batch.draw(welcome, 100, 100);
        batch.end();

    	if( TimeUtils.timeSinceMillis(startTime) > 5000){
    	    super.render();     	    
    	}
    }
    	

    public void dispose() {
        batch.dispose();
        font.dispose();
        font2.dispose();
    }

}