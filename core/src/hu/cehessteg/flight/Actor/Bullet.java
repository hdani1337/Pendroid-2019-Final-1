package hu.cehessteg.flight.Actor;

import hu.cehessteg.flight.FlightGame;
import hu.cehessteg.flight.Stage.GameStage;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class Bullet extends OneSpriteStaticActor {

    public static final String BULLET_TEXTURE = "ammos/lovedek.png";
    public static final String BULLET_SOUND = "sounds/Loves.mp3";

    public static AssetList assetList = new AssetList();
    static
    {
        assetList.addTexture(BULLET_TEXTURE);
        assetList.addSound(BULLET_SOUND);
    }

    public byte damage;
    private GameStage stage;

    public Bullet(MyGame game, Airplane airplane, GameStage stage) {
        super(game, BULLET_TEXTURE);
        this.stage = stage;
        addBaseCollisionRectangleShape();

        damage = (byte) ((byte) (Math.random() * 50) + 30);

        stage.addBullet(this);

        setRotation(airplane.getRotation());
        setPosition(airplane.getX()+airplane.getWidth()*0.65f, airplane.getY()+7);

        if(game instanceof FlightGame){
            if(!((FlightGame)game).isMuted()){
                game.getMyAssetManager().getSound(BULLET_SOUND).play(0.5f);
            }
        }
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
