package hu.cehessteg.flight.Stage;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;

public class MyPreLoadingStage extends hu.csanyzeg.master.MyBaseClasses.Assets.LoadingStage {

    public static final String CSAPAT_TEXTURE = "logos/cehessteg.png";

    public static AssetList assetList;
    static
    {
        assetList = new AssetList();
        assetList.addTexture(CSAPAT_TEXTURE).protect = true;
    }

    public MyPreLoadingStage(MyGame game) {
        super(new ResponseViewport(900), game);
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

    @Override
    public AssetList getAssetList() {
        return assetList;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
