package com.git.onedayrex.mdown.main;

import com.git.onedayrex.mdown.download.DownLoadFile;
import org.apache.log4j.Logger;

/**
 * Created by fuxiang.zhong on 2018/2/23.
 */
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        DownLoadFile downLoadFile = new DownLoadFile("http://183.230.81.118/cache/u.dl.baofeng.com/upload/storm.bd/bdsBaofeng.[[1421_0022]].exe?ich_args2=82-23152513054926_6db82aa8f3389751cd3f41a42b7df092_10075001_9c896525d4c4f1d6923a518939a83798_1d9ec5c9488fd64f93a1c1a031309cfd");
        downLoadFile.downLoad();
        long time = System.currentTimeMillis() - start;
        logger.info("<==========" + time + "ms===========>");
    }
}
