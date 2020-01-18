package hu.cehessteg.flight.Screen;

import hu.cehessteg.flight.Stage.GameOverStage;
import hu.cehessteg.flight.Stage.GameStage;
import hu.cehessteg.flight.Stage.HudStage;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyScreen;

public class GameScreen extends MyScreen {
    public GameScreen(MyGame game) {
        super(game);
    }

    @Override
    protected void afterAssetsLoaded() {
        addStage(new GameStage(game),1,false);
        addStage(new HudStage(game),2,true);
        addStage(new GameOverStage(game),3,true);
    }

    @Override
    public AssetList getAssetList() {
        AssetList assetList = new AssetList();
        assetList.collectAssetDescriptor(GameStage.class, assetList);
        assetList.collectAssetDescriptor(HudStage.class, assetList);
        assetList.collectAssetDescriptor(GameOverStage.class,assetList);
        return assetList;
    }

    @Override
    public void init() {

    }
}
