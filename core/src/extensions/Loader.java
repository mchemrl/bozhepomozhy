package extensions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Loader {

    public boolean loadMusicSettings(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("core/src/extensions/settings.txt")));
            String line = reader.readLine();
            reader.close();
            return Boolean.parseBoolean(line);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean loadSoundSettings(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("core/src/extensions/settings.txt")));
            reader.readLine();
            String secondLine = reader.readLine();
            reader.close();
            return Boolean.parseBoolean(secondLine);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int loadProgress(){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(new File("core/src/extensions/progress.txt")));
            int coins = Integer.parseInt(reader.readLine());
            reader.close();
            return coins;
        }catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static boolean isLevel1passed() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("core/src/extensions/levels.txt")));
            String line = reader.readLine();
            reader.close();
            return Boolean.parseBoolean(line);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean isLevel2passed() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("core/src/extensions/levels.txt")));
            reader.readLine();
            String secondLine = reader.readLine();
            reader.close();
            return Boolean.parseBoolean(secondLine);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


}
