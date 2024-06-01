package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import tween.ActorAccessor;

public class BottomMenu {
    private Stage stage;
    private Table table;
    private TweenManager tweenManager;

    public BottomMenu(Stage stage, Skin skin, Runnable shopAction, Runnable levelsAction, Runnable arcadeAction) {
        this.stage = stage;
        this.tweenManager = new TweenManager();

        table = new Table(skin);
        table.setFillParent(true);
        table.center().bottom();

        createTextButton("Shop", skin, shopAction, true, false, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                shopAction.run();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new ShopScreen());
            }
        });
        createTextButton("Levels", skin, levelsAction, true, true, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                levelsAction.run();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Levels());
            }
        });
        createTextButton("Arcade", skin, arcadeAction, true, false, new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                arcadeAction.run();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new ArcadeScreen());
            }
        });

        stage.addActor(table);

        Tween.registerAccessor(Actor.class, new ActorAccessor());
    }

    private void createTextButton(String text, Skin skin, Runnable action, boolean animate, boolean levelChoosen, ClickListener clickListener) {
        TextButton button = new TextButton(text, skin);
        button.getLabel().setAlignment(Align.center);

        // Додайте відступ зверху для тексту кнопки
        button.getLabelCell().padBottom(20);

        if (levelChoosen) {
            // Рух тексту на кнопці, якщо вкладка вибрана
            animateButtonText(button.getLabel(), 7);
        }

        button.addListener(clickListener);

        table.add(button).expandX().fillX().height(80).pad(0);
    }

    private void animateButtonText(Actor actor, int distance) {
        actor.addAction(Actions.forever(Actions.sequence(
                Actions.moveBy(0, distance, 0.3f),
                Actions.moveBy(0, -distance, 0.3f)
        )));
    }

    public void update(float delta) {
        tweenManager.update(delta);
    }
}
