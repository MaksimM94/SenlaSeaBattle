package seaBattle.ai;

import seaBattle.game.game.BattleBoard;

import java.util.Random;

public class AI {

	public BattleBoard field;
	public AIBase action;
	public Random rand;
	
	
	public AI(BattleBoard field) {
		this.rand = new Random();
		this.field = field;
		this.action = new AIRandom(this);
	}

	public int doShot() {
		return action.doShot();
	}

	public BattleBoard getField() {
		return field;
	}

}
