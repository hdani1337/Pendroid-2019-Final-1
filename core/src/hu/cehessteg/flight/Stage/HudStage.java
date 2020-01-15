package hu.cehessteg.flight.Stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

import static hu.cehessteg.flight.Stage.MenuStage.trebuc;

public class HudStage extends MyStage {
    public static AssetList assetList = new AssetList();
    static {
        assetList.addFont(trebuc, trebuc, 120, Color.WHITE, AssetList.CHARS);
    }

    MyLabel down;
    MyLabel up;

    private enum Direction
    {
        DOWN,
        UP,
        NULL

        /**
         * Az enumokba különböző állapotokat felsorolhatsz
         * Utána ezekből az enumokból tudsz változókat származtatni, tehát:
         *  - Azokat az értékeket adhatod meg nekik, amiket ide felsoroltál
         *  - Ezeket az értékeket folyamatosan letudod kérdezni if vagy switch használatával
         *  - Átláthatóbbá teszi a kódot, például nem true-false párosokkal vagy minden lehetőséghez egy számot rendelve kell elágazásokat csinálnod
         *  - Menőbb leszel, hogy már ezt is tudod
         * */
    }

    private Direction direction;//Ezt az irányt az actban figyeljük folyamatosan

    public HudStage(MyGame game) {
        super(new ResponseViewport(900), game);

        direction = Direction.NULL;//Kezdetben lenullázzuk az irányt, ekkor nem megy semerre se a repülőgép

        down = new MyLabel("Le", new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            //Új MyLabel "Le" szöveggel
            @Override
            public void init() {
                addListener(controlListener(Direction.DOWN));//Új DragListener hozzáadása "LE" iránnyal
            }
        };

        up = new MyLabel("Fel",  new Label.LabelStyle(game.getMyAssetManager().getFont(trebuc), Color.WHITE)) {
            @Override
            public void init() {
                addListener(controlListener(Direction.UP));//Új DragListener hozzáadása "FEL" iránnyal
            }
        };

        down.setPosition(getViewport().getWorldWidth()-250, getViewport().getWorldHeight()*0.2f);
        up.setPosition(getViewport().getWorldWidth()-250, getViewport().getWorldHeight()*0.7f);

        addActor(down);
        addActor(up);
    }

    /**
     * @param d Az az irány, amerre menni fog a repülő miközben nyomva tartjuk a gombot
     * */
    private DragListener controlListener(Direction d)
    {//Ne kelljen annyiszor új listenereket létrehozni minden labelnél, egyszerűbb egy függvény
        switch (d)
        {
            case DOWN:
            {
                //Ha a lefelé irányítónak kell, ezt kapja meg
                return new DragListener()
                {
                    //Ekkor tartjuk nyomva
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        direction = Direction.DOWN;//Az irány "LE" értékre vált
                        return super.touchDown(event, x, y, pointer, button);
                    }

                    //Ekkor engedjük fel
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        direction = Direction.NULL;//Az irány "NULLA" értékre vált
                        super.touchUp(event, x, y, pointer, button);
                    }

                };
            }

            case UP:
            {
                //Ha a felfelé irányítónak kell, ezt kapja meg
                return new DragListener()
                {

                    //Ekkor tartjuk nyomva
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        direction = Direction.UP;//Az irány "FEL" értékre vált
                        return super.touchDown(event, x, y, pointer, button);
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        direction = Direction.NULL;//Az irány "NULLA" értékre vált
                        super.touchUp(event, x, y, pointer, button);
                    }

                };
            }

            default:
            {
                //Egyéb esetben egy sima DragListenert kap
                return new DragListener();
            }
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        switch (direction)
        {//Folyamatosan figyeljük az irányt
            case UP: {
                //Ha "FEL", akkor felfele mozdul a gép egészen a ,,plafonig"
                GameStage.moveUp();
                break;
            }

            case DOWN: {
                //Ha "LE", akkor lefele mozdul a gép egészen a ,,padlóig"
                GameStage.moveDown();
                break;
            }

            case NULL: {
                //Ha "NULLA", akkor semmit se csináljon
                break;
            }
        }
    }
}
