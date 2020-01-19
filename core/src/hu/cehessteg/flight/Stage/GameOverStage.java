package hu.cehessteg.flight.Stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hu.cehessteg.flight.Screen.GameScreenCombat;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

import static hu.cehessteg.flight.Stage.GameStageCombat.isAct;
import static hu.cehessteg.flight.Stage.MenuStage.trebuc;

public class GameOverStage extends MyStage {

    public static final String BLANK_TEXTURE = "black.png";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(BLANK_TEXTURE);
        assetList.addFont(trebuc, trebuc, 120, Color.WHITE, AssetList.CHARS);
    }

    MyLabel text;
    MyLabel again;
    MyLabel menu;
    OneSpriteStaticActor black;

    public GameOverStage(MyGame game) {
        super(new ResponseViewport(900),game);
        addedActors = false;

        text = new MyLabel(game, "Vége a játéknak", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setAlignment(0);
                setColor(1,1,1,0);
                setPosition(getViewport().getWorldWidth()/2-this.getWidth()/2, getViewport().getWorldHeight()*0.6f);
            }
        };

        again = new MyLabel(game, "Újraindítás", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setAlignment(0);
                setFontScale(0.7f);
                setColor(1,1,1,0);
                setPosition(getViewport().getWorldWidth()/2-this.getWidth()/2, getViewport().getWorldHeight()*0.35f);

                addListener(new ClickListener()
                {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        game.setScreen(new GameScreenCombat(game), false);
                    }
                });
            }
        };

        menu = new MyLabel(game, "Vissza a menübe", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setAlignment(0);
                setFontScale(0.7f);
                setColor(1,1,1,0);
                setPosition(getViewport().getWorldWidth()/2-this.getWidth()/2, getViewport().getWorldHeight()*0.2f);

                addListener(new ClickListener()
                {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        game.setScreenBackByStackPop();
                    }
                });
            }
        };

        black = new OneSpriteStaticActor(game, BLANK_TEXTURE);
        black.setAlpha(0);
        black.setSize(getViewport().getWorldWidth(),getViewport().getWorldHeight());
    }

    private boolean addedActors;
    private float alpha = 0;

    @Override
    public void act(float delta) {

        if (!isAct){

            if(!addedActors){
                addActor(black);
                addActor(text);
                addActor(again);
                addActor(menu);
                addedActors = true;
            }

            if(alpha < 0.95)
            {
                black.setAlpha(alpha * 0.4f);
                text.setColor(1,1,1,alpha);
                again.setColor(1,1,1,alpha);
                menu.setColor(1,1,1,alpha);
                alpha += 0.05;
            }else alpha = 1;
        }
    }
}
