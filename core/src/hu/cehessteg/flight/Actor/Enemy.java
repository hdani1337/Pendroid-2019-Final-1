package hu.cehessteg.flight.Actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.viewport.Viewport;

import hu.cehessteg.flight.FlightGame;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.SimpleWorld.MyCircle;
import hu.csanyzeg.master.MyBaseClasses.SimpleWorld.MyRectangle;

import static hu.cehessteg.flight.Stage.GameStage.isAct;

public class Enemy extends OneSpriteStaticActor {

    public static final String ENEMY_TEXTURE = "planes/enemy.png";
    public static AssetList assetList = new AssetList();
    static{

        assetList.addTexture(ENEMY_TEXTURE);

    }

    private Viewport viewport;
    public byte hp;

    //HITBOXOK
    private MyRectangle myRectangle;
    private MyRectangle myRectangle2;
    private MyRectangle myRectangle3;

    public Enemy(MyGame game, Viewport viewport) {
        super(game, ENEMY_TEXTURE);
        this.viewport = viewport;
        this.setZIndex(2);
        this.setColor(Color.RED);
        this.hp = 100;
        setHitbox();
    }

    private void setHitbox(){
        myRectangle = new MyRectangle(1500,150);
        myRectangle.setOffsetX(430);
        myRectangle.setOffsetY(175);

        myRectangle2 = new MyRectangle(400,120);
        myRectangle2.setOffsetX(800);
        myRectangle2.setOffsetY(320);

        myRectangle3 = new MyRectangle(650,75);
        myRectangle3.setOffsetX(820);
        myRectangle3.setOffsetY(90);

        addCollisionShape("hitbox", myRectangle);
        addCollisionShape("hitbox2", myRectangle2);
        addCollisionShape("hitbox3", myRectangle3);
    }

    @Override
    public void act(float delta) {
        if (isAct) {
            if (getX() > -getWidth()) {
                setX(getX() - 10);
            } else if(getX() == -1000){
                replace();
            } else {
                replace();
                if(game instanceof FlightGame)((FlightGame) game).setPenz(((FlightGame) game).getPenz() + 1);
            }
        }
    }

    public void replace()
    {
        hp = 100;
        setX((float) Math.random() * 750 + 2000);
        setY((float) Math.random() * (viewport.getWorldHeight() - getHeight()));
    }
}
