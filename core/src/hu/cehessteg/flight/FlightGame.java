package hu.cehessteg.flight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import hu.cehessteg.flight.Screen.IntroScreen;
import hu.cehessteg.flight.Screen.MenuScreen;
import hu.cehessteg.flight.Screen.ShopScreen;
import hu.cehessteg.flight.Stage.MyLoadingStage;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;

public class FlightGame extends MyGame{
	public FlightGame(boolean debug) {
		super(debug);
	}

	public long penz;
	public int difficulty;
	public int skinID;
	public boolean muted;
	public Preferences gameSave;

	@Override
	public void create() {
		super.create();
		penz = 0;
		setLoadingStage(new MyLoadingStage(this, true));
		setScreen(new IntroScreen(this), false);

		gameSave = Gdx.app.getPreferences("FlightGameSave");

		if(!gameSave.contains("boot")){//ha még nem volt elindítva
			resetSave();
		}

		baseValues();
	}

	public long getPenz() {
		return penz;
	}

	public void setPenz(long penz) {
		this.penz = penz;
	}

	public int getSkinID() {
		return skinID;
	}

	public void setSkinID(int skinID) {
		this.skinID = skinID;
		gameSave.putInteger("skinID", skinID);
		gameSave.flush();
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
		gameSave.putInteger("difficulty", difficulty);
		gameSave.flush();
	}

	public boolean isMuted() {
		return muted;
	}

	public void setMuted(boolean muted) {
		this.muted = muted;
		gameSave.putBoolean("muted", muted);
		gameSave.flush();
	}

	public void setPlaneLevel(int level)
	{
		gameSave.putInteger("planeLevel", level);
		gameSave.flush();
	}

	public int getPlaneLevel()
	{
		return gameSave.getInteger("planeLevel");
	}

	public void saveCoins(){
		gameSave.putLong("coins", penz);
		gameSave.flush();
	}

	public Preferences getGameSave()
	{
		return gameSave;
	}

	public void resetSave(){
		gameSave.putBoolean("boot", true);
		gameSave.putLong("coins", 0);
		gameSave.putBoolean("muted", false);
		gameSave.putInteger("planeLevel", 1);
		gameSave.putInteger("skinID", 1);
		gameSave.putInteger("difficulty", 1);
		gameSave.flush();
	}

	public void baseValues(){
		penz = gameSave.getLong("coins");
		difficulty = gameSave.getInteger("difficulty");
		skinID = gameSave.getInteger("skinID");
		muted = gameSave.getBoolean("muted");
	}
}
