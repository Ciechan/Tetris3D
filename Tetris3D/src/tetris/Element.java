package tetris;

/**
 * A single smallest atomic piece of tetris' logic. Both board and blocks consist of elements
 *
 */
public class Element {
	private Color color;
	
	/**
	 * Creates Element with a given Color
	 * @param color Element's desired Color
	 */
	public Element(Color color)
	{
		this.color = color;
	}

	/**
	 * Getter for element's color
	 * @return element's color
	 */
	public Color getColor() {
		return color;
	}
}
