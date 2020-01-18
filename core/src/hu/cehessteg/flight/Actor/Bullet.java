package hu.cehessteg.flight.Actor;

import hu.cehessteg.flight.Stage.GameStage;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class Bullet extends OneSpriteStaticActor {

    public static final String BULLET_TEXTURE = "bullet.png";

    public static AssetList assetList = new AssetList();
    static
    {
        assetList.addTexture(BULLET_TEXTURE);
    }

    public byte damage;
    private GameStage stage;

    public Bullet(MyGame game, Airplane airplane, GameStage stage) {
        super(game, BULLET_TEXTURE);
        damage = (byte) (Math.random() * 3);
        addBaseCollisionRectangleShape();
        this.setZIndex(1);
        this.stage = stage;
        stage.addBullet(this);
        setSize(getWidth()*0.1f, getHeight()*0.1f);
        setPosition(airplane.getX()+airplane.getWidth(), airplane.getY()+airplane.getHeight()/2-this.getHeight()/2);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(getX() < stage.getViewport().getWorldWidth() + this.getWidth())
        {
            setX(getX()+50);
        }
        else {
            this.remove();
            this.removeBaseCollisionRectangleShape();
        }
    }
}
