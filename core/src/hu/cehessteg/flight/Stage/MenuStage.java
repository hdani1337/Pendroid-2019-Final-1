package hu.cehessteg.flight.Stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hu.cehessteg.flight.Screen.GameScreen;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

public class MenuStage extends MyStage {
    public static String trebuc = "trebuc.ttf";
    public static AssetList assetList = new AssetList();
    static {
        assetList.addFont(trebuc, trebuc, 120, Color.WHITE, AssetList.CHARS);
    }

    public MenuStage(MyGame game) {
        super(new ResponseViewport(900), game);
        addBackButtonScreenBackByStackPopListener();
        addActor(new MyLabel("Elindultam", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE), game) {
            @Override
            public void init() {
                setAlignment(0);
                setPosition(getViewport().getWorldWidth()/2-this.getWidth()/2,getViewport().getWorldHeight()/2-this.getHeight()/2);
                addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        //Ez a game a MyLabelből származott. Ezért volt null. Majd módosítom a mester projektben az ilyen konstruktorokat.
                        game.setScreen(new GameScreen(game));
                    }
                });
            }
        });
    }
}
