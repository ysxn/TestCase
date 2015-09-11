
package com.codezyw.common;

import java.io.File;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class OpenFileHelper {
    public static final String[] fileEndingImage = {
        ".png",
        ".gif",
        ".jpg",
        ".jpeg",
        ".bmp",
    };
    
    public static final String[] fileEndingAudio = {
        ".mp3",
        ".wav",
        ".ogg",
        ".midi",
    };
    
    public static final String[] fileEndingVideo = {
        ".mp4",
        ".rmvb",
        ".avi",
        ".flv",
    };
    
    public static final String[] fileEndingPackage = {
        ".jar",
        ".zip",
        ".rar",
        ".gz",
        ".apk",
        ".img",
    };
    
    public static final String[] fileEndingWebText = {
        ".htm",
        ".html",
        ".php",
        ".jsp",
    };
    
    public static final String[] fileEndingText = {
        ".txt",
        ".java",
        ".c",
        ".cpp",
        ".py",
        ".xml",
        ".json",
        ".log",
    };
    
    public static final String[] fileEndingWord = {
        ".doc",
        ".docx",
    };
    
    public static final String[] fileEndingExcel = {
        ".xls",
        ".xlsx",
    };
    
    public static final String[] fileEndingPPT = {
        ".ppt",
        ".pptx",
    };
    
    public static final String[] fileEndingPdf = {
        ".pdf",
    };

    public static boolean checkEndsWithInStringArray(String checkItsEnd, String[] fileEndings) {
        for (String aEnd : fileEndings) {
            if (checkItsEnd.endsWith(aEnd))
                return true;
        }
        return false;
    }

    public static void tryOpenFile(Context c, File f) {
        if (f != null && f.isFile()) {
            String fileName = f.getName();
            Intent intent;
            if (checkEndsWithInStringArray(fileName, fileEndingImage)) {
                intent = OpenFileHelper.getImageFileIntent(f);
                c.startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, fileEndingWebText)) {
                intent = OpenFileHelper.getHtmlFileIntent(f);
                c.startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, fileEndingPackage)) {
                intent = OpenFileHelper.getApkFileIntent(f);
                c.startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, fileEndingAudio)) {
                intent = OpenFileHelper.getAudioFileIntent(f);
                c.startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, fileEndingVideo)) {
                intent = OpenFileHelper.getVideoFileIntent(f);
                c.startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, fileEndingText)) {
                intent = OpenFileHelper.getTextFileIntent(f);
                c.startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, fileEndingPdf)) {
                intent = OpenFileHelper.getPdfFileIntent(f);
                c.startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, fileEndingWord)) {
                intent = OpenFileHelper.getWordFileIntent(f);
                c.startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, fileEndingExcel)) {
                intent = OpenFileHelper.getExcelFileIntent(f);
                c.startActivity(intent);
            } else if (checkEndsWithInStringArray(fileName, fileEndingPPT)) {
                intent = OpenFileHelper.getPPTFileIntent(f);
                c.startActivity(intent);
            } else {
                try {
                    intent = OpenFileHelper.getTextFileIntent(f);
                    c.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static Intent getHtmlFileIntent(File file) {
        Uri uri = Uri.parse(file.toString()).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(file.toString())
                .build();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "text/html");
        return intent;
    }

    public static Intent getImageFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    public static Intent getPdfFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }

    public static Intent getTextFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "text/plain");
        return intent;
    }

    public static Intent getAudioFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }

    public static Intent getVideoFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "video/*");
        return intent;
    }

    public static Intent getChmFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }

    public static Intent getWordFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    public static Intent getExcelFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    public static Intent getPPTFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    public static Intent getApkFileIntent(File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        return intent;
    }

}
