package hu.cehessteg.flight.Stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

import static hu.cehessteg.flight.Stage.GameOverStage.BLANK_TEXTURE;
import static hu.cehessteg.flight.Stage.MenuStage.trebuc;

public class HudStageCombat extends MyStage {
    public static AssetList assetList = new AssetList();
    static {
        assetList.addFont(trebuc, trebuc, 100, Color.WHITE, AssetList.CHARS);
        assetList.addTexture(BLANK_TEXTURE);
    }

    OneSpriteStaticActor PositionController;
    OneSpriteStaticActor BombingController;

    public static float planeY;

    public HudStageCombat(MyGame game) {
        super(new ResponseViewport(900), game);
        assignment();
        setSizesAndPositions();
        addListeners();
        addActors();
        planeY = getViewport().getWorldHeight()/2;
    }


    private void assignment()
    {
        PositionController = new OneSpriteStaticActor(game, BLANK_TEXTURE);
        BombingController = new OneSpriteStaticActor(game, BLANK_TEXTURE);
    }

    private void addListeners()
    {
        PositionController.addListener(new DragListener()
        {
            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                super.drag(event, x, y, pointer);
                planeY = y;
            }
        });

        BombingController.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                GameStageCombat.isShoot = true;
            }
        });
    }

    private void setSizesAndPositions()
    {
        PositionController.setSize(getViewport().getWorldWidth()/2, getViewport().getWorldHeight());
        PositionController.setAlpha(0.05f);
        BombingController.setSize(getViewport().getWorldWidth()/2, getViewport().getWorldHeight());
        BombingController.setX(PositionController.getX() + PositionController.getWidth());
        BombingController.setAlpha(0.05f);
    }

    private void addActors()
    {
        addActor(PositionController);
        addActor(BombingController);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

    }
}
