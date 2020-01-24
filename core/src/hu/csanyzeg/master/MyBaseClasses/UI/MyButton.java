package hu.csanyzeg.master.MyBaseClasses.UI;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import hu.csanyzeg.master.MyBaseClasses.Game.InitableInterface;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.IZindex;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.IZindexCode;

/**
 * Created by tuskeb on 2016. 09. 30..
 */
public abstract class MyButton extends TextButton implements InitableInterface, IZindex {

    protected MyGame game;

    public MyButton(MyGame game, String text, TextButtonStyle style) {
        super(text, style);
        this.game = game;
        init();
    }

    public MyGame getGame() {
        return game;
    }


    protected int zIndex = 0;

    @Override
    public int getZIndex() {
        return zIndex;
    }

    @Override
    public boolean setZIndex(int index) {
        this.zIndex = index;
        return IZindexCode.setZIndex(index, this);
    }


}
