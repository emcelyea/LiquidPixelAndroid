package shadercode;

/*Code for OpenGL vertex Shaders
 * These shaders will be linked to a fragment shader
 * and compiled into a program. These programs are how we 
 * set attributes to vertices, the program will run once
 * for each vertex.
 * */

public class VertexShaders {

	public static final String simple_vertex_shader1 =
			"attribute vec4 a_Position;" +
			"void main(){" +
			"gl_Position = a_Position;" +
			"gl_PointSize = 4.0;"+
			"}";
	
	public static final String simple_vertex_shader2 =
			"attribute vec4 a_Position;" +
			"void main(){" +
			"gl_Position = a_Position;" +
			"gl_PointSize = 6.0;"+
			"}";
	
	public static final String simple_vertex_shader3 =
			"attribute vec4 a_Position;" +
			"void main(){" +
			"gl_Position = a_Position;" +
			"gl_PointSize = 8.0;"+
			"}";
	
	public static final String simple_vertex_shader4 =
			"attribute vec4 a_Position;" +
			"void main(){" +
			"gl_Position = a_Position;" +
			"gl_PointSize = 10.67;"+
			"}";
	
	public static final String texture_vertex_shader = 
			"attribute vec4 a_Position;" +
			"attribute vec2 a_TextureCoordinates;" +
			"varying vec2 v_TextureCoordinates;" +
			"void main(){" +
			"v_TextureCoordinates = a_TextureCoordinates;" +
			"gl_Position = a_Position;"+
			"}";
}
