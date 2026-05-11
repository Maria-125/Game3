package aqualand.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import aqualand.GameResources;

public class AudioManager {
    private Music backgroundMusic;
    private Sound shootSound;
    private Sound explosionSound;
    private Sound coinSound;

    public boolean isMusicOn = true;
    public boolean isSoundOn = true;

    public AudioManager() {
        try {
            backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(GameResources.MUSIC_PATH));
            backgroundMusic.setLooping(true);
        } catch (Exception e) { System.out.println("Music not loaded"); }

        try {
            shootSound = Gdx.audio.newSound(Gdx.files.internal(GameResources.SHOOT_SOUND));
            explosionSound = Gdx.audio.newSound(Gdx.files.internal(GameResources.EXPLOSION_SOUND));
            coinSound = Gdx.audio.newSound(Gdx.files.internal(GameResources.COIN_SOUND));
        } catch (Exception e) { System.out.println("Sound not loaded"); }
    }

    public void playMusic() {
        if (isMusicOn && backgroundMusic != null) {
            backgroundMusic.setVolume(0.6f);
            backgroundMusic.play();
        }
    }

    public void stopMusic() {
        if (backgroundMusic != null) backgroundMusic.stop();
    }

    public void playShootSound() {
        if (isSoundOn && shootSound != null) {
            shootSound.play(0.6f);
        }
    }

    public void playExplosionSound() {
        if (isSoundOn && explosionSound != null) {
            explosionSound.play(1.0f);
        }
    }

    public void playCoinSound() {
        if (isSoundOn && coinSound != null) {
            coinSound.play(0.8f);
        }
    }

    public void dispose() {
        if (backgroundMusic != null) backgroundMusic.dispose();
        if (shootSound != null) shootSound.dispose();
        if (explosionSound != null) explosionSound.dispose();
        if (coinSound != null) coinSound.dispose();
    }
}
