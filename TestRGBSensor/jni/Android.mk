LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_LDLIBS := -llog
LOCAL_MODULE    := operate_jni
LOCAL_SRC_FILES := operate_jni.cpp

include $(BUILD_SHARED_LIBRARY)