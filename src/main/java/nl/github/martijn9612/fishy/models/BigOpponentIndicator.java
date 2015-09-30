package nl.github.martijn9612.fishy.models;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;

/**
 * The arrow which indicates that a whale is coming
 * Created by Skullyhoofd on 25/09/2015.
 */
public class BigOpponentIndicator extends Entity {

    private Player player;
    private static final int LAG_BY_FRAMES = 15;
    private static final float WHALE_START_X = 580;
    private static final float WHALE_SIZE_X = 115;
    private static final float WHALE_SIZE_Y = 100;
    private static final String SPRITE_PATH = "resources/whale.png";
    private List<Float> positionHistory = new ArrayList<Float>();

    public BigOpponentIndicator(Player player, boolean loadResource) {
    	super(loadResource);
    	this.player = player;
        loadResources(SPRITE_PATH);
        position = new Vector(WHALE_START_X, player.position.y - (WHALE_SIZE_Y / 2));
        dimensions = new Vector(WHALE_SIZE_X, WHALE_SIZE_Y);
        velocity = new Vector(0,0);
    }

	@Override
	public void objectLogic(GameContainer gc, int deltaTime) {
		positionHistory.add(player.position.y - (WHALE_SIZE_Y / 2));
		if(positionHistory.size() > LAG_BY_FRAMES) {
			position.y = positionHistory.get(0);
			positionHistory.remove(0);
		}
	}

}
