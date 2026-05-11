package aqualand.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonView {
    private Texture background;
    private BitmapFont font;
    private String text;
    private float x, y, width, height;
    private float textX, textY;

    public ButtonView(float x, float y, float width, float height, String text, BitmapFont font, String bgPath) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.font = font;
        this.background = new Texture(bgPath);
        GlyphLayout layout = new GlyphLayout(font, text);
        textX = x + (width - layout.width) / 2;
        textY = y + (height + layout.height) / 2;
    }

    public boolean isHit(float tx, float ty) {
        return tx >= x && tx <= x + width && ty >= y && ty <= y + height;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(background, x, y, width, height);
        font.draw(batch, text, textX, textY);
    }

    public void dispose() { background.dispose(); }
}
