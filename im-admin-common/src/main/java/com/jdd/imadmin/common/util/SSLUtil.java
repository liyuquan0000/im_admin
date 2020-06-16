/*
 * Copyright (C) 2014 SEC BFO, Inc. All Rights Reserved.
 */
package com.jdd.imadmin.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.net.ssl.SSLContext;

public class SSLUtil {
    private static Log log = LogFactory.getLog(SSLUtil.class);

    public static CloseableHttpClient createSSLInsecureClient() {
        try {
            TrustStrategy strategy = new TrustStrategy() {
                @Override
                public boolean isTrusted(java.security.cert.X509Certificate[] x509Certificates, String s)
                        throws java.security.cert.CertificateException {
                    return true;
                }
            };

            SSLContext sslContext = SSLContexts.custom().useTLS().loadTrustMaterial(null, strategy).build();
            SSLConnectionSocketFactory f = new SSLConnectionSocketFactory(sslContext, new String[]{"TLSv1", "TLSv1.1", "TLSv1.2"}, null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
            return HttpClients.custom().setSSLSocketFactory(f).build();


			/*SSLContext sslContext = new SSLContextBuilder().useTLS().loadTrustMaterial(null, strategy).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();*/
        } catch (Exception e) {
            log.error(e);
        }
        return HttpClients.createDefault();
    }
}
