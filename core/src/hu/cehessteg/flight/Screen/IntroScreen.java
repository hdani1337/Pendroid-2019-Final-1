package hu.cehessteg.flight.Screen;

import hu.cehessteg.flight.Stage.IntroStage;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyScreen;

public class IntroScreen extends MyScreen {

    public static AssetList assetList = new AssetList();
    static {
        AssetList.collectAssetDescriptor(IntroStage.class, assetList);
    }

    public IntroScreen(MyGame game) {
        super(game);
    }

    @Override
    protected void afterAssetsLoaded() {
        addStage(new IntroStage(game), 1, false);
    }

    @Override
    public AssetList getAssetList() {
        return assetList;
    }

    @Override
    public void init() {

    }
}
