package hu.cehessteg.flight.Stage;



import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;

import hu.cehessteg.flight.Actor.Airplane;
import hu.cehessteg.flight.Actor.Bomb;
import hu.cehessteg.flight.Actor.Bullet;
import hu.cehessteg.flight.Actor.Cloud;
import hu.cehessteg.flight.Actor.Enemy;
import hu.cehessteg.flight.Actor.Explosion;
import hu.cehessteg.flight.Actor.Fuel;
import hu.cehessteg.flight.Actor.Health;
import hu.cehessteg.flight.Actor.House;
import hu.cehessteg.flight.Actor.PlusPoint;
import hu.cehessteg.flight.Actor.Shelter;
import hu.cehessteg.flight.Actor.Sky;
import hu.cehessteg.flight.FlightGame;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimer;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimerListener;
import hu.csanyzeg.master.MyBaseClasses.Timers.Timer;

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
        assetList.collectAssetDescriptor(House.class,assetList);
        assetList.collectAssetDescriptor(Shelter.class,assetList);
        assetList.addMusic(WIND_SOUND);
        assetList.addFont(trebuc, trebuc, 30, Color.WHITE, AssetList.CHARS);
    }

    private Airplane airplane;//REPCSI
    private Sky sky;//ÉGBOLT
    private Fuel fuel;//ÜZEMANYAG

    //STATIKUS VÁLTOZÓK
    public static boolean isAct;
    public static boolean isShoot;
    public static boolean isBomb;
    public static boolean isDead;

    //LISTÁK, NEVEIK GONDOLOM EGYÉRTELMŰEK
    private ArrayList<Cloud> clouds;
    private ArrayList<Bullet> bullets;
    private ArrayList<Bomb> bombs;
    private ArrayList<Enemy> enemies;
    private ArrayList<Health> enemyHPs;
    private ArrayList<House> houses;
    private ArrayList<Airplane> friends;
    private ArrayList<Shelter> shelters;

    private Health playerHP;//JÁTÉKOS ÉLETJELZŐ CSÍKJA

    public GameStage(MyGame game) {
        super(new ResponseViewport(900), game);
        assignment();
        setSizesAndPositions();
        addActors();
        addTimers();

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
        airplane.setFriendlyMode(false);
        fuel = new Fuel(game);

        clouds = new ArrayList<>();
        enemies = new ArrayList<>();
        enemyHPs = new ArrayList<>();
        bullets = new ArrayList<>();
        bombs = new ArrayList<>();
        houses = new ArrayList<>();
        friends = new ArrayList<>();
        shelters = new ArrayList<>();

        for (int i = 0; i < 18; i++)
            clouds.add(new Cloud(game, getViewport()));

        for (int i = 0; i < 2; i++) {
            friends.add(new Airplane(game));
            friends.get(i).setTexture(i+2);
            friends.get(i).setFriendlyMode(true);
        }

        if(game instanceof FlightGame)
            for (int i = 0; i < ((FlightGame)game).getDifficulty()*2; i++) enemies.add(new Enemy(game, getViewport()));

        setHpLabels();
    }

    private void addTimers(){
        final GameStage tempStage = this;
        addTimer(new TickTimer(1.4f, true, new TickTimerListener(){
            @Override
            public void onTick(Timer sender, float correction) {
                super.onTick(sender, correction);
                houses.add(new House(game, tempStage));
                addActor(houses.get(houses.size()-1));
            }
        }));
        addTimer(new TickTimer(2.1f, true, new TickTimerListener(){
            @Override
            public void onTick(Timer sender, float correction) {
                super.onTick(sender, correction);
                shelters.add(new Shelter(game, tempStage));
                addActor(shelters.get(shelters.size()-1));
            }
        }));
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
                setHealth(enemy.hp, false);
                }
            });
        }

        playerHP = new Health(game) {
            @Override
            public void act(float delta) {
                super.act(delta);
                setX(airplane.getX() + airplane.getWidth()/2 - 100);
                setY(airplane.getY() + airplane.getHeight()*0.9f);
                setHealth(airplane.hp, true);
            }
        };
    }

    private void setSizesAndPositions()
    {
        /**SIZES**/
        sky.setSize(getViewport().getWorldWidth(),getViewport().getWorldHeight());
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
        for (Airplane friend : friends) addActor(friend);
        addActor(fuel);

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

    public void removeHouse(House house){
        houses.remove(house);
    }

    public void removeShelter(Shelter shelter){
        shelters.remove(shelter);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        /**
         * ÜTKŐZÉSVIZSGÁLATOK
         * */
        if (isAct) {//Az isAct változó false lesz, ha a játékos veszít, így ezek nem futnak le feleslegesen
            movePlayer();//A repülő mozgatása
            playerOverlapsEnemy();//Ha a játékos nekimegy az ellenfélnek
            bulletOverlapsEnemy();//Megnézzük, melyik golyó találta el az ellenfelet
            bombOverlapsEnemy();//Megnézzük, melyik bomba találja el az ellenfelet
            bombOverlapsHouse();//Megnézzük, melyik bomba találja el a házat
            playerOverlapsHouse();//Ha a játékos nekimegy egy háznak
            playerShoot();//A játékos lő
            playerBomb();//A játékos bombázik
            friendFrontOfEnemy();//Barát az ellenféllel egyvonalban van
            friendOverlapsBomb();//Barátot eltalálja a bomba
            friendOverlapsPlayer();//Barát ütközik a játékossal
            friendOverlapsEnemy();//Barát ütközik az ellenféllel
            repeatMusic();//A zene ismétlése kicsit cselesen
            bombOverlapsShelter();//Bombával eltaláljuk a katonai bunkert
            playerOverlapsShelter();//Játékos eltalálja a katonai bunkert
            playerOverlapsFuel();//Játékos eltalálja a katonai bunkert
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
                addActor(new PlusPoint(game,airplane,"-5"));
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
                        addActor(new PlusPoint(game,enemy,"+3"));
                        enemy.replace();
                        if (game instanceof FlightGame)
                            ((FlightGame) game).setPenz(((FlightGame) game).getPenz() + 3);
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
                    addActor(new PlusPoint(game,enemy,"+5"));
                    enemy.replace();
                    if (game instanceof FlightGame)
                        ((FlightGame) game).setPenz(((FlightGame) game).getPenz() + 5);
                }
            }
        }
    }

    private void bombOverlapsHouse(){
        for (Bomb bomb : bombs){
            for (House house : houses){
                if(overlaps(bomb, house)){
                    addActor(new Explosion(game, house));
                    addActor(new PlusPoint(game,house,"-5"));
                    bomb.remove();
                    if (game instanceof FlightGame)
                        ((FlightGame) game).setPenz(((FlightGame) game).getPenz() - 5);
                    house.remove();
                    break;
                }
            }
        }
    }

    private void bombOverlapsShelter(){
        for (Bomb bomb : bombs){
            for (Shelter shelter : shelters){
                if(overlaps(bomb, shelter)){
                    addActor(new Explosion(game, shelter));
                    addActor(new PlusPoint(game,shelter,"+3"));
                    bomb.remove();
                    if (game instanceof FlightGame)
                        ((FlightGame) game).setPenz(((FlightGame) game).getPenz() + 3);
                    shelter.remove();
                    break;
                }
            }
        }
    }

    private void playerOverlapsShelter(){
        for (Shelter shelter : shelters){
            if(overlaps(airplane, shelter)){
                addActor(new Explosion(game, shelter));
                airplane.hp -= Math.random()*30;
                addActor(new Explosion(game, shelter));
                addActor(new PlusPoint(game,shelter,"+2"));
                if (game instanceof FlightGame)
                    ((FlightGame) game).setPenz(((FlightGame) game).getPenz() + 2);
                shelter.remove();
                break;
            }
        }
    }

    private void playerOverlapsHouse(){
        for (House house : houses){
            if(overlaps(airplane, house)){
                addActor(new Explosion(game, house));
                airplane.hp -= Math.random()*50;
                addActor(new Explosion(game, house));
                addActor(new PlusPoint(game,house,"-2"));
                if (game instanceof FlightGame)
                    ((FlightGame) game).setPenz(((FlightGame) game).getPenz() - 2);
                house.remove();
                break;
            }
        }
    }

    private void playerOverlapsEnemy()
    {
        for (Enemy enemy : enemies) {
            if (overlaps(airplane, enemy)) {
                airplane.hp -= Math.random() * 20;
                addActor(new Explosion(game, enemy));
                addActor(new PlusPoint(game,enemy,"+3"));
                enemy.replace();
                if (game instanceof FlightGame)
                    ((FlightGame) game).setPenz(((FlightGame) game).getPenz() + 3);
            }
        }
    }

    private float pElapsed;

    private void friendFrontOfEnemy(){
        for (Airplane friend : friends){
            for (Enemy enemy : enemies){
                if(friend.getY() >= enemy.getY() && friend.getY() <= enemy.getY()+enemy.getHeight()*0.6f){
                    if(elapsedTime > pElapsed + 0.2f) {
                        friend.shoot(this);
                        pElapsed = elapsedTime;
                    }
                }
            }
        }
    }

    private void friendOverlapsBomb(){
        for (Airplane friend : friends){
            for (Bomb bomb : bombs){
                if(overlaps(friend, bomb)){
                    if (game instanceof FlightGame)
                        ((FlightGame) game).setPenz(((FlightGame) game).getPenz() - 5);
                    addActor(new Explosion(game, friend));
                    addActor(new PlusPoint(game,friend,"-5"));
                    friend.replace();
                    bomb.remove();
                }
            }
        }
    }

    private void friendOverlapsEnemy(){
        for (Airplane friend : friends){
            for (Enemy enemy : enemies){
                if(overlaps(friend, enemy)){
                    if (game instanceof FlightGame)
                        ((FlightGame) game).setPenz(((FlightGame) game).getPenz() + 2);
                    addActor(new Explosion(game, friend));
                    addActor(new PlusPoint(game,enemy,"+2"));
                    friend.replace();
                    enemy.replace();
                }
            }
        }
    }

    private void friendOverlapsPlayer(){
        for (Airplane friend : friends){
            if(overlaps(friend, airplane)){
                if (game instanceof FlightGame)
                    ((FlightGame) game).setPenz(((FlightGame) game).getPenz() - 2);
                addActor(new Explosion(game, friend));
                addActor(new PlusPoint(game,friend,"-2"));
                friend.replace();
                airplane.hp -= Math.random() * 20;
            }
        }
    }

    private void playerOverlapsFuel(){
        if(overlaps(airplane, fuel)){
            fuel.replace();
            if(100 + (airplane.level-1)*15 >= airplane.hp+20) {
                airplane.hp += 20;
            }
            else airplane.hp = 100 + (airplane.level-1)*15;
        }
    }

    private float prevY;//A játékos előző pozíciója
    private float prevX;//A játékos előző pozíciója
    private void movePlayer()
    {
        if(prevY != HudStage.planeY) {
            airplane.setY(HudStage.planeY - airplane.getHeight() / 2);
            airplane.setRotation((((airplane.getY()+airplane.getHeight()/2) / getViewport().getWorldHeight()) - 0.5f) * 60);
            prevY = HudStage.planeY;
        }

        if(prevX != HudStage.planeX) {
            airplane.setX(HudStage.planeX - airplane.getWidth()/2);
            prevX = HudStage.planeX;
        }

        airplane.setRotateBack(HudStage.isRotateBack);
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
