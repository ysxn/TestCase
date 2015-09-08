
package com.codezyw.common;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;

public class ProgressMultipartEntity extends MultipartEntity {

    private ProgressListener listener;

    private volatile static boolean  mCancel = false;

    public static interface ProgressListener {
        void transferred(long num);
    }
    
    public static void enable() {
    	mCancel = false;
    }
    
    public static void cancel() {
    	mCancel = true;
    }

    public ProgressMultipartEntity() {
        super();
    }

    public ProgressMultipartEntity(final HttpMultipartMode mode) {
        super(mode);
    }

    public ProgressMultipartEntity(HttpMultipartMode mode, final String boundary, final Charset charset) {
        super(mode, boundary, charset);
    }

    public void setListener(final ProgressListener listener) {
        this.listener = listener;
    }

    @Override
    public void writeTo(OutputStream outstream) throws IOException {
        super.writeTo(new CountingOutputStream(outstream, this.listener));
    }

    public static class CountingOutputStream extends FilterOutputStream {

        private final ProgressListener listener;

        private long transferred;

        public CountingOutputStream(final OutputStream out, final ProgressListener listener) {
            super(out);
            this.listener = listener;
            this.transferred = 0;
        }

        public void write(byte[] b, int off, int len) throws IOException {
        	if (mCancel) {
        		out.close();
        		return;
        	}
        	int last = off + len;
        	int index = off;
        	while (index <= last - 1 && !mCancel) {
	            out.write(b, index, 1);
	            index += 1;
	            this.transferred += 1;
	            if (this.listener != null) {
	                this.listener.transferred(this.transferred);
	            }
        	}
        }

        public void write(int b) throws IOException {
        	if (mCancel) {
        		out.close();
        		return;
        	}
            out.write(b);
            this.transferred++;
            if (this.listener != null) {
                this.listener.transferred(this.transferred);
            }
        }
    }

}
