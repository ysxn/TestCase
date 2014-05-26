/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_zhuyawen_android_ffmpegwrapper_EngineWrapper */

#ifndef _Included_com_zhuyawen_android_ffmpegwrapper_EngineWrapper
#define _Included_com_zhuyawen_android_ffmpegwrapper_EngineWrapper
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_zhuyawen_android_ffmpegwrapper_EngineWrapper
 * Method:    close
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_zhuyawen_android_ffmpegwrapper_EngineWrapper_close
  (JNIEnv *, jclass);

/*
 * Class:     com_zhuyawen_android_ffmpegwrapper_EngineWrapper
 * Method:    convertFrameData
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_zhuyawen_android_ffmpegwrapper_EngineWrapper_convertFrameData
  (JNIEnv *, jclass);

/*
 * Class:     com_zhuyawen_android_ffmpegwrapper_EngineWrapper
 * Method:    getDTS
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_zhuyawen_android_ffmpegwrapper_EngineWrapper_getDTS
  (JNIEnv *, jclass);

/*
 * Class:     com_zhuyawen_android_ffmpegwrapper_EngineWrapper
 * Method:    getDirectFrameData
 * Signature: (Ljava/lang/Object;IIII)V
 */
JNIEXPORT void JNICALL Java_com_zhuyawen_android_ffmpegwrapper_EngineWrapper_getDirectFrameData
  (JNIEnv *, jclass, jobject, jint, jint, jint, jint);

/*
 * Class:     com_zhuyawen_android_ffmpegwrapper_EngineWrapper
 * Method:    getDuration
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_zhuyawen_android_ffmpegwrapper_EngineWrapper_getDuration
  (JNIEnv *, jclass);

/*
 * Class:     com_zhuyawen_android_ffmpegwrapper_EngineWrapper
 * Method:    getNextFrame
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_zhuyawen_android_ffmpegwrapper_EngineWrapper_getNextFrame
  (JNIEnv *, jclass);

/*
 * Class:     com_zhuyawen_android_ffmpegwrapper_EngineWrapper
 * Method:    getVideoFrameRate
 * Signature: ()D
 */
JNIEXPORT jdouble JNICALL Java_com_zhuyawen_android_ffmpegwrapper_EngineWrapper_getVideoFrameRate
  (JNIEnv *, jclass);

/*
 * Class:     com_zhuyawen_android_ffmpegwrapper_EngineWrapper
 * Method:    getVideoHeight
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_zhuyawen_android_ffmpegwrapper_EngineWrapper_getVideoHeight
  (JNIEnv *, jclass);

/*
 * Class:     com_zhuyawen_android_ffmpegwrapper_EngineWrapper
 * Method:    getVideoWidth
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_zhuyawen_android_ffmpegwrapper_EngineWrapper_getVideoWidth
  (JNIEnv *, jclass);

/*
 * Class:     com_zhuyawen_android_ffmpegwrapper_EngineWrapper
 * Method:    init
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_zhuyawen_android_ffmpegwrapper_EngineWrapper_init
  (JNIEnv *, jclass);

/*
 * Class:     com_zhuyawen_android_ffmpegwrapper_EngineWrapper
 * Method:    open
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_zhuyawen_android_ffmpegwrapper_EngineWrapper_open
  (JNIEnv *, jclass, jstring);

#ifdef __cplusplus
}
#endif
#endif