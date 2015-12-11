
package com.codezyw.common;

import java.io.Serializable;
import java.util.List;

/**
 * <a href="php.codezyw.com">php.codezyw.com</a>
 */
public class HttpAdvBean implements Serializable {

    private static final long serialVersionUID = -1730701982915028282L;

    private int version;

    private List<DataBean> data;

    public HttpAdvBean() {
        super();
    }

    public int getVersion() {
        return version;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setVersion(int is) {
        this.version = is;
    }

    public void setData(List<DataBean> l) {
        this.data = l;
    }

    public static class DataBean {

        private String type;

        private String click;

        private String content;

        private String url;

        public DataBean() {
            super();
        }

        public String getType() {
            return type;
        }

        public String getClick() {
            return click;
        }

        public String getContent() {
            return content;
        }

        public String getUrl() {
            return url;
        }

        public void setType(String i) {
            this.type = i;
        }

        public void setClick(String s) {
            this.click = s;
        }

        public void setContent(String s) {
            this.content = s;
        }

        public void setUrl(String s) {
            this.url = s;
        }
    }
}
