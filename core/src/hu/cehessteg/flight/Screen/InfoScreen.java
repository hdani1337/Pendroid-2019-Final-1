package hu.cehessteg.flight.Screen;

import hu.cehessteg.flight.Stage.InfoStage;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyScreen;

public class InfoScreen extends MyScreen {
    public static AssetList assetList = new AssetList();
    static {
        AssetList.collectAssetDescriptor(InfoStage.class, assetList);
    }

    public InfoScreen(MyGame game) {
        super(game);
    }

    @Override
    protected void afterAssetsLoaded() {
        addStage(new InfoStage(game),1,true);
    }

    @Override
    public AssetList getAssetList() {

        return assetList;
    }

    @Override
    public void init() {

    }
}
