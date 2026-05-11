package aqualand.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import aqualand.GameSettings;

public abstract class GameObject {
    public Body body;
    public float width, height;
    public boolean toDestroy = false;
    protected short cBits;

    public GameObject(World world, float x, float y, float width, float height, short categoryBits) {
        this.width = width;
        this.height = height;
        this.cBits = categoryBits;

        BodyDef bodyDef = new BodyDef();

        // Корабль не движется
        if (categoryBits == GameSettings.SHIP_BIT) {
            bodyDef.type = BodyDef.BodyType.StaticBody;
        } else {
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        }

        bodyDef.position.set(x * GameSettings.WORLD_TO_BOX, y * GameSettings.WORLD_TO_BOX);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 * GameSettings.WORLD_TO_BOX, height / 2 * GameSettings.WORLD_TO_BOX);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.restitution = 0f;
        fixtureDef.filter.categoryBits = categoryBits;

        if (categoryBits == GameSettings.SHIP_BIT) {
            // Корабль сталкивается только с медузами и монетками
            fixtureDef.filter.maskBits = (short)(GameSettings.JELLY_BIT | GameSettings.COIN_BIT);
        }
        else if (categoryBits == GameSettings.BULLET_BIT) {
            // Пуля сталкивается с мусором и медузами
            fixtureDef.filter.maskBits = (short)(GameSettings.TRASH_BIT | GameSettings.JELLY_BIT);
        }
        else if (categoryBits == GameSettings.TRASH_BIT) {
            // Мусор сталкивается только с пулями
            fixtureDef.filter.maskBits = GameSettings.BULLET_BIT;
        }
        else if (categoryBits == GameSettings.JELLY_BIT) {
            // Медуза сталкивается с пулями и кораблём
            fixtureDef.filter.maskBits = (short)(GameSettings.BULLET_BIT | GameSettings.SHIP_BIT);
        }
        else if (categoryBits == GameSettings.COIN_BIT) {
            // Монетка сталкивается только с кораблём
            fixtureDef.filter.maskBits = GameSettings.SHIP_BIT;
        }
        else {
            fixtureDef.filter.maskBits = -1;
        }

        body.createFixture(fixtureDef);
        body.setUserData(this);

        if (categoryBits != GameSettings.SHIP_BIT) {
            body.setBullet(true);
        }

        shape.dispose();
    }

    public Vector2 getPosition() {
        return body.getPosition().scl(GameSettings.BOX_TO_WORLD);
    }

    public void setVelocity(float vx, float vy) {
        body.setLinearVelocity(vx, vy);
    }

    public void destroy() {
        toDestroy = true;
    }
}
