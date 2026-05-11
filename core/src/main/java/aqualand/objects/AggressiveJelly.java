package aqualand.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import aqualand.GameResources;
import aqualand.GameSettings;

public class AggressiveJelly extends GameObject {
    private Texture texture;
    private int points = 25;

    public AggressiveJelly(World world, float x, float y) {
        super(world, x, y, 115, 115, GameSettings.JELLY_BIT);
        texture = new Texture(GameResources.JELLY_PATH);
        setVelocity(0, -5);
    }

    public int getPoints() { return points; }

    public void draw(SpriteBatch batch) {
        if (texture != null && !toDestroy) {
            Vector2 pos = getPosition();
            batch.draw(texture, pos.x - width/2, pos.y - height/2, width, height);
        }
    }

    public void dispose() { if (texture != null) texture.dispose(); }
}
