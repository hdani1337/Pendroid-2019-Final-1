package hu.cehessteg.flight.Stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.Viewport;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

import static hu.cehessteg.flight.Stage.GameStage.isAct;
import static hu.cehessteg.flight.Stage.MenuStage.trebuc;

public class GameOverStage extends MyStage {

    public static AssetList assetList = new AssetList();
    static {
        assetList.addFont(trebuc, trebuc, 120, Color.WHITE, AssetList.CHARS);
    }

    MyLabel text;

    public GameOverStage(MyGame game) {
        super(new ResponseViewport(900), game);
        addedActors = false;
        text = new MyLabel(game, "Vége a játéknak!", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {

            }
        };
    }

    private boolean addedActors;

    @Override
    public void act(float delta) {
        super.act(delta);
        if(!isAct)
        {
            if(!addedActors)
            {
                //ADDING ACTORS
                addActor(text);
                addedActors = true;
            }
        }
    }
}
