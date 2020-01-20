package hu.cehessteg.flight.Screen;

import hu.cehessteg.flight.Stage.GameOverStage;
import hu.cehessteg.flight.Stage.GameStageBombing;
import hu.cehessteg.flight.Stage.HudStageBombing;
import hu.cehessteg.flight.Stage.HudStageCombat;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyScreen;

public class GameScreenBombing extends MyScreen {
    public GameScreenBombing(MyGame game) {
        super(game);
    }

    @Override
    protected void afterAssetsLoaded() {
        addStage(new GameStageBombing(game),1,false);
        addStage(new HudStageBombing(game),2,true);
        addStage(new GameOverStage(game),3,true);
    }

    @Override
    public AssetList getAssetList() {
        AssetList assetList = new AssetList();
        assetList.collectAssetDescriptor(GameStageBombing.class, assetList);
        assetList.collectAssetDescriptor(HudStageBombing.class, assetList);
        assetList.collectAssetDescriptor(GameOverStage.class,assetList);
        return assetList;
    }

    @Override
    public void init() {

    }
}
