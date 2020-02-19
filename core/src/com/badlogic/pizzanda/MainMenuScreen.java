package com.badlogic.pizzanda;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class MainMenuScreen implements Screen {

	final PizzaNda game;
	Texture menuPanda;
	Texture playImage;
	Texture helpImage;
	Texture hiscoreImage;
	OrthographicCamera camera;

	public MainMenuScreen(final PizzaNda game) {
		
		this.game = game;
		menuPanda=new Texture(Gdx.files.internal("menuPanda.png"));
		playImage = new Texture(Gdx.files.internal("play_ic.png"));
		helpImage = new Texture(Gdx.files.internal("help.png"));
		hiscoreImage = new Texture(Gdx.files.internal("hiscore.png"));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

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
        game.batch.draw(menuPanda, 0, 0);

        game.font.draw(game.batch, "Welcome to Pizzanda!!! collect as much pizzas as you can, if you miss 3, you lose! ", 30, 40);
        game.font.draw(game.batch, "MBWOE ", 720, 20);
        game.font.draw(game.batch, "Graphics & Design: Khalid", 30, 20);
        game.batch.draw(playImage, 100, 400);
        game.batch.draw(helpImage, 700, 400);
        game.batch.draw(hiscoreImage, 700, 300);
        game.batch.end();
        
        
        
        if (Gdx.input.isTouched()) {

			if (Gdx.input.getY() > (Gdx.graphics.getHeight()/18)
					&&Gdx.input.getY() < (Gdx.graphics.getHeight()/5.4)
					&& Gdx.input.getX() > (Gdx.graphics.getWidth()/1.197)
					&& Gdx.input.getX() < (Gdx.graphics.getWidth()/1.054)) {
				game.setScreen(new InstructionScreen(game));
				dispose();
			}
			if (Gdx.input.getY() > (Gdx.graphics.getHeight()/18)
					&&Gdx.input.getY() < (Gdx.graphics.getHeight()/5.4)
					&& Gdx.input.getX() > (Gdx.graphics.getWidth()/8.86)
					&& Gdx.input.getX() < (Gdx.graphics.getWidth()/2.9533)) {
				game.setScreen(new GameScreen(game));
				dispose();
			}
			if (Gdx.input.getY() > (Gdx.graphics.getHeight()/3.6)
					&&Gdx.input.getY() < (Gdx.graphics.getHeight()/2.7)
					&& Gdx.input.getX() > (Gdx.graphics.getWidth()/1.197)
					&& Gdx.input.getX() < (Gdx.graphics.getWidth())) {
				game.setScreen(new HiscoreScreen(game));
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
