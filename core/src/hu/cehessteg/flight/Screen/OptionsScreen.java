package hu.cehessteg.flight.Screen;

import hu.cehessteg.flight.Stage.OptionsStage;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyScreen;

public class OptionsScreen extends MyScreen {
    public OptionsScreen(MyGame game) {
        super(game);
    }

    @Override
    protected void afterAssetsLoaded() {
        addStage(new OptionsStage(game),1,true);
    }

    @Override
    public AssetList getAssetList() {
        AssetList assetList = new AssetList();
        assetList.collectAssetDescriptor(OptionsStage.class, assetList);
        return assetList;
    }

    @Override
    public void init() {

    }
}
