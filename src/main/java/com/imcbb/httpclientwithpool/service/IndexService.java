package com.imcbb.httpclientwithpool.service;

import com.imcbb.httpclientwithpool.HttpClientUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author kevin.chen
 * Date 2019/9/6
 * Time 16:02
 */

@RestController
public class IndexService {

    @RequestMapping(path = "send")
    public String sendWithHttpClient() {


        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter("http.connection.timeout", 30000);
        String url = "http://imcbb.com:8000";
        HttpPost httpPost = new HttpPost(url);
        HttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            System.out.println("Error,StatusCode:" + statusCode);
        } else {
            HttpEntity entity = response.getEntity();
            String resContent = null;
            try {
                resContent = EntityUtils.toString(entity);
            } catch (IOException e) {
                e.printStackTrace();
            }
            HttpClientUtils.closeQuietly(response);
            HttpClientUtils.closeQuietly(httpClient);
            return resContent;
        }

        return "sendWithHttpClient";

    }


    @RequestMapping(path = "poolSend")
    public String sendWithHttpClientPool() {

        HttpClient client = HttpClientUtil.getHttpClient();

        String url = "http://imcbb.com:8000";
        HttpGet get = new HttpGet(url);

        try {
            HttpResponse response = client.execute(get, HttpClientContext.create());
            HttpEntity entity = response.getEntity();
            HttpClientUtils.closeQuietly(response);
            return EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "sendWithHttpClientPool";

    }
}
