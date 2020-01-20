package hu.cehessteg.flight.Stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import hu.cehessteg.flight.Actor.Airplane;
import hu.cehessteg.flight.Actor.Bomb;
import hu.cehessteg.flight.Actor.Cloud;
import hu.cehessteg.flight.Actor.Sky;
import hu.cehessteg.flight.Screen.GameScreenCombat;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;

import static hu.cehessteg.flight.Stage.MenuStage.trebuc;

public class GameStageBombing extends MyStage {

    public static AssetList assetList = new AssetList();
    static {
        assetList.collectAssetDescriptor(Airplane.class, assetList);
        assetList.collectAssetDescriptor(Cloud.class, assetList);
        assetList.collectAssetDescriptor(Sky.class, assetList);
        assetList.collectAssetDescriptor(Bomb.class,assetList);
        assetList.addFont(trebuc, trebuc, 30, Color.WHITE, AssetList.CHARS);
    }

    private Airplane airplane;
    private Sky sky;
    public static boolean isShoot;
    private ArrayList<Cloud> clouds;
    private ArrayList<Bomb> bombs;

    public GameStageBombing(final MyGame game) {
        super(new ResponseViewport(900), game);
        assignment();
        setSizesAndPositions();
        addActors();
    }

    private void assignment()
    {
        GameStageCombat.isAct = true;//Ezen még csiszolni kell
        isShoot = false;
        sky = new Sky(game);
        airplane = new Airplane(game);
        clouds = new ArrayList<>();
        bombs = new ArrayList<>();
        for (int i = 0; i < 18; i++) clouds.add(new Cloud(game, getViewport()));
    }

    private void setSizesAndPositions()
    {
        /**SIZES**/
        sky.setSize(getViewport().getWorldWidth(),getViewport().getWorldHeight());
        airplane.setSize(airplane.getWidth()*0.2f, airplane.getHeight()*0.2f);

        /**POSITIONS**/
        airplane.setY(getViewport().getWorldHeight()/2-airplane.getHeight()/2);
    }

    private void addActors()
    {
        addActor(sky);
        for (int i = 0; i < clouds.size(); i++) addActor(clouds.get(i));
        addActor(airplane);
        airplane.setZIndex(7);
    }

    public void addBomb(Bomb bomb)
    {
        bombs.add(bomb);
    }

    public void removeBomb(Bomb bomb)
    {
        bombs.remove(bomb);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(GameStageCombat.isAct)
        {
            /**
             * ACT STUFF GOES HERE
             * */

            airplane.setY(HudStageBombing.planeY - airplane.getHeight()/2);
            airplane.setX(HudStageBombing.planeX - airplane.getWidth()/2);

            if(isShoot)
            {
                airplane.bomb(this);
                isShoot = false;
            }
        }
    }
}
