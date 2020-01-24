package hu.cehessteg.flight.Actor;

import hu.cehessteg.flight.Stage.GameStageBombing;
import hu.cehessteg.flight.Stage.GameStage;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class Bomb extends OneSpriteStaticActor {
    public static final String BOMB_TEXTURE = "ammos/bomba.png";
    public static final String KIRBY_TEXTURE = "ammos/kirby_v2.png";

    public static AssetList assetList;
    static
    {
        assetList = new AssetList();
        assetList.addTexture(BOMB_TEXTURE);
        assetList.addTexture(KIRBY_TEXTURE);
    }

    private GameStage stage;
    public byte damage;

    public Bomb(MyGame game, Airplane airplane, GameStage stage) {
        super(game, BOMB_TEXTURE);
        this.stage = stage;
        addBaseCollisionRectangleShape();
        damage = (byte) (Math.random() * 10);
        setTextureSizePosition(airplane);
        stage.addBomb(this);
    }

    private void setTextureSizePosition(Airplane airplane)
    {
        if(damage == 4) {
            this.sprite.setTexture(game.getMyAssetManager().getTexture(KIRBY_TEXTURE));
            setRotation(airplane.getRotation());
            setSize(getWidth()*0.15f, getHeight()*0.4f);
        }
        else{
            setRotation(-90 + airplane.getRotation());
            setSize(getWidth()*0.25f, getHeight()*0.25f);
        }

        setPosition(airplane.getX()+airplane.getWidth()*0.5f, airplane.getY()+7);
    }

    private void move()
    {
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
    public void act(float delta) {
        super.act(delta);
        move();
    }

    @Override
    public boolean remove() {
        this.removeBaseCollisionRectangleShape();
        return super.remove();
    }
}
