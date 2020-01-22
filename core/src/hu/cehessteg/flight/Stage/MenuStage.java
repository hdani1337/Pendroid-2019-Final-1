package hu.cehessteg.flight.Stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hu.cehessteg.flight.Screen.GameScreenBombing;
import hu.cehessteg.flight.Screen.GameScreenCombat;
import hu.cehessteg.flight.Screen.InfoScreen;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

public class MenuStage extends MyStage {
    public static String trebuc = "trebuc.ttf";
    public static AssetList assetList = new AssetList();
    static {
        assetList.addFont(trebuc, trebuc, 120, Color.WHITE, AssetList.CHARS);
    }

    private MyLabel elindultam;
    private MyLabel bombazas;
    private MyLabel legicsata;
    private MyLabel infostage;

    public MenuStage(MyGame game) {
        super(new ResponseViewport(900), game);
        addBackButtonScreenBackByStackPopListener();
        addActor(elindultam = new MyLabel(game, "Elindultam", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setAlignment(0);
                setPosition(getViewport().getWorldWidth()/2-this.getWidth()/2,getViewport().getWorldHeight()/2-this.getHeight()/2);
            }
        });

        addActor(bombazas =new MyLabel(game, "Bombázás", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setAlignment(0);
                setPosition(getViewport().getWorldWidth()/4-this.getWidth()/2,getViewport().getWorldHeight()/4-this.getHeight()/2);
                addListener(new ClickListener()
                {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        game.setScreen(new GameScreenBombing(game));
                    }
                });
            }
        });

        addActor(legicsata = new MyLabel(game, "Légicsata", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setAlignment(0);
                setPosition(getViewport().getWorldWidth()/2+this.getWidth()/2,getViewport().getWorldHeight()/4-this.getHeight()/2);
                addListener(new ClickListener()
                {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        game.setScreen(new GameScreenCombat(game));
                    }
                });
            }
        });

        addActor(infostage = new MyLabel(game, "InfoStage", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setAlignment(0);
                setPosition(getViewport().getWorldWidth()/2+this.getWidth()/2,getViewport().getWorldHeight()*0.7f);
                addListener(new ClickListener()
                {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        game.setScreen(new InfoScreen(game));
                    }
                });
            }
        });
    }

    float alpha = 0;

    void fadeIn()
    {
        if(alpha < 0.98) {
            alpha += 0.02;
            setAlphas();
        }
        else alpha = 1;

    }

    void setAlphas()
    {
        elindultam.setColor(1,1,1, alpha);
        legicsata.setColor(1,1,1, alpha);
        bombazas.setColor(1,1,1, alpha);
        infostage.setColor(1,1,1, alpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        fadeIn();
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            /**
             * !!!NEM MŰKÖDIK A addBackButtonScreenBackByStackPopListener()!!!
             * **/
            game.setScreenBackByStackPop();
        }
    }
}
