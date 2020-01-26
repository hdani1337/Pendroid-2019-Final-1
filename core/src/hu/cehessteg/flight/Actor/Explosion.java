package hu.cehessteg.flight.Actor;

import hu.cehessteg.flight.FlightGame;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteAnimatedActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class Explosion extends OneSpriteAnimatedActor {

    public static final String EXPLOSION_TEXTURE = "atlas/explosion.atlas";
    public static final String EXPLOSION_SOUND = "sounds/Explosion.mp3";

    public static AssetList assetList;
    static {
        assetList = new AssetList();
        assetList.addTextureAtlas(EXPLOSION_TEXTURE);
        assetList.addSound(EXPLOSION_SOUND);
    }

    public Explosion(MyGame game, OneSpriteStaticActor enemy) {
        super(game, EXPLOSION_TEXTURE);
        setSize(getWidth()*3, getHeight()*3);
        if(enemy != null) setPosition(enemy.getX(), enemy.getY() + enemy.getHeight()/2 - this.getHeight()/2);
        setFps(60);
        setLooping(false);

        if(game instanceof FlightGame){
            if(!((FlightGame)game).isMuted()){
                game.getMyAssetManager().getSound(EXPLOSION_SOUND).play(0.5f);
            }
        }
    }

    @Override
    public void stop() {
        super.stop();
        this.remove();
    }
}
