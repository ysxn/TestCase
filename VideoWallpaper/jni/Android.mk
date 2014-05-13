LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
PATH_TO_FFMPEG_SOURCE:=$(LOCAL_PATH)/ffmpeg
LOCAL_C_INCLUDES += $(PATH_TO_FFMPEG_SOURCE)
LOCAL_LDLIBS := -lffmpeg -llog -ljnigraphics -lsurfaceflinger -lsurfaceflinger_client
LOCAL_MODULE    := ffmpeg_jni
LOCAL_SRC_FILES := ffmpeg_jni.cpp

include $(BUILD_SHARED_LIBRARY)