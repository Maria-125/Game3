package io.github.some_example_name;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import aqualand.GameSettings;

public class Lwjgl3 {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("AQUALAND");
        config.setWindowedMode(GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);
        config.setForegroundFPS(60);

        new Lwjgl3Application(new Main(), config);
    }
}
