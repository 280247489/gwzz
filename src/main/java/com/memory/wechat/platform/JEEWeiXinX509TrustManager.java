package com.memory.wechat.platform;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
/**
 * @author INS6+
 * @date 2019/6/4 13:28
 */

public class JEEWeiXinX509TrustManager  implements X509TrustManager  {
    public void checkClientTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
    }

    public void checkServerTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
    }

    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

}
