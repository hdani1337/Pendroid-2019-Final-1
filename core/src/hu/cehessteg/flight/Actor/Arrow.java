package hu.cehessteg.flight.Actor;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hu.cehessteg.flight.FlightGame;
import hu.cehessteg.flight.Stage.OptionsStage;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class Arrow extends OneSpriteStaticActor {

    private OptionsStage optionsStage;
    private String hash;

    public enum ArrowModes{
        DIFFICULTY, SKIN
    }

    ArrowModes arrowMode;

    public Arrow(MyGame game, String hash, OptionsStage optionsStage, ArrowModes arrowMode) {
        super(game, hash);
        this.hash = hash;
        this.optionsStage = optionsStage;
        this.arrowMode = arrowMode;
        addListener();
    }

    private void addListener(){
        this.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(game != null) {
                    if (game instanceof FlightGame) {
                        if (hash.equals(OptionsStage.NYILBAL_TEXUTE)) {

                            if(arrowMode == ArrowModes.DIFFICULTY) {
                                if (((FlightGame) game).getDifficulty() != 1) {
                                    ((FlightGame) game).setDifficulty(((FlightGame) game).getDifficulty() - 1);
                                } else ((FlightGame) game).setDifficulty(1);
                            }
                            else if(arrowMode == ArrowModes.SKIN){
                                if(optionsStage.id != 1) optionsStage.id--;
                                else optionsStage.id = 1;
                                ((FlightGame) game).setSkinID(optionsStage.id);
                            }

                        } else if (hash.equals(OptionsStage.NYILJOBB_TEXUTE)) {

                            if(arrowMode == ArrowModes.DIFFICULTY) {
                                if (((FlightGame) game).getDifficulty() != 3) {
                                    ((FlightGame) game).setDifficulty(((FlightGame) game).getDifficulty() + 1);
                                } else ((FlightGame) game).setDifficulty(3);
                            }

                            else if(arrowMode == ArrowModes.SKIN){
                                if(optionsStage.id != optionsStage.maxId) optionsStage.id++;
                                else optionsStage.id = optionsStage.maxId;
                                ((FlightGame) game).setSkinID(optionsStage.id);
                            }
                        }
                    }
                }
            }
        });
    }
}
