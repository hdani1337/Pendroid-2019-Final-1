package hu.cehessteg.flight.Stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Align;

import hu.cehessteg.flight.Actor.Bomb;
import hu.cehessteg.flight.Actor.BombButton;
import hu.cehessteg.flight.Actor.Coin;
import hu.cehessteg.flight.Actor.ShootButton;
import hu.cehessteg.flight.FlightGame;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyGroup;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

import static hu.cehessteg.flight.Stage.GameOverStage.BLANK_TEXTURE;
import static hu.cehessteg.flight.Stage.MenuStage.trebuc;

public class HudStage extends MyStage {

    public static final String PAUSE_TEXTURE = "menu/pause.png";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addFont(trebuc, trebuc, 100, Color.WHITE, AssetList.CHARS);
        assetList.addTexture(BLANK_TEXTURE);
        assetList.addTexture(PAUSE_TEXTURE);
        assetList.collectAssetDescriptor(Coin.class, assetList);
        assetList.collectAssetDescriptor(ShootButton.class, assetList);
        assetList.collectAssetDescriptor(BombButton.class, assetList);
    }

    OneSpriteStaticActor PositionController;
    ShootButton ShootingController;
    BombButton BombingController;
    OneSpriteStaticActor pause;

    public static float planeY;
    public static float planeX = 250;
    public static boolean isRotateBack;

    private Coin coin;
    private MyGroup bombGroup;

    public HudStage(MyGame game) {
        super(new ResponseViewport(900), game);
        assignment();
        setSizesAndPositions();
        addListeners();
        addActors();
        planeY = getViewport().getWorldHeight()/2;

        /**PÉNZ ELÉRÉSE A GAMEBŐL
         * TUDOM HOGY NEM SZABADNA CASTOLNI DE MŰKÖDIK!!
         * if(game instanceof FlightGame) System.out.println(((FlightGame) game).getPenz());
         * **/
    }

    float increment = 0.15f;

    private void assignment()
    {
        PositionController = new OneSpriteStaticActor(game, BLANK_TEXTURE);
        ShootingController = new ShootButton(game);
        BombingController = new BombButton(game);
        pause = new OneSpriteStaticActor(game, PAUSE_TEXTURE);
        coin = new Coin(game);
        if(game instanceof FlightGame){
            if(((FlightGame) game).getPlaneLevel() >= 5) increment = 0;
        }
        setBombGroup();
    }

    private Bomb bomb;
    public static int remainingBombs;

    private void setBombGroup(){
        if(game != null) {
            if (game instanceof FlightGame) {
                if (((FlightGame) game).getPlaneLevel() >= 6) {
                    bombGroup = new MyGroup(game);
                    bombGroup.addActor(bomb = new Bomb(game, null, null) {
                        @Override
                        public void init() {
                            super.init();
                            setRotation(0);
                            setSize(getWidth() * 0.3f, getHeight() * 0.3f);
                        }
                    });
                    bombGroup.addActor(new MyLabel(game, "UNDEFINED", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
                        @Override
                        public void init() {
                            setFontScale(0.3f);
                            setAlignment(Align.bottomLeft);
                            setPosition(bomb.getX() + bomb.getWidth() + 5, bomb.getY() + bomb.getHeight() / 8);
                        }

                        @Override
                        public void act(float delta) {
                            super.act(delta);
                            if (game != null) {
                                if (game instanceof FlightGame) {
                                    if (((FlightGame) game).getPlaneLevel() == 10 && !getText().equals("Végtelen")) {
                                        setText("Végtelen");
                                    } else {
                                        setText(remainingBombs);
                                    }
                                }
                            }
                        }
                    });
                }
            }
        }
        else bombGroup = null;
    }

    private void addListeners()
    {
        PositionController.addListener(new DragListener()
        {
            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                super.drag(event, x, y, pointer);
                planeY = y;
                if(x < getViewport().getWorldWidth()*0.4f) planeX = x;
                isRotateBack = false;
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                super.dragStop(event, x, y, pointer);
                isRotateBack = true;
            }

            @Override
            public void dragStart(InputEvent event, float x, float y, int pointer) {
                super.dragStart(event, x, y, pointer);
                isRotateBack = false;
            }
        });

        pause.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                GameStage.isAct = false;
            }
        });
    }

    private void setSizesAndPositions()
    {
        PositionController.setSize(getViewport().getWorldWidth()/2, getViewport().getWorldHeight());
        PositionController.setAlpha(0.01f);

        ShootingController.setSize(ShootingController.getWidth()/2, ShootingController.getHeight()/2);
        ShootingController.setX(getViewport().getWorldWidth()-ShootingController.getWidth());
        ShootingController.setY(0);
        ShootingController.setAlpha(1);

        BombingController.setSize(BombingController.getWidth()/2, BombingController.getHeight()/2);
        BombingController.setX(getViewport().getWorldWidth()-ShootingController.getWidth()*2);
        BombingController.setY(0);
        BombingController.setAlpha(1);

        pause.setPosition(getViewport().getWorldWidth()-pause.getWidth(), getViewport().getWorldHeight()-pause.getHeight());
        if(bombGroup != null) bombGroup.setPosition(0, getViewport().getWorldHeight()-50);
    }

    private void addActors()
    {
        addActor(PositionController);
        addActor(pause);
        addActor(coin);
        addActor(ShootingController);
        addActor(BombingController);
        if(bombGroup != null) addActor(bombGroup);
    }
}
