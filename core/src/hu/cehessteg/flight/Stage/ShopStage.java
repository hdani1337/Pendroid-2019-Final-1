package hu.cehessteg.flight.Stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;

import hu.cehessteg.flight.Actor.Cloud;
import hu.cehessteg.flight.Actor.Sky;
import hu.cehessteg.flight.FlightGame;
import hu.cehessteg.flight.Screen.GameScreen;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

import static hu.cehessteg.flight.Stage.GameStage.isAct;
import static hu.cehessteg.flight.Stage.MenuStage.trebuc;

public class ShopStage extends MyStage {
    public static final String BLANK_TEXTURE = "other/black.png";
    public static final String PRICES_TEXTURE = "other/arlista.png";
    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(BLANK_TEXTURE);
        assetList.addTexture(PRICES_TEXTURE);
        assetList.addFont(trebuc, trebuc, 120, Color.WHITE, AssetList.CHARS);
    }

    MyLabel text;
    MyLabel lvlup;
    MyLabel menu;
    MyLabel lvlcost;
    OneSpriteStaticActor black;
    OneSpriteStaticActor arlista;
    Sky sky;
    ArrayList<Cloud> clouds;

    public ShopStage(MyGame game) {
        super(new ResponseViewport(900), game);
        assignment();
        addActors();
    }

    private void assignment(){
        clouds = new ArrayList<>();
        for (int i = 0; i < 18; i++) clouds.add(new Cloud(game, getViewport()));
        sky = new Sky(game){
            @Override
            public void init() {
                super.init();
                setSize(getViewport().getWorldWidth(), getViewport().getWorldHeight());
            }
        };

        text = new MyLabel(game, "Üdv a boltban, itt vásárolhatsz fejlesztéseket a repülőgépedhez.\n Bizonyos fejlesztések után már lőni, későbbb bombázni is tudni fogsz.", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setAlignment(0);
                setFontScale(0.3f);
                setColor(1,1,1,0);
                setPosition(getViewport().getWorldWidth()/2-this.getWidth()/2, getViewport().getWorldHeight()*0.79f);
            }
        };

        lvlup = new MyLabel(game, "Szint fejlesztése", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setAlignment(0);
                setFontScale(0.4f);
                setColor(1,1,1,0);
                setPosition(0, getViewport().getWorldHeight()*0.55f);
                addListener(new ClickListener()
                {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        if(((FlightGame) game).getPenz() >= (((FlightGame)game).getPlaneLevel()) * 25){ //pénz check
                            ((FlightGame) game).setPenz(((FlightGame) game).getPenz() - (((FlightGame)game).getPlaneLevel()) * 25); // pénz levétel
                            ((FlightGame) game).setPlaneLevel(((FlightGame) game).getPlaneLevel() + 1); // szint up
                            ((FlightGame) game).saveCoins(); //pénz mentése
                            lvlcost.setText("Jelenlegi szint: " + ((FlightGame) game).getPlaneLevel() + "\nFejlesztés ára: " + (((FlightGame)game).getPlaneLevel()) * 25);
                        }
                    }
                });
            }
        };

        lvlcost = new MyLabel(game, "Jelenlegi szint: " + ((FlightGame) game).getPlaneLevel() + "\nFejlesztés ára: " + (((FlightGame)game).getPlaneLevel()) * 25 ,new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setAlignment(0);
                setFontScale(0.3f);
                setColor(1,1,1,0);
                setPosition(0, getViewport().getWorldHeight()*0.4f);
                setTouchable(null);
            }
        };

        menu = new MyLabel(game, "Vissza a menübe", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setAlignment(0);
                setFontScale(0.7f);
                setColor(1,1,1,0);
                setPosition(getViewport().getWorldWidth()-this.getWidth()*7/8, 0f);

                addListener(new ClickListener()
                {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        if(game instanceof FlightGame)
                            ((FlightGame) game).saveCoins();
                        game.setScreenBackByStackPop();
                    }
                });
            }
        };

        arlista = new OneSpriteStaticActor(game, PRICES_TEXTURE){
            @Override
            public void init() {
                super.init();
                setSize(getWidth()*0.8f, getHeight()*0.8f);
                setPosition(getViewport().getWorldWidth()*0.65f, getViewport().getWorldHeight()/2-this.getHeight()/2);
            }
        };

        black = new OneSpriteStaticActor(game, BLANK_TEXTURE){
            @Override
            public void init() {
                super.init();
                setAlpha(0);
                setSize(getViewport().getWorldWidth(),getViewport().getWorldHeight());
            }
        };
    }

    private void addActors()
    {
        addActor(sky);
        addActor(black);
        for (Cloud c : clouds) addActor(c);
        addActor(text);
        addActor(lvlup);
        addActor(menu);
        addActor(lvlcost);
        addActor(arlista);
    }

    private float alpha = 0;

    private void fadeIn(){
        if(alpha < 0.95) {
            setAlphas();
            alpha += 0.05;
        }
        else alpha = 1;
    }

    private void setAlphas()
    {
        black.setAlpha(alpha * 0.4f);
        arlista.setAlpha(alpha);
        text.setColor(1,1,1,alpha);
        lvlup.setColor(1,1,1,alpha);
        menu.setColor(1,1,1,alpha);
        lvlcost.setColor(1,1,1,alpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        fadeIn();
    }
}