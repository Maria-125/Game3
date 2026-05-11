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

public class RecordsScreen extends ScreenAdapter {
    private Main game;
    private MovingBackgroundView backgroundView;
    private TextView titleView;
    private TextView recordsView;
    private ButtonView backButton;

    public RecordsScreen(Main game) {
        this.game = game;
        backgroundView = new MovingBackgroundView(GameResources.MENU_BACKGROUND_PATH);
        titleView = new TextView(game.largeWhiteFont, 250, 1100, "BEST SCORES");

        int bestScore = MemoryManager.loadRecord();
        int totalCoins = MemoryManager.loadCoins();
        recordsView = new TextView(game.commonWhiteFont, 250, 900, "Best Score: " + bestScore + "\nTotal Coins: " + totalCoins);

        backButton = new ButtonView(250, 500, 220, 60, "BACK", game.commonWhiteFont, GameResources.BUTTON_PATH);
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
        recordsView.draw(game.batch);
        backButton.draw(game.batch);

        game.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.camera.unproject(touchPos);
            float tx = touchPos.x, ty = touchPos.y;

            if (backButton.isHit(tx, ty)) {
                game.setScreen(new MenuScreen(game));
            }
        }
    }

    @Override
    public void dispose() {
        backgroundView.dispose();
        titleView.dispose();
        recordsView.dispose();
        backButton.dispose();
    }
}
