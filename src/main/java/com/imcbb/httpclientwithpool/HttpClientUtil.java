package com.imcbb.httpclientwithpool;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
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
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(1000)
                .setConnectionRequestTimeout(1000)
                .setSocketTimeout(1000).build();
        System.out.println("cm: " + cm);
        cm.setDefaultMaxPerRoute(10);
        cm.setMaxTotal(200);
        cm.setDefaultSocketConfig(SocketConfig.custom().setSoTimeout(1000).build());
        chc = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(requestConfig).build();
    }

    private static class SingletonClassInstance {
        private static final HttpClientUtil INSTANCE = new HttpClientUtil();
    }

    public static CloseableHttpClient getHttpClient() {

        return SingletonClassInstance.INSTANCE.chc;
    }


    public static void main(String[] args) throws InterruptedException {


        for (; ; ) {
            new Thread(() -> {

                HttpClient client = HttpClientUtil.getHttpClient();
                System.out.println(client);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }).start();
        }


    }
}
