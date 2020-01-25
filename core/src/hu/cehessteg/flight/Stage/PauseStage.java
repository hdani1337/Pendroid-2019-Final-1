package hu.cehessteg.flight.Stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

import static hu.cehessteg.flight.Stage.GameOverStage.BLANK_TEXTURE;
import static hu.cehessteg.flight.Stage.GameStage.isAct;
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
    private OneSpriteStaticActor black;

    public PauseStage(MyGame game) {
        super(new ResponseViewport(900), game);
        assignment();
        setPositions();
        addListeners();
        addActors();
    }

    private void assignment()
    {
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

        resume.setPosition(getViewport().getWorldWidth()/2-resume.getWidth()/2, getViewport().getWorldHeight()/2);
        menu.setPosition(getViewport().getWorldWidth()/2-menu.getWidth()/2, resume.getY() - resume.getHeight()*1.5f);
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
                game.setScreenBackByStackPop();
            }
        });
    }

    private void addActors()
    {
        addActor(black);
        addActor(resume);
        addActor(menu);
    }

    private float alpha = 0;
    private boolean addedActors = false;

    @Override
    public void act(float delta) {
        super.act(delta);

        if (!isAct){
            if(!addedActors){
                addActor(black);
                addActor(resume);
                addActor(menu);
                addedActors = true;
            }

            if(alpha < 0.95)
            {
                black.setAlpha(alpha * 0.4f);
                resume.setColor(1,1,1,alpha);
                menu.setColor(1,1,1,alpha);
                alpha += 0.05;
            }else alpha = 1;
        }
        else
        {
            if(alpha > 0)
            {
                black.setAlpha(alpha * 0.4f);
                resume.setColor(1,1,1,alpha);
                menu.setColor(1,1,1,alpha);
                alpha -= 0.05;
            }else {
                alpha = 0;
                black.remove();
                resume.remove();
                menu.remove();
                addedActors = false;
            }
        }
    }
}
