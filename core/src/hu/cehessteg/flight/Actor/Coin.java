package hu.cehessteg.flight.Actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import hu.cehessteg.flight.FlightGame;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyGroup;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

import static hu.cehessteg.flight.Stage.MenuStage.trebuc;

public class Coin extends MyGroup {

    public static final String COIN_TEXTURE = "other/coin.png";

    public static AssetList assetList;
    static {
        assetList = new AssetList();
        assetList.addTexture(COIN_TEXTURE);
        assetList.addFont(trebuc, trebuc, 120, Color.WHITE, AssetList.CHARS);
    }

    private OneSpriteStaticActor coinActor;
    private MyLabel coinLabel;

    public Coin(MyGame game) {
        super(game);
        assignment();
        setPositions();
        addActors();
        setTouchable(null);
    }

    private void assignment()
    {
        coinActor = new OneSpriteStaticActor(game, COIN_TEXTURE);
        coinLabel = new MyLabel(game, "UNDEFINED", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setFontScale(0.7f);
            }
        };
    }

    private void setPositions()
    {
        coinActor.setPosition(0,0);
        coinLabel.setAlignment(-1);
        coinLabel.setPosition(coinActor.getX() + coinActor.getWidth(), coinActor.getY() + coinActor.getHeight()/2 - coinLabel.getHeight() * 0.65f);
    }

    private void addActors()
    {
        addActor(coinActor);
        addActor(coinLabel);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(game != null)
            if(game instanceof FlightGame){
                if(!coinLabel.getText().equals(((FlightGame) game).penz)){
                    coinLabel.setText((((FlightGame) game).penz) + "");
                    setPositions();
                }
            }
    }
}
