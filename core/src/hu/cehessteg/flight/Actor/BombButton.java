package hu.cehessteg.flight.Actor;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hu.cehessteg.flight.FlightGame;
import hu.cehessteg.flight.Stage.GameStage;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class BombButton extends OneSpriteStaticActor {

    public static final String BOMB_DISABLED = "other/bombagomb1.png";
    public static final String BOMB_READY = "other/bombagomb2.png";
    public static final String BOMB_BUSY = "other/bombagomb3.png";

    public static AssetList assetList;
    static {
        assetList = new AssetList();
        assetList.addTexture(BOMB_BUSY);
        assetList.addTexture(BOMB_READY);
        assetList.addTexture(BOMB_DISABLED);
    }

    private float pElapsed;

    public BombButton(final MyGame game) {
        super(game, isReady(game));
        System.out.println(isReady(game));

        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(elapsedTime > pElapsed + 0.15f) {
                    GameStage.isBomb = true;
                    pElapsed = elapsedTime;
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                if(game != null) {
                    if (game instanceof FlightGame) {
                        if (((FlightGame) game).getPlaneLevel() >= 6) {
                            if(Airplane.remainingBombs == 0) sprite.setTexture(game.getMyAssetManager().getTexture(BOMB_DISABLED));
                            else sprite.setTexture(game.getMyAssetManager().getTexture(BOMB_READY));
                        }
                        else sprite.setTexture(game.getMyAssetManager().getTexture(BOMB_DISABLED));
                    }
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                sprite.setTexture(game.getMyAssetManager().getTexture(BOMB_BUSY));
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    private static String isReady(MyGame game){
        if(game != null){
            if(game instanceof FlightGame){
                if(((FlightGame)game).getPlaneLevel() >= 6) return BOMB_READY;
                else return BOMB_DISABLED;
            }
            else return "";
        }
        else return "";
    }
}