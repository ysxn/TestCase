#!/bin/sh
# AUTO-GENERATED FILE, DO NOT EDIT!
if [ -f $1.org ]; then
  sed -e 's!^D:/cygwin_old/lib!/usr/lib!ig;s! D:/cygwin_old/lib! /usr/lib!ig;s!^D:/cygwin_old/bin!/usr/bin!ig;s! D:/cygwin_old/bin! /usr/bin!ig;s!^D:/cygwin_old/!/!ig;s! D:/cygwin_old/! /!ig;s!^Z:!/cygdrive/z!ig;s! Z:! /cygdrive/z!ig;s!^X:!/cygdrive/x!ig;s! X:! /cygdrive/x!ig;s!^D:!/cygdrive/d!ig;s! D:! /cygdrive/d!ig;s!^C:!/cygdrive/c!ig;s! C:! /cygdrive/c!ig;' $1.org > $1 && rm -f $1.org
fi
