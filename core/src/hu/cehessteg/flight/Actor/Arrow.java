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
        DIFFICULTY, SKIN, NULL
    }

    ArrowModes arrowMode;

    public Arrow(MyGame game, String hash, OptionsStage optionsStage, ArrowModes arrowMode) {
        super(game, hash);
        this.hash = hash;
        if(optionsStage != null) this.optionsStage = optionsStage;
        this.arrowMode = arrowMode;
        addListener();
    }

    //EZ GYÖNYÖRŰ
    private void addListener(){
        if(optionsStage != null) {
            //STAGE NULLKEZELÉS
            if (arrowMode != ArrowModes.NULL) {
                //HA VALAMIT KELL FIGYELNIE CSAK AKKOR KAPJON LISTENERT
                this.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        if (game != null) {
                            //GAME NULLKEZELÉS
                            if (game instanceof FlightGame) {
                                //A GAMENEK A FLIGHTGANE PÉLDÁNYÁNAK KELL LENNIE
                                if (hash.equals(OptionsStage.NYILBAL_TEXUTE)) {
                                    //HA Ő A BALOLDALI NYÍL

                                    if (arrowMode == ArrowModes.DIFFICULTY) {
                                        //HA A NEHÉZSÉGET KELL ÁLLÍTANIA

                                        //HA A NEHÉZSÉG NEM ÉRI EL A LEGKISEBBET (1 - KÖNNYŰ) AKKOR CSÖKKENTSE ÉS MENTSE
                                        if (((FlightGame) game).getDifficulty() != 1)
                                            ((FlightGame) game).setDifficulty(((FlightGame) game).getDifficulty() - 1);

                                        //HA ELÉRI AKKOR MARADJON 1 ÉS MENTSE
                                        else ((FlightGame) game).setDifficulty(1);

                                    } else if (arrowMode == ArrowModes.SKIN) {
                                        //HA A KINÉZETET KELL ÁLLÍTANIA

                                        //HA NEM ÉRI EL AZ 1-ET AKKOR CSÖKKENTSE
                                        if (optionsStage.id != 1) optionsStage.id--;

                                        //HA ELÉRI AKKOR MARADJON 1
                                        else optionsStage.id = 1;

                                        //MENTSE EL A SKIN ID-T
                                        ((FlightGame) game).setSkinID(optionsStage.id);
                                    }

                                } else if (hash.equals(OptionsStage.NYILJOBB_TEXUTE)) {
                                    //HA Ő A JOBBOLDALI NYÍL

                                    if (arrowMode == ArrowModes.DIFFICULTY) {
                                        //HA A NEHÉZSÉGET KELL ÁLLÍTANIA

                                        //HA A NEHÉZSÉG NEM ÉRI EL A MAXOT (3 - NEHÉZ) AKKOR NÖVELJE ÉS MENTSE
                                        if (((FlightGame) game).getDifficulty() != 3)
                                            ((FlightGame) game).setDifficulty(((FlightGame) game).getDifficulty() + 1);

                                        //HA ELÉRI AKKOR MARADJON 3 ÉS MENTSE
                                        else ((FlightGame) game).setDifficulty(3);

                                    } else if (arrowMode == ArrowModes.SKIN) {
                                        //HA A KINÉZETET KELL ÁLLÍTANIA

                                        //HA MÉG NEM ÉRTE EL A MAXOT AKKOR NÖVELJE
                                        if (optionsStage.id != optionsStage.maxId)
                                            optionsStage.id++;

                                        //HA ELÉRTE AKKOR MARADJON UGYANAZ
                                        else optionsStage.id = optionsStage.maxId;

                                        //MENTSE EL A SKIN ID-JÉT
                                        ((FlightGame) game).setSkinID(optionsStage.id);
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }
    }
}
