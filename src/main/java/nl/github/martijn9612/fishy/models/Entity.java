package nl.github.martijn9612.fishy.models;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Ellipse;

import nl.github.martijn9612.fishy.Main;
import nl.github.martijn9612.fishy.utils.MusicPlayer;

/**
 * Implements the basic object in the game on which players and opponents are
 * based. The entity object has a defined position, velocity, acceleration and
 * dimension. It provides methods for updating the bounding box and rendering (a
 * scaled copy of) itself.
 */
public abstract class Entity {
	public Vector position;
	public Vector velocity;
	public Vector acceleration;
	public Vector dimensions;
	public static final int IMAGE_ORIENTATE_LEFT = 0;
	public static final int IMAGE_ORIENTATE_RIGHT = 1;
	protected MusicPlayer musicPlayer;
	protected boolean hasOpenGL;
	private int orientation = IMAGE_ORIENTATE_LEFT;
	private Vector oldDimensions;
	private Image originalImage;
	private Image scaledImage;
	private Ellipse boundingBox;

	/**
	 * Setup a new Entity object.
	 * 
	 * @param dimensions size of the entity. 
	 * @param position start position of the entity.
	 * @param velocity initial velocity of the entity.
	 * @param acceleration initial acceleration of the entity.
	 * @param hasOpenGL whether openGL context should be invoked.
	 */
	public Entity(Vector dimensions, Vector position, Vector velocity, Vector acceleration, boolean hasOpenGL) {
		this.position = position;
		this.velocity = velocity;
		this.acceleration = acceleration;
		this.dimensions = dimensions;
		this.oldDimensions = new Vector(-1,-1);
		this.hasOpenGL = hasOpenGL;
		updateBoundingbox();
	}

	/**
	 * Loads the resources associated with this entity. These are
	 * the given sprite location and the musicPlayer instance. Also,
	 * a scaled copy of the sprite is made related to the dimensions.
	 * 
	 * @param imagePath resource path to filesystem location of the sprite.
	 */
	public void loadResources(String imagePath) {
		if (hasOpenGL) {
			try {
				originalImage = new Image(imagePath);
				musicPlayer = MusicPlayer.getInstance();
				getScaledImage();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Set the orientation of the sprite. When the orientation changes,
	 * a new copy is made by flipping the y axis and will be stored.
	 * 
	 * @param newOrientation integer defining the orientation.
	 */
	public void setImageOrientation(int newOrientation) {
		if(orientation != newOrientation) {
			scaledImage = scaledImage.getFlippedCopy(true, false);
			originalImage = originalImage.getFlippedCopy(true, false);
			orientation = newOrientation;
		}
	}

	/**
	 * Renders the object and the boundary box when enabled.
	 * 
	 * @param g the graphics content used to render.
	 */
	public void renderObject(Graphics g) {
		if (hasOpenGL) {
			Image image = getScaledImage();
			g.drawImage(image, position.x, position.y);
			if (Main.DEBUG_MODE) {
				g.drawOval(boundingBox.getX(), boundingBox.getY(), boundingBox.getWidth(), boundingBox.getHeight());
			}
		}
	}

	/**
	 * Returns the size of the opponent.
	 * 
	 * @return size value
	 */
	public float getSize() {
		return ((dimensions.x + dimensions.y) / 2);
	}

	/**
	 * Checks whether the bounding boxes of two entities collide.
	 * 
	 * @param entity the instance to check for.
	 * @return boolean wether they collide.
	 */
	public boolean intersects(Entity entity) {
		return boundingBox.intersects(entity.getEllipse());
	}

	/**
	 * Get the bounding box ellipse instance and returns it.
	 * 
	 * @return Ellipse the bounding box.
	 */
	public Ellipse getEllipse() {
		return boundingBox;
	}

	/**
	 * Updates the boundary box around the entity according to the dimensions.
	 * When the bounding box wasn't calculated before, a new Ellipse instance is
	 * created to act as bounding box.
	 */
	public void updateBoundingbox() {
		float radiiX = (dimensions.x / 2);
		float radiiY = (dimensions.y / 2);

		if (boundingBox == null) {
			boundingBox = new Ellipse(position.x, position.y, radiiX, radiiY);
		} else {
			boundingBox.setLocation(position);
			boundingBox.setRadii(radiiX, radiiY);
		}
	}

	/**
	 * Get a scaled version of the image sprite. When the dimensions of an
	 * entity changes, a new copy from the original sprite is created and
	 * returned.
	 * 
	 * @return Image a scaled version of the entity image.
	 */
	private Image getScaledImage() {
		if (!oldDimensions.equals(dimensions)) {
			int width = Math.round(dimensions.x);
			int height = Math.round(dimensions.y);
			scaledImage = originalImage.getScaledCopy(width, height);
			oldDimensions = dimensions;
		}
		return scaledImage;
	}

	/**
	 * Execute the logic calculations associated with this entity.
	 * 
	 * @param gc the container holding the game.
	 * @param deltaTime milliseconds passed since last call.
	 */
	public abstract void objectLogic(GameContainer gc, int deltaTime);
}