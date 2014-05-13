#define LOG_TAG "TestJni_jni"


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



#include "Jnilogger.h"

extern "C" {
	#include "libavcodec/avcodec.h"
	#include "libavformat/avformat.h"
	#include "libswscale/swscale.h"
}

static AVFormatContext *pFormatCtx = NULL;
static AVCodecContext *pCodecCtx = NULL;
static AVFrame *pFrame = NULL;
static AVFrame *pFrameRGB = NULL;
static uint8_t *buffer = NULL;
static int target_width = 320;
static int target_height = 240;
static struct SwsContext *img_convert_ctx = NULL;
static int videoStream = -1;
static uint8_t debug = 1;



static void fill_bitmap(AndroidBitmapInfo* info, void *pixels, AVFrame* pFrame)
{
    uint8_t *frameLine = NULL;
    int yy;
    if (debug) LOGI("fill bitmap start");
    for (yy = 0; yy < info->height; yy++)
    {
        uint8_t *line = (uint8_t *)pixels;
        frameLine = (uint8_t *)pFrame->data[0] + (yy * pFrame->linesize[0]);

        int xx;
        for (xx = 0; xx < info->width; xx++)
        {
            int out_offset = xx * 4;
            int in_offset = xx * 3;

            line[out_offset] = frameLine[in_offset];
            line[out_offset+1] = frameLine[in_offset+1];
            line[out_offset+2] = frameLine[in_offset+2];
            line[out_offset+3] = 255;
            
        }
        pixels = (char*)pixels + info->stride;
    }
    if (debug) LOGI("fill bitmap end");
}


/*
 * Class:     com_testjni_TestJni
 * Method:    init
 * Signature: ()V
 */
static void
Java_com_testjni_TestJni_init
  (JNIEnv * env, jclass thiz) 
{
    av_register_all();
    return;
}

/*
 * Class:     com_testjni_TestJni
 * Method:    getInt
 * Signature: ()I
 */
static jint
Java_com_testjni_TestJni_getTargetWidth
  (JNIEnv * env, jclass thiz) 
{
    return target_width;
}

/*
 * Class:     com_testjni_TestJni
 * Method:    getInt
 * Signature: ()I
 */
static jint
Java_com_testjni_TestJni_getTargetHeight
  (JNIEnv * env, jclass thiz) 
{
    return target_height;
}


/*
 * Class:     com_testjni_TestJni
 * Method:    getVersion
 * Signature: ()Ljava/lang/String;
 */
static jstring
Java_com_testjni_TestJni_getVersion
  (JNIEnv * env, jclass thiz) 
{
    char str[25];
    sprintf(str, "%u", avcodec_version());
    if (debug) LOGI(">>>>>>>>>>Java_com_testjni_TestJni_getVersion");
    return env->NewStringUTF(str);
}

/*
 * Class:     com_testjni_TestJni
 * Method:    getTimeBase
 * Signature: ()F
 */
JNIEXPORT jfloat JNICALL Java_com_testjni_TestJni_getTimeBase
  (JNIEnv * env, jclass thiz) 
{
    if (pFormatCtx == NULL || videoStream == -1) {
        return -1;
    } else {
        return (float)(pFormatCtx->streams[videoStream]->time_base.den) / (float)(pFormatCtx->streams[videoStream]->time_base.num);
    }
}


/*
 * Class:     com_testjni_TestJni
 * Method:    openFile
 * Signature: (Ljava/lang/String;III)I
 */
