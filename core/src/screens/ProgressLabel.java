package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class ProgressLabel extends Table {
    private Label pointsLabel;
    private Image pearlImage;
private Texture pearlTexture;
    public ProgressLabel() {
        pearlTexture = new Texture(Gdx.files.internal("assets/img/pearlLabel.png"));
        Sprite pearlSprite = new Sprite(pearlTexture);
        pointsLabel = new Label("X 0", new LabelStyle(new BitmapFont(), Color.WHITE));
        pointsLabel.setFontScale(4);
        pearlImage = new Image(pearlSprite);
        this.add(pearlImage).size(pearlSprite.getWidth(), pearlSprite.getHeight()).pad(0);
        this.add(pointsLabel).expandX().pad(0);
        this.setPosition(60, Gdx.graphics.getHeight() - this.getHeight() - 100);
        this.pack();
    }

    public void updatePoints(int collectedCoins) {
        pointsLabel.setText("x " + collectedCoins);
    }

    public void dispose() {
      pearlTexture.dispose();
    }
}