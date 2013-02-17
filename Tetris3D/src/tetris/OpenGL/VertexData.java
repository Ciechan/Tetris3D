package tetris.OpenGL;

/**
 * This class represents position, normal and UV-mapping data of a single vertex.
 * It's based on the VertexData class from original LWJGL tutorial.
 */
public class VertexData {
	// Vertex data
	private float[] pos = new float[] {0f, 0f, 0f};
	private float[] normal = new float[] {0f, 0f, 0f};
	private float[] uv = new float[] {0f, 0f};
	
	// The amount of bytes an element has
	public static final int elementBytes = 4; 
	
	// Elements per parameter
	public static final int positionElementCount = 3;
	public static final int normalElementCount = 3;
	public static final int uvElementCount = 2;
	
	// Bytes per parameter
	public static final int positionBytesCount = positionElementCount * elementBytes;
	public static final int normalBytesCount = normalElementCount * elementBytes;
	public static final int uvBytesCount = uvElementCount * elementBytes;
	
	// Byte offsets per parameter
	public static final int positionByteOffset = 0;
	public static final int normalByteOffset = positionByteOffset + positionBytesCount;
	public static final int uvByteOffset = normalByteOffset + normalBytesCount;
	
	// The amount of elements that a vertex has
	public static final int elementCount = positionElementCount + normalElementCount + uvElementCount;
	
	// The size of a vertex in bytes, like in C/C++: sizeof(Vertex)
	public static final int stride = positionBytesCount + normalBytesCount + uvBytesCount;
	
	
	public VertexData(float x, float y, float z, float nx, float ny, float nz, float u, float v)
	{
		this.pos = new float[] {x, y, z};
		this.normal = new float[] {nx, ny, nz};
		this.uv = new float[] {u, v};
	}
	
	public VertexData(float[] v)
	{
		this.pos = new float[] {v[0], v[1], v[2]};
		this.normal = new float[] {v[3], v[4], v[5]};
		this.uv = new float[] {v[6], v[7]};
	}

	
	public float[] getElements() {
		float[] out = new float[VertexData.elementCount];
		int i = 0;
		
		out[i++] = this.pos[0];
		out[i++] = this.pos[1];
		out[i++] = this.pos[2];
		
		out[i++] = this.normal[0];
		out[i++] = this.normal[1];
		out[i++] = this.normal[2];

		out[i++] = this.uv[0];
		out[i++] = this.uv[1];
		
		return out;
	}
	


}