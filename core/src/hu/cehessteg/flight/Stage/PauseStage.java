package hu.cehessteg.flight.Stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hu.cehessteg.flight.FlightGame;
import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

import static hu.cehessteg.flight.Stage.GameOverStage.BLANK_TEXTURE;
import static hu.cehessteg.flight.Stage.GameStage.isAct;
import static hu.cehessteg.flight.Stage.GameStage.isDead;
import static hu.cehessteg.flight.Stage.MenuStage.trebuc;

public class PauseStage extends MyStage {

    public static AssetList assetList;
    static {
        assetList = new AssetList();
        assetList.addFont(trebuc, trebuc, 100, Color.WHITE, AssetList.CHARS);
        assetList.addTexture(BLANK_TEXTURE);
    }

    private MyLabel resume;
    private MyLabel menu;
    private MyLabel text;
    private OneSpriteStaticActor black;

    public PauseStage(MyGame game) {
        super(new ResponseViewport(900), game);
        assignment();
        setPositions();
        addListeners();
    }

    private void assignment()
    {
        text = new MyLabel(game, "Megállítva", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setAlignment(0);
                setColor(1,1,1,0);
                setPosition(getViewport().getWorldWidth()/2-this.getWidth()/2, getViewport().getWorldHeight()*0.6f);
            }
        };

        resume = new MyLabel(game,"Folytatás", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setFontScale(0.75f);
                setAlignment(0);
                setColor(1,1,1,0);
            }
        };

        menu = new MyLabel(game,"Kilépés a menübe", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                setFontScale(0.75f);
                setAlignment(0);
                setColor(1,1,1,0);
            }
        };

        black = new OneSpriteStaticActor(game, BLANK_TEXTURE){
            @Override
            public void init() {
                super.init();
                setAlpha(0);
            }
        };
    }

    private void setPositions()
    {
        black.setPosition(0,0);
        black.setSize(getViewport().getWorldWidth(), getViewport().getWorldHeight());

        resume.setPosition(getViewport().getWorldWidth()/2-resume.getWidth()/2, getViewport().getWorldHeight()*0.35f);
        menu.setPosition(getViewport().getWorldWidth()/2-menu.getWidth()/2, getViewport().getWorldHeight()*0.2f);
    }

    private void addListeners()
    {
        resume.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                isAct = true;
            }
        });

        menu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(game instanceof FlightGame)
                    ((FlightGame) game).saveCoins();
                game.setScreenBackByStackPopWithPreloadAssets(new MyPreLoadingStage(game));
            }
        });
    }

    private void addActors()
    {
        addActor(black);
        addActor(text);
        addActor(resume);
        addActor(menu);
    }

    private float alpha = 0;
    private boolean addedActors = false;

    @Override
    public void act(float delta) {
        super.act(delta);

        if (!isAct && !isDead){
            //HA NEM MEGY A JÁTÉKMENEK ÉS NEM VESZTETT A JÁTÉKOS
            if(!addedActors){
                //HA MÉG NEM ADTUK HOZZÁ AZ ACTOROKAT A STAGEHEZ AKKOR ADJUK HOZZÁ, TÖBBSZÖR NE
                addActors();
                addedActors = true;
            }

            //ÁTTŰNÉSSEL BEJÖNNEK A GOMBOK
            if(alpha < 0.95)
            {
                black.setAlpha(alpha * 0.4f);
                resume.setColor(1,1,1,alpha);
                menu.setColor(1,1,1,alpha);
                text.setColor(1,1,1,alpha);
                alpha += 0.05;
            }else alpha = 1;
        }
        else
        {
            //HA FOLYTATÓDIK A JÁTÉKMENET, AKKOR ÁTTŰNÉSSEL KIMENNEK A GOMBOK
            if(alpha > 0)
            {
                black.setAlpha(alpha * 0.4f);
                resume.setColor(1,1,1,alpha);
                menu.setColor(1,1,1,alpha);
                text.setColor(1,1,1,alpha);
                alpha -= 0.05;
            }else {
                //HA KIMENTEK A GOMBOK EL IS TÁVOLÍTJUK ŐKET
                alpha = 0;
                black.remove();
                resume.remove();
                menu.remove();
                text.remove();
                addedActors = false;
            }
        }
    }
}
