package com.daily.english.app.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.channels.FileChannel;


import ws.schild.jave.MultimediaInfo;
import ws.schild.jave.MultimediaObject;

public class VideoUtil {

    /**
     * 一小时的秒数
     */
    private static final int HOUR_SECOND = 60 * 60;

    /**
     * 一分钟的秒数
     */
    private static final int MINUTE_SECOND = 60;

    /**
     * @param FileUrl 文件路径
     * @return
     * @描述：获取视频时长
     * @时间：2018年8月28日 上午10:18:59
     */
    public static String ReadVideoTime(String FileUrl) {
        File source = new File(FileUrl);
        String length = "";
        try {
            MultimediaObject instance = new MultimediaObject(source);
            MultimediaInfo result = instance.getInfo();
            long ls = result.getDuration() / 1000;
            return getTimeStrBySecond((int) ls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return length;
    }

    /**
     * 根据秒数获取时间串
     *
     * @param second (eg: 100s)
     * @return (eg : 00 : 01 : 40)
     */
    public static String getTimeStrBySecond(int second) {
        if (second <= 0) {
            return "00:00";
        }

        StringBuilder sb = new StringBuilder();
        int hours = second / HOUR_SECOND;
        if (hours > 0) {

            second -= hours * HOUR_SECOND;
        }

        int minutes = second / MINUTE_SECOND;
        if (minutes > 0) {

            second -= minutes * MINUTE_SECOND;
        }

//        return (hours >= 10 ? (hours + "")
//                : ("0" + hours) + ":" + (minutes >= 10 ? (minutes + "") : ("0" + minutes)) + ":"
//                + (second >= 10 ? (second + "") : ("0" + second)));
        return ((minutes >= 10 ? (minutes + "") : ("0" + minutes)) + ":"
                + (second >= 10 ? (second + "") : ("0" + second)));
    }


    /**
     * @param source
     * @return
     * @描述：获取视频大小
     * @时间：2018年8月28日 上午10:30:17
     */
    @SuppressWarnings({"resource"})
    String ReadVideoSize(File source) {
        FileChannel fc = null;
        String size = "";
        try {
            FileInputStream fis = new FileInputStream(source);
            fc = fis.getChannel();
            BigDecimal fileSize = new BigDecimal(fc.size());
            size = fileSize.divide(new BigDecimal(1048576), 2, RoundingMode.HALF_UP) + "MB";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != fc) {
                try {
                    fc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return size;
    }

}