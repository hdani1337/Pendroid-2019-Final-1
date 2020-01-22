package hu.cehessteg.flight.Stage;

import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.viewport.Viewport;

import hu.cehessteg.flight.Actor.Bomb;
import hu.cehessteg.flight.Screen.GameScreenBombing;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;

import static hu.cehessteg.flight.Stage.GameOverStage.BLANK_TEXTURE;

public class HudStageBombing extends MyStage {

    public static AssetList assetList;
    static
    {
        assetList = new AssetList();
        assetList.addTexture(BLANK_TEXTURE);
    }

    OneSpriteStaticActor PositionController;
    OneSpriteStaticActor BombingController;

    public static float planeY;
    public static float planeX;
    private float pElapsed;

    public HudStageBombing(MyGame game) {
        super(new ResponseViewport(900), game);
        assignment();
        addListeners();
        setSizesAndPositions();
        addActors();
        planeX = 250;
        planeY = getViewport().getWorldHeight()/2;
        pElapsed = elapsedTime;
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
                planeX = x;
            }
        });

        BombingController.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(elapsedTime > pElapsed + 0.4f) {
                    GameStageBombing.isShoot = true;
                    pElapsed = elapsedTime;
                }
            }
        });
    }

    private void setSizesAndPositions()
    {
        PositionController.setSize(getViewport().getWorldWidth()*0.7f, getViewport().getWorldHeight());
        PositionController.setAlpha(0.05f);
        BombingController.setSize(getViewport().getWorldWidth()*0.3f, getViewport().getWorldHeight());
        BombingController.setX(PositionController.getX() + PositionController.getWidth());
        BombingController.setAlpha(0.05f);
    }

    private void addActors()
    {
        addActor(PositionController);
        addActor(BombingController);
    }

}
