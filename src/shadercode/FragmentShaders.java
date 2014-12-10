package shadercode;
/*openGl breaks display into a bunch of small fragments through rasterization
 * these fragments. This code will run once for each fragment, so if a square has 5,000
 * fragments this will run 5,000 times. uniform can be set by getting uniform location with glGetUniformLocation()
 * then color can be set by glUniform(uniform location, color); */
public class FragmentShaders {
	
	public static final String simple_fragment_shader =
			"precision mediump float;" +
			"uniform vec4 u_Color;" +
			"void main(){" +
			"gl_FragColor = u_Color;" +
			"}";
	
	//each fragment will receive the texture coordinates at v_TextureCoordinates
	//and the actualy texture data at u_TextureUnit and use texture2D to read the
	//color value at that coordinate and pass it on to gl_FragColor
	public static final String texture_fragment_shader = 
			"precision mediump float;" +
			"uniform sampler2D u_TextureUnit;" +
			"varying vec2 v_TextureCoordinates;" +
			"void main(){" +
			"gl_FragColor = texture2D(u_TextureUnit, v_TextureCoordinates);" + 
			"}";
}
