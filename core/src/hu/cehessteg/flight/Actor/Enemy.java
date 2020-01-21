package hu.cehessteg.flight.Actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.viewport.Viewport;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

import static hu.cehessteg.flight.Stage.GameStageCombat.isAct;

public class Enemy extends OneSpriteStaticActor {

    public static final String ENEMY_TEXTURE = "planes/enemy.png";
    public static AssetList assetList = new AssetList();
    static{

        assetList.addTexture(ENEMY_TEXTURE);

    }

    private Viewport viewport;
    public byte hp;

    public Enemy(MyGame game, Viewport viewport) {
        super(game,ENEMY_TEXTURE);
        this.viewport = viewport;
        this.setZIndex(2);
        this.setColor(Color.RED);
        addBaseCollisionRectangleShape();
        hp = 100;

    }

    @Override
    public void act(float delta) {
        if (isAct) {
            if (getX() > -getWidth()) {
                setX(getX() - 10);
            } else {
                replace();
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
