package aqualand.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class MemoryManager {
    private static Preferences prefs = Gdx.app.getPreferences("aqualand_prefs");

    public static void saveRecord(int score) {
        prefs.putInteger("best_score", score);
        prefs.flush();
        System.out.println("Рекорд сохранён: " + score);
    }

    public static int loadRecord() {
        int record = prefs.getInteger("best_score", 0);
        System.out.println("Загружен рекорд: " + record);
        return record;
    }

    public static void saveCoins(int coins) {
        prefs.putInteger("total_coins", coins);
        prefs.flush();
        System.out.println("Монетки сохранены: " + coins);
    }

    public static int loadCoins() {
        int coins = prefs.getInteger("total_coins", 0);
        System.out.println("Загружено монеток: " + coins);
        return coins;
    }

    public static void saveMusicSettings(boolean isOn) {
        prefs.putBoolean("music", isOn);
        prefs.flush();
    }

    public static boolean loadIsMusicOn() {
        return prefs.getBoolean("music", true);
    }

    public static void saveSoundSettings(boolean isOn) {
        prefs.putBoolean("sound", isOn);
        prefs.flush();
    }

    public static boolean loadIsSoundOn() {
        return prefs.getBoolean("sound", true);
    }
}
