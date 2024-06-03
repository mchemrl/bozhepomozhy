package extensions;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import entities.Player;
import screens.Levels;

public class LevelMaker{
    public void win(Player player, int coordinateX, int coordinateY){
        if(player.getX() == coordinateX && player.getY()==coordinateY)
            ((Game) Gdx.app.getApplicationListener()).setScreen(new Levels());
    }
}
