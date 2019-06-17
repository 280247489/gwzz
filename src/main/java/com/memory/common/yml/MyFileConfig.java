package com.memory.common.yml;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

/**
 * @author INS6+
 * @date 2019/5/15 17:11
 */

@Component
@ConfigurationProperties(prefix = "file")
public class MyFileConfig {

    /**
     * file:
     *   upload_local_path: G:/upload/course
     *   upload_local_article_path: G:/upload/article
     *   jodit:
     *     path: G:/upload/jodit/course
     *     base_url: http://192.168.1.119:8091/file/jodit/course
     *     path_article: G:/upload/jodit/article
     *     base_url_article: http://192.168.1.119:8091/file/jodit/article
     */
    /**
     *   show_url:  http://192.168.1.119:8091/file/
     *   cms_path: cms
     *   gwzz_path: gwzz
     * @param parent_path
     * @param dir
     * @return
     */



    private String upload_local_path;
    private String cms_path;
    private String gwzz_path;
    private String show_url;
    private Map<String,String> jodit =new HashMap<String,String>();


    public String getUpload_local_path() {
        return upload_local_path;
    }

    public void setUpload_local_path(String upload_local_path) {
        this.upload_local_path = upload_local_path;
    }

    public Map<String, String> getJodit() {
        return jodit;
    }

    public void setJodit(Map<String, String> jodit) {
        this.jodit = jodit;
    }

    public String getCms_path() {
        return cms_path;
    }

    public void setCms_path(String cms_path) {
        this.cms_path = cms_path;
    }

    public String getGwzz_path() {
        return gwzz_path;
    }

    public void setGwzz_path(String gwzz_path) {
        this.gwzz_path = gwzz_path;
    }

    public String getShow_url() {
        return show_url;
    }

    public void setShow_url(String show_url) {
        this.show_url = show_url;
    }
}
