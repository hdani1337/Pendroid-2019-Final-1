package hu.cehessteg.flight.Stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

import hu.cehessteg.flight.Actor.Arrow;
import hu.cehessteg.flight.Actor.Cloud;
import hu.cehessteg.flight.Actor.Sky;
import hu.cehessteg.flight.FlightGame;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

import static hu.cehessteg.flight.Stage.GameOverStage.BLANK_TEXTURE;
import static hu.cehessteg.flight.Stage.MenuStage.trebuc;

public class OptionsStage extends MyStage {

    public static final String NYILBAL_TEXUTE = "other/nyilB.png";
    public static final String NYILJOBB_TEXUTE = "other/nyilJ.png";
    public static final String WIND_SOUND = "sounds/wind.mp3";

    public static AssetList assetList;
    static {
        assetList = new AssetList();
        assetList.addTexture(NYILBAL_TEXUTE);
        assetList.addTexture(NYILJOBB_TEXUTE);
        assetList.addTexture(BLANK_TEXTURE);
        assetList.addFont(trebuc, trebuc, 120, Color.WHITE, AssetList.CHARS);
        assetList.addMusic(WIND_SOUND);
        assetList.collectAssetDescriptor(Sky.class, assetList);
        assetList.collectAssetDescriptor(Cloud.class, assetList);
    }

    private Sky sky;
    private Arrow arrowLeft;
    private Arrow arrowRigth;
    private MyLabel difi;
    private MyLabel difiLabel;
    private OneSpriteStaticActor blankDif;
    private OneSpriteStaticActor blankBack;
    private OneSpriteStaticActor blankTitle;
    private MyLabel title;
    private MyLabel back;
    private ArrayList<Cloud> clouds;

    public OptionsStage(MyGame game) {
        super(new ResponseViewport(900), game);
        addBackButtonScreenBackByStackPopListener();
        assignment();
        setSizesAndPositions();
        addListeners();
        addActors();

        if(game instanceof FlightGame){
            if(!((FlightGame)game).isMuted()){
                game.getMyAssetManager().getMusic(WIND_SOUND).play();
            }
        }
    }

    private void assignment()
    {
        sky = new Sky(game);

        clouds = new ArrayList<>();
        for (int i = 0; i < 10; i++) clouds.add(new Cloud(game, getViewport()));

        blankDif = new OneSpriteStaticActor(game, BLANK_TEXTURE);
        blankBack = new OneSpriteStaticActor(game, BLANK_TEXTURE);
        blankTitle = new OneSpriteStaticActor(game, BLANK_TEXTURE);
        arrowLeft = new Arrow(game, NYILBAL_TEXUTE, this);
        arrowRigth = new Arrow(game, NYILJOBB_TEXUTE, this);

        back = new MyLabel(game,"Vissza a menübe", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setFontScale(0.4f);
                setAlignment(Align.bottomLeft);
            }
        };

        title = new MyLabel(game,"Beállítások", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setFontScale(0.65f);
                setAlignment(0);
            }
        };

        difi = new MyLabel(game,"Nehézségi fokozat", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setFontScale(0.4f);
                setAlignment(0);
                setTouchable(null);
            }
        };

        difiLabel = new MyLabel(game,"UNDEFINED", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setFontScale(0.5f);
                setAlignment(0);
                setTouchable(null);
            }

            @Override
            public void act(float delta) {
                super.act(delta);
                if(game != null){
                    if(game instanceof FlightGame) {
                        switch (((FlightGame)game).getDifficulty()){
                            case 1:{
                                if(!getText().equals("Könnyű")) {
                                    setText("Könnyű");
                                    setColor(Color.GREEN);
                                    setAlignment(0);
                                    difiLabel.setPosition(blankDif.getX() + blankDif.getWidth()/2 - difiLabel.getWidth() / 2, arrowLeft.getY() - difiLabel.getHeight() * 0.25f);
                                }
                                break;
                            }

                            case 2:{
                                if(!getText().equals("Normál")) {
                                    setText("Normál");
                                    setColor(Color.YELLOW);
                                    setAlignment(0);
                                    difiLabel.setPosition(blankDif.getX() + blankDif.getWidth()/2 - difiLabel.getWidth() / 2, arrowLeft.getY() - difiLabel.getHeight() * 0.25f);
                                }
                                break;
                            }

                            case 3:{
                                if(!getText().equals("Nehéz")) {
                                    setText("Nehéz");
                                    setColor(Color.RED);
                                    setAlignment(0);
                                    difiLabel.setPosition(blankDif.getX() + blankDif.getWidth()/2 - difiLabel.getWidth() / 2, arrowLeft.getY() - difiLabel.getHeight() * 0.25f);
                                }
                                break;
                            }
                        }
                    }
                }
            }
        };
    }

    private void setSizesAndPositions()
    {
        /**Sizes**/
        sky.setSize(getViewport().getWorldWidth(), getViewport().getWorldHeight());
        blankDif.setSize(450,200);
        blankTitle.setSize(420,100);
        blankBack.setSize(400,90);

        /**Positions**/
        blankDif.setPosition(getViewport().getWorldWidth() / 2 - blankDif.getWidth() * 1.5f, getViewport().getWorldHeight() / 2 - blankDif.getHeight() / 2);
        arrowLeft.setPosition(blankDif.getX() + 25,blankDif.getY() + 25);
        arrowRigth.setPosition(blankDif.getX() + blankDif.getWidth() - 25 - arrowRigth.getWidth(),arrowLeft.getY());
        difi.setPosition(blankDif.getX() + blankDif.getWidth() - (difi.getWidth() * 0.725f), blankDif.getY() + blankDif.getHeight() - difi.getHeight() + 15);
        back.setPosition(20,20);
        blankTitle.setPosition(getViewport().getWorldWidth()/2-blankTitle.getWidth()/2,getViewport().getWorldHeight()-blankTitle.getHeight());
        title.setPosition(blankTitle.getX() + blankTitle.getWidth()/2 - title.getWidth()/2, blankTitle.getY() - 20);

        /**Alphas**/
        blankDif.setAlpha(0.5f);
        blankBack.setAlpha(0.5f);
        blankTitle.setAlpha(0.7f);
    }

    private void addListeners()
    {
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreenBackByStackPop();
            }
        });
    }

    private void addActors()
    {
        addActor(sky);
        for (Cloud c : clouds) addActor(c);
        addActor(blankDif);
        addActor(arrowLeft);
        addActor(arrowRigth);
        addActor(difi);
        addActor(difiLabel);
        addActor(blankBack);
        addActor(back);
        addActor(blankTitle);
        addActor(title);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(game instanceof FlightGame) {
            if (!((FlightGame) game).isMuted()) {
                if (game.getMyAssetManager().getMusic(WIND_SOUND).getPosition() >= 8.9)
                    game.getMyAssetManager().getMusic(WIND_SOUND).setPosition(0);
            }
        }
    }
}
