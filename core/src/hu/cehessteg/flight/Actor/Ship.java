package hu.cehessteg.flight.Actor;

import com.badlogic.gdx.utils.viewport.Viewport;

import hu.cehessteg.flight.Stage.GameStageBombing;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class Ship extends OneSpriteStaticActor {

    public static final String SHIP_TEXTURE = "planes/ship.png";

    public static AssetList assetList;
    static {
        assetList = new AssetList();
        assetList.addTexture(SHIP_TEXTURE);
    }

    private GameStageBombing stage;

    public Ship(MyGame game, GameStageBombing stage) {
        super(game, SHIP_TEXTURE);
        this.stage = stage;
        setPosition((float) (stage.getViewport().getWorldWidth() + Math.random() * 200), -20);
        setSize(getWidth()*0.3f, getHeight()*0.3f);
        addBaseCollisionRectangleShape();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(getX() > -getWidth()){
            setX(getX()-10);
        }
        else {
            this.remove();
            stage.removeShip(this);
        }
    }

    @Override
    public boolean remove() {
        this.removeBaseCollisionRectangleShape();
        return super.remove();
    }
}
