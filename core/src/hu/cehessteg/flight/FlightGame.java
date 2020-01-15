package hu.cehessteg.flight;

import hu.cehessteg.flight.Screen.GameScreen;
import hu.cehessteg.flight.Screen.MenuScreen;
import hu.cehessteg.flight.Stage.LoadingStage;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;

public class FlightGame extends MyGame{
	public FlightGame(boolean debug) {
		super(debug);
	}

	@Override
	public void create() {
		super.create();
		setScreen(new GameScreen(this));
		setLoadingStage(new LoadingStage(this));
	}
}
