package hu.cehessteg.flight.Actor;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

import static hu.cehessteg.flight.Stage.GameStage.isAct;

public class Fuel extends OneSpriteStaticActor {

    public static final String FUEL_TEXTURE = "other/fuel.png";

    public static AssetList assetList;
    static {
        assetList = new AssetList();
        assetList.addTexture(FUEL_TEXTURE);
    }

    public Fuel(MyGame game) {
        super(game, FUEL_TEXTURE);
        setSize(getWidth()*1.5f, getHeight()*1.5f);
        addBaseCollisionRectangleShape();
        replace();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (isAct) {
            if (getX() >= -getWidth()) {
                setX(getX() - 7);
            } else {
                replace();
            }
        }
    }

    public void replace()
    {
        setX((float) Math.random() * 10000 + 5000);
        setY((float) Math.random() * (900 - getHeight()-150)+150);
    }
}
