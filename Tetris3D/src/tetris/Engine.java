package tetris;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import tetris.OpenGL.Camera;
import tetris.OpenGL.GLRenderer;

/**
 * This class is responsible for running game loop and calling logic and drawing functions
 * periodically. Provides a clean separation between business and presentation layer.
 *
 */
public class Engine {
	
	public static void main(String[] args) {
		Engine engine = new Engine();
		engine.startGame();
	}

	private final int kWidth = 1024;
	private final int kHeight = 768;	
	
	private GLRenderer renderer;
	private Camera camera;
	private GameLogic logic;
	
	private int highScore;
	
	/**
	 * Starts game
	 */
	public void startGame()
	{
		readHighScore();
		
		renderer = new GLRenderer();
		logic = new GameLogic();
		
		Vector3f lookPoint = new Vector3f(GameLogic.WIDTH/2.0f, GameLogic.DEPTH/2.0f, GameLogic.HEIGHT/2.0f);
		camera = new Camera((float)kWidth/(float)kHeight, lookPoint);
		
		renderer.setup(kWidth,kHeight);

		while (!Display.isCloseRequested()) {

			processKeyboardInput();
			processMouseInput();
			
			logic.tick();
			
			renderer.startRenderingWithCamera(camera);
			
			renderBlock();
			renderBoard();
			renderBB();
			
			renderer.endRendering();

			// Force a maximum FPS of about 60
			Display.sync(60);
			// Let the CPU synchronize with the GPU if GPU is tagging behind
			Display.update();
			Display.setTitle("Your score: " + logic.getScore() + " -- High score: " +  highScore);
			
			if (logic.isOver) {
				if (logic.getScore() > highScore)
				{
					highScore = logic.getScore();
					saveHighScore();
					JOptionPane.showMessageDialog(null, "New highscore!","Game Over", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Game Over.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
				}
				logic.newGame();
			}
		}
		
		renderer.tearDown();
	}
	
	private void saveHighScore() {
		PrintWriter out;
		try {
			out = new PrintWriter("highscore.txt");
			out.print(highScore);		
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void readHighScore() {

		try {
			File file =  new File("highscore.txt");
			this.highScore = new Scanner(file).nextInt();
		} catch (FileNotFoundException e) {
			this.highScore = 0;
		} catch (java.util.NoSuchElementException e) {
			this.highScore = 0;
		}
	}

	private void renderBlock()
	{
		Block block = logic.block;
		for (int i = 0; i < block.getSize(); i++) {
			for (int j = 0; j < block.getSize(); j++) {
				for (int k = 0; k < block.getSize(); k++) {
					Element element;
					if ((element = block.elementAtLocation(i, j, k)) != null) {
					
						renderer.renderElementAtOffsetWithColor(new Vector3f(block.getX() + i, block.getY() + j, block.getZ() + k), element.getColor());
					}
				}
			}
		}
	}
	
	private void renderBoard()
	{
		Board board = logic.board;
		for (int i = 0; i < board.getWidth(); i++) {
			for (int j = 0; j < board.getDepth(); j++) {
				for (int k = 0; k < board.getHeight(); k++) {
					Element element;
					if ((element = board.elementAtLocation(i, j, k)) != null) {
						renderer.renderElementAtOffsetWithColor(new Vector3f(i, j, k), element.getColor());
					}
				}
			}
		}
	}
	
	private void renderBB()
	{
		final float ALPHA = 0.1f;
		
		for (int i = 0; i <= GameLogic.WIDTH; i++) {
			Vector3f a = new Vector3f(-0.5f + i, -0.5f, -0.5f);
			Vector3f b = new Vector3f(-0.5f + i, -0.5f, GameLogic.HEIGHT -0.5f);

			Vector3f c = new Vector3f(-0.5f + i, GameLogic.DEPTH -0.5f, -0.5f);
			Vector3f d = new Vector3f(-0.5f + i, GameLogic.DEPTH -0.5f, GameLogic.HEIGHT -0.5f);
			
			renderer.renderLineWithAlpha(a, b, ALPHA);
			renderer.renderLineWithAlpha(c, d, ALPHA);
			renderer.renderLineWithAlpha(a, c, ALPHA);
		}
		
		for (int i = 0; i <= GameLogic.DEPTH; i++) {
			Vector3f a = new Vector3f(-0.5f, -0.5f + i, -0.5f);
			Vector3f b = new Vector3f(-0.5f, -0.5f + i, GameLogic.HEIGHT -0.5f);

			Vector3f c = new Vector3f(GameLogic.WIDTH -0.5f, -0.5f + i, -0.5f);
			Vector3f d = new Vector3f(GameLogic.WIDTH -0.5f, -0.5f + i, GameLogic.HEIGHT -0.5f);
			
			renderer.renderLineWithAlpha(a, b, ALPHA);
			renderer.renderLineWithAlpha(c, d, ALPHA);
			renderer.renderLineWithAlpha(a, c, ALPHA);
		}

		for (int i = 0; i <= GameLogic.HEIGHT; i++) {
			Vector3f a = new Vector3f(-0.5f, -0.5f, -0.5f + i);
			Vector3f b = new Vector3f(GameLogic.WIDTH -0.5f, -0.5f, -0.5f + i);
			Vector3f c = new Vector3f(GameLogic.WIDTH -0.5f, GameLogic.DEPTH -0.5f, -0.5f + i);
			Vector3f d = new Vector3f(-0.5f,GameLogic.DEPTH -0.5f, -0.5f + i);
			
			renderer.renderLineWithAlpha(a, b, ALPHA);
			renderer.renderLineWithAlpha(b, c, ALPHA);
			renderer.renderLineWithAlpha(c, d, ALPHA);
			renderer.renderLineWithAlpha(d, a, ALPHA);
		}
		
		Vector3f a = new Vector3f(-0.6f, -0.6f, -0.5f);
		Vector3f b = new Vector3f( 0.0f, -0.6f, -0.5f);
		Vector3f c = new Vector3f(-0.6f,  0.0f, -0.5f);
		
		renderer.renderLineWithAlpha(a, b, 1.0f);
		renderer.renderLineWithAlpha(a, c, 1.0f);
	}
	
	private void processMouseInput() {

		if (Mouse.isButtonDown(0)) {
		
			final float scale = 0.01f;
			
			int dx = Mouse.getDX();
			int dy = Mouse.getDY();
			
			camera.setYaw(camera.getYaw() + dx*scale);
			camera.setPitch(camera.getPitch() - dy*scale);
		}

		final float wheelScale = 120.0f;
		camera.setDistance(camera.getDistance() + Mouse.getDWheel()/wheelScale);

	}

	private void processKeyboardInput() {

		Input input = Input.None;
		
		while(Keyboard.next()) {			
			if (!Keyboard.getEventKeyState()) continue;
			
			
			// Change model scale, rotation and translation values
			switch (Keyboard.getEventKey()) {
			// Move
			case Keyboard.KEY_UP:
				input = Input.MoveUp;
				break;
			case Keyboard.KEY_DOWN:
				input = Input.MoveDown;
				break;
			case Keyboard.KEY_LEFT:
				input = Input.MoveLeft;
				break;
			case Keyboard.KEY_RIGHT:
				input = Input.MoveRight;
				break;
			case Keyboard.KEY_A:
				input = Input.RotateX;
				break;
			case Keyboard.KEY_S:
				input = Input.RotateY;
				break;
			case Keyboard.KEY_D:
				input = Input.RotateZ;
				break;
			case Keyboard.KEY_SPACE:
				input = Input.Drop;
				break;
			}
		}
		
		logic.currentInput = input;
	}
	


}
