package com.daily.english.app.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class DateUtil {

    /**
     * 13位时间戳+4位随机码
     * @return
     */
    public synchronized static String getRandom(){
        StringBuffer temp = new StringBuffer();
        String id = "";
        try{
            //Thread.sleep(10000);
            temp.append(System.currentTimeMillis());
            Random random = new Random();
            for(int i=0;i<4;i++){
                temp.append(random.nextInt(10));
            }
            id = temp.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return id;
    }
    /**
     * 判断当前日期是星期几
     * @param pTime 修要判断的时间
     * @return dayForWeek 结果 1 - 7
     * @Exception 发生异常
     */
    public static int dayForWeek(String pTime){
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(format.parse(pTime));
            int dayForWeek = 0;
            if (c.get(Calendar.DAY_OF_WEEK) == 1) {
                dayForWeek = 7;
            } else {
                dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
            }
            return dayForWeek;
        } catch (ParseException e) {
            e.printStackTrace();
            return 1;
        }
    }

    /**
     * 比较两个世间差
     * @param startTime
     * @param endTime
     * @param format
     * @return
     */
    public static String dateDiff(String startTime, String endTime, String format) {
// 按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat(format);
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long time=0;
        String strTime="";
        try {
            diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
            day = diff / nd;// 计算差多少天
            hour = diff % nd / nh + day * 24;// 计算差多少小时
            min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟
            sec = diff % nd % nh % nm / ns;// 计算差多少秒
            if(day>=1){
                time=day;
                strTime=time+"天前";
            }
            else if(hour>=1 && hour<24){
                time=hour;
                strTime=time+"小时前";
            }
            else{
                if(min==0)
                    time=1;
                time=min;
                strTime=time+"分钟前";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return strTime;
    }

    /**
     * 判断时间是否在有效时间之内
     * @param dateTimeStr
     * @return
     */
    public static boolean timeIsBefore(String dateTimeStr) {
        String nowDate = DateTime.now().toString("yyyy-MM-dd");
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime nowTime = dateTimeFormatter.parseDateTime(nowDate);//学校合作时间
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);//学校合作时间
        if (dateTime.isBefore(nowTime.toDateTime().getMillis())) {
            //过期
            return true;
        } else {
            //有效时间内
            return false;
        }
    }
}