static jint
Java_com_testjni_TestJni_openFile
  (JNIEnv * env, jclass thiz, jstring path, jint screenWidth, jint screenHeight 
  , jint fixWH)
{    
    int ret;
    int err;
    int i;
    AVCodec *pCodec = NULL;

    int numBytes;
    const char *pathStr= env->GetStringUTFChars(path, NULL);
    if (debug) LOGI("open file start");
    if (debug) LOGI(">>>>>>>>Java_com_testjni_TestJni_openFile %s fixWH=%i", 
    pathStr, fixWH);

    //
    err = avformat_open_input(&pFormatCtx, pathStr, NULL, NULL);
    if (err != 0)
    {
        if (debug) LOGE("open file error");
        return -1;
    }
    if (debug) LOGI("open file ok");
    
    if (avformat_find_stream_info(pFormatCtx, NULL) < 0)
    {
        if (debug) LOGE("find stream info error");
        return -1;
    }
    if (debug) LOGI("find stream info ok");

    videoStream = -1;
    for (i = 0;i < pFormatCtx->nb_streams; i++)
    {
        if (pFormatCtx->streams[i]->codec->codec_type == AVMEDIA_TYPE_VIDEO)
        {
            videoStream = i;
            break;
        }
    }
    
    if (videoStream == -1) 
    {
        if (debug) LOGE("not find video stream");
        return -1;
    }
    if (debug) LOGI("find video stream ok == %i, total stream == %u bitrate = %i duration = %lli start_time = %lli packet_size = %u max_delay = %i max_analyze_duration = %i", 
        videoStream, pFormatCtx->nb_streams, pFormatCtx->bit_rate,
        pFormatCtx->duration, pFormatCtx->start_time,
        pFormatCtx->packet_size, pFormatCtx->max_delay,
        pFormatCtx->max_analyze_duration);
    
    if (debug) LOGI("find videoStream info num/den=%lli-%lli time_base = %i/%i start_time== %lli, duration == %lli nb_frames = %lli disposition = %i", 
        (int64_t)(pFormatCtx->streams[videoStream]->time_base.num),
        (int64_t)(pFormatCtx->streams[videoStream]->time_base.den),
        pFormatCtx->streams[videoStream]->time_base.num,
        pFormatCtx->streams[videoStream]->time_base.den,
        pFormatCtx->streams[videoStream]->start_time,
        pFormatCtx->streams[videoStream]->duration,
        pFormatCtx->streams[videoStream]->nb_frames,
        pFormatCtx->streams[videoStream]->disposition);

    pCodecCtx = pFormatCtx->streams[videoStream]->codec;

    if (fixWH == 3) {
        target_width = screenWidth;
        target_height = screenHeight;
    } else if (fixWH == 2) {
        target_width = screenWidth;
        target_height =  screenWidth * (pCodecCtx->height) / (pCodecCtx->width);
    } else if (fixWH == 1) {
        target_width = screenHeight * (pCodecCtx->width) / (pCodecCtx->height);
        target_height =  screenHeight;
    } else {
        target_width = pCodecCtx->width;
        target_height = pCodecCtx->height;
    }
    if (debug) LOGI("video is [%i x %i] screen is [%i x %i] target is [%i x %i]", 
        pCodecCtx->width, pCodecCtx->height, screenWidth, screenHeight,
        target_width, target_height);

    
    img_convert_ctx = sws_getContext(pCodecCtx->width, pCodecCtx->height, pCodecCtx->pix_fmt,
        target_width, target_height, PIX_FMT_RGB24, SWS_BICUBIC, NULL, NULL, NULL);
    if (img_convert_ctx == NULL)
    {
        if (debug) LOGE("init convertion context error");
        return -1;
    }
    pCodec = avcodec_find_decoder(pCodecCtx->codec_id);
    if (pCodec == NULL)
    {
        if (debug) LOGE("find coedc error");
        return -1;
    }
    if (debug) LOGI("find codec ok");

    if (avcodec_open2(pCodecCtx, pCodec, NULL) < 0)
    {
        if (debug) LOGE("open codec error");
        return -1;
    }
    if (debug) LOGI("open codec ok");

    pFrame = avcodec_alloc_frame();
    if (pFrame == NULL) 
    {
        if (debug) LOGE("pFrame alloc error");
        return -1;
    }
    pFrameRGB = avcodec_alloc_frame();
    if (pFrameRGB == NULL)
    {
        if (debug) LOGE("pFrameRGB alloc error");
        return -1;
    }

    if (debug) LOGI("video size is [%i x %i]", pCodecCtx->width, pCodecCtx->height);
    //
    numBytes = avpicture_get_size(PIX_FMT_RGB24, target_width, target_height);
    buffer = (uint8_t*)av_malloc(numBytes * sizeof(uint8_t));
    
    ret = avpicture_fill((AVPicture *) pFrameRGB, buffer, 
        PIX_FMT_RGB24, target_width, target_height);
    if (debug) LOGI("alloc pFrameRGB mem %i bytes, avpicture_fill return %i", numBytes, ret);
    
    env->ReleaseStringUTFChars(path, pathStr);
    if (debug) LOGI("open file end");
    return 0;
}

/*
 * Class:     com_testjni_TestJni
 * Method:    drawFrame
 * Signature: (Landroid/graphics/Bitmap;)I
 */
