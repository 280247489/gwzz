package com.memory.common.yml;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author INS6+
 * @date 2019/5/15 17:11
 */

@Component
@ConfigurationProperties(prefix = "filesdfsdfs")
public class MyFileConfig {

  /*
      file:
          upload_local_path:  G:/upload
          jodit:
            path: G:/upload/jodit
            base_url: http://192.168.1.119:8091/file/jodit/
   */
    @Value("${}")
    private String upload_local_path;
    private Map<String,String> jodits = new HashMap<String,String>();

    public String getUpload_local_path() {
        return upload_local_path;
    }

    public void setUpload_local_path(String upload_local_path) {
        this.upload_local_path = upload_local_path;
    }

    public Map<String, String> getJodits() {
        return jodits;
    }

    public void setJodits(Map<String, String> jodits) {
        this.jodits = jodits;
    }
}
