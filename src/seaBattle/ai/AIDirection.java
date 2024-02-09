package seaBattle.ai;

import java.util.ArrayList;

public class AIDirection extends AIBase {
	public AIDirection(AI ai) {
		super(ai);
	}

	@Override
	public int doShot() {
		/*ArrayList<Cell> list = new ArrayList<Cell>();
		draw(list, dx, dy);
		draw(list, -dx, -dy);

		if (list.size() > 0) {
			return list.get(ai.rand.nextInt(list.size())).doShot();
		}

		ai.action = new AIRandom(ai);*/
		return ai.doShot();
	}

}
