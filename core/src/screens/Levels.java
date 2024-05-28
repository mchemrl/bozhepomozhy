package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Levels implements Screen {
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private TextButton settingButton;
    private TextButton level1Button;
    private Label heading1;
    private Label heading2;
    private Sound buttonClickSound;
    private Music menuMusic;
    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("ui/levels/buttons.txt");
        skin = new Skin(Gdx.files.internal("ui/levels/levelsSkin.json"), atlas);

        table = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        buttonClickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/select.ogg"));
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/menumusic.wav"));

        menuMusic.setVolume(0.15f);
        menuMusic.play();
        menuMusic.setLooping(true);

        heading1 = new Label("MAP", skin, "map");
        heading1.setColor(1, 1,1,1);
        heading2 = new Label("STAGEBASED MOD", skin, "mod");
        heading1.setFontScale(.8f);
        heading2.setFontScale(.5f);

        level1Button = new TextButton("1", skin, "levelbutton");
        level1Button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClickSound.play();
                menuMusic.stop();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Level1());
            }});

        settingButton = new TextButton("", skin, "settingbutton"); //add later

        table.add(heading1).spaceBottom(10).row();
        table.add(heading2).spaceBottom(50).row();
        table.add(level1Button);
        table.debug();
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor( 0, 0,0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        atlas.dispose();
        skin.dispose();
        menuMusic.dispose();
        buttonClickSound.dispose();
    }
}
