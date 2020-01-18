package hu.cehessteg.flight.Stage;



import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

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
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

import static hu.cehessteg.flight.Stage.MenuStage.trebuc;
import static hu.csanyzeg.master.MyBaseClasses.Scene2D.MyActor.overlaps;

public class GameStage extends MyStage {
    public static AssetList assetList = new AssetList();
    static {
        assetList.collectAssetDescriptor(Airplane.class, assetList);
        assetList.collectAssetDescriptor(Cloud.class, assetList);
        assetList.collectAssetDescriptor(Sky.class, assetList);
        assetList.collectAssetDescriptor(Enemy.class,assetList);
        assetList.collectAssetDescriptor(Bullet.class,assetList);
        assetList.addFont(trebuc, trebuc, 30, Color.WHITE, AssetList.CHARS);
    }

    private Airplane airplane;
    private Sky sky;
    private Enemy enemy;
    public static boolean isAct;
    public static boolean isShoot;
    private ArrayList<Cloud> clouds;
    private ArrayList<Bullet> bullets;
    private static Direction direction;
    private MyLabel enemyHP;
    private MyLabel playerHP;

    private enum Direction
    {
        UP, DOWN, NULL
    }

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
        direction = Direction.NULL;
        airplane = new Airplane(game);
        enemy = new Enemy(game,getViewport());
        clouds = new ArrayList<>();
        bullets = new ArrayList<>();
        for (int i = 0; i < 18; i++) clouds.add(new Cloud(game, getViewport()));
        setHpLabels();
    }

    private void setHpLabels()
    {
        enemyHP = new MyLabel(game, "UNDEFINED", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setAlignment(0);
                setFontScale(0.3f);
            }

            @Override
            public void act(float delta) {
                super.act(delta);
                setX(enemy.getX() + enemy.getWidth()/2 - this.getWidth()/2);
                setY(enemy.getY() + enemy.getHeight()*0.4f);
                setText(enemy.hp);
            }
        };

        playerHP = new MyLabel(game, "UNDEFINED", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setAlignment(0);
                setFontScale(0.3f);
            }

            @Override
            public void act(float delta) {
                super.act(delta);
                setX(airplane.getX() + airplane.getWidth()/2 - this.getWidth()/2);
                setY(airplane.getY() + airplane.getHeight()*0.4f);
                setText(airplane.hp);
            }
        };
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
        airplane.setZIndex(7);
        addActor(playerHP);
        playerHP.setZIndex(8);
        addActor(enemy);
        addActor(enemyHP);
    }

    public static void moveDown()
    {
        direction = Direction.DOWN;
    }

    public static void moveUp()
    {
        direction = Direction.UP;
    }

    public void shoot()
    {
        airplane.shoot(this);
    }

    public void addBullet(Bullet bullet)
    {
        bullets.add(bullet);
    }

    public void removeBullet(Bullet bullet)
    {
        bullets.remove(bullet);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (isAct) {
            switch (direction)
            {
                case UP:{
                    if(airplane.getY()+airplane.getHeight() < 900)//Így nem megy ki a képből
                        airplane.setY(airplane.getY()+15); //15 pixellel feljebb helyezés
                    direction = Direction.NULL;
                    break;
                }

                case DOWN:{
                    if(airplane.getY() > 0) //Így nem megy ki a képből
                        airplane.setY(airplane.getY()-15); //15 pixellel lejjebb helyezés
                    direction = Direction.NULL;
                    break;
                }

                case NULL: {
                    break;
                }
            }

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
        }

        if(airplane.hp<=0)
            isAct = false;
    }
}
