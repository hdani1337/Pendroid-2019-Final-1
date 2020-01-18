package hu.cehessteg.flight.Stage;



import org.omg.IOP.ENCODING_CDR_ENCAPS;

import java.util.ArrayList;

import hu.cehessteg.flight.Actor.Airplane;
import hu.cehessteg.flight.Actor.Bullet;
import hu.cehessteg.flight.Actor.Cloud;
import hu.cehessteg.flight.Actor.Enemy;
import hu.cehessteg.flight.Actor.Sky;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;

import static hu.csanyzeg.master.MyBaseClasses.Scene2D.MyActor.overlaps;

public class GameStage extends MyStage {
    public static AssetList assetList = new AssetList();
    static {
        assetList.collectAssetDescriptor(Airplane.class, assetList);
        assetList.collectAssetDescriptor(Cloud.class, assetList);
        assetList.collectAssetDescriptor(Sky.class, assetList);
        assetList.collectAssetDescriptor(Enemy.class,assetList);
        assetList.collectAssetDescriptor(Bullet.class,assetList);
    }

    private static Airplane airplane;
    private static Sky sky;
    private static Enemy enemy;
    public static boolean isAct;
    public static boolean isShoot;
    private ArrayList<Cloud> clouds;
    private ArrayList<Bullet> bullets;

    public GameStage(MyGame game) {
        super(new ResponseViewport(900), game);//Ha lesz Box2D, akkor 900 helyett mondjuk 9 lesz
        assignment();
        setSizesAndPositions();
        addActors();
    }

    private void assignment()
    {
        isAct = true;
        isShoot = false;
        sky = new Sky(game);
        airplane = new Airplane(game);
        enemy = new Enemy(game,getViewport());
        clouds = new ArrayList<>();
        bullets = new ArrayList<>();
        for (int i = 0; i < 18; i++) clouds.add(new Cloud(game, getViewport()));
    }

    private void setSizesAndPositions()
    {
        /**SIZES**/
        sky.setSize(getViewport().getWorldWidth(),getViewport().getWorldHeight());
        airplane.setSize(airplane.getWidth()*0.2f, airplane.getHeight()*0.2f);
        enemy.setSize(enemy.getWidth()*0.2f,enemy.getHeight()*0.2f);

        /**POSITIONS**/
        airplane.setY(getViewport().getWorldHeight()/2-airplane.getHeight()/2);
        enemy.setX(-1000);
    }

    private void addActors()
    {
        addActor(sky);
        for (int i = 0; i < clouds.size(); i++) addActor(clouds.get(i));
        addActor(airplane);
        addActor(enemy);
    }

    public static void moveDown()
    {
        if(airplane.getY() > 0 && isAct) //Így nem megy ki a képből
            airplane.setY(airplane.getY()-15); //15 pixellel lejjebb helyezés
    }

    public static void moveUp()
    {
        if(airplane.getY()+airplane.getHeight() < 900 && isAct)//Így nem megy ki a képből
            airplane.setY(airplane.getY()+15); //15 pixellel feljebb helyezés
    }

    public void shoot()
    {
        airplane.shoot(this);
    }

    public void addBullet(Bullet bullet)
    {
        bullets.add(bullet);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (isAct) {
            if (overlaps(airplane, enemy)) {
                airplane.hp -= Math.random() * 20;
                enemy.replace();
            }

            for (Bullet bullet : bullets)
            {
                if(overlaps(bullet, enemy))
                {
                    enemy.hp -= bullet.damage;
                    bullet.remove();
                    if(enemy.hp <= 0) {
                        enemy.replace();
                    }
                }
            }

            if(isShoot)
            {
                shoot();
                isShoot = false;
            }

            HudStage.hp.setText("HP: " + airplane.hp);
        }

        if(airplane.hp<=0)
            isAct = false;
    }
}
