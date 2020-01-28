package hu.cehessteg.flight.Stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

import hu.cehessteg.flight.Actor.Arrow;
import hu.cehessteg.flight.Actor.Cloud;
import hu.cehessteg.flight.Actor.Coin;
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

    //-----LABELEK-----
    MyLabel text;
    MyLabel lvlup;
    MyLabel menu;
    MyLabel lvlcost;
    MyLabel shopTitle;
    //-----------------

    //-----SZÖVEGHÁTTEREK-----
    OneSpriteStaticActor black;
    OneSpriteStaticActor blackMenu;
    OneSpriteStaticActor blackShopTitle;
    OneSpriteStaticActor blackUp;
    //------------------------

    //-----ÁRLISTA-----
    OneSpriteStaticActor arlista;

    //-----HÁTTÉR-----
    Sky sky;
    ArrayList<Cloud> clouds;

    //-----PÉNZKIJELZŐ-----
    Coin coin;

    //-----SZINTJELZŐ-----
    Arrow currentLevel;

    public ShopStage(MyGame game) {
        super(new ResponseViewport(900), game);
        background();
        assignment();
        textBackgrounds();
        addActors();
    }

    private void background(){
        clouds = new ArrayList<>();
        for (int i = 0; i < 18; i++) clouds.add(new Cloud(game, getViewport()));
        sky = new Sky(game){
            @Override
            public void init() {
                super.init();
                setSize(getViewport().getWorldWidth(), getViewport().getWorldHeight());
            }
        };
    }

    private void assignment(){
        coin = new Coin(game);

        currentLevel = new Arrow(game, OptionsStage.NYILJOBB_TEXUTE, null, Arrow.ArrowModes.NULL);

        shopTitle = new MyLabel(game,"Bolt", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setFontScale(0.65f);
                setAlignment(0);
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

        lvlcost = new MyLabel(game, "Jelenlegi szint: " + ((FlightGame) game).getPlaneLevel() + "\nFejlesztés ára: " + (((FlightGame)game).getPlaneLevel()) * 25 ,new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setAlignment(Align.bottomLeft);
                setFontScale(0.3f);
                setColor(1,1,1,0);
                setPosition(getViewport().getWorldWidth()*0.25f, getViewport().getWorldHeight()*0.44f);
                setTouchable(null);
            }

            @Override
            public void act(float delta) {
                super.act(delta);
                if(game != null){
                    if(game instanceof FlightGame){
                        if((((FlightGame) game).getPlaneLevel()) == 10){
                            if(getY() != getViewport().getWorldHeight()/2-this.getHeight()/2){
                                if(getX() != getViewport().getWorldWidth()/2 - this.getWidth()){
                                    setFontScale(0.45f);
                                    setAlignment(0);
                                    setPosition(getViewport().getWorldWidth()/2 - lvlcost.getWidth(), getViewport().getWorldHeight()/2-lvlcost.getHeight()/2);
                                    setText("Elérted a maximális szintet!");
                                    lvlup.setText("");
                                    lvlup.setTouchable(null);
                                    blackUp.setAlpha(0);
                                }
                            }
                        }
                    }
                }
            }
        };

        lvlup = new MyLabel(game, "Szint fejlesztése", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setAlignment(Align.bottomLeft);
                setFontScale(0.4f);
                setColor(1,1,1,0);
                setPosition(getViewport().getWorldWidth()*0.23f, getViewport().getWorldHeight()*0.55f);
                if(((FlightGame) game).getPlaneLevel() < 10) {
                    addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            super.clicked(event, x, y);

                            if (((FlightGame) game).getPenz() >= (((FlightGame) game).getPlaneLevel()) * 25) { //pénz check
                                ((FlightGame) game).setPenz(((FlightGame) game).getPenz() - (((FlightGame) game).getPlaneLevel()) * 25); // pénz levétel
                                ((FlightGame) game).setPlaneLevel(((FlightGame) game).getPlaneLevel() + 1); // szint up
                                ((FlightGame) game).saveCoins(); //pénz mentése
                                lvlcost.setText("Jelenlegi szint: " + ((FlightGame) game).getPlaneLevel() + "\nFejlesztés ára: " + (((FlightGame) game).getPlaneLevel()) * 25);

                            }

                        }
                    });
                }
            }
        };

        menu = new MyLabel(game, "Vissza a menübe", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setAlignment(Align.bottomLeft);
                setFontScale(0.4f);
                setColor(1,1,1,0);

                addListener(new ClickListener()
                {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        if(game instanceof FlightGame)
                            ((FlightGame) game).saveCoins();
                        game.setScreenBackByStackPopWithPreloadAssets(new MyPreLoadingStage(game));
                    }
                });
            }
        };

        arlista = new OneSpriteStaticActor(game, PRICES_TEXTURE){
            @Override
            public void init() {
                super.init();
                setSize(getWidth()*0.8f, getHeight()*0.8f);
                setPosition(getViewport().getWorldWidth()*0.65f, getViewport().getWorldHeight()/2-this.getHeight()*0.6f);
                currentLevel.setSize(currentLevel.getWidth()*0.7f, currentLevel.getHeight()*0.7f);
                currentLevel.setX(this.getX() - currentLevel.getWidth()*0.55f);
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

    private void textBackgrounds(){
        blackMenu = new OneSpriteStaticActor(game, BLANK_TEXTURE){
            @Override
            public void init() {
                super.init();
                setSize(410,80);
                setPosition(getViewport().getWorldWidth()-this.getWidth(),0);
                menu.setPosition(this.getX() + 25, 10);
            }
        };

        blackShopTitle = new OneSpriteStaticActor(game, BLANK_TEXTURE){
            @Override
            public void init() {
                super.init();
                setSize(200,100);
                setPosition(getViewport().getWorldWidth()/2-this.getWidth()/2,getViewport().getWorldHeight()-this.getHeight());
                shopTitle.setPosition(this.getX() + this.getWidth()/2 - shopTitle.getWidth()/2, this.getY() - 20);
                text.setY(this.getY() - text.getHeight() * 0.7f);
            }
        };

        blackUp = new OneSpriteStaticActor(game, BLANK_TEXTURE){
            @Override
            public void init() {
                super.init();
                setPosition(lvlup.getX() - 10, lvlup.getY() - 10);
                setSize(370,80);
            }
        };
    }

    private void addActors()
    {
        //-----HÁTTÉR-----
        addActor(sky);
        addActor(black);
        for (Cloud c : clouds) addActor(c);

        addActor(text);//LEÍRÁS A BOLTRÓL

        //-----HA MÁR ELÉRTE A 10-ES SZINTET AKKOR NEM ADOM HOZZÁ A FEJLESZTÉST-----
        if(((FlightGame) game).getPlaneLevel() < 10) {
            addActor(blackUp);
            addActor(lvlup);
        }

        //-----VISSZA A MENÜBE ÉS A HÁTTERE-----
        addActor(blackMenu);
        addActor(menu);

        //-----BOLT FELIRAT ÉS A HÁTTERE-----
        addActor(blackShopTitle);
        addActor(shopTitle);

        addActor(lvlcost);//KÖVETKEZŐ SZINT ÁRA
        addActor(arlista);//ÁRLISTA
        addActor(coin);//PÉNZKIJELZŐ
        addActor(currentLevel);
    }

    private float alpha = 0;

    private void fadeIn(){
        if(alpha < 0.98) {
            alpha += 0.02;
            setAlphas();
        }
        else alpha = 1;
    }

    private void setAlphas()
    {
        black.setAlpha(alpha * 0.4f);
        blackMenu.setAlpha(alpha/2);
        blackShopTitle.setAlpha(alpha/2);
        blackUp.setAlpha(alpha/2);
        arlista.setAlpha(alpha);
        coin.setAlpha(alpha);
        shopTitle.setColor(1,1,1,alpha);
        text.setColor(1,1,1,alpha);
        lvlup.setColor(1,1,1,alpha);
        menu.setColor(1,1,1,alpha);
        lvlcost.setColor(1,1,1,alpha);
    }

    private void setCurrentLevelPosition(){
        if(game != null){
            if(game instanceof FlightGame){
                if(currentLevel.getY() != arlista.getY() + arlista.getHeight() - 50 - (45*((FlightGame)game).getPlaneLevel())) {
                    currentLevel.setY(arlista.getY() + arlista.getHeight() - 50 - (45 * ((FlightGame) game).getPlaneLevel()));
                }
            }
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        fadeIn();
        setCurrentLevelPosition();
    }
}