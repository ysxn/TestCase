#!/bin/bash

FFCONFIG_COMMON='
	--enable-version3
	--disable-gpl
	--disable-nonfree
	--disable-debug
	--disable-ffmpeg
	--disable-ffplay
	--disable-ffserver
	--disable-ffprobe
	--disable-encoders
	--disable-muxers
	--disable-devices
	--disable-protocols
	--enable-protocol=file
	--enable-swscale
	--disable-avfilter
	--disable-network
	--enable-mpegaudio-hp
	--disable-avdevice
	--enable-parsers
	--enable-decoders
	--enable-demuxers
'

