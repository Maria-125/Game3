package aqualand.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import aqualand.GameResources;
import aqualand.GameSettings;

public class MovingBackgroundView {
    private Texture texture;
    private float y1, y2;
    private int speed = -3;
    private int overlap = 5;  // перекрытие

    public MovingBackgroundView() {
        this(GameResources.BACKGROUND_PATH);
    }

    public MovingBackgroundView(String texturePath) {
        texture = new Texture(texturePath);
        y1 = 0;
        y2 = -GameSettings.SCREEN_HEIGHT + overlap;
    }

    public void update() {
        y1 += speed;
        y2 += speed;

        y1 = Math.round(y1);
        y2 = Math.round(y2);

        // Зацикливание
        if (y1 <= -GameSettings.SCREEN_HEIGHT) {
            y1 = GameSettings.SCREEN_HEIGHT - overlap;
        }
        if (y2 <= -GameSettings.SCREEN_HEIGHT) {
            y2 = GameSettings.SCREEN_HEIGHT - overlap;
        }

        // Для движения вверх
        if (speed > 0) {
            if (y1 >= GameSettings.SCREEN_HEIGHT) {
                y1 = -GameSettings.SCREEN_HEIGHT + overlap;
            }
            if (y2 >= GameSettings.SCREEN_HEIGHT) {
                y2 = -GameSettings.SCREEN_HEIGHT + overlap;
            }
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, 0, y1, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT + overlap);
        batch.draw(texture, 0, y2, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT + overlap);
    }

    public void dispose() {
        texture.dispose();
    }
}