static jint 
Java_com_testjni_TestJni_drawFrame
  (JNIEnv * env, jclass thiz, jobject bitmap)
{
    AndroidBitmapInfo info;
    void* pixels = NULL;
    int ret;

    int err;
    int i;
    int frameFinished;
    AVPacket packet;

//    int64_t seek_target;
    if (debug) LOGI("drawFrame start");

    if ((ret = AndroidBitmap_getInfo(env, bitmap, &info)) < 0)
    {
        if (debug) LOGE("getBitmap error = %i", ret);
        return -1;
    }

    if ((ret = AndroidBitmap_lockPixels(env, bitmap, &pixels)) < 0)
    {
        if (debug) LOGE("lock bitmap error = %i", ret);
        return -1;
    }

    if (debug) LOGI("drawframe bitmap info [height=%u width=%u stride=%u format=%i  flag=%u]", 
        info.height, info.width, info.stride, info.format, info.flags);

    i = 0;
    while((i == 0) && (ret = av_read_frame(pFormatCtx, &packet) >= 0)) 
    {
        if (debug) LOGI("while loop");
        if (packet.stream_index == videoStream)
        {
            avcodec_decode_video2(pCodecCtx, pFrame, &frameFinished, &packet);
            if (debug) LOGI("this frame pts=%lli pkt_dts=%lli pkt_pts=%lli packet.pts = %lli packet.dts = %lli pict_type=%i format=%i key_frame=%i ",
                pFrame->pts, 
                pFrame->pkt_dts, pFrame->pkt_pts, 
                packet.pts, packet.dts, pFrame->pict_type, 
                pFrame->format, pFrame->key_frame);
            if (frameFinished)
            {
                sws_scale(img_convert_ctx, (const uint8_t* const*)pFrame->data, pFrame->linesize, 
                    0, pCodecCtx->height, pFrameRGB->data, pFrameRGB->linesize);
                fill_bitmap(&info, pixels, pFrameRGB);
                i = 1;
                
            }
        }
        av_free_packet(&packet);
    }
    AndroidBitmap_unlockPixels(env, bitmap);
    if (debug) LOGI("drawFrame end");
    return ret;
}

static int seek_frame(int tsms)
{
    int64_t frame;
    int ret;

    if (debug) LOGI("before av_rescale frame = %lli tsms = %i time_base=%i/%i   file flags = %i", 
        frame, tsms, 
        pFormatCtx->streams[videoStream]->time_base.num, 
        pFormatCtx->streams[videoStream]->time_base.den, 
        pFormatCtx->iformat->flags);
    frame = av_rescale(tsms, pFormatCtx->streams[videoStream]->time_base.den,
        pFormatCtx->streams[videoStream]->time_base.num);
    if (debug) LOGI("after av_rescale frame = %lli tsms = %i time_base=%i/%i", 
        frame, tsms, 
        pFormatCtx->streams[videoStream]->time_base.num, 
        pFormatCtx->streams[videoStream]->time_base.den);
    
    frame = frame * 0.001;
    if (debug) LOGI("after after av_rescale frame = %lli tsms = %i time_base=%i/%i", 
        frame, tsms, 
        pFormatCtx->streams[videoStream]->time_base.num, 
        pFormatCtx->streams[videoStream]->time_base.den);

    if ((ret = av_seek_frame(pFormatCtx, videoStream, frame, AVSEEK_FLAG_FRAME)) < 0) // (avformat_seek_file(pFormatCtx, videoStream, 0, frame, frame, AVSEEK_FLAG_FRAME) < 0)
    {
        return -1;
    }
    if (debug) LOGI("seek return %i", ret);
    avcodec_flush_buffers(pCodecCtx);
    return 1;
}

/*
 * Class:     com_testjni_TestJni
 * Method:    seek_frame
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_testjni_TestJni_seekTo
   (JNIEnv * env, jclass thiz, jint secs)
{
    return seek_frame(secs * 1000);
}

/*
 * Class:     com_testjni_TestJni
 * Method:    drawFrameAt
 * Signature: (Landroid/graphics/Bitmap;I)I
 */
