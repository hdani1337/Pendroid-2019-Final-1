package hu.cehessteg.flight.Stage;

import com.badlogic.gdx.utils.viewport.Viewport;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;

public class ShopStage extends MyStage {
    public static AssetList assetList = new AssetList();
    static {

    }

    public ShopStage(MyGame game) {
        super(new ResponseViewport(900), game);
    }
}
