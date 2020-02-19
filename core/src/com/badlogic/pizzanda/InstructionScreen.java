package com.badlogic.pizzanda;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class InstructionScreen implements Screen {
	final PizzaNda game;
	Texture backgroundImage;
	Texture drop;
	Texture life_inst;
	Texture jewl_inst;
	Texture evil_inst;
	Texture backImage;
	Texture score_1;
	Texture score_10;
	Texture death_1;
	Texture die;

	OrthographicCamera camera;

	public InstructionScreen(final PizzaNda game) {
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		backgroundImage = new Texture(Gdx.files.internal("background.png"));
		drop = new Texture(Gdx.files.internal("droplet.png"));
		life_inst = new Texture(Gdx.files.internal("life_inst.png"));
		jewl_inst = new Texture(Gdx.files.internal("jewl_inst.png"));
		evil_inst = new Texture(Gdx.files.internal("evil_inst.png"));
		backImage = new Texture(Gdx.files.internal("back_ic.png"));
		score_1 = new Texture(Gdx.files.internal("1score.png"));
		score_10 = new Texture(Gdx.files.internal("10score.png"));
		death_1 = new Texture(Gdx.files.internal("1death.png"));
		die = new Texture(Gdx.files.internal("die.png"));
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
		game.batch.draw(drop, 100, 380);
		game.batch.draw(score_1, 180, 400);
		game.batch.draw(life_inst, 100, 260);
		game.batch.draw(death_1, 180, 280);
		game.batch.draw(jewl_inst, 300, 380);
		game.batch.draw(score_10, 380, 400);
		game.batch.draw(evil_inst, 300, 260);
		game.batch.draw(die, 380, 280);

		game.batch.draw(backImage, 92, 60);
		game.batch.end();

		
		if (Gdx.input.isTouched()) {
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