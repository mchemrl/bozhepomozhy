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
    private String choosen;
    private TextButton shopButton;
    private TextButton levelsButton;
    private TextButton arcadeButton;
    private Actor currentAnimatedActor;

    public BottomMenu(Stage stage, Skin skin, Runnable shopAction, Runnable levelsAction, Runnable arcadeAction) {
        this.stage = stage;
        this.tweenManager = new TweenManager();
        this.shopButton = new TextButton("Shop", skin);
        this.levelsButton = new TextButton("Levels", skin);
        this.arcadeButton = new TextButton("Arcade", skin);

        shopButton.getLabel().setAlignment(Align.center);
        levelsButton.getLabel().setAlignment(Align.center);
        arcadeButton.getLabel().setAlignment(Align.center);

        shopButton.getLabelCell().padBottom(20);
        levelsButton.getLabelCell().padBottom(20);
        arcadeButton.getLabelCell().padBottom(20);

        table = new Table(skin);
        table.setFillParent(true);
        table.center().bottom();

        shopButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                updateAnimation(shopButton.getLabel());
                shopAction.run();
                choosen = "Shop";
                ((Game) Gdx.app.getApplicationListener()).setScreen(new ShopScreen());
            }
        });

        levelsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                updateAnimation(levelsButton.getLabel());
                levelsAction.run();
                choosen = "Levels";
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Levels());
            }
        });

        arcadeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                updateAnimation(arcadeButton.getLabel());
                arcadeAction.run();
                choosen = "Arcade";
                ((Game) Gdx.app.getApplicationListener()).setScreen(new ArcadeScreen());
            }
        });


        table.add(shopButton).expandX().fillX().height(85).pad(3);
        table.add(levelsButton).expandX().fillX().height(85).pad(3);
        table.add(arcadeButton).expandX().fillX().height(85).pad(3);


        stage.addActor(table);

        Tween.registerAccessor(Actor.class, new ActorAccessor());

        animateButtonText(levelsButton.getLabel(), 7);
        currentAnimatedActor = levelsButton.getLabel();
    }


    private void animateButtonText(Actor actor, int distance) {
        actor.addAction(Actions.forever(Actions.sequence(
                Actions.moveBy(0, distance, 0.3f),
                Actions.moveBy(0, -distance, 0.3f)
        )));
    }


    private void updateAnimation(Actor actor) {

        stopAnimation(currentAnimatedActor);

        animateButtonText(actor, 7);
        currentAnimatedActor = actor;
    }


    private void stopAnimation(Actor actor) {
        actor.clearActions();
    }


    public void update(float delta) {
        tweenManager.update(delta);
    }
}
