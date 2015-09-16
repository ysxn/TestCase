LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_STATIC_LIBRARIES := libavformat libavcodec libavutil libpostproc libswscale
LOCAL_SRC_FILES := settings.c
LOCAL_MODULE := ffmpeg
LOCAL_LDLIBS := -llog
LOCAL_ARM_MODE := arm
include $(BUILD_SHARED_LIBRARY)
include $(call all-makefiles-under,$(LOCAL_PATH))