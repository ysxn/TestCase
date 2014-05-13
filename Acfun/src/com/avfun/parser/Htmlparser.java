package com.avfun.parser;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.acfun.utils.Debug;

import android.sax.StartElementListener;
import android.util.Log;

public class Htmlparser {
    private static final boolean DEBUG = Debug.HOMEPARSER_DEBUG;
    private static final String TAG = "Htmlparser";
    
    public Htmlparser() {
        if (DEBUG) Log.i(TAG, ">>>>>>>>Htmlparser construction");
    }

    public void parseHome(final String acfunUrl) {
        // TODO Auto-generated method stub
        if (DEBUG) Log.i(TAG, ">>>>>>>>parserHome");
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Connection connection = Jsoup.connect(acfunUrl);
                try {
                    Document document = connection.get();
//                    if (!DEBUG) Log.i(TAG, ">>>>>>>>toString="+document.toString());
//                    if (!DEBUG) Log.i(TAG, ">>>>>>>>outerHtml="+document.outerHtml());
//                    if (!DEBUG) Log.i(TAG, ">>>>>>>>html="+document.html());
                    Elements elements1 = document.getAllElements();
                    Elements elements = document.getElementsByAttributeValueStarting("id", "item-header-guide-");
                    for (Element element : elements) {
                        if (DEBUG) {
                            Log.i(TAG,">>>>>>>>>>>>>>element.toString="+element.toString());
                        }
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
    }
    
    public void parseArticle(final String acfunUrl) {
        // TODO Auto-generated method stub
        if (DEBUG) Log.i(TAG, ">>>>>>>>parseArticle");
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Connection connection = Jsoup.connect(acfunUrl);
                try {
                    Document document = connection.get();
//                    if (!DEBUG) Log.i(TAG, ">>>>>>>>toString="+document.toString());
//                    if (!DEBUG) Log.i(TAG, ">>>>>>>>outerHtml="+document.outerHtml());
//                    if (!DEBUG) Log.i(TAG, ">>>>>>>>html="+document.html());
                    Elements elements = document.getElementsByAttributeValueStarting("class", "item-list-area");
                    for (Element element : elements) {
                        if (DEBUG) {
                            Log.i(TAG,">>>>>>>>>>>>>>element.toString="+element.toString());
                        }
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
    }
}