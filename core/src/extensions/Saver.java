package extensions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Saver {

    // if true - no music/sounds, 1 - music, 2 - sound
    public static void saveSettings(boolean isMusic, boolean isSound, boolean[] levelSettings) {
        try {
            PrintWriter writer = new PrintWriter(new File("core/src/extensions/settings.txt"));
            writer.println(isMusic);
            writer.println(isSound);
            for (boolean levelSetting : levelSettings) {
                writer.println(levelSetting);
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void saveProgress(int coins) {
        try {
            PrintWriter writer = new PrintWriter(new File("core/src/extensions/progress.txt"));
            writer.println(coins);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void saveLevelProgress(boolean[] levelSettings) {
        try {
            // Читаємо існуючі налаштування музики та звуку
            Loader loader = new Loader();
            boolean isMusic = loader.loadMusicSettings();
            boolean isSound = loader.loadSoundSettings();

            // Зберігаємо нові налаштування разом з прогресом рівнів
            saveSettings(isMusic, isSound, levelSettings);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
