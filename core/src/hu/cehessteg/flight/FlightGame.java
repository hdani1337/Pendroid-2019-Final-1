package hu.cehessteg.flight;

import hu.cehessteg.flight.Screen.MenuScreen;
import hu.cehessteg.flight.Stage.MyLoadingStage;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;

public class FlightGame extends MyGame{
	public FlightGame(boolean debug) {
		super(debug);
	}

	public long penz;

	@Override
	public void create() {
		super.create();
		penz = 0;
		setLoadingStage(new MyLoadingStage(this));
		setScreen(new MenuScreen(this));
	}

	public long getPenz() {
		return penz;
	}
}
