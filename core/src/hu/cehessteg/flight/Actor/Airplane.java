package hu.cehessteg.flight.Actor;

import hu.cehessteg.flight.FlightGame;
import hu.cehessteg.flight.Stage.GameStage;
import hu.cehessteg.flight.Stage.HudStage;
import hu.cehessteg.flight.Stage.OptionsStage;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.SimpleWorld.MyCircle;
import hu.csanyzeg.master.MyBaseClasses.SimpleWorld.MyRectangle;

public class Airplane extends OneSpriteStaticActor {

    public static final String AIRPLANE_TEXTURE = "planes/vadaszgep.png";
    public static final String TERRAIN_TEXTURE = "planes/vadaszgep_terep.png";
    public static final String BLACKANDWHITE_TEXTURE = "planes/vadaszgep_szurke.png";
    public static final String TIGER_TEXTURE = "planes/vadaszgep_tigris.png";

    public static AssetList assetList = new AssetList();
    static
    {
        assetList.addTexture(AIRPLANE_TEXTURE);
        assetList.addTexture(TERRAIN_TEXTURE);
        assetList.addTexture(BLACKANDWHITE_TEXTURE);
        assetList.addTexture(TIGER_TEXTURE);
    }

    public byte hp;//Életerő
    public byte fuel;//Üzemanyag
    public int level;
    public int remainingBombs;
    private static MyGame game;
    private MyRectangle myRectangle;
    private MyRectangle myRectangle2;
    private MyRectangle myRectangle3;

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
        myRectangle2 = new MyRectangle(400,120);
        myRectangle2.setOffsetX(1200);
        myRectangle2.setOffsetY(320);
        myRectangle3 = new MyRectangle(650,75);
        myRectangle3.setOffsetX(820);
        myRectangle3.setOffsetY(90);
        addCollisionShape("hitbox", myRectangle);
        addCollisionShape("hitbox2", myRectangle2);
        addCollisionShape("hitbox3", myRectangle3);
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

                    case 3:{
                        sprite.setTexture(game.getMyAssetManager().getTexture(BLACKANDWHITE_TEXTURE));
                        break;
                    }

                    case 4:{
                        sprite.setTexture(game.getMyAssetManager().getTexture(TIGER_TEXTURE));
                        break;
                    }

                    case 5:{

                    }
                }
            }
        }
    }

    public void setTexture(int id){
        switch (id){
            case 1:{
                sprite.setTexture(game.getMyAssetManager().getTexture(AIRPLANE_TEXTURE));
                break;
            }
            case 2:{
                sprite.setTexture(game.getMyAssetManager().getTexture(TERRAIN_TEXTURE));
                break;
            }
            case 3:{
                sprite.setTexture(game.getMyAssetManager().getTexture(BLACKANDWHITE_TEXTURE));
                break;
            }
            case 4:{
                sprite.setTexture(game.getMyAssetManager().getTexture(TIGER_TEXTURE));
                break;
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
        if(getStage() != null) {
            if (!(getStage() instanceof OptionsStage)) {
                rotateBack();
            }
        }
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
