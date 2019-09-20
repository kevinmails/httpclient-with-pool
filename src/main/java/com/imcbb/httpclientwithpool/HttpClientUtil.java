package com.imcbb.httpclientwithpool;

import org.apache.http.client.HttpClient;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * @author kevin.chen
 * Date 2019/9/19
 * Time 17:52
 */
public class HttpClientUtil {
    private final PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    private final CloseableHttpClient chc;

    private HttpClientUtil() {
        cm.setDefaultMaxPerRoute(10);
        cm.setMaxTotal(200);
        chc = HttpClients.custom().setConnectionManager(cm).build();
    }

    private static class SingletonClassInstance {
        private static final HttpClientUtil INSTANCE = new HttpClientUtil();
    }

    public static CloseableHttpClient getHttpClient() {

        return SingletonClassInstance.INSTANCE.chc;
    }


    public static void main(String[] args) throws InterruptedException {


        for (; ; ) {


            HttpClient client = HttpClientUtil.getHttpClient();
            System.out.println(client);

            Thread.sleep(1000);
        }

    }
}
