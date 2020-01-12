package hu.cehessteg.flight.Stage;

import com.badlogic.gdx.utils.viewport.Viewport;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;

public class GameStage extends MyStage {
    public static AssetList assetList = new AssetList();
    static {

    }

    public GameStage(MyGame game) {
        super(new ResponseViewport(900), game);//Ha lesz Box2D, akkor 900 helyett mondjuk 9 lesz
    }
}
