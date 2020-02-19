package com.badlogic.pizzanda;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class LoseScreen implements Screen {
	final PizzaNda game;
	Texture backgroundImage;
	Texture lostImage;
	Texture backImage;
	Rectangle lost;

	OrthographicCamera camera;

	public LoseScreen(final PizzaNda game) {
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		lostImage=new Texture(Gdx.files.internal("lost.png"));
		backgroundImage = new Texture(Gdx.files.internal("background.png"));
		backImage = new Texture(Gdx.files.internal("back_ic.png"));

		lost=new Rectangle();
		lost.x=400;
		lost.y=0;
		lost.width=354;
		lost.height=381;
		
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
		game.batch.draw(backImage, 92, 260);
        game.font.draw(game.batch, "You did not feed Panda enough pizza! ", 100, 190);
        game.font.draw(game.batch, "your high score is: " + game.prefs.getInteger("highScore"), 100, 160);

        game.batch.draw(lostImage, lost.x, lost.y, lost.width, lost.height);
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
			if (Gdx.input.getY() > (Gdx.graphics.getHeight()/2.7)
					&&Gdx.input.getY() < (Gdx.graphics.getHeight()/2.076)
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