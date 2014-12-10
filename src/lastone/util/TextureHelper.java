package lastone.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;
import static android.opengl.GLES20.glGenTextures;
import static android.opengl.GLES20.glDeleteTextures;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glTexParameteri;
import static android.opengl.GLES20.glGenerateMipmap;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES20.GL_LINEAR_MIPMAP_LINEAR;
import static android.opengl.GLES20.GL_LINEAR;


public class TextureHelper {
	private static final String TAG = "TextureHelper";

	
	/* Method will:
	 * Load image data into openGL
	 * return a texture Id to reference image by or 0*/
	public static int loadTexture(Context context, int resourceId){
		final int[] textureObjectIds = new int[1];
		glGenTextures(1,textureObjectIds, 0);
		
		if(textureObjectIds[0] == 0){
			if(LoggerConfig.ON){
				Log.w(TAG, "Could not generate new openGL texture ");
			}
			return 0;
		}
		
		//we have texture object now need to bind bitmap data to it
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inScaled = false;
		
		final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
				resourceId, options);
		
		if(bitmap == null){
			if(LoggerConfig.ON){
				Log.w(TAG, "resource ID: " + resourceId + "could not be decoded");
			}
			
			glDeleteTextures(1, textureObjectIds, 0);
			return 0;
		}
		
		glBindTexture(GL_TEXTURE_2D, textureObjectIds[0]);
		//define filters for image scaling
		//bind mipmap with trilinear filter to min filter 
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		//use bilinear filter for magnified textures
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		//bind bitmap to current texture 
		GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);
		
		//manually free memory
		bitmap.recycle();
		
		//generate mipmap for image
		glGenerateMipmap(GL_TEXTURE_2D);
		
		//unbind texture from image
		glBindTexture(GL_TEXTURE_2D, 0);
		
		return textureObjectIds[0];
	}
}
