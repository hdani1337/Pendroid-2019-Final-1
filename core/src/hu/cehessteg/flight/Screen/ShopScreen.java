package hu.cehessteg.flight.Screen;

import hu.cehessteg.flight.Stage.ShopStage;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyScreen;

public class ShopScreen extends MyScreen {
    public ShopScreen(MyGame game) {
        super(game);
    }

    @Override
    protected void afterAssetsLoaded() {
        addStage(new ShopStage(game),1,true);
    }

    @Override
    public AssetList getAssetList() {
        AssetList assetList = new AssetList();
        assetList.collectAssetDescriptor(ShopStage.class, assetList);
        return assetList;
    }

    @Override
    public void init() {

    }
}
