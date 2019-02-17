package tk.tnicy.tradisilation.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class GetImage {


    public static ArrayList<String> getImg(String question){
        ArrayList<String> result = new ArrayList<>();

        try {
            Document doc = Jsoup.connect("https://cn.bing.com/images/search?q="+question).get();
            Element a = doc.getElementById("mmComponent_images_1");

            for (int i = 0; i < 9; i++) {
                Elements b = a.getElementsByAttributeValue("data-idx", Integer.toString(i));
                for (Element element:b
                ) {
                    result.add(element.getElementsByClass("mimg").attr("src"));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


//    public static String getSource(String url) {
//        String html = "";
//        HttpGet httpget = new HttpGet(url);     //创建Http请求实例，URL 如：https://cd.lianjia.com/
//        // 模拟浏览器，避免被服务器拒绝，返回返回403 forbidden的错误信息
//        httpget.setHeader("User-Agent",
//                "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
//
//        CloseableHttpResponse response = null;
//        CloseableHttpClient httpclient = HttpClients.createDefault();   // 使用默认的HttpClient
//        try {
//            response = httpclient.execute(httpget);
//            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {     // 返回 200 表示成功
//                html = EntityUtils.toString(response.getEntity(), "utf-8");     // 获取服务器响应实体的内容
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (response != null) {
//                try {
//                    response.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return html;
//    }


    public static String getHtml(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5 * 1000);
        InputStream inStream = conn.getInputStream();//通过输入流获取html数据
        byte[] data = readInputStream(inStream);//得到html的二进制数据
        String html = new String(data, "UTF-8");
        return html;
    }
    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }
}