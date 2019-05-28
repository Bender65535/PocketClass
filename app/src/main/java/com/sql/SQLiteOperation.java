package com.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.entity.Student;
import com.entity.Teacher;

public class SQLiteOperation {
    public static SQLiteDatabase getDataBase(Context context) {
        SQLiteHelper helper = new SQLiteHelper(context);
        return helper.getWritableDatabase();
    }


    /**
     * 增
     */
//    public static long addData(Context context, ContentValues values) {
//        SQLiteDatabase db = getDataBase(context);
//        long rowID = db.insert("students", null, values);
//        db.close();
//        return rowID;
//    }
//
//    public static int addData(Context context, String sqlString) {
//        int result = -1;
//        SQLiteDatabase db = getDataBase(context);
//
//        try {
//            db.execSQL(sqlString);
//            result = 1;
//        } catch (SQLException e) {
//
//        }
//        db.close();
//        return result;
//    }
    //增加账户信息
    public static long addUser(Context context, String uname, String pwd, String position) {
        SQLiteDatabase db = getDataBase(context);
        ContentValues values = new ContentValues();
        values.put("uname", uname);
        values.put("pwd", pwd);
        values.put("position", position);
        long id = db.insert("user", null, values);
        db.close();
        return id;
    }

    //录入学生账号
    public static long addStudent(Context context, String uname) {
        SQLiteDatabase db = getDataBase(context);
        ContentValues values = new ContentValues();
        values.put("uname", uname);
        long id = db.insert("student", null, values);
        db.close();
        return 0;
    }

    //录入教师账号
    public static long addTeacher(Context context, String uname) {
        SQLiteDatabase db = getDataBase(context);
        ContentValues values = new ContentValues();
        values.put("uname", uname);
        long id = db.insert("teacher", null, values);
        db.close();
        return 0;
    }

    //增加班级
    public static long addClass(Context context,String className){
        SQLiteDatabase db = getDataBase(context);
        ContentValues values = new ContentValues();
        values.put("className", className);
        long result=db.insert("class",null,values);
        db.close();
        return result;
    }
    /**
     * 删
     */
    public static int deleteData(Context context, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = getDataBase(context);
        int count = db.delete("students", whereClause, whereArgs);
        db.close();
        return count;
    }
    public static int deleteClass(Context context,String whereClause, String[] whereArgs){
        SQLiteDatabase db = getDataBase(context);
        int count = db.delete("class", whereClause, whereArgs);
        db.close();
        return count;
    }
    /**
     * 改
     */
    public static int updateData(Context context, ContentValues values, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = getDataBase(context);
        int count = db.update("student", values, whereClause, whereArgs);
        db.close();
        return count;
    }

    //添加学生资料
    public static void updateStudent(Context context, ContentValues values,String whereClause,String[] whereArgs) {
        SQLiteDatabase db = getDataBase(context);
        db.update("student", values, whereClause, whereArgs);
        db.close();
    }

    //添加教师资料
    public static void updateTeacher(Context context, ContentValues values, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = getDataBase(context);
        db.update("teacher", values, whereClause, whereArgs);
        db.close();
    }


    /**
     * 查
     */

    //查询账号是否正确
    public static boolean isAccountRight(Context context, String account, String pwd) {
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor = db.rawQuery("select uname,pwd from user where uname=? and pwd=?", new String[]{account, pwd});
        boolean result = cursor.moveToNext();
        cursor.close();
        db.close();
        return result;
    }

    //查询账号是否存在
    public static boolean isAccountExit(Context context, String uname) {
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor = db.rawQuery("select uname from user where uname=?", new String[]{uname});
        boolean result = cursor.moveToNext();
        cursor.close();
        db.close();
        return result;
    }

    //根据用户名查询职位
    public static String unameToPosition(Context context, String uname) {
        SQLiteDatabase db = getDataBase(context);
//        Cursor cursor = db.query("user",new String[]{"position"},"uname=?",new String[]{uname},null,null,null);
        Cursor cursor = db.rawQuery("select position from user where uname=?", new String[]{uname});
        cursor.moveToNext();
        String position = cursor.getString(0);
        cursor.close();
        db.close();
        return position;
    }

