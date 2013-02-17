package tetris.OpenGL;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/**
 * A general purpose Camera with adjustable yaw, pitch and distance values.
 *
 */
public class Camera {
	
	private Vector3f focusPoint;
	private float yaw;
	private float pitch;

	private float distance;
	
	private Matrix4f viewProjectionMatrix;
	private Matrix4f projectionMatrix = null;
	private Matrix4f viewMatrix = null;
	
	/**
	 * Designated constructor.
	 * @param aspectRatio - camera's aspect ratio
	 * @param focusPoint - the point at which camera looks
	 */
	public Camera(float aspectRatio, Vector3f focusPoint)
	{
		this.focusPoint = new Vector3f(-focusPoint.x + 0.5f, -focusPoint.z + 0.5f, +focusPoint.y - 0.5f);
		this.pitch = (float)(Math.PI/2.3);
		this.distance = 12.0f;
		
		projectionMatrix = new Matrix4f();

		float fieldOfView = 60f;
		float near_plane = 0.1f;
		float far_plane = 100f;
		
		float y_scale = this.coTangent(this.degreesToRadians(fieldOfView / 2f));
		float x_scale = y_scale / aspectRatio;
		float frustum_length = far_plane - near_plane;
		
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((far_plane + near_plane) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * near_plane * far_plane) / frustum_length);
		
		recalculateMatrices();
	}
	
	private void recalculateMatrices()
	{
		viewMatrix = new Matrix4f();
	
		// surprisingly reverse order ?
		Matrix4f.translate(new Vector3f(0, 0, -distance), viewMatrix, viewMatrix); // final distance transform
		Matrix4f.rotate(pitch, new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
		Matrix4f.rotate(yaw, new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
		Matrix4f.translate(focusPoint, viewMatrix, viewMatrix); // look at point offset
		Matrix4f.rotate((float)(-Math.PI/2.0), new Vector3f(1, 0, 0), viewMatrix, viewMatrix); // OpenGL coords to my coords

		viewProjectionMatrix = new Matrix4f();
		Matrix4f.mul(projectionMatrix, viewMatrix, viewProjectionMatrix);
	}
	
	private float coTangent(float angle) {
		return (float)(1f / Math.tan(angle));
	}
	
	private float degreesToRadians(float degrees) {
		return degrees * (float)(Math.PI/ 180d);
	}


	/**
	 * Getter for camera's view projection matrix
	 * @return View projection matrix
	 */
	public Matrix4f getViewProjectionMatrix() {
		return viewProjectionMatrix;
	}
	
	/**
	 * Getter for camera's view matrix
	 * @return View matrix
	 */
	public Matrix4f getViewMatrix() {
		return viewMatrix;
	}

	/**
	 * Getter for camera's pitch angle
	 * @return camera's pitch angle
	 */
	public float getPitch() {
		return pitch;
	}

	/**
	 * Setter for camera's pitch angle
	 * @param pitch new pitch angle
	 */
	public void setPitch(float pitch) {
		pitch = (float) Math.min(Math.max(0.0f, pitch), Math.PI/2.0);
		this.pitch = pitch;
		
		recalculateMatrices();
	}

	/**
	 * Getter for camera's yaw angle
	 * @return camera's yaw angle
	 */
	public float getYaw() {
		return yaw;
	}

	/**
	 * Setter for camera's yaw angle
	 * @param yaw new yaw angle
	 */
	public void setYaw(float yaw) {
		float twoPi = (float)(2.0*Math.PI);
		yaw = yaw > twoPi ? yaw -= twoPi : yaw < 0 ? yaw += twoPi : yaw;
		
		this.yaw = yaw;
		
		recalculateMatrices();
	}

	/**
	 * Getter for camera's distance from focus point
	 * @return camera's distance from focus point
	 */
	public float getDistance() {
		return distance;
	}

	/**
	 * Setter for camera's distance angle
	 * @param distance new distance angle
	 */
	public void setDistance(float distance) {
		distance = (float) Math.min(Math.max(0, distance), 20.0);
		this.distance = distance;
		
		recalculateMatrices();
	}

}
