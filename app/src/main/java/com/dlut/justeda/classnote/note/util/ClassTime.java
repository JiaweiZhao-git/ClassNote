package com.dlut.justeda.classnote.note.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dlut.justeda.classnote.note.db.ClassDatabaseHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 用于处理上课时间——和照片时间做对比
 * Created by 赵佳伟 on 2016/11/21.
 */
public class ClassTime {

    private int FIRST_BEGIN = 800;
    private int FIRST_END = 935;

    private int SECOND_BEGIN = 950;
    private int SECOND_END = 1125;

    private int THIRD_BEGIN = 1330;
    private int THIRD_END = 1505;

    private int FORTH_BEGIN = 1510;
    private int FORTH_END = 1645;

    private int EVENING_BEGIN = 1800;
    private int EVENING_END = 2130;

    private ClassDatabaseHelper dbHelper;

    public String getDate() {
        Calendar calendar = Calendar.getInstance();
        StringBuilder sb = new StringBuilder();
        sb.append(calendar.get(Calendar.YEAR));
        int month = calendar.get(Calendar.MONTH) + 1;
        sb.append(month < 10 ? "0" + month : month);
        int day = calendar.get(Calendar.DATE);
        sb.append(day < 10 ? "0" + day : day);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        sb.append(hour < 10 ? "0" + hour : hour);
        int minute = calendar.get(Calendar.MINUTE);
        sb.append(minute < 10 ? "0" + minute : minute);
        return sb.toString();

    }

    /**
     * 由yyyyMMddHHmm获取课程名字并返回
     * @param date
     * @return
     */
    public String getClassName(Context context,String date) {
        int WeekDay = getWeekDay(date);
        int Section = getSection(date.substring(date.length() - 4));
        String selectionArgs[] = {String.valueOf(WeekDay), String.valueOf(Section)};
        dbHelper = new ClassDatabaseHelper(context,"Courses.db", null, 2);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Courses where weekday=? and section=?", selectionArgs);
        Cursor secondCursor = db.rawQuery("select * from Courses where weekday2=? and section2=?", selectionArgs);
        Cursor thirdCursor = db.rawQuery("select * from Courses where weekday3=? and section3=?", selectionArgs);
        if (cursor.getCount() == 0 && secondCursor.getCount() == 0 && thirdCursor.getCount() == 0) {
            return "其他";
        } else if (cursor.getCount() != 0) {
            String name = "其他";
            queryFromCursor(cursor,date);
            return name;
        } else if (secondCursor.getCount() != 0) {
            String name = "其他";
            queryFromCursor(secondCursor,date);
            return name;
        } else if (thirdCursor.getCount() != 0) {
            String name = "其他";
            queryFromCursor(thirdCursor,date);
            return name;
        }else{
            return "其他";
        }
    }

    /**
     * 如果cursor不空的话，根据date查询在当前周是否有name这节课
     * @param cursor
     * @param date
     * @return
     */
    private String queryFromCursor(Cursor cursor,String date) {
        String name = "其他";
        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(cursor.getColumnIndex("name"));
            } while (cursor.moveToNext());
        }
        cursor.close();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectionArgs[] = {name};
        Cursor weekCursor = db.rawQuery("select * from Courses where name=?",selectionArgs);
        String Week="[]";
        if (weekCursor.getCount() != 0) {
            if (weekCursor.moveToFirst()) {
                do {
                    Week = weekCursor.getString(weekCursor.getColumnIndex("week"));
                } while (weekCursor.moveToNext());
            }
        }
        weekCursor.close();
        if (Week.contains(getWeek(date))) {
            return name;
        }else{
            return "其他";
        }
    }

    public String getWeek(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int weekOfYear = c.get(Calendar.WEEK_OF_YEAR)-36;
        return String.valueOf(weekOfYear);
//        switch (weekOfYear) {
//            case 1:
//                return "第一周";
//            case 2:
//                return "第二周";
//            case 3:
//                return "第三周";
//            case 4:
//                return "第四周";
//            case 5:
//                return "第五周";
//            case 6:
//                return "第六周";
//            case 7:
//                return "第七周";
//            case 8:
//                return "第八周";
//            case 9:
//                return "第九周";
//            case 10:
//                return "第十周";
//            case 11:
//                return "第十一周";
//            case 12:
//                return "第十二周";
//            case 13:
//                return "第十三周";
//            case 14:
//                return "第十四周";
//            case 15:
//                return "第十五周";
//            case 16:
//                return "第十六周";
//            case 17:
//                return "第十七周";
//            case 18:
//                return "第十八周";
//            default:
//                return "某一周";
//        }
    }

    public int getWeekDay(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int dayForWeek = c.get(Calendar.DAY_OF_WEEK);
        return dayForWeek;
//        switch (dayForWeek) {
//            case 1:
//                return "星期一";
//            case 2:
//                return "星期二";
//            case 3:
//                return "星期三";
//            case 4:
//                return "星期四";
//            case 5:
//                return "星期五";
//            case 6:
//                return "星期六";
//            case 7:
//                return "星期日";
//            default:
//                return "某一天";
//        }
    }

    public int getSection(String moment) {
        int time = Integer.valueOf(moment);
        if (time >= FIRST_BEGIN && time <= FIRST_END) {
            return 1;
        } else if (time >= SECOND_BEGIN && time <= SECOND_END) {
            return 3;
        } else if (time >= THIRD_BEGIN && time <= THIRD_END) {
            return 5;
        } else if (time >= FORTH_BEGIN && time <= FORTH_END) {
            return 7;
        } else if (time >= EVENING_BEGIN && time <= EVENING_END) {
            return 9;
        }else {
            return 0;
        }
    }

    public String getWeekName(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int weekOfYear = c.get(Calendar.WEEK_OF_YEAR)-36;
        switch (weekOfYear) {
            case 1:
                return "第一周";
            case 2:
                return "第二周";
            case 3:
                return "第三周";
            case 4:
                return "第四周";
            case 5:
                return "第五周";
            case 6:
                return "第六周";
            case 7:
                return "第七周";
            case 8:
                return "第八周";
            case 9:
                return "第九周";
            case 10:
                return "第十周";
            case 11:
                return "第十一周";
            case 12:
                return "第十二周";
            case 13:
                return "第十三周";
            case 14:
                return "第十四周";
            case 15:
                return "第十五周";
            case 16:
                return "第十六周";
            case 17:
                return "第十七周";
            case 18:
                return "第十八周";
            default:
                return "某一周";
        }
    }

    public String getWeekDayName(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int dayForWeek = c.get(Calendar.DAY_OF_WEEK);
        switch (dayForWeek) {
            case 1:
                return "星期一";
            case 2:
                return "星期二";
            case 3:
                return "星期三";
            case 4:
                return "星期四";
            case 5:
                return "星期五";
            case 6:
                return "星期六";
            case 7:
                return "星期日";
            default:
                return "某一天";
        }
    }
}
