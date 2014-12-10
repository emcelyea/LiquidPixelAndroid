package lastone.util;

import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.glValidateProgram;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.GL_VALIDATE_STATUS;
import static android.opengl.GLES20.glGetShaderInfoLog;
import static android.opengl.GLES20.glDeleteShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glGetProgramInfoLog;
import static android.opengl.GLES20.glDeleteProgram;
import static android.opengl.GLES20.glShaderSource;
import static android.opengl.GLES20.GL_COMPILE_STATUS;
import android.util.Log;


public class ShaderHelper {
	private static final String TAG = "ShaderHelper";
	
	public static int compileVertexShader(String shaderCode){
		return compileShader(GL_VERTEX_SHADER, shaderCode);
	}
	
	public static int compileFragmentShader(String shaderCode){
		return compileShader(GL_FRAGMENT_SHADER, shaderCode);
	}
	
	
	private static int compileShader(int type, String shaderCode){
		final int shaderObjectId = glCreateShader(type);
		
		//standard method of validation return 0 or use glGetError()
		if(shaderObjectId == 0){
			if(LoggerConfig.ON){
				Log.w(TAG, "Could not create new shader");
			}
			return 0;
		}
		//attach source code to shaderID and compile shader
		glShaderSource(shaderObjectId, shaderCode);
		glCompileShader(shaderObjectId);
		
		//various methods of validation
		final int[] compileStatus = new int[1];
		//store whether compile succeeded or failed in int array
		glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0); 
		
		//print log of shader info from compile, can turn logs on and off
		if(LoggerConfig.ON){
			Log.v(TAG, "Results of compiling source" + "\n" + shaderCode + "\n" + glGetShaderInfoLog(shaderObjectId));
		}
		//check status of compile
		if(compileStatus[0] == 0){
			glDeleteShader(shaderObjectId);
			if(LoggerConfig.ON){
				Log.w(TAG, "Compilation of shader failed.");
			}
			return 0;
		}
		return shaderObjectId;
	}
	
	//linking vertex and fragment shader into one program
	public static int linkProgram(int vertexShaderId, int fragmentShaderId){
		final int programObjectId = glCreateProgram();
	
		if(programObjectId == 0){
			if(LoggerConfig.ON){
				Log.w(TAG, "could not create new program");
			}
			return 0;
		}
		//attach shaders
		glAttachShader(programObjectId, vertexShaderId);
		glAttachShader(programObjectId, fragmentShaderId);
	
		//join shaders together
		glLinkProgram(programObjectId);
		
		//validate link
		//same steps store error status in linkStatus array, get details of compile in log
		//output logs if ON and return 0 if error in linkStatus array
		final int[] linkStatus = new int[1];
		glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0);
		if(LoggerConfig.ON){
			Log.v(TAG, "results of program linking:\n" + glGetProgramInfoLog(programObjectId));
		}
		
		if(linkStatus[0] == 0){
			glDeleteProgram(programObjectId);
			if(LoggerConfig.ON){
				Log.w(TAG, "Linking of program failed");
			}
			return 0;
		}
		return programObjectId;
	}
	
	public static boolean validateProgram(int programObjectId){
		glValidateProgram(programObjectId);
		
		final int[] validateStatus = new int[1];
		//log program
		glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0);
		Log.v(TAG, "Results of validating program: " + validateStatus[0] + "\nLog" + glGetProgramInfoLog(programObjectId));
		
		return validateStatus[0] !=0;
	}
	
}
