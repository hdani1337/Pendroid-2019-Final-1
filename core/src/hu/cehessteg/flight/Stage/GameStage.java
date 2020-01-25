package hu.cehessteg.flight.Stage;



import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.ArrayList;

import hu.cehessteg.flight.Actor.Airplane;
import hu.cehessteg.flight.Actor.Bomb;
import hu.cehessteg.flight.Actor.Bullet;
import hu.cehessteg.flight.Actor.Cloud;
import hu.cehessteg.flight.Actor.Enemy;
import hu.cehessteg.flight.Actor.Explosion;
import hu.cehessteg.flight.Actor.Sky;
import hu.cehessteg.flight.FlightGame;
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
        assetList.collectAssetDescriptor(Explosion.class,assetList);
        assetList.collectAssetDescriptor(Bomb.class,assetList);
        assetList.addFont(trebuc, trebuc, 30, Color.WHITE, AssetList.CHARS);
    }

    private Airplane airplane;
    private Sky sky;
    private Enemy enemy;
    public static boolean isAct;
    public static boolean isShoot;
    public static boolean isBomb;
    private ArrayList<Cloud> clouds;
    private ArrayList<Bullet> bullets;
    private ArrayList<Bomb> bombs;
    private MyLabel enemyHP;
    private MyLabel playerHP;

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
        bombs = new ArrayList<>();
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
        airplane.setX(250 - airplane.getWidth()/2);
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

        addedExplosion = false;
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
        if (isAct) {//Az isAct változó false lesz, ha a játékos veszít, így ezek nem futnak le feleslegesen
            movePlayer();//A repülő mozgatása
            playerOverlapsEnemy();//Ha a játékos nekimegy az ellenfélnek
            bulletOverlapsEnemy();//Megnézzük, melyik golyó találta el az ellenfelet
            bombOverlapsEnemy();//Megnézzük, melyik bomba találja el az ellenfelet
            playerShoot();//A játékos lő
            playerBomb();//A játékos bombázik
        }

        playerDies();//Ha játékos meghal, ezt folyamatosan vizsgáljuk
    }

    private boolean addedExplosion;//Volt-e már robbanás
    private void playerDies()
    {
        if(airplane.hp<=0) {
            isAct = false;
            if(!addedExplosion) {
                Explosion explosion = new Explosion(game, null);
                explosion.setPosition(airplane.getX(), airplane.getY() + airplane.getHeight() / 2 - explosion.getHeight() / 2);
                addActor(explosion);
                airplane.setVisible(false);
                playerHP.setVisible(false);
                addedExplosion = true;
                if(game instanceof FlightGame) {
                    if(((FlightGame) game).penz >= 5) ((FlightGame) game).setPenz(((FlightGame) game).getPenz() - 5);
                    else ((FlightGame) game).setPenz(0);
                }
            }
        }
    }

    private void bulletOverlapsEnemy()
    {
        for (Bullet bullet : bullets)
        {
            if(overlaps(bullet, enemy))
            {
                enemy.hp -= bullet.damage;
                bullet.remove();
                if(enemy.hp <= 0) {
                    addActor(new Explosion(game, enemy));
                    enemy.replace();
                    if(game instanceof FlightGame)((FlightGame) game).setPenz(((FlightGame) game).getPenz() + 2);
                }
            }
        }
    }

    private void bombOverlapsEnemy()
    {
        for (Bomb bomb : bombs)
        {
            if(overlaps(bomb, enemy))
            {
                enemy.hp = 0;
                bomb.remove();
                addActor(new Explosion(game, enemy));
                enemy.replace();
                if(game instanceof FlightGame)((FlightGame) game).setPenz(((FlightGame) game).getPenz() + 2);
            }
        }
    }

    private void playerOverlapsEnemy()
    {
        if (overlaps(airplane, enemy)) {
            airplane.hp -= Math.random() * 20;
            addActor(new Explosion(game, enemy));
            enemy.replace();
            if(game instanceof FlightGame)((FlightGame) game).setPenz(((FlightGame) game).getPenz() + 2);
        }
    }

    private float prevY;//A játékos előző pozíciója
    private void movePlayer()
    {
        if(prevY != HudStage.planeY) {
            airplane.setY(HudStage.planeY - airplane.getHeight() / 2);
            airplane.setRotation(((airplane.getY() / getViewport().getWorldHeight()) - 0.5f) * 90);
            prevY = HudStage.planeY;
        }
    }

    private void playerShoot()
    {
        if(isShoot)
        {
            shoot();
            isShoot = false;
        }
    }

    private void playerBomb()
    {
        if(isBomb)
        {
            airplane.bomb(this);
            isBomb = false;
        }
    }
}