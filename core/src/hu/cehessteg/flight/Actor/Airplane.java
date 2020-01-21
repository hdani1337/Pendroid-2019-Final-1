package hu.cehessteg.flight.Actor;

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
    private static MyGame game;

    public Airplane(MyGame game) {
        super(game, AIRPLANE_TEXTURE);
        hp = 100;
        fuel = 100;
        this.game = game;
        addBaseCollisionRectangleShape();
    }

    public void shoot(GameStageCombat stage)
    {
        try {
            stage.addActor(new Bullet(game, this, stage));
            System.out.println("PUFF");
        }catch (NullPointerException e)
        {
            System.out.println("STAGE NOT FOUND");
            e.printStackTrace();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void bomb(GameStageBombing stage)
    {
        try {
            stage.addActor(new Bomb(game, this, stage));
            System.out.println("PUFF");
        }catch (NullPointerException e)
        {
            System.out.println("STAGE NOT FOUND");
            e.printStackTrace();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