static jint
Java_com_testjni_TestJni_drawFrameAt
  (JNIEnv * env, jclass thiz, jobject bitmap, jint secs)
{
    AndroidBitmapInfo info;
    void* pixels = NULL;
    int ret;
    int err;
    int i;
    int frameFinished = 0;
    AVPacket packet;

//    int64_t seek_target;

    
    if (debug) LOGI("drawFrameAt start");
    if ((ret = AndroidBitmap_getInfo(env, bitmap, &info)) < 0)
    {
        if (debug) LOGE("getInfo error = %i", ret);
        return -1;
    }
    
    if ((ret = AndroidBitmap_lockPixels(env, bitmap, &pixels)) < 0)
    {
        if (debug) LOGE("lock bitmap error = %i", ret);
        return -1;
    }
    if (debug) LOGI("info bitmap is = [height=%u width=%u stride=%u format=%i flag=%u]",
        info.height, info.width, info.stride, info.format, info.flags);

    if (seek_frame(secs * 1000) < 0) 
    {
        if (debug) LOGE("seek error");
        return -1;
    }
    
    i = 0;
    
    while((i == 0) && (ret = av_read_frame(pFormatCtx, &packet) >= 0))
    {
        if (debug) LOGI("while loop");
        if (packet.stream_index == videoStream)
        {
            avcodec_decode_video2(pCodecCtx, pFrame, &frameFinished, &packet);
            if (debug) LOGI("this frame pts=%lli pkt_dts=%lli pkt_pts=%lli packet.pts = %lli packet.dts = %lli pict_type=%i format=%i key_frame=%i ",
                pFrame->pts, 
                pFrame->pkt_dts, pFrame->pkt_pts, 
                packet.pts, packet.dts, pFrame->pict_type, 
                pFrame->format, pFrame->key_frame);
            if (frameFinished)
            {
                sws_scale(img_convert_ctx, (const uint8_t* const*)pFrame->data, pFrame->linesize, 
                    0, pCodecCtx->height, pFrameRGB->data, pFrameRGB->linesize);
                fill_bitmap(&info, pixels, pFrameRGB);
                i = 1;
                
            }
        }
        av_free_packet(&packet);
        
    }
    AndroidBitmap_unlockPixels(env, bitmap);
    if (debug) LOGI("drawFrameAt end");
    return ret;
}

/*
 * Class:     com_testjni_TestJni
 * Method:    exit
 * Signature: ()V
 */
static void
Java_com_testjni_TestJni_exit
  (JNIEnv *env, jclass thiz)
{
    if (buffer != NULL) 
    {
        av_free(buffer);
        buffer = NULL;
    }
    if (pFrame != NULL) 
    {
        av_free(pFrame);
        pFrame = NULL;
    }
    if (pFrameRGB != NULL) 
    {
        av_free(pFrameRGB);
        pFrameRGB = NULL;
    }
    
    if (pCodecCtx != NULL) 
    {
        if (avcodec_close(pCodecCtx) < 0)
            LOGE("close avCodec error");
        pCodecCtx = NULL;
    }
    if (pFormatCtx != NULL) 
    {
        avformat_close_input(&pFormatCtx);
        //av_free(pFormatCtx);
        pFormatCtx = NULL;
    }
    if (debug) LOGI("jni exit");
}

// ----------------------------------------------------------------------------
static JNINativeMethod gMethods[] = {
        {"init",    "()V",   (void *)Java_com_testjni_TestJni_init},
        {"exit",    "()V",  (void *)Java_com_testjni_TestJni_exit},
        {"openFile",    "(Ljava/lang/String;III)I",  (void *)Java_com_testjni_TestJni_openFile},
        {"drawFrame",    "(Landroid/graphics/Bitmap;)I",  (void *)Java_com_testjni_TestJni_drawFrame},
        {"drawFrameAt",    "(Landroid/graphics/Bitmap;I)I",  (void *)Java_com_testjni_TestJni_drawFrameAt},
        {"seekTo",    "(I)I",  (void *)Java_com_testjni_TestJni_seekTo},
        {"getTargetWidth",    "()I",  (void *)Java_com_testjni_TestJni_getTargetWidth},
        {"getTargetHeight",    "()I",  (void *)Java_com_testjni_TestJni_getTargetHeight},
        {"getVersion",    "()Ljava/lang/String;",  (void *)Java_com_testjni_TestJni_getVersion},
        {"getTimeBase",    "()F",  (void *)Java_com_testjni_TestJni_getTimeBase},
};

static const char * const kClassPathName = "com/testjni/TestJni";

static int jniRegisterNativeMethods(JNIEnv* env, const char* className,
    const JNINativeMethod* gMethods, int numMethods)
{
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


static int register_com_testjni(JNIEnv * env)
{
    return jniRegisterNativeMethods(env, 
                kClassPathName, gMethods, sizeof(gMethods)/sizeof(gMethods[0]));
}

jint JNI_OnLoad(JavaVM* vm, void* reserved)
{
    JNIEnv* env = NULL;
    jint result = -1;

    if (vm->GetEnv((void**)&env, JNI_VERSION_1_4) != JNI_OK)
    {
        LOGE("ERROR: GetEnv failed!");
        goto bail;
    }

    assert(env != NULL);

    if (register_com_testjni(env) < 0){
        LOGE("ERROR: jni native registeration failed!");
        goto bail;
    }

    result = JNI_VERSION_1_4;

bail:
    return result;
}

