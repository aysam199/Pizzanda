package com.badlogic.pizzanda;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class HiscoreScreen implements Screen {
	final PizzaNda game;
	Texture backgroundImage;

	Texture backImage;
	Rectangle lost;

	OrthographicCamera camera;

	public HiscoreScreen(final PizzaNda game) {
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		backgroundImage = new Texture(Gdx.files.internal("background.png"));
		backImage = new Texture(Gdx.files.internal("back_ic.png"));
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
		game.batch.draw(backgroundImage, 0, 0, 800, 565);
		game.batch.draw(backImage, 92, 60);
        game.font2.draw(game.batch, "your high score is: " + game.prefs.getInteger("highScore"), 250, 200);
        game.batch.end();
        
        if (Gdx.input.isTouched()) {
			/*int x_pos=Gdx.input.getX();
			int y_pos=Gdx.input.getY();
			System.out.println("x" + x_pos );
			System.out.println("y" + y_pos);
			int wid=Gdx.graphics.getWidth();
			int hey=Gdx.graphics.getHeight();
			System.out.println("width" + wid);
			System.out.println("heigh" + hey);*/
			if (Gdx.input.getY() > (Gdx.graphics.getHeight()/1.35)
					&&Gdx.input.getY() < (Gdx.graphics.getHeight()/1.08)
					&& Gdx.input.getX() > (Gdx.graphics.getWidth()/8.86)
					&& Gdx.input.getX() < (Gdx.graphics.getWidth()/2.9533)) {
				game.setScreen(new MainMenuScreen(game));
				dispose();
			}

		}			
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
}