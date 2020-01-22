package hu.cehessteg.flight.Stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import hu.cehessteg.flight.Actor.Cloud;
import hu.cehessteg.flight.Actor.Sky;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

import static hu.cehessteg.flight.Actor.Sky.SKY_TEXTURE;
import static hu.cehessteg.flight.Stage.GameOverStage.BLANK_TEXTURE;
import static hu.cehessteg.flight.Stage.MenuStage.trebuc;

public class InfoStage extends MyStage {
    public static final String ZOLI_KEP = "portraits/zoli.png";
    public static final String BENCE_KEP = "portraits/bence.png";
    public static final String DANI_KEP = "portraits/dani.png";
    public static final String DAVID_KEP = "portraits/david.png";
    public static AssetList assetList = new AssetList();

    static {
        assetList.collectAssetDescriptor(Sky.class, assetList);
        assetList.collectAssetDescriptor(Cloud.class, assetList);
        assetList.addTexture(ZOLI_KEP);
        assetList.addTexture(BENCE_KEP);
        assetList.addTexture(DANI_KEP);
        assetList.addTexture(DAVID_KEP);
        assetList.addTexture(SKY_TEXTURE);
        assetList.addTexture(BLANK_TEXTURE);
        assetList.addFont(trebuc, trebuc, 120, Color.WHITE, AssetList.CHARS);
    }

    private OneSpriteStaticActor zoli;
    private OneSpriteStaticActor bence;
    private OneSpriteStaticActor dani;
    private OneSpriteStaticActor david;

    private MyLabel zoliLabel;
    private MyLabel benceLabel;
    private MyLabel daniLabel;
    private MyLabel davidLabel;

    private MyLabel zoliLabelTitle;
    private MyLabel benceLabelTitle;
    private MyLabel daniLabelTitle;
    private MyLabel davidLabelTitle;

    private MyLabel infoText;

    private Sky sky;
    private ArrayList<Cloud> clouds;
    private OneSpriteStaticActor black;

    private MyLabel back;

    public InfoStage(MyGame game) {
        super(new ResponseViewport(900),game);
        assignment();
        labelStuff();
        setPositions();
        addActors();
    }

