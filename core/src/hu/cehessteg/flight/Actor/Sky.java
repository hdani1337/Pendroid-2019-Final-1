package hu.cehessteg.flight.Actor;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class Sky extends OneSpriteStaticActor {
    public static final String SKY_TEXTURE = "skyclear.png";

    public static AssetList assetList = new AssetList();
    static
    {
        assetList.addTexture(SKY_TEXTURE);
    }

    public Sky(MyGame game) {
        super(game, SKY_TEXTURE);
    }
}
