package hu.cehessteg.flight.Screen;

import hu.cehessteg.flight.Stage.OptionsStage;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyScreen;

public class OptionsScreen extends MyScreen {

    public static AssetList assetList = new AssetList();
    static {
        AssetList.collectAssetDescriptor(OptionsStage.class, assetList);
    }

    public OptionsScreen(MyGame game) {
        super(game);
    }

    @Override
    protected void afterAssetsLoaded() {
        addStage(new OptionsStage(game),1,true);
    }

    @Override
    public AssetList getAssetList() {
        return assetList;
    }

    @Override
    public void init() {

    }
}
