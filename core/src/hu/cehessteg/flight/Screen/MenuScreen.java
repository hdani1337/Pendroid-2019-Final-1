package hu.cehessteg.flight.Screen;

import hu.cehessteg.flight.Stage.InfoStage;
import hu.cehessteg.flight.Stage.MenuStage;
import hu.cehessteg.flight.Stage.OptionsStage;
import hu.cehessteg.flight.Stage.ShopStage;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyScreen;

public class MenuScreen extends MyScreen {
    public MenuScreen(MyGame game) {
        super(game);
    }

    @Override
    protected void afterAssetsLoaded() {
        addStage(new MenuStage(game),1,true);
    }

    @Override
    public AssetList getAssetList() {
        AssetList assetList = new AssetList();
        assetList.collectAssetDescriptor(MenuStage.class, assetList);
        return assetList;
    }

    @Override
    public void init() {

    }
}
