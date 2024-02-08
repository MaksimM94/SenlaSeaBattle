package seaBattle.ai;

import seaBattle.logic.Field;

import java.util.Random;

public class AI {

	public Field field;
	public AIBase action;
	public Random rand;
	
	
	public AI(Field field) {
		this.rand = new Random();
		this.field = field;
		this.action = new AIRandom(this);
	}

	public int doShot() {
		return action.doShot();
	}

	public Field getField() {
		return field;
	}

}
