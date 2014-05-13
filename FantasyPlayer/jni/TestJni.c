#include <stdio.h>
#include <jni.h>
#include <string.h>

static int i = 0;
/*
 * Class:     com_zhuyawen_android_ffmpegwrapper_TestJni
 * Method:    init
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_zhuyawen_android_ffmpegwrapper_TestJni_init
  (JNIEnv * env, jclass thiz) 
{
    i = 189;
    return;
}

/*
 * Class:     com_zhuyawen_android_ffmpegwrapper_TestJni
 * Method:    getInt
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_zhuyawen_android_ffmpegwrapper_TestJni_getInt
  (JNIEnv *env, jclass thiz) 
{
    return i++;
}
