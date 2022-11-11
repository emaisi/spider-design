package org.spiderflow.minio.utils;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.ProxyHost;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * author: https://gitee.com/lmyanglei
 */
public class DownloadUtil {

    public static void download(String remoteUrl,String remoteFileName, String localFullFileName,String proxyHostParam,String proxyPortParam) {

        HttpClient client = new HttpClient();

        if(StringUtils.isNotEmpty(proxyHostParam) && StringUtils.isNotEmpty(proxyPortParam)){
            HostConfiguration hostConfiguration = new HostConfiguration();
            ProxyHost proxyHost = new ProxyHost(proxyHostParam, Integer.parseInt(proxyPortParam));
            hostConfiguration.setProxyHost(proxyHost);
            client.setHostConfiguration(hostConfiguration);
        }

        GetMethod getMethod = null;
        FileOutputStream output = null;

        try {
            getMethod = new GetMethod(remoteUrl);
            getMethod.setRequestHeader("fileName", remoteFileName);

            int i = client.executeMethod(getMethod);
            if (200 == i) {
                File storeFile = new File(localFullFileName);
                output = new FileOutputStream(storeFile);
                output.write(getMethod.getResponseBody());
            } else {
                System.out.println("DownLoad file occurs exception, the error code is :" + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(output != null){
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            getMethod.releaseConnection();
            client.getHttpConnectionManager().closeIdleConnections(0);
        }
    }

    public static InputStream downloadAsStream(String remoteUrl,String remoteFileName, String localFullFileName,String proxyHostParam,String proxyPortParam) {

        HttpClient client = new HttpClient();

        if(StringUtils.isNotEmpty(proxyHostParam) && StringUtils.isNotEmpty(proxyPortParam)){
            HostConfiguration hostConfiguration = new HostConfiguration();
            ProxyHost proxyHost = new ProxyHost(proxyHostParam, Integer.parseInt(proxyPortParam));
            hostConfiguration.setProxyHost(proxyHost);
            client.setHostConfiguration(hostConfiguration);
        }

        GetMethod getMethod = null;
        FileOutputStream output = null;
        InputStream inputStream = null;

        try {
            getMethod = new GetMethod(remoteUrl);
//            Header header = new Header();
//            header.setName("User-Agent");
//            header.setValue("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
            getMethod.setRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
            getMethod.setRequestHeader("fileName", remoteFileName);

            int i = client.executeMethod(getMethod);
            if (200 == i) {
                if(StringUtils.isNotEmpty(localFullFileName)){
                    File storeFile = new File(localFullFileName);
                    output = new FileOutputStream(storeFile);
                    output.write(getMethod.getResponseBody());
                }
                getMethod.getResponseBody();
                inputStream = getMethod.getResponseBodyAsStream();
            } else {
                System.out.println("DownLoad file occurs exception, the error code is :" + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(output != null){
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            getMethod.releaseConnection();
            client.getHttpConnectionManager().closeIdleConnections(0);
        }

        return inputStream;
    }

    public static void download(String remoteUrl,String remoteFileName, String localPath, String localFileName) {
        HttpClient client = new HttpClient();
        GetMethod get = null;
        FileOutputStream output = null;
        
        try {
            get = new GetMethod(remoteUrl);
            get.setRequestHeader("fileName", remoteFileName);

            int i = client.executeMethod(get);

            if (200 == i) {
                System.out.println("The response value of token:" + get.getResponseHeader("token"));

                File storeFile = new File(localPath + "/" + localFileName);
                output = new FileOutputStream(storeFile);
                
                // 得到网络资源的字节数组,并写入文件
                output.write(get.getResponseBody());
            } else {
                System.out.println("DownLoad file occurs exception, the error code is :" + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(output != null){
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            get.releaseConnection();
            client.getHttpConnectionManager().closeIdleConnections(0);
        }
    }

    /**
     * 将获取的字节数组保存为文件写入硬盘
     *
     * @param data
     * @param fileName
     */
    public static void writeFileToDisk(byte[] data, String fileName) {
        try {
            File file = new File(fileName); // 本地目录
            File fileParent = file.getParentFile();
            if (!fileParent.exists()) {
                fileParent.mkdirs();
                file.createNewFile();
            }
            FileOutputStream fops = new FileOutputStream(file);
            fops.write(data);
            fops.flush();
            fops.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取远程http地址视图片
     *
     * @param strUrl
     * @return
     */
    public static byte[] getFileByUrl(String strUrl) {
        try {
            System.setProperty("http.proxyHost", "127.0.0.1");
            System.setProperty("http.proxyPort", "1083");

            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(20 * 1000);
            InputStream inStream = conn.getInputStream();
            byte[] btData = readInputStream(inStream);
            return btData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取流
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }
    
}
