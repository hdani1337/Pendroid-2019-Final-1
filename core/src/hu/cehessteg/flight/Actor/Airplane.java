package hu.cehessteg.flight.Actor;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class Airplane extends OneSpriteStaticActor {
    public static final String AIRPLANE_TEXTURE = "plane.png";

    public static AssetList assetList = new AssetList();
    static
    {
        assetList.addTexture(AIRPLANE_TEXTURE);
    }

    public byte hp;//Életerő

    public Airplane(MyGame game) {
        super(game, AIRPLANE_TEXTURE);
        hp = 100;
    }
}
