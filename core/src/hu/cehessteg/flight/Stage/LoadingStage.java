package hu.cehessteg.flight.Stage;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;

public class LoadingStage extends hu.csanyzeg.master.MyBaseClasses.Assets.LoadingStage {
    public LoadingStage(MyGame game) {
        super(new ResponseViewport(900), game);
    }

    @Override
    public AssetList getAssetList() {
        AssetList assetList = new AssetList();
        return assetList;
    }
}
