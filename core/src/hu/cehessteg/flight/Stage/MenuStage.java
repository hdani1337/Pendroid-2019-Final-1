package hu.cehessteg.flight.Stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;

import hu.cehessteg.flight.Actor.Cloud;
import hu.cehessteg.flight.Actor.Sky;
import hu.cehessteg.flight.FlightGame;
import hu.cehessteg.flight.Screen.GameScreen;
import hu.cehessteg.flight.Screen.InfoScreen;
import hu.cehessteg.flight.Screen.MenuScreen;
import hu.cehessteg.flight.Screen.OptionsScreen;
import hu.cehessteg.flight.Screen.ShopScreen;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimer;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimerListener;
import hu.csanyzeg.master.MyBaseClasses.Timers.Timer;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

public class MenuStage extends MyStage {
    public static String trebuc = "other/trebuc.ttf";
    public static AssetList assetList = new AssetList();
    public static final String MENU_HATTER = "menu/menu.png";
    public static final String EXIT_PIROS = "menu/exitPiros.png";
    public static final String EXIT_RING = "menu/exit.png";
    public static final String OPTIONS_TEXTURE = "menu/options.png";
    public static final String AIRPLANE_SOUND = "sounds/airplane.mp3";
    static {
        assetList.addFont(trebuc, trebuc, 120, Color.WHITE, AssetList.CHARS);
        assetList.addTexture(MENU_HATTER);
        assetList.addTexture(EXIT_PIROS);
        assetList.addTexture(EXIT_RING);
        assetList.addTexture(OPTIONS_TEXTURE);
        assetList.addMusic(AIRPLANE_SOUND);
        assetList.collectAssetDescriptor(Cloud.class, assetList);
        assetList.collectAssetDescriptor(Sky.class, assetList);
    }

    private MyLabel shop;
    private MyLabel legicsata;
    private MyLabel infostage;
    private OneSpriteStaticActor menuHatter;
    private OneSpriteStaticActor exitPiros;
    private OneSpriteStaticActor exitRing;
    private OneSpriteStaticActor options;
    private Sky sky;
    private ArrayList<Cloud> clouds;


    public MenuStage(final MyGame game) {
        super(new ResponseViewport(900), game);
        addBackButtonListener(new BackButtonListener() {
            @Override
            public void backKeyDown() {
                Gdx.app.exit();
            }
        });//HA A MENÜBEN NYOMJUK MEG A VISSZA GOMBOT AKKOR LÉPJEN KI A JÁTÉKBÓL, NE AZ INTRORA LÉPJEN VISSZA
        assignment();//ÉRTÉKEK HOZZÁRENDELÉSE
        cloudStuff();//FELHŐK BEÁLLÍTÁSA
        addListeners();//LISTENEREK HOZZÁADÁSA
        addActors();//ACTOROK HOZZÁADÁSA A STAGEHEZ
        setPositions();//POZÍCIONÁLGATÁS ÉS ZINDEXELÉS
        labelThings();//LABELEK ALIGNMENTJEI

        //HA NINCS LENÉMÍTVA AKKOR REPÜLŐGÉPZÚGÁS
        if(game instanceof FlightGame){
            if(!((FlightGame)game).isMuted()){
                game.getMyAssetManager().getMusic(AIRPLANE_SOUND).play();
            }
        }
    }

    void assignment(){
        //MAGA A REPÜLŐ
        menuHatter = new OneSpriteStaticActor(game,MENU_HATTER);

        //BEÁLLÍTÁSOK GOMB
        options = new OneSpriteStaticActor(game,OPTIONS_TEXTURE);

        /**
         * Dávid különszedte a gombot és a peremét azért, hogy a peremet a háttérre rakja de lusta vagyok egyberakni őket
         * */
        exitPiros = new OneSpriteStaticActor(game,EXIT_PIROS);
        exitRing = new OneSpriteStaticActor(game,EXIT_RING){
            @Override
            public void init() {
                super.init();
                setTouchable(null);
                /**
                 * !MINDEN LEGYEN KÜLÖN ACTOR, NE EGY STATIKUS KÉPEN LEGYEN MINDEN!
                 * **/
            }
        };

        //JÁTÉK INDÍTÁSA GOMB
        legicsata = new MyLabel(game,"Légicsata", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.BLACK)) {
            @Override
            public void init() {
                addListener(new ClickListener()
                {
                    @Override
                    public void clicked(InputEvent event,float x,float y){
                        super.clicked(event,x,y);
                        game.setScreenWithPreloadAssets(GameScreen.class, new MyPreLoadingStage(game));
                    }

                });
            }
        };

