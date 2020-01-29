package hu.cehessteg.flight.Actor;

import hu.cehessteg.flight.FlightGame;
import hu.cehessteg.flight.Stage.GameStage;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

import static hu.cehessteg.flight.Stage.GameStage.isAct;

public class House extends OneSpriteStaticActor {

    public static final String HOUSE_PINK = "other/haz1.png";
    public static final String HOUSE_YELLOW = "other/haz2.png";
    public static final String HOUSE_BLUE = "other/haz3.png";

    public static AssetList assetList;
    static {
        assetList = new AssetList();
        assetList.addTexture(HOUSE_PINK);
        assetList.addTexture(HOUSE_YELLOW);
        assetList.addTexture(HOUSE_BLUE);
    }

    private GameStage gameStage;

    public House(MyGame game, GameStage gameStage) {
        super(game, getRandomHouse());
        addBaseCollisionRectangleShape();
        if(gameStage != null) {
            this.gameStage = gameStage;
            setSize(getWidth()*0.3f, getHeight()*0.3f);
            setPosition((float)Math.random()*1000+gameStage.getViewport().getWorldWidth(),(float)-Math.random()*150);
        }
    }

    private static String getRandomHouse(){
        byte number = (byte) (Math.random()*3);

        if(number >= 2) return HOUSE_PINK;
        else if (number >= 1) return HOUSE_YELLOW;
        else return HOUSE_BLUE;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (isAct) {
            if (getX() >= -getWidth()) {
                setX(getX() - 7);
            } else {
                if (game != null) {
                    if (game instanceof FlightGame) {
                        ((FlightGame) game).penz += 1;
                        ((FlightGame) game).saveCoins();
                    }
                }
                remove();
            }
        }
    }

    @Override
    public boolean remove() {
        gameStage.removeHouse(this);
        return super.remove();
    }
}