    void assignment()
    {
        zoli = new OneSpriteStaticActor(game,ZOLI_KEP);
        bence = new OneSpriteStaticActor(game,BENCE_KEP);
        dani = new OneSpriteStaticActor(game,DANI_KEP);
        david = new OneSpriteStaticActor(game,DAVID_KEP);

        zoliLabel = new MyLabel(game, "Miklós Zoltán", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setFontScale(0.4f);

            }
        };
        benceLabel = new MyLabel(game, "Kutai Bence", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setFontScale(0.4f);
            }
        };
        daniLabel = new MyLabel(game, "Horváth Dániel", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setFontScale(0.4f);
            }
        };
        davidLabel = new MyLabel(game, "Halász Dávid", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setFontScale(0.4f);
            }
        };


        zoliLabelTitle = new MyLabel(game, "Szoftverfejlesztő", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setFontScale(0.4f);
            }
        };
        benceLabelTitle = new MyLabel(game, "Grafikus", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setFontScale(0.4f);
            }
        };
        daniLabelTitle = new MyLabel(game, "Szoftverfejlesztő", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setFontScale(0.4f);
            }
        };
        davidLabelTitle = new MyLabel(game, "Szoftverfejlesztő", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setFontScale(0.4f);
            }
        };



        infoText = new MyLabel(game, "A Pendroid verseny döntőjére készült alkalmazás!", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setFontScale(0.4f);

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

        black = new OneSpriteStaticActor(game, BLANK_TEXTURE) {
            @Override
            public void init() {
                super.init();
                setSize(getViewport().getWorldWidth(), getViewport().getWorldHeight());
                setAlpha(0.3f);
            }
        };

        back = new MyLabel(game, "Vissza a menübe", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setFontScale(0.4f);
                setAlignment(0);
                setPosition(getViewport().getWorldWidth()-this.getWidth()*0.75f, 15);
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
    }

    void labelStuff()
    {
        zoliLabelTitle.setFontScale(0.3f);
        benceLabelTitle.setFontScale(0.3f);
        daniLabelTitle.setFontScale(0.3f);
        davidLabelTitle.setFontScale(0.3f);

        zoliLabel.setAlignment(0);
        benceLabel.setAlignment(0);
        daniLabel.setAlignment(0);
        davidLabel.setAlignment(0);

        zoliLabelTitle.setAlignment(0);
        benceLabelTitle.setAlignment(0);
        daniLabelTitle.setAlignment(0);
        davidLabelTitle.setAlignment(0);

        infoText.setAlignment(0);
    }

    void setPositions()
    {
        zoli.setPosition(getViewport().getWorldWidth()/2-(zoli.getWidth()+bence.getWidth()+dani.getWidth()+david.getWidth()+450)/2,getViewport().getWorldHeight()/1.25f-zoli.getHeight()/2);
        bence.setPosition(zoli.getX()+zoli.getWidth()+150,zoli.getY());
        dani.setPosition(bence.getX()+bence.getWidth()+150,zoli.getY());
        david.setPosition(dani.getX()+dani.getWidth()+150,zoli.getY());

        zoliLabel.setPosition(zoli.getX()+zoli.getWidth()/2-zoliLabel.getWidth()/2,zoli.getY()-100);
        benceLabel.setPosition(bence.getX()+bence.getWidth()/2-benceLabel.getWidth()/2,bence.getY()-100);
        daniLabel.setPosition(dani.getX()+dani.getWidth()/2-daniLabel.getWidth()/2,dani.getY()-100);
        davidLabel.setPosition(david.getX()+david.getWidth()/2-davidLabel.getWidth()/2,david.getY()-100);

        zoliLabelTitle.setPosition((zoliLabel.getX()+zoliLabel.getWidth()/2)-zoliLabelTitle.getWidth()/2,zoliLabel.getY()-45);
        benceLabelTitle.setPosition((benceLabel.getX()+benceLabel.getWidth()/2)-benceLabelTitle.getWidth()/2,benceLabel.getY()-45);
        daniLabelTitle.setPosition((daniLabel.getX()+daniLabel.getWidth()/2)-daniLabelTitle.getWidth()/2,daniLabel.getY()-45);
        davidLabelTitle.setPosition((davidLabel.getX()+davidLabel.getWidth()/2)-davidLabelTitle.getWidth()/2,davidLabel.getY()-45);

        infoText.setPosition(getViewport().getWorldWidth()/2-infoText.getWidth()/1.66f,getViewport().getWorldHeight()*0.035f);

        /**asd**/

    }



    void addActors()
    {
        addActor(sky);
        for (int i = 0; i < clouds.size(); i++) addActor(clouds.get(i));
        addActor(black);

        addActor(zoli);
        addActor(bence);
        addActor(dani);
        addActor(david);

        addActor(zoliLabel);
        addActor(benceLabel);
        addActor(daniLabel);
        addActor(davidLabel);

        addActor(zoliLabelTitle);
        addActor(benceLabelTitle);
        addActor(daniLabelTitle);
        addActor(davidLabelTitle);

        addActor(infoText);

        addActor(back);
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
        zoli.setAlpha(alpha);
        bence.setAlpha(alpha);
        dani.setAlpha(alpha);
        david.setAlpha(alpha);

        zoliLabel.setColor(1,1,1, alpha);
        benceLabel.setColor(1,1,1, alpha);
        daniLabel.setColor(1,1,1, alpha);
        davidLabel.setColor(1,1,1, alpha);

        zoliLabelTitle.setColor(1,1,1, alpha);
        benceLabelTitle.setColor(1,1,1, alpha);
        daniLabelTitle.setColor(1,1,1, alpha);
        davidLabelTitle.setColor(1,1,1, alpha);

        infoText.setColor(1,1,1, alpha);

        back.setColor(1,1,1, alpha);
    }

    @Override
    public void init() {

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        for (Cloud c : clouds) {
            c.move();
        }

        fadeIn();

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            /**
             * !!!NEM MŰKÖDIK A addBackButtonScreenBackByStackPopListener()!!!
             * **/
            game.setScreenBackByStackPop();
        }
    }
}