        //BOLT GOMBJA
        shop = new MyLabel(game,"Bolt", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.BLACK)) {
            @Override
            public void init() {
                addListener(new ClickListener()
                {
                    @Override
                    public void clicked(InputEvent event,float x,float y){
                        super.clicked(event,x,y);
                        game.setScreenWithPreloadAssets(ShopScreen.class, new MyPreLoadingStage(game));
                    }

                });
            }
        };

        //INFORMÁCIÓ MENÜPONT GOMBJA
        infostage = new MyLabel(game,"Információ", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.BLACK)) {
            @Override
            public void init() {
                addListener(new ClickListener()
                {
                    @Override
                    public void clicked(InputEvent event,float x,float y){
                        super.clicked(event,x,y);
                        game.setScreenWithPreloadAssets(InfoScreen.class, new MyPreLoadingStage(game));
                    }

                });

            }
        };

        //ÉGBOLT
        sky = new Sky(game) {
            @Override
            public void init() {
                super.init();
                setSize(getViewport().getWorldWidth(),getViewport().getWorldHeight());
            }
        };
    }

    void addListeners(){
        //KILÉPÉS LISTENER
        exitPiros.addListener(new ClickListener(){

            public void clicked(InputEvent event,float x, float y){
                super.clicked(event,x,y);
                Gdx.app.exit();
            }
        });

        //BEÁLLÍTÁSOK GOMB LISTENER
        options.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreenWithPreloadAssets(OptionsScreen.class, new MyPreLoadingStage(game));
            }
        });
    }

    void cloudStuff()
    {
        addActor(sky);//ÉG HOZZÁADÁSA A STAGEHEZ

        //FELHŐK LISTA AMIT FELTÖLTÜNK KEZDETBEN 4 FELHŐVEL
        clouds = new ArrayList<>();
        for (int i = 0; i < 4; i++) clouds.add(new Cloud(game, getViewport()));
        for (int i = 0; i < clouds.size(); i++) addActor(clouds.get(i));

        //0.7 MÁSODPERCENKÉNT ÚJ FELHŐ
        addTimer(new TickTimer(0.7f, true, new TickTimerListener(){
            @Override
            public void onTick(Timer sender, float correction) {
                super.onTick(sender, correction);
                clouds.add(new Cloud(game, getViewport()));
                addActor(clouds.get(clouds.size()-1));
                clouds.get(clouds.size()-1).setZIndex(5);
            }
        }));
    }

    private float increment;
    /**
     * EZ AZÉRT KELL, MERT MAGÁRA A HÁTTÉRRE VANNAK RAJZOLVA A GOMBOK ÉS HA NYÚJTUK A HÁTTERET AKKOR CSÚSZTATNI KELL A LABELEKET IS
     *
     * !!!DŐNTŐBEN KÜLÖN KELL FELRAKNI A GOMBOKAT, NE A HÁTTÉRRE LEGYENEK RAJZOLVA!!!
     * **/

    void setPositions(){
        //ZINDEXEK
        menuHatter.setZIndex(1000);
        exitRing.setZIndex(1002);
        exitPiros.setZIndex(1001);
        legicsata.setZIndex(1001);
        options.setZIndex(1001);
        shop.setZIndex(1001);
        infostage.setZIndex(1001);

        //REPCSIHÁTTÉR
        menuHatter.setPosition(getViewport().getWorldWidth()/2-this.getWidth()/1.5f,0);

        if(getViewport().getWorldWidth() > 1800) menuHatter.setX(0);//Ha a képarány nagyobb 18:9-nél, akkor 0-ra rakom a piltafülkét, hogy kiérjen a világ széléig

        //LABELEK
        legicsata.setPosition( menuHatter.getX()+menuHatter.getWidth()/1.99f,menuHatter.getHeight()*0.25f);
        shop.setPosition(menuHatter.getX()+menuHatter.getWidth()/1.76f,menuHatter.getHeight()*0.162f);
        infostage.setPosition(menuHatter.getX()+menuHatter.getWidth()/2.06f,menuHatter.getHeight()*0.075f);

        //EXIT GOMB
        exitRing.setPosition(infostage.getX() + (infostage.getWidth() * 0.8f),infostage.getY());
        exitPiros.setPosition(exitRing.getX() + exitRing.getWidth() * 0.155f, exitRing.getY() + exitRing.getHeight() * 0.18f);
        check21by9AspectRatio();

        //BEÁLLÍTÁSOK GOMB
        options.setPosition(legicsata.getX() - (options.getWidth() * 0.8f), legicsata.getY());
    }

    void check21by9AspectRatio()
    {
        //Ha a képarány nagyobb mint 21:9, akkor már nem fér ki a pilótafülke, így nyújtani kell a képet és arrébbhelyezni a labeleket is
        if(getViewport().getWorldWidth() > 2100) {
            increment = (getViewport().getWorldWidth() / menuHatter.getWidth()) * 1.035f;
            menuHatter.setWidth(getViewport().getWorldWidth());
            legicsata.setX(legicsata.getX() * increment);
            shop.setX(shop.getX() * increment * 0.98f);
            infostage.setX(infostage.getX() * increment);
            exitRing.setX(exitRing.getX() * increment * 1.1f);
            exitPiros.setX(exitPiros.getX() * increment * 1.1f);
        }
    }

    void labelThings(){

        shop.setAlignment(0);
        legicsata.setAlignment(0);
        infostage.setAlignment(0);

        shop.setFontScale(0.38f);
        legicsata.setFontScale(0.38f);
        infostage.setFontScale(0.38f);
    }


    void addActors() {
        addActor(menuHatter);
        addActor(exitPiros);
        addActor(exitRing);
        addActor(shop);
        addActor(legicsata);
        addActor(infostage);
        addActor(options);
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
        shop.setColor(1,1,1, alpha);
        legicsata.setColor(1,1,1, alpha);
        infostage.setColor(1,1,1, alpha);
        exitPiros.setAlpha(alpha);
        exitRing.setAlpha(alpha);
        options.setAlpha(alpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        fadeIn();//ÁTTŰNÉSSEL JÖNNEK BE A CUCCOK

        /**
         * A HANG VÉGE ELŐTT EGY PICIVEL ELÖLRŐL KEZDEM
         * !!!ÍGY NEM LAGGOL BE A HANG!!!
         * **/
        if(game instanceof FlightGame) {
            if (!((FlightGame) game).isMuted()) {
                if (game.getMyAssetManager().getMusic(AIRPLANE_SOUND).getPosition() >= 6.7)
                    game.getMyAssetManager().getMusic(AIRPLANE_SOUND).setPosition(0);
            }
        }
    }
}
