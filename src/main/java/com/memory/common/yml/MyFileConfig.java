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


    private String upload_local_path;
    private String upload_local_article_path;
    private String upload_local_path_xiaozhushou;
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

    public String getUpload_local_article_path() {
        return upload_local_article_path;
    }

    public void setUpload_local_article_path(String upload_local_article_path) {
        this.upload_local_article_path = upload_local_article_path;
    }

    public String getUpload_local_path_xiaozhushou() {
        return upload_local_path_xiaozhushou;
    }

    public void setUpload_local_path_xiaozhushou(String upload_local_path_xiaozhushou) {
        this.upload_local_path_xiaozhushou = upload_local_path_xiaozhushou;
    }
}
