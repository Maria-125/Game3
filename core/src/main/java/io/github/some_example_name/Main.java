package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import aqualand.managers.AudioManager;
import aqualand.managers.FontGenerator;
import aqualand.managers.MemoryManager;
import aqualand.screens.MenuScreen;
import aqualand.GameResources;
import aqualand.GameSettings;

public class Main extends Game {
    public SpriteBatch batch;
    public OrthographicCamera camera;
    public BitmapFont commonWhiteFont;
    public BitmapFont largeWhiteFont;
    public AudioManager audioManager;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera(GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);
        camera.position.set(GameSettings.SCREEN_WIDTH / 2f, GameSettings.SCREEN_HEIGHT / 2f, 0);
        camera.update();

        audioManager = new AudioManager();
        audioManager.isMusicOn = MemoryManager.loadIsMusicOn();
        audioManager.isSoundOn = MemoryManager.loadIsSoundOn();

        commonWhiteFont = FontGenerator.generate(24, Color.WHITE, GameResources.FONT_PATH);
        largeWhiteFont = FontGenerator.generate(48, Color.WHITE, GameResources.FONT_PATH);

        if (audioManager.isMusicOn) {
            audioManager.playMusic();
        }

        setScreen(new MenuScreen(this));
    }

    @Override
    public void dispose() {
        batch.dispose();
        commonWhiteFont.dispose();
        largeWhiteFont.dispose();
        audioManager.dispose();
    }
}
