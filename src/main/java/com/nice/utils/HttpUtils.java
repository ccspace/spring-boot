package com.nice.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @program: payment
 * @description: 通过url取得文件返回InputStream类型数据
 * @author: BaoFei
 * @create: 2018-05-16 14:41
 **/
public class HttpUtils {
    
    /** 
      * @Description:  通过url获取返回InputStream
      * @Param:  
      * @return:  
      * @Author: BaoFei 
      * @Date: 2018/5/17 
      */ 
    public static InputStream readLine(String URL) {
        URL url = null;
        InputStream is =null;
        try {
            url = new URL(URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }
    
    /** 
      * @Description: 将 InputStream 转换成 byte
      * @Param:  
      * @return:  
      * @Author: BaoFei 
      * @Date: 2018/5/18 
      */ 
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    public static void main(String[] args) {

        // 测试
        InputStream in = readLine("http://10.1.0.42/group1/M00/00/00/CgEAKlr9NdGAAA7qAALVsNEG7g0596_big.pdf");

        try {

            OutputStream out = new FileOutputStream("D://123.pdf");

            byte[] bytes = new byte[2048];
            int n = -1;
            try {
                while ((n = in.read(bytes,0, bytes.length)) != -1) {
                    String str = new String(bytes,0,n,"UTF-8");
                    System.out.println(str);
                    out.write(bytes, 0, n);
                }
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}