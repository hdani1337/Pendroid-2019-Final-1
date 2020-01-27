package hu.cehessteg.flight.Screen;

import hu.cehessteg.flight.Stage.MenuStage;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyScreen;

public class MenuScreen extends MyScreen {

    public static AssetList assetList = new AssetList();
    static {
        AssetList.collectAssetDescriptor(MenuStage.class, assetList);
    }

    public MenuScreen(MyGame game) {
        super(game);
    }

    @Override
    protected void afterAssetsLoaded() {
        addStage(new MenuStage(game),1,true);
    }

    @Override
    public AssetList getAssetList() {
        return assetList;
    }

    @Override
    public void init() {

    }
}
