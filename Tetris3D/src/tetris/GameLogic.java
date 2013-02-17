package tetris;

/**
 * GameLogic is responsible for the process of the game. It handles user input,
 * moves block down periodically, counts points and checks for fail conditions.
 *
 */
public class GameLogic {

	public static final int WIDTH = 5;
	public static final int DEPTH = 5;
	public static final int HEIGHT = 10;
	private static final int POINTS_PER_ROW = 10;
	private static final int kSmallTicksPerDrop = 120;
	
	public Boolean isOver;
	public Board board;
	public Block block;
	private int score;
	private int smallTickCount;
		
	public Input currentInput;
	
	/**
	 * Designated constructor
	 */
	public GameLogic()
	{
		newGame();
	}
	
	/**
	 * Starts new game
	 */
	public void newGame()
	{
		board = new Board(WIDTH, DEPTH, HEIGHT);
		score = 0;
		isOver = false;
		createNewBlock();
	}


	/**
	 * Performs one "tick" of game's logic
	 */
	public void tick()
	{
		if (isOver) {
			return;
		}
		
		handleInput();
		
		if (smallTickCount == 0) {
			
			if (board.isBlockInContact(block)) {
				if (! board.addBlock(block)) {
					isOver = true;
					return;
				}
				score += POINTS_PER_ROW*board.reduceLevels();
				createNewBlock();
			} 
			moveBlockWithOffset(0, 0, -1);
			smallTickCount = kSmallTicksPerDrop;
		}
		
		smallTickCount--;			
	}
	
	private void createNewBlock() {
		block = BlockFactory.randomBlockAtPosition(WIDTH/3, DEPTH/3, HEIGHT);
	}



	private void handleInput() {
		switch (currentInput) {
		case MoveLeft:
			moveBlockWithOffset(-1, 0, 0);
			break;
		case MoveRight:
			moveBlockWithOffset(1, 0 ,0);
			break;
		case MoveUp:
			moveBlockWithOffset(0, 1, 0);
			break;
		case MoveDown:
			moveBlockWithOffset(0, -1, 0);
			break;
		case Drop:
			dropBlockDown();
			break;
		case RotateX:
			rotateBlockX();
			break;
		case RotateY:
			rotateBlockY();
			break;
		case RotateZ:
			rotateBlockZ();
			break;
		default:
			break;
		}
		
		currentInput = Input.None;
	}
	
	private void dropBlockDown()
	{
		while (true) {
			Block newBlock = block.translatedBlock(0, 0, -1);
			
			if (board.canBlockBePlacedLegally(newBlock)) {
				block = newBlock;
			} else {
				smallTickCount = 0;
				return;
			}
		}
	}

	private void moveBlockWithOffset(int x, int y, int z)
	{
		Block newBlock = block.translatedBlock(x, y, z);
		
		if (board.canBlockBePlacedLegally(newBlock)) {
			block = newBlock;
		}
	}
	
	private void rotateBlockX()
	{
		Block newBlock = block.xRotatedBlock();
		if (board.canBlockBePlacedLegally(newBlock)) {
			block = newBlock;
		}
	}
	
	private void rotateBlockY() {
		Block newBlock = block.yRotatedBlock();
		if (board.canBlockBePlacedLegally(newBlock)) {
			block = newBlock;
		}		
	}
	
	private void rotateBlockZ() {
		Block newBlock = block.zRotatedBlock();
		if (board.canBlockBePlacedLegally(newBlock)) {
			block = newBlock;
		}		
	}

	
	public int getScore() {
		return score;
	}




}

