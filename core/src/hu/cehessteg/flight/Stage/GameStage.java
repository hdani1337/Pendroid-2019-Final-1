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
import hu.cehessteg.flight.Actor.Health;
import hu.cehessteg.flight.Actor.Sky;
import hu.cehessteg.flight.FlightGame;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

import static hu.cehessteg.flight.Stage.MenuStage.trebuc;
import static hu.cehessteg.flight.Stage.OptionsStage.WIND_SOUND;
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
        assetList.collectAssetDescriptor(Health.class,assetList);
        assetList.addMusic(WIND_SOUND);
        assetList.addFont(trebuc, trebuc, 30, Color.WHITE, AssetList.CHARS);
    }

    private Airplane airplane;
    private Sky sky;
    public static boolean isAct;
    public static boolean isShoot;
    public static boolean isBomb;
    public static boolean isDead;
    private ArrayList<Cloud> clouds;
    private ArrayList<Bullet> bullets;
    private ArrayList<Bomb> bombs;
    private ArrayList<Enemy> enemies;
    private ArrayList<Health> enemyHPs;
    private Health playerHP;

    public GameStage(MyGame game) {
        super(new ResponseViewport(900), game);//Ha lesz Box2D, akkor 900 helyett mondjuk 9 lesz
        assignment();
        setSizesAndPositions();
        addActors();

        if(game instanceof FlightGame){
            if(!((FlightGame)game).isMuted()){
                game.getMyAssetManager().getMusic(WIND_SOUND).play();
            }
        }
    }

    private void assignment()
    {
        isAct = true;
        isShoot = false;
        isDead = false;
        sky = new Sky(game);
        airplane = new Airplane(game);
        clouds = new ArrayList<>();
        enemies = new ArrayList<>();
        enemyHPs = new ArrayList<>();
        bullets = new ArrayList<>();
        bombs = new ArrayList<>();
        for (int i = 0; i < 18; i++) clouds.add(new Cloud(game, getViewport()));
        if(game instanceof FlightGame)
            for (int i = 0; i < ((FlightGame)game).getDifficulty()*2; i++) enemies.add(new Enemy(game, getViewport()));
        setHpLabels();
    }

    private void setHpLabels()
    {
        for (final Enemy enemy : enemies){
            enemyHPs.add(new Health(game) {
                @Override
                public void act(float delta) {
                    super.act(delta);
                setX(enemy.getX() + enemy.getWidth()/2 - 100);
                setY(enemy.getY() + enemy.getHeight()*0.9f);
                setHealth(enemy.hp);
                }
            });
        }

        playerHP = new Health(game) {
            @Override
            public void act(float delta) {
                super.act(delta);
                setX(airplane.getX() + airplane.getWidth()/2 - 100);
                setY(airplane.getY() + airplane.getHeight()*0.9f);
                setHealth(airplane.hp);
            }
        };
    }

    private void setSizesAndPositions()
    {
        /**SIZES**/
        sky.setSize(getViewport().getWorldWidth(),getViewport().getWorldHeight());
        airplane.setSize(airplane.getWidth()*0.2f, airplane.getHeight()*0.2f);
        for (Enemy enemy : enemies) enemy.setSize(enemy.getWidth()*0.2f,enemy.getHeight()*0.2f);

        /**POSITIONS**/
        airplane.setY(getViewport().getWorldHeight()/2-airplane.getHeight()/2);
        airplane.setX(250 - airplane.getWidth()/2);
        for (Enemy enemy : enemies) enemy.setX(-1000);
    }

    private void addActors()
    {
        addActor(sky);
        for (int i = 0; i < clouds.size(); i++) addActor(clouds.get(i));
        addActor(airplane);
        airplane.setZIndex(7);
        addActor(playerHP);
        playerHP.setZIndex(8);
        for (Enemy enemy : enemies) addActor(enemy);
        for (Health enemyHP : enemyHPs) addActor(enemyHP);

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
            repeatMusic();//A zene ismétlése kicsit cselesen
        }
        else{
            if(game instanceof FlightGame) {
                if (!((FlightGame) game).isMuted()) {
                    game.getMyAssetManager().getMusic(WIND_SOUND).stop();
                }
            }
        }

        playerDies();//Ha játékos meghal, ezt folyamatosan vizsgáljuk
    }

    private boolean addedExplosion;//Volt-e már robbanás
    private void playerDies()
    {
        if(airplane.hp<=0) {
            isAct = false;
            isDead = true;
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
            for (Enemy enemy : enemies) {
                if (overlaps(bullet, enemy)) {
                    enemy.hp -= bullet.damage;
                    bullet.remove();
                    if (enemy.hp <= 0) {
                        addActor(new Explosion(game, enemy));
                        enemy.replace();
                        if (game instanceof FlightGame)
                            ((FlightGame) game).setPenz(((FlightGame) game).getPenz() + 2);
                    }
                }
            }
        }
    }

    private void bombOverlapsEnemy()
    {
        for (Bomb bomb : bombs)
        {
            for (Enemy enemy : enemies) {
                if (overlaps(bomb, enemy)) {
                    enemy.hp = 0;
                    bomb.remove();
                    addActor(new Explosion(game, enemy));
                    enemy.replace();
                    if (game instanceof FlightGame)
                        ((FlightGame) game).setPenz(((FlightGame) game).getPenz() + 2);
                }
            }
        }
    }

    private void playerOverlapsEnemy()
    {
        for (Enemy enemy : enemies) {
            if (overlaps(airplane, enemy)) {
                airplane.hp -= Math.random() * 20;
                addActor(new Explosion(game, enemy));
                enemy.replace();
                if (game instanceof FlightGame)
                    ((FlightGame) game).setPenz(((FlightGame) game).getPenz() + 2);
            }
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

    private void repeatMusic()
    {
        if(game instanceof FlightGame) {
            if (!((FlightGame) game).isMuted()) {
                if (game.getMyAssetManager().getMusic(WIND_SOUND).getPosition() >= 8.9)
                    game.getMyAssetManager().getMusic(WIND_SOUND).setPosition(0);
                if (!game.getMyAssetManager().getMusic(WIND_SOUND).isPlaying())
                    game.getMyAssetManager().getMusic(WIND_SOUND).play();
            }
        }
    }
}
