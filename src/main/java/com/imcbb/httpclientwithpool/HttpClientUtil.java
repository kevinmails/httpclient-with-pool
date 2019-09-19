package com.imcbb.httpclientwithpool;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * @author kevin.chen
 * Date 2019/9/19
 * Time 17:52
 */
public class HttpClientUtil {
    PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    CloseableHttpClient httpClient;

    private HttpClientUtil() {
        cm.setDefaultMaxPerRoute(10);
        cm.setMaxTotal(200);
        httpClient = HttpClients.custom().setConnectionManager(cm).build();
    }

    private static class SingletonClassInstance {
        private static final HttpClientUtil instance = new HttpClientUtil();
    }

    public static CloseableHttpClient getHttpClient() {

        return SingletonClassInstance.instance.httpClient;
    }


    public static void main(String[] args) throws InterruptedException {


        for (; ; ) {


            HttpClient client = HttpClientUtil.getHttpClient();
            System.out.println(client);

            Thread.sleep(1000);
        }

    }
}
