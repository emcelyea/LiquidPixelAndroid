LiquidPixelAndroid
==================
Powered By LiquidFun!

This is a pretty bare bones application using openGLES 2.0 and LiquidFun. If you are interested in Rendering graphics with openGL on android or trying to figure out how to use LiquidFun and SWIG this is the simplest way to go about it.

Water particles are created as square ParticleGroups and rendered as GL_Points so there is only one call to render all particles on screen. The World and particleSystem are not thread safe so everything is shoved into the renderer class to keep it simple.

Developers who want to use LiquidFun on android but not download and install SWIG, download liquidfun libraries and write makeFiles and build everything themselves because its a pain. You can grab from this project:

src>com.google.fpl.liquidfun package

src>cpp package

src>cpp.armeabi package

src>cpp.mips package

src>cpp.x86 package

libs>armeabi folder

libs>armeabi-v7a folder

libs>mips folder

libs>x86 folder

Throw these all into the same locations in your project then add:

static{ System.loadLibrary("liquidfun"); System.loadLibrary("liquidfun_jni");}

Inside of your main activity and you should be good to use all the SWIG generated liquidfun functions from java. Look at my code and https://github.com/google/liquidfunpaint (way better but more complicated) for examples of using it.
