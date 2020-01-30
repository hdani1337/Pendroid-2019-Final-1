package hu.cehessteg.flight.Stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

import hu.cehessteg.flight.Actor.Airplane;
import hu.cehessteg.flight.Actor.Arrow;
import hu.cehessteg.flight.Actor.Cloud;
import hu.cehessteg.flight.Actor.Sky;
import hu.cehessteg.flight.Actor.Speaker;
import hu.cehessteg.flight.Actor.Trash;
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
        assetList.collectAssetDescriptor(Speaker.class, assetList);
        assetList.collectAssetDescriptor(Trash.class, assetList);
    }

    //HÁTTEREK
    private OneSpriteStaticActor blank;//STAGE
    private OneSpriteStaticActor blankTrash;//KUKA
    private OneSpriteStaticActor blankPlane;//REPCSI
    private OneSpriteStaticActor blankMute;//NÉMÍTÁS
    private OneSpriteStaticActor blankDif;//NEHÉZSÉG
    private OneSpriteStaticActor blankBack;//VISSZA
    private OneSpriteStaticActor blankTitle;//CÍM

    //LABELEK
    private MyLabel difi;//NEHÉZSÉGI FOKOZAT CÍM
    private MyLabel muti;//NÉMÍTÁS CÍM
    private MyLabel skini;//KINÉZET CÍM
    private MyLabel trashi;//KUKA CÍM
    private MyLabel difiLabel;//KIVÁLASZTOTT NEHÉZSÉGI FOKOZAT
    private MyLabel title;//BEÁLLÍTÁSOK CÍM
    private MyLabel back;//VISSZA

    private Sky sky;//ÉGBOLT

    //NEHÉZSÉGI FOKOZAT NYILAI
    private Arrow arrowLeft;
    private Arrow arrowRigth;

    //KINÉZET NYILAI
    private Arrow arrowLeft2;
    private Arrow arrowRigth2;

    private ArrayList<Cloud> clouds;//FELHŐ
    private Speaker speaker;//HANGSZÓRÓ
    private Trash trash;//KUKA

    private Airplane demoAirplane;//DEMO REPCSI
    public int id;//KINÉZET ID-JA
    public int maxId;//MAXIMIMUM VÁLASZTHATÓ ID

    public OptionsStage(MyGame game) {
        super(new ResponseViewport(900), game);
        addBackButtonScreenBackByStackPopListener();//VISSZA ÉS ESC GOMBNÁL VISSZALÉPÉS
        assignment();//ÉRTÉKEK HOZZÁRENDELÉSE A VÁLTOZÓKHOZ
        setSizesAndPositions();//MÉRETEK ÉS POZÍCIÓK BEÁLLÍTÁSA
        addListeners();//LISTENEREK HOZZÁADÁSA
        addActors();//ACTOROK HOZZÁADÁSA

        //MAXIMUM VÁLASZTHATÓ ID MEGÁLLAPÍTÁSA
        if(game instanceof FlightGame){
            if(!((FlightGame)game).isMuted()){
                game.getMyAssetManager().getMusic(WIND_SOUND).play();
            }
            id = ((FlightGame)game).getSkinID();

            if(((FlightGame)game).getPlaneLevel() >= 9) maxId = 5;
            else if(((FlightGame)game).getPlaneLevel() >= 7) maxId = 4;
            else if(((FlightGame)game).getPlaneLevel() >= 4) maxId = 3;
            else if(((FlightGame)game).getPlaneLevel() >= 3) maxId = 2;
            else maxId = 1;
        }
    }

    private void assignment()
    {
        sky = new Sky(game);

        clouds = new ArrayList<>();
        for (int i = 0; i < 10; i++) clouds.add(new Cloud(game, getViewport()));

        blank = new OneSpriteStaticActor(game, BLANK_TEXTURE);
        blankTrash = new OneSpriteStaticActor(game, BLANK_TEXTURE);
        blankPlane = new OneSpriteStaticActor(game, BLANK_TEXTURE);
        blankMute = new OneSpriteStaticActor(game, BLANK_TEXTURE);
        blankDif = new OneSpriteStaticActor(game, BLANK_TEXTURE);
        blankBack = new OneSpriteStaticActor(game, BLANK_TEXTURE);
        blankTitle = new OneSpriteStaticActor(game, BLANK_TEXTURE);
        arrowLeft = new Arrow(game, NYILBAL_TEXUTE, this, Arrow.ArrowModes.DIFFICULTY);
        arrowRigth = new Arrow(game, NYILJOBB_TEXUTE, this, Arrow.ArrowModes.DIFFICULTY);
        arrowLeft2 = new Arrow(game, NYILBAL_TEXUTE, this, Arrow.ArrowModes.SKIN);
        arrowRigth2 = new Arrow(game, NYILJOBB_TEXUTE, this, Arrow.ArrowModes.SKIN);

        speaker = new Speaker(game, this);

        trash = new Trash(game, speaker);

        demoAirplane = new Airplane(game);

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

        muti = new MyLabel(game,"Némítás", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setFontScale(0.4f);
                setAlignment(0);
                setTouchable(null);
            }
        };

        skini = new MyLabel(game,"Kinézet", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setFontScale(0.4f);
                setAlignment(0);
                setTouchable(null);
            }
        };

        trashi = new MyLabel(game,"Adatok törlése", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
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
        blankMute.setSize(400,200);
        blankTitle.setSize(420,100);
        blankBack.setSize(400,90);
        blankTrash.setSize(400,175);
        blank.setSize(getViewport().getWorldWidth(), getViewport().getWorldHeight());
        blankPlane.setSize(demoAirplane.getWidth() + 240, demoAirplane.getHeight() + 120);

        /**Positions**/
        blankDif.setPosition(getViewport().getWorldWidth() / 2 - blankDif.getWidth() - 35, getViewport().getWorldHeight() * 0.575f);
        blankMute.setPosition(getViewport().getWorldWidth()/2 + 35, blankDif.getY());
        arrowLeft.setPosition(blankDif.getX() + 25,blankDif.getY() + 25);
        arrowRigth.setPosition(blankDif.getX() + blankDif.getWidth() - 25 - arrowRigth.getWidth(),arrowLeft.getY());
        speaker.setPosition(blankMute.getX() + blankMute.getWidth() / 2 - speaker.getWidth()/2, blankMute.getY() + 15);
        difi.setPosition(blankDif.getX() + blankDif.getWidth() - (difi.getWidth() * 0.725f), blankDif.getY() + blankDif.getHeight() - difi.getHeight() + 15);
        muti.setPosition(blankMute.getX(), blankMute.getY() + blankMute.getHeight() - muti.getHeight() + 15);
        back.setPosition(20,20);
        blankTitle.setPosition(getViewport().getWorldWidth()/2-blankTitle.getWidth()/2,getViewport().getWorldHeight()-blankTitle.getHeight());
        title.setPosition(blankTitle.getX() + blankTitle.getWidth()/2 - title.getWidth()/2, blankTitle.getY() - 20);
        demoAirplane.setPosition(getViewport().getWorldWidth()/2-demoAirplane.getWidth()/2,getViewport().getWorldHeight()*0.26f);
        blankPlane.setPosition(demoAirplane.getX() - 120, demoAirplane.getY() - 30);
        arrowLeft2.setPosition(blankPlane.getX() + 25,demoAirplane.getY()+demoAirplane.getHeight()/2-arrowLeft2.getHeight()/2);
        arrowRigth2.setPosition(blankPlane.getX() + blankPlane.getWidth() - 25 - arrowRigth2.getWidth(),arrowLeft2.getY());
        skini.setPosition(blankPlane.getX() + blankPlane.getWidth()/2-skini.getWidth()/2, blankPlane.getY()+blankPlane.getHeight()-skini.getHeight()*0.85f);
        blankTrash.setPosition(getViewport().getWorldWidth()/2-blankTrash.getWidth()/2,0);
        trashi.setPosition(getViewport().getWorldWidth()/2-trashi.getWidth()/2, blankTrash.getY() + blankTrash.getHeight() - trashi.getHeight()*0.85f);
        trash.setPosition(getViewport().getWorldWidth()/2-trash.getWidth()/2,blankTrash.getY()+10);
    }

    private void addListeners()
    {
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreenBackByStackPopWithPreloadAssets(new MyPreLoadingStage(game));
            }
        });
    }

    private void addActors()
    {
        //ÉGBOLT ÉS FELHŐK
        addActor(sky);
        for (Cloud c : clouds) addActor(c);

        //HÁTTEREK
        addActor(blank);
        addActor(blankMute);
        addActor(blankTitle);
        addActor(blankDif);
        addActor(blankTrash);
        addActor(blankPlane);
        addActor(blankTitle);

        //NEHÉZSÉG NYILAK
        addActor(arrowLeft);
        addActor(arrowRigth);

        //KINÉZET NYILAK
        addActor(arrowLeft2);
        addActor(arrowRigth2);

        //LABELEK
        addActor(difi);
        addActor(difiLabel);
        addActor(blankBack);
        addActor(back);
        addActor(title);
        addActor(skini);
        addActor(trashi);
        addActor(muti);

        addActor(speaker);//HANGSZÓRÓ
        addActor(demoAirplane);//DEMOREPCSI
        addActor(trash);//KUKA
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
        //HÁTTEREK
        blank.setAlpha(alpha * 0.4f);
        blankDif.setAlpha(alpha/2);
        blankMute.setAlpha(alpha/2);
        blankBack.setAlpha(alpha/2);
        blankPlane.setAlpha(alpha/2);
        blankTrash.setAlpha(alpha/2);
        blankTitle.setAlpha(alpha*0.7f);

        //NYILAK
        arrowLeft.setAlpha(alpha);
        arrowRigth.setAlpha(alpha);
        arrowRigth2.setAlpha(alpha);
        arrowRigth2.setAlpha(alpha);

        demoAirplane.setAlpha(alpha);//DEMOREPCSI
        speaker.setAlpha(alpha);//HANGSZÓRÓ
        trash.setAlpha(alpha);//KUKA

        //LABELEK
        difi.setColor(1,1,1,alpha);
        difiLabel.setColor(difiLabel.getColor().r,difiLabel.getColor().g,difiLabel.getColor().b,alpha);
        back.setColor(1,1,1,alpha);
        muti.setColor(1,1,1,alpha);
        title.setColor(1,1,1,alpha);
        skini.setColor(1,1,1,alpha);
        trashi.setColor(1,1,1,alpha);
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        if(game instanceof FlightGame) {
            //HA RÁKATTINTUNK A HANGSZÓRÓRA EGYBŐL INDULJON ÚJRA A HANG VAGY NÉMULJON EL
            if (!((FlightGame) game).isMuted()) {
                if(!game.getMyAssetManager().getMusic(WIND_SOUND).isPlaying())
                    game.getMyAssetManager().getMusic(WIND_SOUND).play();
                if (game.getMyAssetManager().getMusic(WIND_SOUND).getPosition() >= 8.9)
                    game.getMyAssetManager().getMusic(WIND_SOUND).setPosition(0);
            }
            else{
                game.getMyAssetManager().getMusic(WIND_SOUND).stop();
            }
            demoAirplane.setTexture(((FlightGame)game).getSkinID());//REPÜLŐ KINÉZETÉNEK BEÁLLÍTÁSA
        }
        fadeIn();//ÁTTŰNÉS
    }
}
