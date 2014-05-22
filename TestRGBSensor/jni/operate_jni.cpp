#define LOG_TAG "zyw"

#include <android/log.h>
#include <stdio.h>
#include <jni.h>
#include <string.h>
#include <android/bitmap.h>
#include <assert.h>
#ifndef INT64_C
#define INT64_C(c) (c ## LL)
#define UINT64_C(c) (c ## ULL)
#endif

#include "jnilogger.h"

//extern "C" {
//	#include "libavcodec/avcodec.h"
//	#include "libavformat/avformat.h"
//	#include "libswscale/swscale.h"
//}

static uint8_t *buffer = NULL;
static int target_width = 320;
static int target_height = 240;
static int videoStream = -1;

static uint8_t DEBUG = 1;
static uint8_t flag = 0;

/*
 * Class:     com_example_testrgbsensor_NativeOperate
 * Method:    native_getVersion
 * Signature: ()Ljava/lang/String;
 */
static jstring Java_com_example_testrgbsensor_NativeOperate_native_getVersion(
		JNIEnv * env, jclass thiz) {
	char str[25];
	sprintf(str, "%u", "1.0.0");
	if (DEBUG)
		LOGI(
				">>>>>>>>>>Java_com_example_testrgbsensor_NativeOperate_native_getVersion");
	return env->NewStringUTF(str);
}

/*
 * Class:     com_example_testrgbsensor_NativeOperate
 * Method:    native_getData
 * Signature: ()I
 */
static jint Java_com_example_testrgbsensor_NativeOperate_native_getData(
		JNIEnv * env, jclass thiz) {
	if (DEBUG)
		LOGI(">>>>>> init enter");
	flag++;
	if (flag > 100) {
		flag = 1;
	}
	if (DEBUG)
		LOGI(">>>>>> init exit");

	return flag;
}

/*
 * Class:     com_example_testrgbsensor_NativeOperate
 * Method:    native_init
 * Signature: ()V
 */
static void Java_com_example_testrgbsensor_NativeOperate_init(JNIEnv *env,
		jclass thiz) {
	if (DEBUG)
		LOGI(">>>>>> init enter");

	if (DEBUG)
		LOGI(">>>>>> init exit");
}

// ----------------------------------------------------------------------------
static JNINativeMethod gMethodsNativeOperate[] =
		{ { "native_init", "()V",
				(void *) Java_com_example_testrgbsensor_NativeOperate_init },
				{ "native_getData", "()I",
						(void *) Java_com_example_testrgbsensor_NativeOperate_native_getData },
				{ "native_getVersion", "()Ljava/lang/String;",
						(void *) Java_com_example_testrgbsensor_NativeOperate_native_getVersion },

		};

static const char * const kClassPathNameNativeOperate =
		"com/example/testrgbsensor/NativeOperate";

static int jniRegisterNativeMethods(JNIEnv* env, const char* className,
		const JNINativeMethod* gMethods, int numMethods) {
	jclass clazz;

	LOGI("Registering %s natives\n", className);
	clazz = env->FindClass(className);
	if (clazz == NULL) {
		LOGE("Native registration unable to find class '%s'\n", className);
		return -1;
	}
	if (env->RegisterNatives(clazz, gMethods, numMethods) < 0) {
		LOGE("RegisterNatives failed for '%s'\n", className);
		return -1;
	}
	return 0;
}

static int register_NativeOperate(JNIEnv * env) {
	return jniRegisterNativeMethods(env, kClassPathNameNativeOperate,
			gMethodsNativeOperate,
			sizeof(gMethodsNativeOperate) / sizeof(gMethodsNativeOperate[0]));
}

jint JNI_OnLoad(JavaVM* vm, void* reserved) {
	JNIEnv* env = NULL;
	jint result = -1;

	if (vm->GetEnv((void**) &env, JNI_VERSION_1_4) != JNI_OK) {
		LOGE("ERROR: GetEnv failed!");
		goto bail;
	}

	assert(env != NULL);

	if (register_NativeOperate(env) < 0) {
		LOGE("ERROR: jni native registeration failed!");
		goto bail;
	}

	result = JNI_VERSION_1_4;

	bail: return result;
}

