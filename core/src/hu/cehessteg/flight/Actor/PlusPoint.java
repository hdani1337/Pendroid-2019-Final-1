package hu.cehessteg.flight.Actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

import static hu.cehessteg.flight.Stage.MenuStage.trebuc;

public class PlusPoint extends MyLabel {

    public static AssetList assetList;
    static {
        assetList = new AssetList();
        assetList.addFont(trebuc, trebuc, 120, Color.WHITE, AssetList.CHARS);
    }

    public PlusPoint(MyGame game, OneSpriteStaticActor actor, String point) {
        super(game, point + " coin", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE));
        setFontScale(0.25f);
        setAlignment(0);
        setPosition(actor.getX() + actor.getWidth()/2 - this.getWidth()/2, actor.getY() + actor.getHeight()*0.75f);
    }

    @Override
    public void init() {

    }

    private float alpha = 1;

    @Override
    public void act(float delta) {
        super.act(delta);
        if(alpha > 0.01) alpha -= 0.01;
        else alpha = 0;

        if(getColor().a != alpha) {
            setColor(1, 1, 1, alpha);
            setY(getY()+1);
        }
        else{
            remove();
        }
    }
}
