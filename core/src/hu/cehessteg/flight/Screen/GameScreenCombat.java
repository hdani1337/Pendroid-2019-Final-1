package hu.cehessteg.flight.Screen;

import hu.cehessteg.flight.Stage.GameOverStage;
import hu.cehessteg.flight.Stage.GameStageCombat;
import hu.cehessteg.flight.Stage.HudStageCombat;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyScreen;

public class GameScreenCombat extends MyScreen {
    public GameScreenCombat(MyGame game) {
        super(game);
    }

    @Override
    protected void afterAssetsLoaded() {
        addStage(new GameStageCombat(game),1,false);
        addStage(new HudStageCombat(game),2,true);
        addStage(new GameOverStage(game),3,true);
    }

    @Override
    public AssetList getAssetList() {
        AssetList assetList = new AssetList();
        assetList.collectAssetDescriptor(GameStageCombat.class, assetList);
        assetList.collectAssetDescriptor(HudStageCombat.class, assetList);
        assetList.collectAssetDescriptor(GameOverStage.class,assetList);
        return assetList;
    }

    @Override
    public void init() {

    }
}
