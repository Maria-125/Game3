package aqualand.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import aqualand.GameResources;
import aqualand.GameSettings;

public class SoundWave extends GameObject {
    private Texture texture;

    public SoundWave(World world, float x, float y) {
        super(world, x, y, 65, 65, GameSettings.BULLET_BIT);
        texture = new Texture(GameResources.BULLET_PATH);
        setVelocity(0, 300);
    }

    public void draw(SpriteBatch batch) {
        if (texture != null && !toDestroy) {
            Vector2 pos = getPosition();
            batch.draw(texture, pos.x - width/2, pos.y - height/2, width, height);
        }
    }
    public void dispose() { if (texture != null) texture.dispose(); }
}
