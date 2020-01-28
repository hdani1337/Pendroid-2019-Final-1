package hu.cehessteg.flight.Actor;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hu.cehessteg.flight.FlightGame;
import hu.cehessteg.flight.Stage.OptionsStage;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class Speaker extends OneSpriteStaticActor {

    public static final String MUTEOFF_TEXTURE = "menu/hangos.png";
    public static final String MUTEON_TEXTURE = "menu/nema.png";

    public static AssetList assetList;
    static {
        assetList = new AssetList();
        assetList.addTexture(MUTEOFF_TEXTURE);
        assetList.addTexture(MUTEON_TEXTURE);
    }

    private MyStage myStage;

    public Speaker(MyGame game, MyStage myStage) {
        super(game, MUTEOFF_TEXTURE);
        this.myStage = myStage;
        setTexture();
        addListener();
        setSize(getWidth()*0.2f, getHeight()*0.2f);
    }

    public void setTexture()
    {
        if(game != null) {
            if (game instanceof FlightGame) {
                if (((FlightGame) game).isMuted()) {
                    sprite.setTexture(game.getMyAssetManager().getTexture(MUTEON_TEXTURE));
                } else {
                    sprite.setTexture(game.getMyAssetManager().getTexture(MUTEOFF_TEXTURE));
                }
            }
        }
    }

    private void addListener(){
        if(myStage != null){
            if(myStage instanceof OptionsStage){
                addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        if(game != null){
                            if(game instanceof FlightGame){
                                if(!((FlightGame) game).isMuted()) {
                                    ((FlightGame) game).setMuted(true);
                                    sprite.setTexture(game.getMyAssetManager().getTexture(MUTEON_TEXTURE));
                                }
                                else{
                                    ((FlightGame) game).setMuted(false);
                                    sprite.setTexture(game.getMyAssetManager().getTexture(MUTEOFF_TEXTURE));
                                }
                            }
                        }
                    }
                });
            }
        }
    }
}
