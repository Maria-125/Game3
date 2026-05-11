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

public class MenuScreen extends ScreenAdapter {
    private Main game;
    private MovingBackgroundView backgroundView;
    private TextView titleView;
    private TextView scoreView;
    private ButtonView startButton;
    private ButtonView settingsButton;
    private ButtonView recordsButton;
    private ButtonView exitButton;

    public MenuScreen(Main game) {
        this.game = game;
        backgroundView = new MovingBackgroundView(GameResources.MENU_BACKGROUND_PATH);
        titleView = new TextView(game.largeWhiteFont, 245, 1000, "AQUALAND");
        startButton = new ButtonView(250, 700, 220, 60, "START", game.commonWhiteFont, GameResources.BUTTON_PATH);
        settingsButton = new ButtonView(250, 600, 220, 60, "SETTINGS", game.commonWhiteFont, GameResources.BUTTON_PATH);
        recordsButton = new ButtonView(250, 500, 220, 60, "RECORDS", game.commonWhiteFont, GameResources.BUTTON_PATH);
        exitButton = new ButtonView(250, 400, 220, 60, "EXIT", game.commonWhiteFont, GameResources.BUTTON_PATH);
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
        startButton.draw(game.batch);
        settingsButton.draw(game.batch);
        recordsButton.draw(game.batch);
        exitButton.draw(game.batch);

        game.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.camera.unproject(touchPos);
            float tx = touchPos.x, ty = touchPos.y;

            if (startButton.isHit(tx, ty)) {
                game.setScreen(new GameScreen(game));
            } else if (settingsButton.isHit(tx, ty)) {
                game.setScreen(new SettingsScreen(game));
            } else if (recordsButton.isHit(tx, ty)) {
                game.setScreen(new RecordsScreen(game));
            } else if (exitButton.isHit(tx, ty)) {
                Gdx.app.exit();
            }
        }
    }

    @Override
    public void dispose() {
        backgroundView.dispose();
        titleView.dispose();
        scoreView.dispose();
        startButton.dispose();
        settingsButton.dispose();
        recordsButton.dispose();
        exitButton.dispose();
    }
}
