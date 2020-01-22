package hu.cehessteg.flight.Actor;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteAnimatedActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class Explosion extends OneSpriteAnimatedActor {

    public static final String EXPLOSION_TEXTURE = "atlas/explosion.atlas";

    public static AssetList assetList;
    static {
        assetList = new AssetList();
        assetList.addTextureAtlas(EXPLOSION_TEXTURE);
    }

    public Explosion(MyGame game, OneSpriteStaticActor enemy) {
        super(game, EXPLOSION_TEXTURE);
        setSize(getWidth()*3, getHeight()*3);
        if(enemy != null) setPosition(enemy.getX(), enemy.getY() + enemy.getHeight()/2 - this.getHeight()/2);
        setFps(60);
        setLooping(false);
    }

    @Override
    public void stop() {
        super.stop();
        this.remove();
    }
}