    //查询学生信息
    public static Student queryStudent(Context context, String uname) {
        String name="";
        int classid=0;
        String sex="";
        String born="";
        String area="";
        String phone="";
        String email="";
        byte[] image=new byte[2048];
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor = db.query("student",new String[]{"name","classid","sex","born","area","phone","email","image"},"uname=?",new String[]{uname},null,null,null);
        cursor.moveToNext();
        name=cursor.getString(cursor.getColumnIndex("name"));
        classid=cursor.getInt(cursor.getColumnIndex("classid"));
        sex=cursor.getString(cursor.getColumnIndex("sex"));
        born=cursor.getString(cursor.getColumnIndex("born"));
        area=cursor.getString(cursor.getColumnIndex("area"));
        phone=cursor.getString(cursor.getColumnIndex("phone"));
        email=cursor.getString(cursor.getColumnIndex("email"));

        image=cursor.getBlob(cursor.getColumnIndex("image"));

        Student student=new Student(name,classid,sex,born,area,phone,email,image);
        cursor.close();
        db.close();
        return student;
    }

    //查询老师信息
    public static Teacher queryTeacher(Context context, String uname) {
        String name="";

        int subjectid=0;
        String sex="";
        String born="";
        String area="";
        String phone="";
        String email="";
        byte[] image=new byte[2048];
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor = db.query("teacher",new String[]{"name","subjectid","sex","born","area","phone","email","image"},"uname=?",new String[]{uname},null,null,null);
        cursor.moveToNext();
        name=cursor.getString(cursor.getColumnIndex("name"));
        subjectid=cursor.getInt(cursor.getColumnIndex("subjectid"));
        sex=cursor.getString(cursor.getColumnIndex("sex"));
        born=cursor.getString(cursor.getColumnIndex("born"));
        area=cursor.getString(cursor.getColumnIndex("area"));
        phone=cursor.getString(cursor.getColumnIndex("phone"));
        email=cursor.getString(cursor.getColumnIndex("email"));

        image=cursor.getBlob(cursor.getColumnIndex("image"));

        Teacher teacher=new Teacher(name,subjectid,sex,born,area,phone,email,image);
        cursor.close();
        db.close();
        return teacher;
    }

    //查询所有班级名
    public static String[] queryAllClass(Context context){
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor=db.rawQuery("select DISTINCT classname from class",null);
        String[] classes=new String[cursor.getCount()];
        for(int i=0;i<cursor.getCount();i++){
            cursor.moveToNext();
            classes[i]=cursor.getString(cursor.getColumnIndex("classname"));
        }
        cursor.close();
        db.close();
        return classes;
    }

    //查询所有科目名
    public static String[] queryAllSubjects(Context context) {
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor=db.rawQuery("select DISTINCT subjectname from subject",null);
        String[] subjectName=new String[cursor.getCount()];
        for(int i=0;i<cursor.getCount();i++){
            cursor.moveToNext();
            subjectName[i]=cursor.getString(cursor.getColumnIndex("subjectname"));
        }
        cursor.close();
        db.close();
        return subjectName;
    }

    //用科目id查科目名
    public static String querySubjectNameBySubjectId(Context context,int subjectid){
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor=db.rawQuery("select subjectname from subject where subjectid=?",new String[]{subjectid+""});
        cursor.moveToNext();
        String subjectName=cursor.getString(cursor.getColumnIndex("subjectname"));
        cursor.close();
        db.close();
        return subjectName;
    }

    //用科目名查科目id
    public static int querySubjectIdBySubjectName(Context context,String subjectName){
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor=db.rawQuery("select subjectid from subject where subjectname=?",new String[]{subjectName});
        cursor.moveToNext();
        int subjectId=cursor.getInt(cursor.getColumnIndex("subjectid"));
        cursor.close();
        db.close();
        return subjectId;
    }
    //用班级id查找班名
    public static String queryClassNameByClassId(Context context,int classid){
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor=db.rawQuery("select classname from class where classid=?",new String[]{classid+""});
        cursor.moveToNext();
        String className=cursor.getString(cursor.getColumnIndex("classname"));
        cursor.close();
        db.close();
        return className;
    }


    //用班名查找班级id
    public static int queryClassIdByClassName(Context context, String className) {
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor=db.rawQuery("select classid from class where classname=?",new String[]{className});
        cursor.moveToNext();
        int classId=cursor.getInt(cursor.getColumnIndex("classid"));
        cursor.close();
        db.close();
        return classId;
    }
}
