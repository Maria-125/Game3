package aqualand.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;

import aqualand.GameResources;
import aqualand.managers.MemoryManager;
import aqualand.ui.ButtonView;
import aqualand.ui.MovingBackgroundView;
import aqualand.ui.TextView;
import io.github.some_example_name.Main;

public class SettingsScreen extends ScreenAdapter {
    private Main game;
    private MovingBackgroundView backgroundView;
    private TextView titleView;
    private ButtonView musicButton;
    private ButtonView soundButton;
    private ButtonView backButton;

    public SettingsScreen(Main game) {
        this.game = game;
        backgroundView = new MovingBackgroundView(GameResources.MENU_BACKGROUND_PATH);
        titleView = new TextView(game.largeWhiteFont, 250, 1000, "SETTINGS");

        String musicText = MemoryManager.loadIsMusicOn() ? "MUSIC: ON" : "MUSIC: OFF";
        String soundText = MemoryManager.loadIsSoundOn() ? "SOUND: ON" : "SOUND: OFF";

        musicButton = new ButtonView(250, 700, 220, 60, musicText, game.commonWhiteFont, GameResources.BUTTON_PATH);
        soundButton = new ButtonView(250, 600, 220, 60, soundText, game.commonWhiteFont, GameResources.BUTTON_PATH);
        backButton = new ButtonView(250, 400, 220, 60, "BACK", game.commonWhiteFont, GameResources.BUTTON_PATH);
    }

    @Override
    public void render(float delta) {
        handleInput();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        game.batch.begin();

        backgroundView.draw(game.batch);
        titleView.draw(game.batch);
        musicButton.draw(game.batch);
        soundButton.draw(game.batch);
        backButton.draw(game.batch);

        game.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.camera.unproject(touchPos);
            float tx = touchPos.x, ty = touchPos.y;

            if (musicButton.isHit(tx, ty)) {
                boolean newState = !MemoryManager.loadIsMusicOn();
                MemoryManager.saveMusicSettings(newState);
                game.audioManager.isMusicOn = newState;
                if (newState) game.audioManager.playMusic();
                else game.audioManager.stopMusic();
                musicButton = new ButtonView(250, 700, 220, 60, newState ? "MUSIC: ON" : "MUSIC: OFF", game.commonWhiteFont, GameResources.BUTTON_PATH);
            } else if (soundButton.isHit(tx, ty)) {
                boolean newState = !MemoryManager.loadIsSoundOn();
                MemoryManager.saveSoundSettings(newState);
                game.audioManager.isSoundOn = newState;
                soundButton = new ButtonView(250, 600, 220, 60, newState ? "SOUND: ON" : "SOUND: OFF", game.commonWhiteFont, GameResources.BUTTON_PATH);
            } else if (backButton.isHit(tx, ty)) {
                game.setScreen(new MenuScreen(game));
            }
        }
    }

    @Override
    public void dispose() {
        backgroundView.dispose();
        titleView.dispose();
        musicButton.dispose();
        soundButton.dispose();
        backButton.dispose();
    }
}
