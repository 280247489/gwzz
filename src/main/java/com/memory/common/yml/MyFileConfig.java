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


    private String upload_local_path;
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
}
