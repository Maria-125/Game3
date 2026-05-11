package aqualand.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import aqualand.GameResources;

public class HeartView {
    private Texture texture;
    private int lives;
    private float x, y;
    private float width = 24;
    private float height = 24;
    private int padding = 5;

    public HeartView(float x, float y) {
        this.x = x;
        this.y = y;
        texture = new Texture(GameResources.HEART_PATH);
        this.lives = 3;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void draw(SpriteBatch batch) {
        for (int i = 0; i < lives; i++) {
            batch.draw(texture, x + i * (width + padding), y, width, height);
        }
    }

    public void dispose() {
        texture.dispose();
    }
}
