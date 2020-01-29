package hu.cehessteg.flight.Actor;

import hu.cehessteg.flight.FlightGame;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyGroup;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class Health extends MyGroup {

    public static final String PIROS_TEXTURE = "other/piros.png";
    public static final String ZOLD_TEXTURE = "other/zold.png";

    public static AssetList assetList;

    static {
        assetList = new AssetList();
        assetList.addTexture(PIROS_TEXTURE);
        assetList.addTexture(ZOLD_TEXTURE);
    }

    private OneSpriteStaticActor piros;
    private OneSpriteStaticActor zold;

    public Health(MyGame game) {
        super(game);
        assignment();
        setSizes();
        addActors();
    }

    private void assignment() {
        piros = new OneSpriteStaticActor(game, PIROS_TEXTURE);
        zold = new OneSpriteStaticActor(game, ZOLD_TEXTURE);
    }

    private void setSizes() {
        zold.setSize(100, 10);
        piros.setSize(0, 10);
    }

    private void addActors()
    {
        addActor(zold);
        addActor(piros);
    }

    public void setHealth(int hp, boolean senderIsPlane){
        if(senderIsPlane){
            piros.setWidth(Math.abs(hp - (100 + (((FlightGame)game).getPlaneLevel()-1)*15)));
            zold.setWidth(100 + (((FlightGame)game).getPlaneLevel()-1)*15);
            piros.setX(zold.getX() + zold.getWidth() - piros.getWidth());
        }
        else {
            piros.setWidth(Math.abs(hp - 100));
            piros.setX(zold.getX() + zold.getWidth() - piros.getWidth());
        }
    }
}
