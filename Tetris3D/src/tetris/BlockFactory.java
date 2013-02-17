package tetris;


import java.util.Random;

/**
 * BlockFactory provides a mean to create random Blocks.
 *
 */
public final class BlockFactory {

	/**
	 * Creates a block with random shape and color at given position
	 * @param x "Width" coordinate
	 * @param y "Depth" coordinate
	 * @param z "Height" coordinate
	 * @return Created block
	 */
	public static Block randomBlockAtPosition(int x, int y, int z)
	{
		char[][][] template = blocks[random.nextInt(blocks.length)];
		//char[][][] template = blocks[0];
		Color[] colorValues = Color.values();
		Color color = colorValues[random.nextInt(colorValues.length)];
		
		Element[][][] elements = new Element[template.length][template.length][template.length];
		
		for (int i = 0; i < template.length; i++) {
			for (int j = 0; j < template[i].length; j++) {
				
				assert(template[i].length == template.length); // shape should be square
				
				for (int k = 0; k < template[i][j].length; k++)
				{
					assert(template[i].length == template[i][j].length); // shape should be square
					
					if (template[i][j][k] == 0) continue;
					
					Element element = new Element(color);
					elements[i][j][k] = element;
				}
			}
		}
		
		return new Block(elements, x, y, z);
	}
	
	private static Random random = new Random();
	private static char blocks[][][][] = {
		{ // cube shape
			{
				{1,},
			},
		},
		{ // T shape
			{
				{0, 0, 0},
				{0, 0, 0},
				{0, 0, 0}
			},
			{
				{0, 1, 0},
				{1, 1, 1},
				{0, 0, 0}
			},
			{
				{0, 0, 0},
				{0, 0, 0},
				{0, 0, 0}
			}
		}, 
		{ // Z Shape
			{
				{0, 0, 0},
				{0, 0, 0},
				{0, 0, 0}
			},
			{
				{0, 1, 0},
				{1, 1, 0},
				{1, 0, 0}
			},
			{
				{0, 0, 0},
				{0, 0, 0},
				{0, 0, 0}
			}
		},
		{ // I Shape
			{
				{0, 0, 0},
				{0, 1, 0},
				{0, 0, 0}
			},
			{
				{0, 0, 0},
				{0, 1, 0},
				{0, 0, 0}
			},
			{
				{0, 0, 0},
				{0, 1, 0},
				{0, 0, 0}
			}
		},
		{ // L shape
			{
				{0, 0, 0},
				{0, 0, 0},
				{0, 0, 0}
			},
			{
				{0, 1, 0},
				{0, 1, 0},
				{0, 1, 1}
			},
			{
				{0, 0, 0},
				{0, 0, 0},
				{0, 0, 0}
			}
		},
		{ 
			{
				{0, 0, 0},
				{0, 0, 0},
				{0, 0, 0},
			},
			{
				{1, 1, 0},
				{0, 1, 0},
				{0, 1, 1},
			},
			{
				{0, 0, 0},
				{0, 0, 0},
				{0, 0, 0},
			}
		},
		{
			{
				{0, 1},
				{1, 1},
			},
			{
				{0, 0},
				{0, 1},
			},
		},
		
		{ // fancy shape
			{
				{0, 1, 0},
				{0, 0, 0},
				{0, 0, 0},
			},
			{
				{0, 1, 0},
				{0, 1, 0},
				{0, 0, 0},
			},
			{
				{0, 0, 0},
				{1, 1, 0},
				{0, 0, 0},
			}
		}
	};
}
