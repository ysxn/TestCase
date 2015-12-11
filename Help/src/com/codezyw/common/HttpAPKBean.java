
package com.codezyw.common;

import java.io.Serializable;

/**
 * <a href="php.codezyw.com">php.codezyw.com</a>
 */
public class HttpAPKBean implements Serializable {

    private static final long serialVersionUID = -4275153168634891648L;

    private String version_code;
    private String version_name;
    private String package_name;
    private String update_word;
    private String url;

    public HttpAPKBean() {
        super();
    }

    public String getVersion_code() {
        return version_code;
    }

    public String getVersion_name() {
        return version_name;
    }

    public String getPackage_name() {
        return package_name;
    }

    public String getUpdate_word() {
        return update_word;
    }

    public String getUrl() {
        return url;
    }

    public void setVersion_code(String i) {
        this.version_code = i;
    }

    public void setVersion_name(String s) {
        this.version_name = s;
    }

    public void setPackage_name(String s) {
        this.package_name = s;
    }

    public void setUpdate_word(String s) {
        this.update_word = s;
    }

    public void setUrl(String s) {
        this.url = s;
    }
}
