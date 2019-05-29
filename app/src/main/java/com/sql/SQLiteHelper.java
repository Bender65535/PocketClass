package com.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {
    public SQLiteHelper(Context context) {
        super(context, "pocket_class", null, 8);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //用户表
        db.execSQL("create table user(" +
                "uname varchar(30) primary key," +
                "pwd varchar(30)," +
                "position varchar(30));");
        //学生表
        db.execSQL("create table student(" +
                "sid integer primary key autoincrement," +
                "name varchar(30)," +
                "uname varchar(30)," +
                "sex varchar(30)," +
                "born varchar(50)," +
                "classid integer," +
                "area varchar(60)," +
                "phone varchar(30)," +
                "email varchar(30)," +
                "image BLOB," +
                "FOREIGN KEY(uname) REFERENCES user(uname)," +
                "FOREIGN KEY(classid) REFERENCES class(classid));");
        //教师表
        db.execSQL("create table teacher(" +
                "tid integer primary key autoincrement," +
                "name varchar(30)," +
                "uname varchar(30)," +
                "born varchar(50)," +
                "sex varchar(30)," +
                "subjectid varchar(30)," +
                "area varchar(60)," +
                "phone varchar(30)," +
                "email varchar(30)," +
                "image BLOB," +
                "FOREIGN KEY(uname) REFERENCES user(uname)," +
                "FOREIGN KEY(subjectid) REFERENCES subject(subjectid));");
        //课堂评价表
        db.execSQL("create table appraise(" +
                "aid integer primary key autoincrement," +
                "sid integer," +
                "tid integer," +
                "word varchar(255)," +
                "score integer," +
                "FOREIGN KEY(sid) REFERENCES student(sid)," +
                "FOREIGN KEY(tid) REFERENCES teacher(tid));");
        //考勤表
        db.execSQL("create table attendance(" +
                "attendid integer primary key autoincrement," +
                "sid integer," +
                "tid integer," +
                "FOREIGN KEY(sid) REFERENCES student(sid)," +
                "FOREIGN KEY(tid) REFERENCES teacher(tid));");
        //作业成绩表
        db.execSQL("create table homework(" +
                "hid integer primary key autoincrement," +
                "sid integer," +
                "tid integer," +
                "score integer," +
                "subjectid integer," +
                "FOREIGN KEY(sid) REFERENCES student(sid)," +
                "FOREIGN KEY(tid) REFERENCES teacher(tid)," +
                "FOREIGN KEY(subjectid) REFERENCES homework(subjectid));");
        //班级表
        db.execSQL("create table class(" +
                "classid integer primary key autoincrement," +
                "classname varchar(30));");

        //任课表
        db.execSQL("create table teach(" +
                "teachid integer primary key autoincrement," +
                "classid integer," +
                "tid integer," +
                "FOREIGN KEY(classid) REFERENCES class(classid)," +
                "FOREIGN KEY(tid) REFERENCES teacher(tid));");

        //科目表
        db.execSQL("create table subject(" +
                "subjectid integer primary key autoincrement," +
                "subjectname varchar(30));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("students,", "----版本更新" + oldVersion + "---->" + newVersion);
    }
}
