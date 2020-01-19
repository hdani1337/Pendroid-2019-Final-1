package hu.cehessteg.flight.Actor;

import hu.cehessteg.flight.Stage.GameStage;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class Bomb extends OneSpriteStaticActor {
    public static final String BOMB_TEXTURE = "bomb.png";

    public static AssetList assetList;
    static
    {
        assetList = new AssetList();
        assetList.addTexture(BOMB_TEXTURE);
    }

    private GameStage stage;
    public byte damage;

    public Bomb(MyGame game, Airplane airplane, GameStage stage) {
        super(game, BOMB_TEXTURE);
        damage = (byte) (Math.random() * 10);
        addBaseCollisionRectangleShape();
        this.stage = stage;
        stage.addBomb(this);
        setSize(getWidth()*0.07f, getHeight()*0.07f);
        setPosition(airplane.getX()+airplane.getWidth()*0.65f, airplane.getY()+7);
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
