package hu.cehessteg.flight.Actor;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

import hu.cehessteg.flight.FlightGame;
import hu.cehessteg.flight.Stage.GameStage;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class ShootButton extends OneSpriteStaticActor {

    public static final String SHOOT_DISABLED = "other/lovesgomb1.png";
    public static final String SHOOT_READY = "other/lovesgomb2.png";
    public static final String SHOOT_BUSY = "other/lovesgomb3.png";

    public static AssetList assetList;
    static {
        assetList = new AssetList();
        assetList.addTexture(SHOOT_BUSY);
        assetList.addTexture(SHOOT_READY);
        assetList.addTexture(SHOOT_DISABLED);
    }

    private float pElapsed;
    private float increment = 0.15f;

    public ShootButton(final MyGame game) {
        super(game, isReady(game));
        if(game instanceof FlightGame){
            if(((FlightGame) game).getPlaneLevel() >= 5) increment = 0;
        }

        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(elapsedTime > pElapsed + increment) {
                    GameStage.isShoot = true;
                    pElapsed = elapsedTime;
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                if(game != null) {
                    if (game instanceof FlightGame) {
                        if (((FlightGame) game).getPlaneLevel() >= 2)
                            sprite.setTexture(game.getMyAssetManager().getTexture(SHOOT_READY));
                        else sprite.setTexture(game.getMyAssetManager().getTexture(SHOOT_DISABLED));
                    }
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                sprite.setTexture(game.getMyAssetManager().getTexture(SHOOT_BUSY));
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    private static String isReady(MyGame game){
        if(game != null){
            if(game instanceof FlightGame){
                if(((FlightGame)game).getPlaneLevel() >= 2) return SHOOT_READY;
                else return SHOOT_DISABLED;
            }
            else return "";
        }
        else return "";
    }
}
