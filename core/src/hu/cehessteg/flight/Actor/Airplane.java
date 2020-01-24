package hu.cehessteg.flight.Actor;

import hu.cehessteg.flight.FlightGame;
import hu.cehessteg.flight.Screen.GameScreenBombing;
import hu.cehessteg.flight.Stage.GameStageBombing;
import hu.cehessteg.flight.Stage.GameStageCombat;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class Airplane extends OneSpriteStaticActor {

    public static final String AIRPLANE_TEXTURE = "planes/vadaszgep.png";

    public static AssetList assetList = new AssetList();
    static
    {
        assetList.addTexture(AIRPLANE_TEXTURE);
    }

    public byte hp;//Életerő
    public byte fuel;//Üzemanyag
    public int level;
    public int remainingBombs;
    private static MyGame game;

    public Airplane(MyGame game) {
        super(game, AIRPLANE_TEXTURE);
        hp = 100;
        fuel = 100;
        remainingBombs = 12;

        this.game = game;
        addBaseCollisionRectangleShape();

        if(game instanceof FlightGame){
            level = ((FlightGame) game).getPlaneLevel();
        }

        if(level >= 8) remainingBombs = 24;
    }

    public void shoot(GameStageCombat stage)
    {
        if(this.level >= 2) {
            try {
                stage.addActor(new Bullet(game, this, stage));
            } catch (NullPointerException e) {
                System.out.println("STAGE NOT FOUND OR ASSETS NOT LOADED!!");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else System.out.println("Még nem érhető el a lőfegyver!");
    }

    public void bomb(GameStageBombing stage)
    {
        if(this.level >= 6) {
            if(remainingBombs > 0) {
                try {
                    stage.addActor(new Bomb(game, this, stage));
                    if(level < 10) remainingBombs--;
                } catch (NullPointerException e) {
                    System.out.println("STAGE NOT FOUND OR ASSETS NOT LOADED!!");
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else System.out.println("Elfogyott a bomba!");
        }else System.out.println("Még nem érhető el a bomba!");
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(getRotation() < -2 || getRotation() > 2)
        {
            if(getRotation() > 2) setRotation(getRotation() - 2);
            else if (getRotation() < 2) setRotation(getRotation() + 2);
        }
    }
}
