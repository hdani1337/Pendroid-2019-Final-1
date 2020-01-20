package hu.cehessteg.flight.Stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.Viewport;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

import static hu.cehessteg.flight.Stage.MenuStage.trebuc;

public class InfoStage extends MyStage {
    public static final String ZOLI_KEP = "zoli.png";
    public static final String BENCE_KEP = "bence.png";
    public static final String DANI_KEP = "dani.png";
    public static final String DAVID_KEP = "david.png";
    public static final String BACKGROUND_KEP = "unity.png";
    public static AssetList assetList = new AssetList();

    static {
        assetList.addTexture(ZOLI_KEP);
        assetList.addTexture(BENCE_KEP);
        assetList.addTexture(DANI_KEP);
        assetList.addTexture(DAVID_KEP);
        assetList.addTexture(BACKGROUND_KEP);
        assetList.addFont(trebuc, trebuc, 120, Color.WHITE, AssetList.CHARS);

    }

    OneSpriteStaticActor zoli;
    OneSpriteStaticActor bence;
    OneSpriteStaticActor dani;
    OneSpriteStaticActor david;

    MyLabel zoliLabel;
    MyLabel benceLabel;
    MyLabel daniLabel;
    MyLabel davidLabel;

    MyLabel zoliLabelTitle;
    MyLabel benceLabelTitle;
    MyLabel daniLabelTitle;
    MyLabel davidLabelTitle;

    MyLabel infoText;

    OneSpriteStaticActor background;





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
        infoText.setFontScale(0.33f);
        background = new OneSpriteStaticActor(game,BACKGROUND_KEP);
        background.setColor(1,1,1,1);
    }

    void labelStuff()
    {
        zoliLabelTitle.setFontScale(0.33f);
        benceLabelTitle.setFontScale(0.33f);
        daniLabelTitle.setFontScale(0.33f);
        davidLabelTitle.setFontScale(0.33f);

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

        background.setPosition(0,0);
        background.setSize(getViewport().getWorldWidth(),getViewport().getWorldHeight());
    }



    void addActors()
    {
        background.setDebug(false);
        zoli.setDebug(false);
        bence.setDebug(false);
        dani.setDebug(false);
        david.setDebug(false);

        addActor(background);

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
    }

    @Override
    public void init() {

    }


}