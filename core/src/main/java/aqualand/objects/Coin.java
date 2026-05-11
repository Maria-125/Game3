package aqualand.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import aqualand.GameResources;
import aqualand.GameSettings;

public class Coin extends GameObject {
    private Texture texture;
    private int value = 5;

    public Coin(World world, float x, float y) {
        super(world, x, y, 55, 55, GameSettings.COIN_BIT);
        texture = new Texture(GameResources.COIN_PATH);
        setVelocity(0, -5);
    }

    public int getValue() { return value; }

    public void draw(SpriteBatch batch) {
        if (texture != null && !toDestroy) {
            Vector2 pos = getPosition();
            batch.draw(texture, pos.x - width/2, pos.y - height/2, width, height);
        }
    }

    public void dispose() { if (texture != null) texture.dispose(); }
}
