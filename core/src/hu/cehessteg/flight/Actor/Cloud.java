package hu.cehessteg.flight.Actor;

import com.badlogic.gdx.utils.viewport.Viewport;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

import static hu.cehessteg.flight.Stage.GameStage.isAct;

public class Cloud extends OneSpriteStaticActor {
    public static final String cloud3 = "cloud3.png";
    public static final String cloud4 = "cloud4.png";

    public static AssetList assetList = new AssetList();
    static
    {
        assetList.addTexture(cloud3);
        assetList.addTexture(cloud4);
    }

    private Viewport viewport;

    public Cloud(MyGame game, Viewport viewport) {
        super(game, Math.random() > 0.5f ? cloud3 : cloud4);
        this.viewport = viewport;
        setSize(getWidth()*0.4f, getHeight()*0.4f);
        setPosition((float) Math.random() * viewport.getWorldWidth(), (float) Math.random()*viewport.getWorldHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (isAct) {
            if (getX() > -getWidth()) setX(getX() - 5);
            else {
                setX((float) Math.random() * 500 + viewport.getWorldWidth());
                setY((float) Math.random() * viewport.getWorldHeight());
            }
        }
    }
}
