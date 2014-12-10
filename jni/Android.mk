#UNCOMPLETED
#Only contains the path to the swig Android.mk file which it will invoke swig and build c++ components
#of swig library

#call my-dir will return the directory of this file
LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE:=test1
#include swig/liquidfun library in project
LOCAL_SHARED_LIBRARIES:=libliquidfun_jni
include $(BUILD_SHARED_LIBRARY)
#To build this on your computer change the path below to the path liquidfun libraries are located
#on your computer
$(call import-add-path,/Users/eric/Documents/workspace/liquidfun-1.1.0/liquidfun/)
$(call import-module,Box2D/swig/jni)