package com.git.onedayrex.mdown.download;

import com.git.onedayrex.mdown.config.ConfigUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.*;

/**
 * Created by fuxiang.zhong on 2018/2/23.
 * 单线程下载
 */
public class DownLoadFile {
    private static final Logger logger = Logger.getLogger(DownLoadFile.class);

    private String path;

    public DownLoadFile(String path) {
        this.path = path;
    }

    public void downLoad() {
        try {
            URL url = new URL(path);
            String proxyIp = ConfigUtils.getPropertie("porxy.ip");
            String proxyPort = ConfigUtils.getPropertie("porxy.port");
            HttpURLConnection connection = null;
            //是否使用代理
            if (StringUtils.isNotBlank(proxyIp) && StringUtils.isNotBlank(proxyPort)) {
                Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(proxyIp, Integer.valueOf(proxyPort)));
                connection = (HttpURLConnection) url.openConnection(proxy);
            }else {
                connection = (HttpURLConnection) url.openConnection();
            }
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(20 * 1000);
            //取文件名称
            String headerField = connection.getHeaderField("Content-Disposition");
            String filename = headerField.substring(headerField.indexOf("filename") + 9, headerField.length());
            String path = ConfigUtils.getPropertie("path");
            if (!path.endsWith("/")) {
                path = path + "/" + filename;
            }else {
                path = path + filename;
            }
            //取设置线程数量
            String threadProperties = ConfigUtils.getPropertie("thread");
            int thread = 10;
            if (StringUtils.isNotBlank(threadProperties)) {
                thread = Integer.valueOf(threadProperties);
            }
            int responseCode = connection.getResponseCode();
            int contentLength = connection.getContentLength();
            //线程下载长度
            int threadDownloadLength = this.calcDownloadLength(thread, contentLength);
            logger.info("请求返回状态码==>" + responseCode);
            //200 所有资源 206 部分
            if (responseCode == 200) {
                File file = new File(path);
                InputStream inputStream = connection.getInputStream();
                int i = -1;
                byte[] b = new byte[1024 * 512];
                RandomAccessFile out = new RandomAccessFile(file,"rw");
                int offest = 0;
                double percent = 0;
                double old = percent;
                while ((i = inputStream.read(b)) != -1) {
                    out.write(b, 0, i);
                    percent = ((double) offest / (double) contentLength) * 100;
                    offest += i;
//                    System.out.print("\u001b[1000D" + percent + "%");
                    if (percent == 0) {
                        logger.info("percent==>" + percent + "%");
                    } else if (percent - old >= 0.01) {
                        logger.info("percent==>" + percent + "%");
                        old = percent;
                    }
                }
                inputStream.close();
                out.close();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 计算线程下载长度
     * @param thread
     * @param contentLength
     * @return
     */
    private int calcDownloadLength(int thread, int contentLength) {
        return contentLength / thread;
    }
}
