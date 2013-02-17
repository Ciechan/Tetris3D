package tetris;

/**
 * Represents a 3D Tetris board, i.e. well with width, depth and height
 *
 */
public class Board {

	private int width;
	private int depth;
	private int height;
	
	private Element[][][] elements;
	
	/**
	 * Designated constructor
	 * @param width Board width
	 * @param depth Board's depth
	 * @param height Board's height
	 */
	public Board(int width, int depth, int height)
	{
		this.width = width;
		this.depth = depth;
		this.height = height;
		this.elements = new Element[width][depth][height];
	}
	
	/**
	 * Checks whether a given arbitrarily positioned block can be placed legally on the board
	 * @param block Checked block
	 * @return If true then block can be placed legally, false otherwise.
	 */
	public boolean canBlockBePlacedLegally(Block block)
	{		
		for (int i = 0; i < block.getSize(); i++) {
			for (int j = 0; j < block.getSize(); j++) {
				for (int k = 0; k < block.getSize(); k++) {
					if (block.elementAtLocation(i, j, k) == null) {
						continue;
					}
					int x = i + block.getX();
					int y = j + block.getY();
					int z = k + block.getZ();
										
					if (x < 0 || x >= width || y < 0 || y >= depth || z < 0)
						return false; // out of range
					
					if (z >= height)
						continue;
					
					if (this.elements[x][y][z] != null)
						return false; // already taken
				}
			}
		}
		return true;
	}
	
	/**
	 * Checks whether given block is in contact with any of board's elements, i.e.
	 * If bottom face of any of block's elements touched top face of any of board's elements
	 * @param block Checked block
	 * @return Check result.
	 */
	public boolean isBlockInContact(Block block)
	{				
		for (int i = 0; i < block.getSize(); i++) {
			for (int j = 0; j < block.getSize(); j++) {
						
				int x = i + block.getX();
				int y = j + block.getY();
				
				for (int k = 0; k < block.getSize(); k++) {
					if (block.elementAtLocation(i, j, k) != null) {
						int z = k + block.getZ();
						
						assert(z >= 0);
						
						if (z == 0) {
							return true; // block is touching ground
						}
						
						if (z <= height && elements[x][y][z - 1] != null) {
							return true; // there is a piece underneath checked block
						}
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Adds given block to board, incorporating its elements into boards own.
	 * @param block Added block
	 * @return If return value is true then the board has overflowed
	 */
	public Boolean addBlock(Block block)
	{
		for (int i = 0; i < block.getSize(); i++) {
			for (int j = 0; j < block.getSize(); j++) {
				for (int k = 0; k < block.getSize(); k++) {
					Element element;
					if ((element = block.elementAtLocation(i, j, k)) == null) {
						continue;
					}
					int x = i + block.getX();
					int y = j + block.getY();
					int z = k + block.getZ();
					
					assert(x >= 0 && x < width && y >= 0 && y < depth);
					assert(this.elements[x][y][z] == null);
					
					if (z >= height) {
						return false; // added block is over the top edge
					}
					
					this.elements[x][y][z] = element;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Checks if any of boards levels is filled completely and removes them, counting the total
	 * number of reduced levels.
	 * @return Number of reduced levels (can be more than 1)
	 */
	public int reduceLevels()
	{
		int reducedLevels = 0;
		Element[][][] newElements = new Element[width][depth][height];

		for( int z = 0; z < height; z++ )
		{
			boolean isLevelFull = true;

			for( int x = 0; x < width; x++ )
			{
				for (int y = 0; y < depth; y++) 
				{
					if (elements[x][y][z] == null)
					{
						isLevelFull = false;
						break; // no need to check other elements, level is not full
					}
				}
			}
			
			if (isLevelFull) {
				reducedLevels++;
				continue;
			} 
			
			for( int x = 0; x < width; x++ )
			{
				for (int y = 0; y < depth; y++) 
				{
					newElements[x][y][z - reducedLevels] = elements[x][y][z]; // copy contents of level at proper place
				}
			}
		}
		
		elements = newElements;

		return reducedLevels;
	}
	
	/**
	 * Returns the element placed at given position
	 * @param i Index among "width" axis
	 * @param j Index among "depth" axis
	 * @param k Index among "height" axis
	 * @return Element at position
	 */
	public Element elementAtLocation(int i, int j, int k)
	{
		return elements[i][j][k];
	}

	/**
	 * Getter for board's width
	 * @return board's width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Getter for board's depth
	 * @return board's depth
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * Getter for board's height
	 * @return board's height
	 */
	public int getHeight() {
		return height;
	}

}
