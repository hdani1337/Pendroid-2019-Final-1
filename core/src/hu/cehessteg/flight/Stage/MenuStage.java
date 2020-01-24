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
import hu.cehessteg.flight.Screen.GameScreenBombing;
import hu.cehessteg.flight.Screen.GameScreen;
import hu.cehessteg.flight.Screen.InfoScreen;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

public class MenuStage extends MyStage {
    public static String trebuc = "other/trebuc.ttf";
    public static AssetList assetList = new AssetList();
    public static final String MENU_HATTER = "other/menu.png";
    static {
        assetList.addFont(trebuc, trebuc, 120, Color.WHITE, AssetList.CHARS);
        assetList.addTexture(MENU_HATTER);
    }

    private MyLabel bombazas;
    private MyLabel legicsata;
    private MyLabel infostage;
    private OneSpriteStaticActor menuHatter;
    private Sky sky;
    private ArrayList<Cloud> clouds;


    public MenuStage(MyGame game) {
        super(new ResponseViewport(900), game);
        addBackButtonScreenBackByStackPopListener();
        assignment();
        addActors();
        setPositions();
        labelThings();
    }

    void assignment(){

        menuHatter = new OneSpriteStaticActor(game,MENU_HATTER);
        bombazas = new MyLabel(game,"Bombázás", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.BLACK)) {
            @Override
            public void init() {
                addListener(new ClickListener()
                {
                    @Override
                    public void clicked(InputEvent event,float x,float y){
                        super.clicked(event,x,y);
                        game.setScreen(new GameScreenBombing(game));
                    }

                });
            }
        };
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

        clouds = new ArrayList<>();
        for (int i = 0; i < 18; i++) clouds.add(new Cloud(game, getViewport()));

        sky = new Sky(game) {
            @Override
            public void init() {
                super.init();
                setSize(getViewport().getWorldWidth(),getViewport().getWorldHeight());
            }
        };
    }

    void setPositions(){

        menuHatter.setPosition(getViewport().getWorldWidth()/2-this.getWidth()/1.5f,getViewport().getWorldHeight()/2*0.001f);
        legicsata.setPosition( getViewport().getWorldWidth()-this.getWidth()/1.97f,getViewport().getWorldHeight()-this.getHeight()*0.625f);
        bombazas.setPosition(getViewport().getWorldWidth()-this.getWidth()/1.93f,getViewport().getWorldHeight()-this.getHeight()*0.755f);
        infostage.setPosition(getViewport().getWorldWidth()-this.getWidth()/1.97f,getViewport().getWorldHeight()-this.getHeight()*0.883f);



    }

    void labelThings(){
        bombazas.setAlignment(0);
        legicsata.setAlignment(0);
        infostage.setAlignment(0);

        bombazas.setFontScale(0.38f);
        legicsata.setFontScale(0.38f);
        infostage.setFontScale(0.38f);


    }


    void addActors() {


        addActor(sky);
        for (int i = 0; i < clouds.size(); i++) addActor(clouds.get(i));

        addActor(menuHatter);
        addActor(bombazas);
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


        bombazas.setColor(1,1,1, alpha);
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
