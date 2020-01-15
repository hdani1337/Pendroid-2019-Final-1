package hu.cehessteg.flight.Stage;

import hu.cehessteg.flight.Actor.Airplane;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;

public class GameStage extends MyStage {
    public static AssetList assetList = new AssetList();
    static {
        assetList.collectAssetDescriptor(Airplane.class, assetList);
    }

    private static Airplane airplane;

    public GameStage(MyGame game) {
        super(new ResponseViewport(900), game);//Ha lesz Box2D, akkor 900 helyett mondjuk 9 lesz
        airplane = new Airplane(game);
        airplane.setSize(airplane.getWidth()*0.7f, airplane.getHeight()*0.7f);
        airplane.setY(getViewport().getWorldHeight()/2-airplane.getHeight()/2);
        addActor(airplane);
    }

    public static void moveDown()
    {
        if(airplane.getY() > 0) //Így nem megy ki a képből
            airplane.setY(airplane.getY()-15); //15 pixellel lejjebb helyezés
    }

    public static void moveUp()
    {
        if(airplane.getY()+airplane.getHeight() < 900)//Így nem megy ki a képből
            airplane.setY(airplane.getY()+15); //15 pixellel feljebb helyezés
    }
}
