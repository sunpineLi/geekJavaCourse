/*
 * 文件名称：HttpClientHelper.java
 * 作    者：lisongbai
 * 完成时间： 2021年09月26日
 */
package geek.java.course;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.fluent.Content;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;

/**
 * HttpClientHelper
 *
 * @author lisongbai
 * @since 1.0.0
 */
public class HttpClientHelper {

    public static void main(String[] args) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            // HttpClient native API
            HttpGet httpGet = new HttpGet("http://localhost:8801");
            try (CloseableHttpResponse response1 = httpclient.execute(httpGet)) {
                HttpEntity entity1 = response1.getEntity();
                System.out.println(EntityUtils.toString(entity1));
            }

            // fluent API
            Content content = Request.get("http://localhost:8801")
                    .execute().returnContent();
            System.out.println(content.asString(content.getType().getCharset()));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
