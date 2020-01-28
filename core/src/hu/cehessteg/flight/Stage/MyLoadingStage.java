package hu.cehessteg.flight.Stage;

import com.badlogic.gdx.scenes.scene2d.Actor;

import hu.cehessteg.flight.Actor.Sky;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;

public class MyLoadingStage extends hu.csanyzeg.master.MyBaseClasses.Assets.LoadingStage {

    public static final String CSAPAT_TEXTURE = "logos/cehessteg.png";

    public static AssetList assetList;
    static
    {
        assetList = new AssetList();
        assetList.addTexture(Sky.SKY_TEXTURE).protect = true;
        assetList.addTexture(CSAPAT_TEXTURE).protect = true;
    }

    private boolean first;

    public MyLoadingStage(MyGame game, boolean first) {
        super(new ResponseViewport(900), game);
        this.first = first;

        addActor(new OneSpriteStaticActor(game, Sky.SKY_TEXTURE) {
            @Override
            public void init() {
                super.init();
                setSize(getViewport().getWorldWidth(), getViewport().getWorldHeight());
            }
        });

        addActor(new OneSpriteStaticActor(game, CSAPAT_TEXTURE) {
            @Override
            public void init() {
                super.init();
                setPosition(getViewport().getWorldWidth() / 2 - this.getWidth() / 2, getViewport().getWorldHeight() / 2 - this.getHeight() / 2);
            }

            @Override
            public void act(float delta) {
                super.act(delta);
                setRotation(getRotation() - 10);
            }
        });
    }

    public void setFirst(boolean first)
    {
        this.first = first;
    }

    @Override
    public AssetList getAssetList() {
        return assetList;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(first)
        {
            for (Actor actor : getActors()){
                actor.setVisible(false);
            }
        }
        else
        {
            for (Actor actor : getActors()){
                actor.setVisible(true);
            }
        }
    }
}
