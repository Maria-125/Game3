package aqualand.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextView {
    private BitmapFont font;
    private String text;
    private float x, y;

    public TextView(BitmapFont font, float x, float y, String text) {
        this.font = font;
        this.x = x;
        this.y = y;
        setText(text);
    }

    public void setText(String text) { this.text = text; }
    public void draw(SpriteBatch batch) { font.draw(batch, text, x, y); }
    public void dispose() {}
}
