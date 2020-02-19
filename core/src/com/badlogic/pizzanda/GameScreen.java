package com.badlogic.pizzanda;
import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {
	final PizzaNda game;
	Texture backgroundImage;
	Texture dropImage;
	Texture bucketImage;
	Texture lifeImage;
	Texture jewlImage;
	Texture evilImage;
	Sound lifeSound;
	Sound jewlSound;
	Sound dropSound;
	Sound evilSound;
	Music rainMusic;
	OrthographicCamera camera;
	Rectangle bucket;
	Rectangle life;
	Rectangle jewl;
	Rectangle evil;
	Array<Rectangle> raindrops;
	Array<Rectangle> lifes; // (i know bad spelling stfu)
	Array<Rectangle> jewls;
	Array<Rectangle> evils;
	long lastDropTime;
	long lastLifeSpawn;
	long lastJewlSpawn;
	long lastEvilSpawn;
	int deathscore;
	int dropsGathered;
	int dropspeed;
	int speed_check;
	int random_life;
	int random_jewl;
	int random_evil;
	int jewl_random_spawner;
	int evil_random_spawner;
	boolean life_flag;
	boolean spawn_life_flag;
	boolean jewl_flag;
	boolean spawn_jewl_flag;
	boolean evil_flag;
	boolean spawn_evil_flag;
	int max;
	int min;
	int _udpateGameNum;

	public GameScreen(final PizzaNda gam) {
		this.game = gam;
		speed_check = 0;
		life_flag = false;
		spawn_life_flag = true;
		jewl_flag = false;
		spawn_jewl_flag = true;
		evil_flag=false;
		spawn_evil_flag= true;
		max = 10;
		min = 1;
		evil_random_spawner=0;
		jewl_random_spawner = 0;
		_udpateGameNum = 0;
		// load the images for the droplet and the bucket, 64x64 pixels each
		backgroundImage = new Texture(Gdx.files.internal("background.png"));
		dropImage = new Texture(Gdx.files.internal("droplet.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));
		lifeImage = new Texture(Gdx.files.internal("life.png"));
		jewlImage = new Texture(Gdx.files.internal("jewl.png"));
		evilImage = new Texture(Gdx.files.internal("evil.png"));
		// load the drop sound effect and the rain background "music"
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		lifeSound = Gdx.audio.newSound(Gdx.files.internal("yay.wav"));
		jewlSound = Gdx.audio.newSound(Gdx.files.internal("jewl.wav"));
		evilSound = Gdx.audio.newSound(Gdx.files.internal("die.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
		rainMusic.setLooping(true);

		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		// create a Rectangle to logically represent the life n bucket

		bucket = new Rectangle();
		bucket.x = 800 / 2 + 44 / 2; // center the bucket horizontally
		bucket.y = 10;
		bucket.width = 44;
		bucket.height = 60;
		// create the raindrops array and spawn the first raindrop
		raindrops = new Array<Rectangle>();
		lifes = new Array<Rectangle>();
		jewls = new Array<Rectangle>();
		evils = new Array<Rectangle>();
		spawnRaindrop();
	}

	private int generateRand_life() {
		life_flag = true;
		Random random = new Random();
		random_life = random.nextInt((max - min) + 1) + min;
		return random_life;

	}

	private int generateRand_jewl() {
		jewl_flag = true;
		Random random = new Random();
		random_jewl = MathUtils.random(jewl_random_spawner,
				jewl_random_spawner + 50);
		return random_jewl;

	}
	
	private int generateRand_evil() {
		evil_flag = true;
		Random random = new Random();
		random_evil = MathUtils.random(evil_random_spawner,
				evil_random_spawner + 10);
		return random_evil;

	}

	private void spawnRaindrop() {
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 800 - 64);
		raindrop.y = 480;
		raindrop.width = 64;
		raindrop.height = 80;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}

	private void spawnLife() {
		life = new Rectangle();
		life.x = MathUtils.random(0, 800 - 64);
		life.y = 480;
		lifes.add(life);
		spawn_life_flag = false;
		lastLifeSpawn = TimeUtils.nanoTime();
	}

	private void spawnJewl() {
		jewl = new Rectangle();
		jewl.x = MathUtils.random(0, 800 - 64);
		jewl.y = 480;
		
		jewls.add(jewl);
		spawn_jewl_flag = false;
		lastJewlSpawn = TimeUtils.nanoTime();
	}
	
	private void spawnEvil() {
		evil = new Rectangle();
		evil.x = MathUtils.random(0, 800 - 64);
		evil.y = 480;
		evil.width = 60;
		evil.height = 60;
		evils.add(evil);
		spawn_evil_flag = false;
		lastEvilSpawn = TimeUtils.nanoTime();
	}

	@Override
	public void render(float delta) {
		// clear the screen with a dark blue color. The
		// arguments to glClearColor are the red, green
		// blue and alpha component in the range [0,1]
		// of the color to be used to clear the screen.
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// tell the camera to update its matrices.
		camera.update();

		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		game.batch.setProjectionMatrix(camera.combined);

		// begin a new batch and draw the bucket and
		// all drops
		game.batch.begin();
		game.batch.draw(backgroundImage, 0, 0, 800, 565);
		game.font.draw(game.batch, "Pizza Missed: " + deathscore, 0, 450);
		game.font.draw(game.batch, "Pizza Collected: " + dropsGathered, 0, 470);
		game.batch.draw(bucketImage, bucket.x, bucket.y, 63, bucket.height);
		for (Rectangle raindrop : raindrops) {
			game.batch.draw(dropImage, raindrop.x, raindrop.y);

		}
		for (Rectangle life : lifes) {
			game.batch.draw(lifeImage, life.x, life.y);

		}
		for (Rectangle jewl : jewls) {
			game.batch.draw(jewlImage, jewl.x, jewl.y);

		}
		for (Rectangle evil : evils) {
			game.batch.draw(evilImage, evil.x, evil.y);

		}
		game.batch.end();

		if (!life_flag) {
			generateRand_life();
		}
		if (!jewl_flag) {
			generateRand_jewl();
		}
		if (!evil_flag) {
			generateRand_evil();
		}


		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 44 / 2;
		
		}



		if (Gdx.input.isKeyPressed(Keys.LEFT))
			bucket.x -= (dropspeed + 300) * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.RIGHT))
			bucket.x += (dropspeed + 300) * Gdx.graphics.getDeltaTime();

		// make sure the bucket stays within the screen bounds
		if (bucket.x < 0)
			bucket.x = 0;
		if (bucket.x > 800 - 63)
			bucket.x = 800 - 63;

		// check if we need to create a new raindrop/life/jewl
		if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
			spawnRaindrop();
		if (dropsGathered == random_life
				&& TimeUtils.nanoTime() - lastLifeSpawn > 1000000000) {
			if (spawn_life_flag == true) {
				spawnLife();
			}
		}
		if (dropsGathered == random_jewl
				&& TimeUtils.nanoTime() - lastJewlSpawn > 1000000000) {
			if (spawn_jewl_flag == true) {
				spawnJewl();
			}
		}
		
		if (dropsGathered == random_evil
				&& TimeUtils.nanoTime() - lastEvilSpawn > 1000000000) {
			if (spawn_evil_flag == true) {
				spawnEvil();
			}
		}
		
		if (dropsGathered != random_life)
			spawn_life_flag = true;

		if (dropsGathered != random_jewl)
			spawn_jewl_flag = true;
		
		if (dropsGathered != random_evil)
			spawn_evil_flag = true;


		Iterator<Rectangle> iter = raindrops.iterator();
		while (iter.hasNext()) {
			Rectangle raindrop = iter.next();
			raindrop.y -= dropspeed * Gdx.graphics.getDeltaTime();
			if (raindrop.y + 64 < 0) {
				deathscore++;
				iter.remove();
			}
			if (raindrop.overlaps(bucket)) {
				dropsGathered++;
				dropSound.play();
				iter.remove();
			}
		}

		Iterator<Rectangle> iter2 = lifes.iterator();
		while (iter2.hasNext()) {
			Rectangle life = iter2.next();
			life.y -= 400 * Gdx.graphics.getDeltaTime();
			if (life.y + 64 < 0) {
				iter2.remove();
			}
			if (life.overlaps(bucket)) {
				if (deathscore != 0) {
					deathscore--;
				}
				lifeSound.play();
				iter2.remove();

			}
		}

		Iterator<Rectangle> iter3 = jewls.iterator();
		while (iter3.hasNext()) {
			Rectangle jewl = iter3.next();
			jewl.y -= 400 * Gdx.graphics.getDeltaTime();
			if (jewl.y + 64 < 0) {
				iter3.remove();
			}
			if (jewl.overlaps(bucket)) {
				dropsGathered = dropsGathered + 10;
				jewlSound.play();
				iter3.remove();
			}
		}
		
		Iterator<Rectangle> iter4 = evils.iterator();
		while (iter4.hasNext()) {
			Rectangle evil = iter4.next();
			evil.y -= 400 * Gdx.graphics.getDeltaTime();
			if (evil.y + 64 < 0) {
				iter4.remove();
			}
			if (evil.overlaps(bucket)) {
				deathscore=3;
				evilSound.play();
				iter4.remove();
			}
		}
		gameupdate();

	}

	public void gameupdate() {
		// updates for the life spawn
		if (dropsGathered >= _udpateGameNum
				&& dropsGathered < _udpateGameNum + 10) {
			if (dropsGathered > max) {
				max = _udpateGameNum + 20;
				min = _udpateGameNum + 12;
				life_flag = false;
			}
			
			// speed updates
			speed_check = dropsGathered / 10;	
			if (speed_check == 0)
				dropspeed = 200;
			else if (speed_check > 0 && speed_check < 7)
				dropspeed = (speed_check + 2) * 100;
			else
				dropspeed = (speed_check + 10) * 50;
			_udpateGameNum += 10;
		}

		// updates for the jewl spawn
		if (dropsGathered > jewl_random_spawner + 50) {
			jewl_random_spawner += 50;
			jewl_flag = false;
		}
		
		//updates for the evil spawn
		if (dropsGathered > evil_random_spawner + 10) {
			evil_random_spawner += 10;
			evil_flag = false;
		}

		if (deathscore == 3) {
			if (dropsGathered > game.prefs.getInteger("highScore")) {
				game.prefs.putInteger("highScore", dropsGathered);
				game.prefs.flush();
			}
			dispose();
			game.setScreen(new LoseScreen(game));

		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		// start the playback of the background music
		// when the screen is shown
		rainMusic.play();
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
		backgroundImage.dispose();
		lifeImage.dispose();
		jewlImage.dispose();
		evilImage.dispose();
		lifeSound.dispose();
		jewlSound.dispose();

	}
}