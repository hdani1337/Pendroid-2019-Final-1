package hu.cehessteg.flight.Actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.Viewport;

import hu.cehessteg.flight.Stage.GameStage;
import hu.cehessteg.flight.Stage.InfoStage;
import hu.cehessteg.flight.Stage.MenuStage;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

import static hu.cehessteg.flight.Stage.GameStage.isAct;

public class Cloud extends OneSpriteStaticActor {
    public static final String cloud3 = "sky/cloud3.png";
    public static final String cloud4 = "sky/cloud4.png";

    public static AssetList assetList = new AssetList();
    static
    {
        assetList.addTexture(cloud3);
        assetList.addTexture(cloud4);
    }

    private Viewport viewport;
    private float origWidth;

    public Cloud(MyGame game, Viewport viewport) {
        super(game, Math.random() > 0.5f ? cloud3 : cloud4);
        this.viewport = viewport;
        this.origWidth = getWidth();
        setAlpha(0);
        setSize(getWidth()*0.4f, getHeight()*0.4f);
        setPosition((float) Math.random() * viewport.getWorldWidth(), (float) Math.random()*viewport.getWorldHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(getStage() != null){
            if(getStage() instanceof GameStage) {
                if (isAct)
                    moveInGame();
            }
            else if (getStage() instanceof InfoStage){
                moveInGame();
            }
            else if (getStage() instanceof MenuStage){
                moveInMenu();
            }
        }
    }

    float alphaEmpty = 0;
    float alphaFull = 1;

    private void fadeIn()
    {
        if(alphaEmpty < 0.99) {
            alphaEmpty += 0.01;
            setAlpha(alphaEmpty);
        }
        else alphaEmpty = 1;
    }

    private void fadeOut()
    {
        if(alphaFull > 0.02){
            alphaFull -= 0.01;
            setAlpha(alphaFull);
        }
        else {
            alphaFull = 0;
            this.remove();
        }
    }

    public void moveInGame()
    {
        fadeIn();
        if (getX() > -getWidth()) setX(getX() - 5);
        else {
            setX((float) Math.random() * 500 + viewport.getWorldWidth());
            setY((float) Math.random() * viewport.getWorldHeight());
        }
    }

    public void moveInMenu()
    {
        if(getWidth() <= origWidth * 1.25) fadeIn();
        else fadeOut();
        setSize(getWidth()*1.005f, getHeight()*1.005f);

        if(Gdx.input.getAccelerometerY() * 5 > 0 && getRotation() < (Gdx.input.getAccelerometerY() * 5)) setRotation(getRotation() + 0.5f);
        else if (Gdx.input.getAccelerometerY() * 5 < 0 && getRotation() > (Gdx.input.getAccelerometerY() * 5)) setRotation(getRotation() - 0.5f);

        setX(getX()-3);
    }
}
