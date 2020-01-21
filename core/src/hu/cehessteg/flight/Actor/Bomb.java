package hu.cehessteg.flight.Actor;

import hu.cehessteg.flight.Stage.GameStageBombing;
import hu.cehessteg.flight.Stage.GameStageCombat;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class Bomb extends OneSpriteStaticActor {
    public static final String BOMB_TEXTURE = "ammos/bomba.png";

    public static AssetList assetList;
    static
    {
        assetList = new AssetList();
        assetList.addTexture(BOMB_TEXTURE);
    }

    private GameStageBombing stage;
    public byte damage;

    public Bomb(MyGame game, Airplane airplane, GameStageBombing stage) {
        super(game, BOMB_TEXTURE);
        damage = (byte) (Math.random() * 10);
        addBaseCollisionRectangleShape();
        this.stage = stage;
        stage.addBomb(this);
        setRotation(-90);
        setSize(getWidth()*0.3f, getHeight()*0.3f);
        setPosition(airplane.getX()+airplane.getWidth()*0.5f, airplane.getY()+7);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(getY() > -getHeight())
        {
            setY(getY()-10);
        }
        else {
            this.remove();
            stage.removeBomb(this);
        }
    }

    @Override
    public boolean remove() {
        this.removeBaseCollisionRectangleShape();
        return super.remove();
    }
}
