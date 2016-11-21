package com.dlut.justeda.classnote.note.util;

import java.util.ArrayList;

/**
 * Week
 * Created by 赵佳伟 on 2016/11/21.
 */
public class Lesson {

    private ArrayList<Integer> Week;
    private int WeekDay;
    private int Section;
    private int Duration;
    private String Location;

    public String getWeek() {
        return Week.toString();
    }

    public int getWeekDay() {
        return WeekDay;
    }

    public int getSection() {
        return Section;
    }

    public int getDuration() {
        return Duration;
    }

    public String getLocation() {
        return Location;
    }
}
