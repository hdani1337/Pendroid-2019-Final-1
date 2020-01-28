package hu.cehessteg.flight.Actor;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hu.cehessteg.flight.FlightGame;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class Trash extends OneSpriteStaticActor {

    public static final String TRASH_TEXTURE = "other/kuka.png";

    public static AssetList assetList;
    static {
        assetList = new AssetList();
        assetList.addTexture(TRASH_TEXTURE);
    }

    private Speaker speaker;

    public Trash(MyGame game, Speaker speaker) {
        super(game, TRASH_TEXTURE);
        addListeners();
        setSize(getWidth()*0.12f, getHeight()*0.12f);
        this.speaker = speaker;
    }

    private void addListeners(){
        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(game != null){
                    if(game instanceof FlightGame){
                        ((FlightGame) game).resetSave();
                        ((FlightGame) game).baseValues();
                        speaker.setTexture();
                    }
                }
            }
        });
    }
}
