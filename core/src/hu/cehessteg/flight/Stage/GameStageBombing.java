package hu.cehessteg.flight.Stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import hu.cehessteg.flight.Actor.Airplane;
import hu.cehessteg.flight.Actor.Bomb;
import hu.cehessteg.flight.Actor.Cloud;
import hu.cehessteg.flight.Actor.Explosion;
import hu.cehessteg.flight.Actor.Ship;
import hu.cehessteg.flight.Actor.Sky;
import hu.cehessteg.flight.Screen.GameScreenCombat;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimer;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimerListener;
import hu.csanyzeg.master.MyBaseClasses.Timers.Timer;

import static hu.cehessteg.flight.Stage.MenuStage.trebuc;
import static hu.csanyzeg.master.MyBaseClasses.Scene2D.MyActor.overlaps;

public class GameStageBombing /*extends MyStage*/ {

    /***
    public static AssetList assetList = new AssetList();
    static {
        assetList.collectAssetDescriptor(Airplane.class, assetList);
        assetList.collectAssetDescriptor(Cloud.class, assetList);
        assetList.collectAssetDescriptor(Sky.class, assetList);
        assetList.collectAssetDescriptor(Bomb.class,assetList);
        assetList.collectAssetDescriptor(Ship.class,assetList);
        assetList.addFont(trebuc, trebuc, 30, Color.WHITE, AssetList.CHARS);
    }

    private Airplane airplane;
    private Sky sky;
    public static boolean isShoot;
    private ArrayList<Cloud> clouds;
    private ArrayList<Bomb> bombs;
    private ArrayList<Ship> ships;

    public GameStageBombing(final MyGame game) {
        super(new ResponseViewport(900), game);
        assignment();
        setSizesAndPositions();
        addActors();
        addBackButtonScreenBackByStackPopListener();
    }

    private void assignment()
    {
        GameStageCombat.isAct = true;//Ezen még csiszolni kell
        isShoot = false;
        sky = new Sky(game);
        airplane = new Airplane(game);
        clouds = new ArrayList<>();
        bombs = new ArrayList<>();
        ships = new ArrayList<>();
        for (int i = 0; i < 18; i++) clouds.add(new Cloud(game, getViewport()));
    }

    private void setSizesAndPositions()
    {
        /**SIZES**/
    /**
        sky.setSize(getViewport().getWorldWidth(),getViewport().getWorldHeight()*1.75f);
        airplane.setSize(airplane.getWidth()*0.2f, airplane.getHeight()*0.2f);
    **/
        /**POSITIONS**/
        /**
        airplane.setY(getViewport().getWorldHeight()/2-airplane.getHeight()/2);

    }**/

    /**

    private void addActors()
    {
        addActor(sky);
        for (int i = 0; i < clouds.size(); i++) addActor(clouds.get(i));
        addActor(airplane);
        airplane.setZIndex(7);
    }

    private void addShip()
    {
        ships.add(new Ship(game, this));
        addActor(ships.get(ships.size()-1));
    }

    public void removeShip(Ship s){
        ships.remove(s);
    }

    public void addBomb(Bomb bomb)
    {
        bombs.add(bomb);
    }

    public void removeBomb(Bomb bomb)
    {
        bombs.remove(bomb);
    }

    private float prevY;
    private float pElapsed = elapsedTime;

    @Override
    public void act(float delta) {
        super.act(delta);
        if(GameStageCombat.isAct)
        {
            /**
             * ACT STUFF GOES HERE
             * */

            /**
            airplane.setY(HudStageBombing.planeY - airplane.getHeight()/2);
            airplane.setX(HudStageBombing.planeX - airplane.getWidth()/2);

            if(prevY != HudStageBombing.planeY) {
                airplane.setY(HudStageBombing.planeY - airplane.getHeight() / 2);
                airplane.setRotation(((airplane.getY() / getViewport().getWorldHeight()) - 0.5f) * 90);
                prevY = HudStageBombing.planeY;
            }

            if(isShoot)
            {
                airplane.bomb(this);
                isShoot = false;
            }

            if(elapsedTime > pElapsed)
            {
                addShip();
                pElapsed = (float) (elapsedTime + Math.random() * 3 + 1);
            }

            for (Bomb b : bombs)
            {
                if(!ships.isEmpty())
                    for (Ship s : ships)
                    {
                        if(overlaps(b,s))
                        {
                            addActor(new Explosion(game,s));
                            addActor(new Explosion(game,b));
                            s.remove();
                            ships.remove(s);
                            b.remove();
                            break;
                        }
                    }
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            /**
             * !!!NEM MŰKÖDIK A addBackButtonScreenBackByStackPopListener()!!!
             * **/
            /**
            game.setScreenBackByStackPop();
        }
    }
    **/
}
