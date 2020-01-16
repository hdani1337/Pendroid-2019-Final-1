package hu.cehessteg.flight.Actor;

import com.badlogic.gdx.utils.viewport.Viewport;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

import static hu.cehessteg.flight.Stage.GameStage.isAct;

public class Enemy extends OneSpriteStaticActor {

    public static final String ENEMY_TEXTURE = "airplane.png";
    public static AssetList assetList = new AssetList();
    static{

        assetList.addTexture(ENEMY_TEXTURE);

    }

    private Viewport viewport;

    public Enemy(MyGame game, Viewport viewport) {
        super(game,ENEMY_TEXTURE);
        this.viewport = viewport;
        addBaseCollisionRectangleShape();

    }



    @Override
    public void act(float delta) {
        if (isAct) {
            if (getX() > -getWidth()) {

                setX(getX() - 15);

            } else {

                setX((float) Math.random() * 500 + 1500);
                setY((float) Math.random() * (viewport.getWorldHeight() - getHeight()));

            }
        }
    }
}
