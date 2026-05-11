package aqualand.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public abstract class View implements Disposable {
    public float x, y;
    public float width, height;

    public View(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean isHit(float tx, float ty) {
        return tx >= x && tx <= x + width && ty >= y && ty <= y + height;
    }

    public abstract void draw(SpriteBatch batch);
}
