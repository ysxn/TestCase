#!/bin/bash

source path.sh
source config_common.sh

cd ffmpeg

./configure --target-os=linux \
	--arch=arm \
	--enable-shared \
	--enable-cross-compile \
	--strip=$PREBUILT/bin/arm-linux-androideabi-strip \
	--cc=$PREBUILT/bin/arm-linux-androideabi-gcc \
	--cross-prefix=$PREBUILT/bin/arm-linux-androideabi- \
	--extra-cflags="-fPIC -DANDROID" \
	--extra-ldflags="-Wl,-T,$PREBUILT/windows/arm-eabi/lib/ldscripts/armelf.x -Wl,-rpath-link=$PLATFORM/usr/lib -L$PLATFORM/usr/lib -nostdlib $PREBUILT/windows/lib/gcc/arm-eabi/4.4.0/crtbegin.o $PREBUILT/windows/lib/gcc/arm-eabi/4.4.0/crtend.o -lc -lm -ldl" \
	$FFCONFIG_COMMON

sed -i "s/HAVE_SYMVER 1/HAVE_SYMVER 0/g" config.h
sed -i "s/HAVE_SYMVER_GNU_ASM 1/HAVE_SYMVER_GNU_ASM 0/g" config.h
sed -i "s/#define restrict restrict/#define restrict/g" config.h
sed -i "s/HAVE_LRINT 0/HAVE_LRINT 1/g" config.h
sed -i "s/HAVE_LRINTF 0/HAVE_LRINTF 1/g" config.h
sed -i "s/HAVE_ROUND 0/HAVE_ROUND 1/g" config.h
sed -i "s/HAVE_ROUNDF 0/HAVE_ROUNDF 1/g" config.h
sed -i "s/HAVE_TRUNC 0/HAVE_TRUNC 1/g" config.h
sed -i "s/HAVE_TRUNCF 0/HAVE_TRUNCF 1/g" config.h

cd ..

echo "APP_ABI := armeabi" > Application.mk
ndk-build clean
