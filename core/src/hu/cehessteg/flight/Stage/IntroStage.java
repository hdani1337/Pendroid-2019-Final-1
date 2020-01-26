package hu.cehessteg.flight.Stage;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import hu.cehessteg.flight.Actor.Sky;
import hu.cehessteg.flight.Screen.MenuScreen;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

import static hu.cehessteg.flight.Stage.MenuStage.trebuc;
import static hu.cehessteg.flight.Stage.MyLoadingStage.CSAPAT_TEXTURE;

public class IntroStage extends MyStage {

    public static final String GDX_TEXTURE = "logos/gdx.png";
    public static final String CSANY_TEXTURE = "logos/csany.png";
    public static final String PENDROID_TEXTURE = "logos/pendroid.png";

    public static AssetList assetList;
    static {
        assetList = new AssetList();
        assetList.addTexture(GDX_TEXTURE);
        assetList.addTexture(CSANY_TEXTURE);
        assetList.addTexture(PENDROID_TEXTURE);
        assetList.addTexture(CSAPAT_TEXTURE).protect = true;
        assetList.addTexture(Sky.SKY_TEXTURE).protect = true;
        assetList.addFont(trebuc, trebuc, 120, Color.WHITE, AssetList.CHARS);
    }

    private OneSpriteStaticActor gdxLogo;
    private OneSpriteStaticActor cehesstegLogo;
    private OneSpriteStaticActor pendroidLogo;
    private OneSpriteStaticActor csanyLogo;
    private MyLabel copyright;
    private Sky sky;

    public IntroStage(MyGame game) {
        super(new ResponseViewport(900), game);
        if(game.getLoadingStage() != null)
            if(game.getLoadingStage() instanceof MyLoadingStage)
                ((MyLoadingStage) game.getLoadingStage()).setFirst(false);

        assignment();
        setPositions();
        addActors();
    }

    private void assignment()
    {
        gdxLogo = new OneSpriteStaticActor(game, GDX_TEXTURE);
        cehesstegLogo = new OneSpriteStaticActor(game, CSAPAT_TEXTURE);
        pendroidLogo = new OneSpriteStaticActor(game, PENDROID_TEXTURE);
        csanyLogo = new OneSpriteStaticActor(game, CSANY_TEXTURE);
        sky = new Sky(game);

        copyright = new MyLabel(game,"Copyright 2020 Céhessteg. Minden jog fenntartva.", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setFontScale(0.33f);
                setAlignment(0);
            }
        };
    }

    private void setPositions()
    {
        sky.setSize(getViewport().getWorldWidth(), getViewport().getWorldHeight());

        gdxLogo.setPosition(getViewport().getWorldWidth()/2-gdxLogo.getWidth()/2,getViewport().getWorldHeight()/2-gdxLogo.getHeight()/2);
        pendroidLogo.setPosition(getViewport().getWorldWidth()/2-pendroidLogo.getWidth()-30, getViewport().getWorldHeight()/2-pendroidLogo.getHeight()/2);
        csanyLogo.setPosition(getViewport().getWorldWidth()/2+30, getViewport().getWorldHeight()/2-csanyLogo.getHeight()/2);
        cehesstegLogo.setPosition(getViewport().getWorldWidth()/2-cehesstegLogo.getWidth()/2,getViewport().getWorldHeight()/2-cehesstegLogo.getHeight()/2);
        copyright.setPosition(getViewport().getWorldWidth()/2-copyright.getWidth()/2, 20);
    }

    private void addActors()
    {
        addActor(gdxLogo);
        addActor(pendroidLogo);
        addActor(csanyLogo);
        addActor(sky);
        addActor(cehesstegLogo);
        addActor(copyright);

        for (Actor actor : getActors()) actor.setColor(1,1,1,0);
    }

    float alpha = 0;

    private void fadeIn(OneSpriteStaticActor... actor) {
        if (alpha < 0.98) alpha += 0.02;
        else alpha = 1;

        for (OneSpriteStaticActor actor1 : actor)
        {
            actor1.setAlpha(alpha);
        }
    }

    private void fadeOut(OneSpriteStaticActor... actor) {
        if (alpha > 0.02) alpha -= 0.02;
        else {
            alpha = 0;
            pElapsed = 0;
            index++;
        }

        for (OneSpriteStaticActor actor1 : actor)
        {
            actor1.setAlpha(alpha);
        }
    }

    private float pElapsed;
    private byte index = 0;

    @Override
    public void act(float delta) {
        super.act(delta);
        sky.setAlpha((1/8.0f) * elapsedTime);
        switch (index) {
            case 0: {
                pElapsed += delta;
                if (pElapsed < 1) fadeIn(gdxLogo);
                else if (pElapsed > 2) fadeOut(gdxLogo);
                break;
            }

            case 1: {
                pElapsed += delta;
                if (pElapsed < 1) {
                    fadeIn(pendroidLogo, csanyLogo);
                }
                else if (pElapsed > 2) {
                    fadeOut(pendroidLogo, csanyLogo);
                }
                break;
            }

            case 2: {
                pElapsed += delta;
                if (pElapsed < 1) {
                    fadeIn(cehesstegLogo);
                    copyright.setColor(1,1,1,alpha);
                }
                else if (pElapsed > 1.5) copyright.setColor(1,1,1, copyright.getColor().a - 0.02f);
                break;
            }
        }

        if(elapsedTime > 8) {
            game.setScreenWithPreloadAssets(MenuScreen.class, true, new MyLoadingStage(game, false));
        }
    }
}
