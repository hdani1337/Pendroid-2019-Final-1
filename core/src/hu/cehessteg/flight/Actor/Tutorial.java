package hu.cehessteg.flight.Actor;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;

public class Tutorial extends OneSpriteStaticActor {

    public static final String TURORIAL_TEXTURE = "other/jelmagyarazat.png";

    public static AssetList assetList;
    static {
        assetList = new AssetList();
        assetList.addTexture(TURORIAL_TEXTURE);
    }

    public Tutorial(MyGame game) {
        super(game, TURORIAL_TEXTURE);
        setAlpha(0);
        clicked = 0;
        if(new ResponseViewport(900).getWorldWidth() < 1600){
            setSize(getWidth()*(new ResponseViewport(900).getWorldWidth()/1600.0f), getHeight()*(new ResponseViewport(900).getWorldWidth()/1600.0f));
            setY(900/2-getHeight()/2);
        }
    }

    float alpha = 0;
    public long clicked;

    private void fadeIn(){
        if(alpha < 0.97) alpha+=0.03;
        else alpha = 1;
        setAlpha(alpha);
    }

    private void fadeOut(){
        if(alpha > 0.03) alpha-=0.03;
        else alpha = 0;
        setAlpha(alpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(clicked % 2 == 1) fadeIn();
        else fadeOut();
    }

    public void clicked(){
        clicked+=1;
    }
}
