package tetris;
/**
 * Block is a single game piece falling down, it consists of Elements.
 * Blocks are assumed to be cubes, i.e. width, height and depth are equal. This does not mean
 * that all blocks are completely filled, some of the 3D array elements might be (and will be) empty
 * Blocks are intended to be *immutable* all the transform operations (move, rotate)
 * return a new copy of a block.
 *
 */
public class Block {
	
	private int x;
	private int y;
	private int z;
		
	private int size;
	
	private Element[][][] elements;
	
	/**
	 * Default constructor, creates a Block from 3D array of Elements
	 * @param elements
	 */
	public Block(Element[][][] elements)
	{
		size = elements.length;
		this.elements = elements;
	}

	/**
	 * Additional constructor, creates a Block from 3D array of Elements at a given position
	 * @param elements
	 */
	public Block(Element[][][] elements, int x, int y, int z)
	{
		size = elements.length;
		this.elements = elements;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Creates a translated copy of block 
	 * @param x "Width" coordinate of translation
	 * @param y "Depth" coordinate of translation
	 * @param z "Height" coordinate of translation
	 * @return Translated copy of a block
	 */
	public Block translatedBlock(int x, int y, int z)
	{
		Block newBlock = new Block(elements.clone());
		newBlock.x += this.x + x;
		newBlock.y += this.y + y;
		newBlock.z += this.z + z;
		
		return newBlock;
	}
	
	/**
	 * Creates a copy of a block rotated along X axis
	 * @return Rotated copy of a block
	 */
	public Block xRotatedBlock()
	{
		Element[][][] newElements = new Element[size][size][size];
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				for (int k = 0; k < size; k++) {
					newElements[i][j][k] = elements[i][size - k - 1][j];
				}
			}
		}
		
		return new Block(newElements, x, y, z);
	}
	
	/**
	 * Creates a copy of a block rotated along Y axis
	 * @return Rotated copy of a block
	 */
	public Block yRotatedBlock()
	{
		Element[][][] newElements = new Element[size][size][size];
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				for (int k = 0; k < size; k++) {
					newElements[i][j][k] = elements[k][j][size - i - 1];
				}
			}
		}
		
		return new Block(newElements, x, y, z);
	}
	
	/**
	 * Creates a copy of a block rotated along Z axis
	 * @return Rotated copy of a block
	 */
	public Block zRotatedBlock()
	{
		Element[][][] newElements = new Element[size][size][size];
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				for (int k = 0; k < size; k++) {
					newElements[i][j][k] = elements[size - j - 1][i][k];
				}
			}
		}
		
		return new Block(newElements, x, y, z);
	}
	
	/**
	 * Returns an element at given location
	 * @param x "Width" coordinate
	 * @param y "Depth" coordinate
	 * @param z "Height" coordinate
	 * @return Either Element or null
	 */
	public Element elementAtLocation(int x, int y, int z)
	{
		return elements[x][y][z];
	}


	/**
	 * Size of block (size of a cube encapsulating all the Elements)
	 * @return Size of block
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Getter for "width" coordinate
	 * @return "width" coordinate of block's position
	 */
	public int getX() {
		return x;
	}


	/**
	 * Getter for "depth" coordinate
	 * @return "depth" coordinate of block's position
	 */
	public int getY() {
		return y;
	}


	/**
	 * Getter for "height" coordinate
	 * @return "height" coordinate of block's position
	 */
	public int getZ() {
		return z;
	}




	
}
