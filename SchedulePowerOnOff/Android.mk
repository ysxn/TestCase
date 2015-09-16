LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_JAVA_LIBRARIES := schpwronoff_pluginlib
LOCAL_STATIC_JAVA_LIBRARIES +=schpwronoff_searchindexablelib

LOCAL_MODULE_TAGS := optional

LOCAL_SRC_FILES := $(call all-java-files-under, src)

LOCAL_PACKAGE_NAME := SchedulePowerOnOff
LOCAL_CERTIFICATE := platform
LOCAL_MODULE_PATH :=$(TARGET_OUT_APP)
include $(BUILD_PACKAGE)
include $(CLEAR_VARS)
LOCAL_MODULE_TAGS := optional

LOCAL_PREBUILT_JAVA_LIBRARIES := schpwronoff_pluginlib:libs/settings-plugin.jar\
                                 schpwronoff_searchindexablelib:libs/search-indexables.jar
include $(BUILD_MULTI_PREBUILT)
# Use the folloing include to make our test apk.
include $(call all-makefiles-under,$(LOCAL_PATH))
