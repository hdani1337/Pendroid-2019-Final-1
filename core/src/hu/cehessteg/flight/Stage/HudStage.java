package hu.cehessteg.flight.Stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

import hu.cehessteg.flight.Actor.Coin;
import hu.cehessteg.flight.FlightGame;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;

import static hu.cehessteg.flight.Stage.GameOverStage.BLANK_TEXTURE;
import static hu.cehessteg.flight.Stage.MenuStage.trebuc;

public class HudStage extends MyStage {
    public static AssetList assetList = new AssetList();
    static {
        assetList.addFont(trebuc, trebuc, 100, Color.WHITE, AssetList.CHARS);
        assetList.addTexture(BLANK_TEXTURE);
        assetList.collectAssetDescriptor(Coin.class, assetList);
    }

    OneSpriteStaticActor PositionController;
    OneSpriteStaticActor ShootingController;
    OneSpriteStaticActor BombingController;

    public static float planeY;
    private float pElapsed;

    private Coin coin;

    public HudStage(MyGame game) {
        super(new ResponseViewport(900), game);
        assignment();
        setSizesAndPositions();
        addListeners();
        addActors();
        addBackButtonScreenBackByStackPopListener();
        planeY = getViewport().getWorldHeight()/2;
        pElapsed = elapsedTime;

        /**PÉNZ ELÉRÉSE A GAMEBŐL
         * TUDOM HOGY NEM SZABADNA CASTOLNI DE MŰKÖDIK!!
         * if(game instanceof FlightGame) System.out.println(((FlightGame) game).getPenz());
         * **/
    }

    float increment = 0.15f;

    private void assignment()
    {
        PositionController = new OneSpriteStaticActor(game, BLANK_TEXTURE);
        ShootingController = new OneSpriteStaticActor(game, BLANK_TEXTURE);
        BombingController = new OneSpriteStaticActor(game, BLANK_TEXTURE);
        coin = new Coin(game);
        if(game instanceof FlightGame){
            if(((FlightGame) game).getPlaneLevel() >= 5) increment = 0;
        }
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

        ShootingController.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                if(elapsedTime > pElapsed + increment) {
                    GameStage.isShoot = true;
                    pElapsed = elapsedTime;
                }
            }
        });

        BombingController.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                if(elapsedTime > pElapsed + increment) {
                    GameStage.isBomb = true;
                    pElapsed = elapsedTime;
                }
            }
        });
    }

    private void setSizesAndPositions()
    {
        PositionController.setSize(getViewport().getWorldWidth()/2, getViewport().getWorldHeight());
        PositionController.setAlpha(0.05f);

        ShootingController.setSize(getViewport().getWorldWidth()/2, getViewport().getWorldHeight()/2);
        ShootingController.setX(PositionController.getX() + PositionController.getWidth());
        ShootingController.setY(getViewport().getWorldHeight()/2);
        ShootingController.setAlpha(0.05f);

        BombingController.setSize(getViewport().getWorldWidth()/2, getViewport().getWorldHeight()/2);
        BombingController.setX(PositionController.getX() + PositionController.getWidth());
        BombingController.setY(0);
        BombingController.setAlpha(0.05f);
    }

    private void addActors()
    {
        addActor(PositionController);
        addActor(ShootingController);
        addActor(BombingController);
        addActor(coin);
    }

}
