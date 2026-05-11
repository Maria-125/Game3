package aqualand.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import aqualand.GameResources;
import aqualand.GameSession;
import aqualand.GameSettings;
import aqualand.managers.*;
import aqualand.objects.*;
import aqualand.ui.*;
import io.github.some_example_name.Main;

public class GameScreen extends ScreenAdapter {
    private Main game;
    private World world;
    private GameSession session;
    private ContactManager contactManager;

    private MovingBackgroundView background;
    private TextView scoreText, coinsText;
    private HeartView heartView;
    private ButtonView pauseButton;

    private ResearchPod ship;
    private Array<ToxicTrash> trash = new Array<>();
    private Array<AggressiveJelly> jellies = new Array<>();
    private Array<SoundWave> bullets = new Array<>();
    private Array<Coin> coins = new Array<>();

    private float trashTimer = 0, jellyTimer = 0, coinTimer = 0;
    private boolean paused = false;

    private final float[] lanes = {200, 350, 500};
    private int currentLane = 1;

    private boolean[] laneOccupied = {false, false, false};  // занята ли полоса
    private float[] laneOccupiedTimer = {0, 0, 0};  // таймер

    public GameScreen(Main game) {
        this.game = game;
        world = new World(new Vector2(0, 0), true);
        session = new GameSession();
        contactManager = new ContactManager(session, game.audioManager);
        world.setContactListener(contactManager);

        ship = new ResearchPod(world, lanes[1], 120);
        background = new MovingBackgroundView();
        scoreText = new TextView(game.commonWhiteFont, 20, GameSettings.SCREEN_HEIGHT - 50, "Score: 0");
        coinsText = new TextView(game.commonWhiteFont, 20, GameSettings.SCREEN_HEIGHT - 100, "Coins: 0");
        heartView = new HeartView(20, GameSettings.SCREEN_HEIGHT - 200);
        pauseButton = new ButtonView(GameSettings.SCREEN_WIDTH - 100, GameSettings.SCREEN_HEIGHT - 80, 80, 40, "Pause", game.commonWhiteFont, GameResources.BUTTON_PATH);
    }

    @Override
    public void render(float delta) {
        handleInput();
        if (!paused && !session.isGameOver()) update(delta);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        game.batch.begin();

        background.draw(game.batch);
        ship.draw(game.batch);
        for (ToxicTrash t : trash) t.draw(game.batch);
        for (AggressiveJelly j : jellies) j.draw(game.batch);
        for (Coin c : coins) c.draw(game.batch);
        for (SoundWave b : bullets) b.draw(game.batch);
        scoreText.draw(game.batch);
        coinsText.draw(game.batch);
        heartView.draw(game.batch);
        pauseButton.draw(game.batch);

        game.batch.end();
        if (session.isGameOver()) game.setScreen(new MenuScreen(game));
    }

    private int getRandomFreeLane() {  // проверка свободных полос
        java.util.ArrayList<Integer> freeLanes = new java.util.ArrayList<>();
        for (int i = 0; i < 3; i++) {
            if (!laneOccupied[i]) {
                freeLanes.add(i);
            }
        }

        if (freeLanes.size() > 0) {
            int randomIndex = (int)(Math.random() * freeLanes.size());
            return freeLanes.get(randomIndex);
        }

        return -1;
    }

    private void occupyLane(int lane) {
        laneOccupied[lane] = true;
        laneOccupiedTimer[lane] = 1.0f;
    }

    private void updateLaneOccupancy(float delta) {
        for (int i = 0; i < 3; i++) {
            if (laneOccupied[i]) {
                laneOccupiedTimer[i] -= delta;
                if (laneOccupiedTimer[i] <= 0) {
                    laneOccupied[i] = false;
                }
            }
        }
    }

    private void update(float delta) {
        world.step(1/60f, 6, 2);
        background.update();

        updateLaneOccupancy(delta);

        // Спавн мусора
        trashTimer += delta;
        if (trashTimer > 2.5f) {
            trashTimer = 0;
            int lane = getRandomFreeLane();
            if (lane != -1) {
                occupyLane(lane);
                trash.add(new ToxicTrash(world, lanes[lane], GameSettings.SCREEN_HEIGHT + 50));
            }
        }

        // Спавн медуз
        jellyTimer += delta;
        if (jellyTimer > 5f) {
            jellyTimer = 0;
            int lane = getRandomFreeLane();
            if (lane != -1) {
                occupyLane(lane);
                jellies.add(new AggressiveJelly(world, lanes[lane], GameSettings.SCREEN_HEIGHT + 50));
            }
        }

        // Спавн монеток
        coinTimer += delta;
        if (coinTimer > 4f) {
            coinTimer = 0;
            int lane = getRandomFreeLane();
            if (lane != -1) {
                occupyLane(lane);
                coins.add(new Coin(world, lanes[lane], GameSettings.SCREEN_HEIGHT + 50));
            }
        }

        // Удаление мусора
        for (int i = 0; i < trash.size; i++) {
            ToxicTrash t = trash.get(i);
            if (t.toDestroy || t.getPosition().y < -100) {
                world.destroyBody(t.body);
                trash.removeIndex(i);
                i--;
            }
        }

        // Удаление медуз
        for (int i = 0; i < jellies.size; i++) {
            AggressiveJelly j = jellies.get(i);
            if (j.toDestroy || j.getPosition().y < -100) {
                world.destroyBody(j.body);
                jellies.removeIndex(i);
                i--;
            }
        }

        // Удаление пуль
        for (int i = 0; i < bullets.size; i++) {
            SoundWave b = bullets.get(i);
            if (b.toDestroy || b.getPosition().y > GameSettings.SCREEN_HEIGHT + 100) {
                world.destroyBody(b.body);
                bullets.removeIndex(i);
                i--;
            }
        }

        // Удаление монеток
        for (int i = 0; i < coins.size; i++) {
            Coin c = coins.get(i);
            if (c.toDestroy || c.getPosition().y < -100) {
                world.destroyBody(c.body);
                coins.removeIndex(i);
                i--;
            }
        }

        // Обновление UI
        scoreText.setText("Score: " + session.getScore());
        coinsText.setText("Coins: " + session.getCoins());
        heartView.setLives(session.getLives());
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Keys.A) || Gdx.input.isKeyJustPressed(Keys.LEFT)) {
            currentLane = Math.max(0, currentLane - 1);
            ship.setPositionX(lanes[currentLane]);
        }
        if (Gdx.input.isKeyJustPressed(Keys.D) || Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
            currentLane = Math.min(2, currentLane + 1);
            ship.setPositionX(lanes[currentLane]);
        }
        if (Gdx.input.isKeyJustPressed(Keys.SPACE) && !paused && ship.canShoot()) {
            ship.shoot();
            bullets.add(new SoundWave(world, ship.getPosition().x, ship.getPosition().y + 40));
            game.audioManager.playShootSound();
        }
        if (Gdx.input.isKeyJustPressed(Keys.P)) paused = !paused;
        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) game.setScreen(new MenuScreen(game));

        if (Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.camera.unproject(touch);
            if (pauseButton.isHit(touch.x, touch.y)) paused = !paused;
        }
    }

    @Override
    public void dispose() {
        world.dispose();
        background.dispose();
        scoreText.dispose();
        coinsText.dispose();
        heartView.dispose();
        pauseButton.dispose();
        ship.dispose();
        for (ToxicTrash t : trash) t.dispose();
        for (AggressiveJelly j : jellies) j.dispose();
        for (Coin c : coins) c.dispose();
        for (SoundWave b : bullets) b.dispose();
    }
}
