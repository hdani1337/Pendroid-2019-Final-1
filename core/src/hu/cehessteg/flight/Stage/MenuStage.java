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
import hu.cehessteg.flight.Screen.GameScreen;
import hu.cehessteg.flight.Screen.InfoScreen;
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
    static {
        assetList.addFont(trebuc, trebuc, 120, Color.WHITE, AssetList.CHARS);
        assetList.addTexture(MENU_HATTER);
        assetList.addTexture(EXIT_PIROS);
    }

    private MyLabel shop;
    private MyLabel legicsata;
    private MyLabel infostage;
    private OneSpriteStaticActor menuHatter;
    private OneSpriteStaticActor exitPiros;
    private Sky sky;
    private ArrayList<Cloud> clouds;


    public MenuStage(MyGame game) {
        super(new ResponseViewport(900), game);
        addBackButtonScreenBackByStackPopListener();
        assignment();
        cloudStuff();
        addActors();
        setPositions();
        labelThings();
    }

    void assignment(){

        menuHatter = new OneSpriteStaticActor(game,MENU_HATTER);
        exitPiros = new OneSpriteStaticActor(game,EXIT_PIROS);
        legicsata = new MyLabel(game,"Légicsata", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.BLACK)) {
            @Override
            public void init() {
                addListener(new ClickListener()
                {
                    @Override
                    public void clicked(InputEvent event,float x,float y){
                        super.clicked(event,x,y);
                        game.setScreen(new GameScreen(game));
                    }

                });
            }
        };
        shop = new MyLabel(game,"Bolt", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.BLACK)) {
            @Override
            public void init() {
                addListener(new ClickListener()
                {
                    @Override
                    public void clicked(InputEvent event,float x,float y){
                        super.clicked(event,x,y);
                        //game.setScreen(new GameScreenBombing(game));
                    }

                });
            }
        };
        infostage = new MyLabel(game,"InfoStage", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.BLACK)) {
            @Override
            public void init() {
                addListener(new ClickListener()
                {
                    @Override
                    public void clicked(InputEvent event,float x,float y){
                        super.clicked(event,x,y);
                        game.setScreen(new InfoScreen(game));
                    }

                });

            }
        };
        exitPiros.addListener(new ClickListener(){

           public void clicked(InputEvent event,float x, float y){
               super.clicked(event,x,y);
               Gdx.app.exit();
           }
       });

        sky = new Sky(game) {
            @Override
            public void init() {
                super.init();
                setSize(getViewport().getWorldWidth(),getViewport().getWorldHeight());
            }
        };
    }

    void cloudStuff()
    {
        addActor(sky);
        clouds = new ArrayList<>();
        for (int i = 0; i < 4; i++) clouds.add(new Cloud(game, getViewport()));
        for (int i = 0; i < clouds.size(); i++) addActor(clouds.get(i));

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

    void setPositions(){
        menuHatter.setZIndex(1000);
        exitPiros.setZIndex(1001);
        legicsata.setZIndex(1001);
        shop.setZIndex(1001);
        infostage.setZIndex(1001);
        menuHatter.setPosition(getViewport().getWorldWidth()/2-this.getWidth()/1.5f,0);
        exitPiros.setPosition(menuHatter.getX()+menuHatter.getWidth()/1.3799f,menuHatter.getHeight()*0.0275f);

        if(getViewport().getWorldWidth() > 1800) menuHatter.setX(0);//Ha a képarány nagyobb 18:9-nél, akkor 0-ra rakom a piltafülkét, hogy kiérjen a világ széléig

        legicsata.setPosition( menuHatter.getX()+menuHatter.getWidth()/1.99f,menuHatter.getHeight()*0.25f);
        shop.setPosition(menuHatter.getX()+menuHatter.getWidth()/1.76f,menuHatter.getHeight()*0.162f);
        infostage.setPosition(menuHatter.getX()+menuHatter.getWidth()/2,menuHatter.getHeight()*0.075f);
        check21by9AspectRatio();
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
        addActor(shop);
        addActor(legicsata);
        addActor(infostage);
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
