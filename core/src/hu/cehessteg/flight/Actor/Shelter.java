package hu.cehessteg.flight.Actor;

import hu.cehessteg.flight.FlightGame;
import hu.cehessteg.flight.Stage.GameStage;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.SimpleWorld.MyCircle;
import hu.csanyzeg.master.MyBaseClasses.SimpleWorld.MyRectangle;

import static hu.cehessteg.flight.Stage.GameStage.isAct;

public class Shelter extends OneSpriteStaticActor {

    public static final String SHELTER_TEXTURE = "other/bazis.png";

    public static AssetList assetList;
    static {
        assetList = new AssetList();
        assetList.addTexture(SHELTER_TEXTURE);
    }

    private GameStage gameStage;

    public Shelter(MyGame game, GameStage gameStage) {
        super(game, SHELTER_TEXTURE);
        if(gameStage != null) {
            this.gameStage = gameStage;
            setSize(getWidth()*0.3f, getHeight()*0.3f);
            addCollisionShape("hitbox", new MyCircle(getWidth()/2,0,-getHeight()*0.775f));
            setPosition((float)Math.random()*1000+gameStage.getViewport().getWorldWidth(),(float)-Math.random()*75);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(isAct){
            if(getX()>-getWidth()){
                setX(getX()-7);
            }else{
                if (game != null) {
                    if (game instanceof FlightGame) {
                        ((FlightGame) game).penz -= 1;
                        ((FlightGame) game).saveCoins();
                    }
                }
                remove();
            }
        }
    }

    @Override
    public boolean remove() {
        gameStage.removeShelter(this);
        return super.remove();
    }
}
