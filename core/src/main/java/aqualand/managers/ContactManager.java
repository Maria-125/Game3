package aqualand.managers;

import com.badlogic.gdx.physics.box2d.*;
import aqualand.objects.*;
import aqualand.GameSession;

public class ContactManager implements ContactListener {
    private GameSession session;
    private AudioManager audio;

    public ContactManager(GameSession session, AudioManager audio) {
        this.session = session;
        this.audio = audio;
    }

    @Override
    public void beginContact(Contact contact) {
        Object a = contact.getFixtureA().getBody().getUserData();
        Object b = contact.getFixtureB().getBody().getUserData();

        if (a == null || b == null) return;

        // 1. ПУЛЯ + МУСОР
        if (a instanceof SoundWave && b instanceof ToxicTrash) {
            handleBulletTrash((SoundWave)a, (ToxicTrash)b);
        }
        else if (a instanceof ToxicTrash && b instanceof SoundWave) {
            handleBulletTrash((SoundWave)b, (ToxicTrash)a);
        }

        // 2. ПУЛЯ + МЕДУЗА
        else if (a instanceof SoundWave && b instanceof AggressiveJelly) {
            handleBulletJelly((SoundWave)a, (AggressiveJelly)b);
        }
        else if (a instanceof AggressiveJelly && b instanceof SoundWave) {
            handleBulletJelly((SoundWave)b, (AggressiveJelly)a);
        }

        // 3. КОРАБЛЬ + МЕДУЗА
        else if (a instanceof ResearchPod && b instanceof AggressiveJelly) {
            handleShipJelly((ResearchPod)a, (AggressiveJelly)b);
        }
        else if (a instanceof AggressiveJelly && b instanceof ResearchPod) {
            handleShipJelly((ResearchPod)b, (AggressiveJelly)a);
        }

        // 4. КОРАБЛЬ + МОНЕТКА
        else if (a instanceof ResearchPod && b instanceof Coin) {
            handleShipCoin((ResearchPod)a, (Coin)b);
        }
        else if (a instanceof Coin && b instanceof ResearchPod) {
            handleShipCoin((ResearchPod)b, (Coin)a);
        }

    }

    private void handleBulletTrash(SoundWave bullet, ToxicTrash trash) {
        if (!bullet.toDestroy && !trash.toDestroy) {
            bullet.destroy();
            trash.destroy();
            session.addScore(trash.getPoints());
            if (audio != null) audio.playExplosionSound();
            System.out.println("Мусор уничтожен!");
        }
    }

    private void handleBulletJelly(SoundWave bullet, AggressiveJelly jelly) {
        if (!bullet.toDestroy && !jelly.toDestroy) {
            bullet.destroy();
            jelly.destroy();
            session.addScore(jelly.getPoints());
            if (audio != null) audio.playExplosionSound();
            System.out.println("Медуза уничтожена!");
        }
    }

    private void handleShipJelly(ResearchPod ship, AggressiveJelly jelly) {
        if (!jelly.toDestroy) {
            jelly.destroy();
            ship.hit();
            session.loseLife();
            if (audio != null) audio.playExplosionSound();
            System.out.println("Корабль столкнулся с медузой!");
        }
    }

    private void handleShipCoin(ResearchPod ship, Coin coin) {
        if (!coin.toDestroy) {
            coin.destroy();
            session.addCoins(coin.getValue());
            session.addScore(coin.getValue());
            if (audio != null) audio.playCoinSound();
            System.out.println("Монетка собрана!");
        }
    }

    @Override public void endContact(Contact contact) {}
    @Override public void preSolve(Contact contact, Manifold oldManifold) {}
    @Override public void postSolve(Contact contact, ContactImpulse impulse) {}
}
