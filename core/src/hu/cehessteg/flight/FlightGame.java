package hu.cehessteg.flight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import hu.cehessteg.flight.Screen.MenuScreen;
import hu.cehessteg.flight.Stage.MyLoadingStage;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;

public class FlightGame extends MyGame{
	public FlightGame(boolean debug) {
		super(debug);
	}

	public long penz;
	public boolean muted;
	public Preferences gameSave;

	@Override
	public void create() {
		super.create();
		penz = 0;
		setLoadingStage(new MyLoadingStage(this));
		setScreen(new MenuScreen(this));

		gameSave = Gdx.app.getPreferences("FlightGameSave");

		if(!gameSave.contains("boot"))//ha még nem volt elindítva
		{
			gameSave.putBoolean("boot", true);
			gameSave.putLong("coins", 0);
			gameSave.putBoolean("muted", false);
			gameSave.flush();
		}else {
			penz = gameSave.getLong("coins");
			muted = gameSave.getBoolean("muted");
		}

	}

	public long getPenz() {
		return penz;
	}

	public void setPenz(long penz) {
		this.penz = penz;
	}

	public boolean isMuted() {
		return muted;
	}

	public void setMuted(boolean muted) {
		this.muted = muted;
		gameSave.putBoolean("muted", muted);
		gameSave.flush();
	}

	public void saveCoins(){
		gameSave.putLong("coins", penz);
		gameSave.flush();
	}

	public Preferences getGameSave()
	{
		return gameSave;
	}
}
