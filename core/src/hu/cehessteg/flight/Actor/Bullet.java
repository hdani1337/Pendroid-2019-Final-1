package hu.cehessteg.flight.Actor;

import hu.cehessteg.flight.Stage.GameStageCombat;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class Bullet extends OneSpriteStaticActor {

    public static final String BULLET_TEXTURE = "ammos/lovedek.png";

    public static AssetList assetList = new AssetList();
    static
    {
        assetList.addTexture(BULLET_TEXTURE);
    }

    public byte damage;
    private GameStageCombat stage;

    public Bullet(MyGame game, Airplane airplane, GameStageCombat stage) {
        super(game, BULLET_TEXTURE);
        damage = (byte) ((byte) (Math.random() * 50) + 30);
        addBaseCollisionRectangleShape();
        this.stage = stage;
        stage.addBullet(this);
        setRotation(airplane.getRotation());
        setPosition(airplane.getX()+airplane.getWidth()*0.65f, airplane.getY()+7);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(getX() < stage.getViewport().getWorldWidth() + this.getWidth())
        {
            setY(getY() + getRotation() / 2.0f);
            setX(getX()+50);
        }
        else {
            this.remove();
            stage.removeBullet(this);
        }
    }

    @Override
    public boolean remove() {
        this.removeBaseCollisionRectangleShape();
        return super.remove();
    }
}
