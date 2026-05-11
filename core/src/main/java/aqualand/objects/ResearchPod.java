package aqualand.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import aqualand.GameResources;
import aqualand.GameSettings;

public class ResearchPod extends GameObject {
    private Texture texture;
    private long lastShootTime = 0;
    private float shootDelay = 0.3f;
    private int lives = 3;

    public ResearchPod(World world, float x, float y) {
        super(world, x, y, 130, 130, GameSettings.SHIP_BIT);
        texture = new Texture(GameResources.SHIP_PATH);
    }

    public void setPositionX(float x) {
        body.setTransform(new Vector2(x * GameSettings.WORLD_TO_BOX, body.getPosition().y), 0);
    }

    public boolean canShoot() {
        return System.currentTimeMillis() - lastShootTime >= shootDelay * 1000;
    }

    public void shoot() { lastShootTime = System.currentTimeMillis(); }

    public void hit() {
        lives--;
        System.out.println("Корабль получил урон! Жизней осталось: " + lives);
        if (lives <= 0) destroy();
    }

    public int getLives() { return lives; }

    public void draw(SpriteBatch batch) {
        if (texture != null) {
            Vector2 pos = getPosition();
            batch.draw(texture, pos.x - width/2, pos.y - height/2, width, height);
        }
    }

    public void dispose() { if (texture != null) texture.dispose(); }
}
