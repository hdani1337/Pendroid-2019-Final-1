package hu.cehessteg.flight.Actor;

import hu.cehessteg.flight.FlightGame;
import hu.cehessteg.flight.Stage.GameStage;
import hu.cehessteg.flight.Stage.HudStage;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.SimpleWorld.MyCircle;
import hu.csanyzeg.master.MyBaseClasses.SimpleWorld.MyRectangle;

public class Airplane extends OneSpriteStaticActor {

    public static final String AIRPLANE_TEXTURE = "planes/vadaszgep.png";
    public static final String TERRAIN_TEXTURE = "planes/vadaszgep_terep.png";

    public static AssetList assetList = new AssetList();
    static
    {
        assetList.addTexture(AIRPLANE_TEXTURE);
        assetList.addTexture(TERRAIN_TEXTURE);
    }

    public byte hp;//Életerő
    public byte fuel;//Üzemanyag
    public int level;
    public int remainingBombs;
    private static MyGame game;
    private MyRectangle myRectangle;

    public Airplane(MyGame game) {
        super(game, AIRPLANE_TEXTURE);
        this.game = game;
        setHitbox();
        baseValues();
        setTexture();
    }

    private void setHitbox(){
        myRectangle = new MyRectangle(1450,150);
        myRectangle.setOffsetX(300);
        myRectangle.setOffsetY(175);
        addCollisionShape("hitbox", myRectangle);
    }

    private void setTexture(){
        if(game!=null){
            if(game instanceof FlightGame){
                switch (((FlightGame)game).getSkinID()){
                    case 1:{
                        sprite.setTexture(game.getMyAssetManager().getTexture(AIRPLANE_TEXTURE));
                        break;
                    }

                    case 2:{
                        sprite.setTexture(game.getMyAssetManager().getTexture(TERRAIN_TEXTURE));
                        break;
                    }
                }
            }
        }
    }

    private void baseValues()
    {
        hp = 100;
        fuel = 100;

        if(game != null)
            if(game instanceof FlightGame)
                level = ((FlightGame) game).getPlaneLevel();

        if(level >= 8)
            remainingBombs = 24;
        else if (level >= 6)
            remainingBombs = 12;

        HudStage.remainingBombs = this.remainingBombs;
    }

    public void shoot(GameStage stage)
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

    public void bomb(GameStage stage)
    {
        if(this.level >= 6) {
            if(remainingBombs > 0) {
                try {
                    stage.addActor(new Bomb(game, this, stage));
                    if(level < 10) {
                        remainingBombs--;
                        HudStage.remainingBombs = this.remainingBombs;
                    }
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
        rotateBack();
    }

    public boolean isRotateBack;

    private void rotateBack()
    {
        if(isRotateBack) {
            if (getRotation() < -2 || getRotation() > 2) {
                if (getRotation() > 2) setRotation(getRotation() - 2);
                else if (getRotation() < 2) setRotation(getRotation() + 2);
            }
            else {
                setRotation(0);
                isRotateBack = false;
            }
        }
    }

    public void setRotateBack(boolean rotateBack) {
        isRotateBack = rotateBack;
    }
}
