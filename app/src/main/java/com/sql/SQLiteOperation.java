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
    //增加科目
    public static long addSubject(Context context,String subjectName){
        SQLiteDatabase db = getDataBase(context);
        ContentValues values = new ContentValues();
        values.put("subjectName", subjectName);
        long result=db.insert("subject",null,values);
        db.close();
        return result;
    }
    //增加任课
    public static long addTeach(Context context,int teacherid,int classid){
        SQLiteDatabase db = getDataBase(context);
        ContentValues values = new ContentValues();
        values.put("classid",classid);
        values.put("tid",teacherid);
        long result=db.insert("teach",null,values);
        db.close();
        return result;
    }
    //增加出勤
    public static long addAttendance(Context context,int tid,int sid){
        SQLiteDatabase db = getDataBase(context);
        ContentValues values = new ContentValues();
        values.put("tid",tid);
        values.put("sid",sid);
        long result=db.insert("attendance",null,values);
        db.close();
        return result;
    }
    //增加作业记录
    public static long addHomework(Context context,int tid,int sid,int score){
        SQLiteDatabase db = getDataBase(context);
        ContentValues values = new ContentValues();
        values.put("tid",tid);
        values.put("sid",sid);
        values.put("score",score);
        //添加科目
        int subjectid=querySubjectIdByTeacherId(context,tid);
        values.put("subjectid",subjectid);
        long result=db.insert("homework",null,values);
        db.close();
        return result;
    }
    //增加课堂评价
    public static long addAppraise(Context context,int tid,int sid,String words,int score){
        SQLiteDatabase db = getDataBase(context);
        ContentValues values = new ContentValues();
        values.put("tid",tid);
        values.put("sid",sid);
        values.put("words",words);
        values.put("score",score);
        //添加科目
        long result=db.insert("appraise",null,values);
        db.close();
        return result;
    }
    /**
     * 删
     */
    //删除班级
    public static int deleteClass(Context context, int classid){
        SQLiteDatabase db = getDataBase(context);
        int count = db.delete("class", "classid=?", new String[]{classid+""});
        //删除班级id的所有任课
        deleteTeachByClassid(context,classid);
        //将学生表的classid制为null
        updateStudentClassidToNull(context,classid);
        //删除该班级的老师对学生的评价


        db.close();
        return count;
    }
    //删除班级id的所有任课
    public static int deleteTeachByClassid(Context context,int classid){
        SQLiteDatabase db = getDataBase(context);
        int count=db.delete("teach","classid=?",new String[]{classid+""});
        db.close();
        return count;
    }
    //删除科目
    public static int deleteSubject(Context context, int subjectid){
        SQLiteDatabase db = getDataBase(context);
        int count = db.delete("subject", "subjectid=?", new String[]{subjectid+""});
        //删除所有该科目id的作业表
        deleteHomeworkBySubjectId(context,subjectid);
        //将老师的科目制为空
        updateTeacherSubjectIdToNull(context,subjectid);
        //删除该科目的老师对学生的评价

        db.close();
        return count;
    }
    //删除所有该科目id的作业表
    public static int deleteHomeworkBySubjectId(Context context, int subjectid){
        SQLiteDatabase db = getDataBase(context);
        int count = db.delete("homework", "subjectid=?", new String[]{subjectid+""});
        return count;
    }
    //通过id删除学生
    public static int deleteStudentById(Context context,int id){
        SQLiteDatabase db = getDataBase(context);
        int count = db.delete("student", "sid=?", new String[]{id+""});

        //删除学生账号
        deleteUserByStudentid(context,id);
        //删除学生id所在出勤表
        deleteStudentIdInAttendance(context,id);
        //删除学生id所在作业成绩表
        deleteStudentIdInHomework(context,id);
        //删除学生id所在课堂评价表
        deleteStudentIdInAppraise(context,id);

        db.close();
        return count;
    }

    //通过学生id删除学生账号
    public static int deleteUserByStudentid(Context context,int id){
        SQLiteDatabase db = getDataBase(context);

        //通过学生id得到学生username
        String uname= queryUnameByStudentId(context,id);

        int count = db.delete("user", "uname=?", new String[]{uname});

        return count;
    }

    //删除学生id所在出勤表
    public static int deleteStudentIdInAttendance(Context context,int sid){
        SQLiteDatabase db = getDataBase(context);
        int count = db.delete("attendance", "sid=?", new String[]{sid+""});
        return count;
    }
    //删除学生id所在作业成绩表
    public static int deleteStudentIdInHomework(Context context,int sid){
        SQLiteDatabase db = getDataBase(context);
        int count = db.delete("homework", "sid=?", new String[]{sid+""});
        return count;
    }
    //删除学生id所在课堂评价表
    public static int deleteStudentIdInAppraise(Context context,int sid){
        SQLiteDatabase db = getDataBase(context);
        int count = db.delete("appraise", "sid=?", new String[]{sid+""});
        return count;
    }



    //通过id删除老师
    public static int deleteTeacherById(Context context,int id){
        SQLiteDatabase db = getDataBase(context);
        int count = db.delete("teacher", "tid=?", new String[]{id+""});

        //删除老师账号
        deleteUserByTeacherId(context,id);

        //删除老师id所在出勤表
        deleteTeacherIdInAttendance(context,id);
        //删除老师id所在作业成绩表
        deleteTeacherIdInHomework(context,id);
        //删除老师id所在课堂评价表
        deleteTeacherIdInAppraise(context,id);

        db.close();
        return count;
    }
    //根据老师id删除老师账号
    public static int deleteUserByTeacherId(Context context,int id){
        SQLiteDatabase db = getDataBase(context);

        //通过学生id得到学生username
        String uname= queryuUnameByTeacherId(context,id);

        int count = db.delete("user", "uname=?", new String[]{uname});

        return count;
    }
    //删除老师id所在出勤表
    public static int deleteTeacherIdInAttendance(Context context,int tid){
        SQLiteDatabase db = getDataBase(context);
        int count = db.delete("attendance", "tid=?", new String[]{tid+""});
        return count;
    }
    //删除老师id所在作业成绩表
    public static int deleteTeacherIdInHomework(Context context,int tid){
        SQLiteDatabase db = getDataBase(context);
        int count = db.delete("homework", "tid=?", new String[]{tid+""});
        return count;
    }
    //删除老师id所在课堂评价表
    public static int deleteTeacherIdInAppraise(Context context,int tid){
        SQLiteDatabase db = getDataBase(context);
        int count = db.delete("appraise", "tid=?", new String[]{tid+""});
        return count;
    }
    /**
     * 改
     */

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

    //将学生的classid制为空
    public static void updateStudentClassidToNull(Context context,int classid){
        SQLiteDatabase db = getDataBase(context);
        ContentValues values=new ContentValues();
        values.put("classid","");
        db.update("student", values, "classid=?", new String[]{classid+""});
        db.close();
    }

    //将老师的科目制为空
    public static void updateTeacherSubjectIdToNull(Context context,int subjectid){
        SQLiteDatabase db = getDataBase(context);
        ContentValues values=new ContentValues();
        values.put("subjectid","");
        db.update("subject", values, "subjectid=?", new String[]{subjectid+""});
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
    public static boolean isAccountExist(Context context, String uname) {
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

    //通过账户名查询学生信息
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
    //通过学生

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
    //根据账户名查老师id
    public static int queryTeacherIdByUname(Context context,String uname){
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor=db.rawQuery("select tid from teacher where uname=?",new String[]{uname});
        cursor.moveToNext();
        int id=cursor.getInt(cursor.getColumnIndex("tid"));
        cursor.close();
        db.close();
        return id;
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
    //查询所有学生的id
    public static int[] queryAllStudentId(Context context) {
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor=db.rawQuery("select sid  from student",null);
        int[] studentId=new int[cursor.getCount()];
        for(int i=0;i<cursor.getCount();i++){
            cursor.moveToNext();
            studentId[i]=cursor.getInt(cursor.getColumnIndex("sid"));
        }
        cursor.close();
        db.close();
        return studentId;
    }
    //查询所有老师的id
    public static int[] queryAllTeacherId(Context context){
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor=db.rawQuery("select tid  from teacher",null);
        int[] teacherId=new int[cursor.getCount()];
        for(int i=0;i<cursor.getCount();i++){
            cursor.moveToNext();
            teacherId[i]=cursor.getInt(cursor.getColumnIndex("tid"));
        }
        cursor.close();
        db.close();
        return teacherId;
    }
    //用学生的id查学生的姓名
    public static String queryStudentNameById(Context context,int studentId){
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor=db.rawQuery("select name  from student where sid=?",new String[]{studentId+""});
        cursor.moveToNext();
        String name=cursor.getString(cursor.getColumnIndex("name"));
        cursor.close();
        db.close();
        return name;
    }
    //用老师的id查老师的名字
    public static String queryTeacherNameByid(Context context ,int teacherId){
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor=db.rawQuery("select name  from teacher where tid=?",new String[]{teacherId+""});
        cursor.moveToNext();
        String name=cursor.getString(cursor.getColumnIndex("name"));
        cursor.close();
        db.close();
        return name;
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
        if(classid==0){
            return " ";
        }
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
    //按班级名查找所有学生id
    public static int[] queryAllStudentIdByClassName(Context context,String className){
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor=db.rawQuery("select sid from student,class where student.classid=class.classid and classname=?",new String[]{className});
        int[] studentId=new int[cursor.getCount()];
        for(int i=0;i<cursor.getCount();i++){
            cursor.moveToNext();
            studentId[i]=cursor.getInt(cursor.getColumnIndex("sid"));
        }

        cursor.close();
        db.close();
        return studentId;
    }
    //根据老师id查科目id
    public static int querySubjectIdByTeacherId(Context context,int teacherid){
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor=db.rawQuery("select subjectid from teacher where tid=?",new String[]{teacherid+""});
        cursor.moveToNext();
        int subjectid=cursor.getInt(cursor.getColumnIndex("subjectid"));
        cursor.close();
        db.close();
        return subjectid;
    }

    //根据班级查老师id
    public static int[] queryTeacherByClassId(Context context,int classid){
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor=db.rawQuery("select * from teach,teacher where classid=? and teach.tid=teacher.tid",new String[]{classid+""});

        int[] tids=new int[cursor.getCount()];
        for(int i=0;i<cursor.getCount();i++){
            cursor.moveToNext();
            tids[i]=cursor.getInt(cursor.getColumnIndex("tid"));
        }

        cursor.close();
        db.close();
        return tids;
    }
    //根据学生uname查学生所在班级id
    public static int queryClassIdByUname(Context context,String uname){
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor=db.rawQuery("select classid from student where uname=?",new String[]{uname});
        if(cursor.getCount()==0)
            return 0;
        cursor.moveToNext();
        int classid=cursor.getInt(cursor.getColumnIndex("classid"));
        cursor.close();
        db.close();
        return classid;
    }

    //通过学生id,老师id查询缺勤次数
    public static int queryTimesByStudentIdAndTeacherId(Context context,int studentId,int teacherId){
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor=db.rawQuery("select * from attendance where sid=? and tid=?",new String[]{studentId+"",teacherId+""});
        cursor.moveToNext();
        int times=cursor.getCount();
        cursor.close();
        db.close();
        return times;
    }
    //通过学生uname查找学生id
    public static int queryStudetIdByUname(Context context,String uname){
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor=db.rawQuery("select sid from student where uname=?",new String[]{uname});
        cursor.moveToNext();
        int sid=cursor.getInt(cursor.getColumnIndex("sid"));
        cursor.close();
        db.close();
        return sid;
    }
    //根据学生id,老师id查询老师给学生的评语
    public static String queryWordsByStudentIdAndTeacherId(Context context,int studentId,int teacherId){
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor=db.rawQuery("select words from appraise where sid=? and tid=?",new String[]{studentId+"",teacherId+""});
        cursor.moveToNext();
        String words=cursor.getString(cursor.getColumnIndex("words"));
        cursor.close();
        db.close();
        return words;
    }
    //根据学生id,老师id查询老师给学生的课堂评分
    public static int queryClassScoreByStudentIdAndTeacherId(Context context,int studentId,int teacherId){
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor=db.rawQuery("select score from appraise where sid=? and tid=?",new String[]{studentId+"",teacherId+""});
        cursor.moveToNext();
        int score=cursor.getInt(cursor.getColumnIndex("score"));
        cursor.close();
        db.close();
        return score;
    }
    //根据科目id查科目名
    public static String querySubjectNameById(Context context,int subjectid){
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor=db.rawQuery("select subjectname from subject where subjectid=?",new String[]{subjectid+""});
        cursor.moveToNext();
        String subjectname=cursor.getString(cursor.getColumnIndex("subjectname"));
        cursor.close();
        db.close();
        return subjectname;
    }
    //根据学生id,老师id查询老师给学生的作业评分
    public static int queryHomeworkScoreByStudentIdAndTeacherId(Context context,int studentId,int teacherId){
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor=db.rawQuery("select score from homework where sid=? and tid=?",new String[]{studentId+"",teacherId+""});
        cursor.moveToNext();
        int score=cursor.getInt(cursor.getColumnIndex("score"));
        cursor.close();
        db.close();
        return score;
    }
    //根据科目id查找科目名
    public static int querySubjectIdByName(Context context,String subjectname){
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor=db.rawQuery("select subjectid from subject where subjectname=? ",new String[]{subjectname});
        cursor.moveToNext();
        int subjectid=cursor.getInt(cursor.getColumnIndex("subjectid"));
        cursor.close();
        db.close();
        return subjectid;
    }
    //查询班名是否重复
    public static boolean isClassNameExist(Context context, String className){
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor=db.rawQuery("select classname from class where classname=? ",new String[]{className});

        if(cursor.getCount()>0) {
            cursor.close();
            db.close();
            return true;
        }
        else {
            cursor.close();
            db.close();
            return false;
        }

    }
    //查询科目名是否重复
    public static boolean isSubjectNameExist(Context context, String subjectName){
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor=db.rawQuery("select subjectname from subject where subjectname=? ",new String[]{subjectName});

        if(cursor.getCount()>0) {
            cursor.close();
            db.close();
            return true;
        }
        else {
            cursor.close();
            db.close();
            return false;
        }

    }
    //通过学生id找到学生账号
    public static String queryUnameByStudentId(Context context, int id){
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor=db.rawQuery("select uname from student where sid=? ",new String[]{id+""});

        cursor.moveToNext();
        String uname=cursor.getString(cursor.getColumnIndex("uname"));
        cursor.close();
        db.close();
        return uname;
    }
    //通过老师id找到老师账号
    public static String queryuUnameByTeacherId(Context context, int id){
        SQLiteDatabase db = getDataBase(context);
        Cursor cursor=db.rawQuery("select uname from teacher where tid=? ",new String[]{id+""});

        cursor.moveToNext();
        String uname=cursor.getString(cursor.getColumnIndex("uname"));
        cursor.close();
        db.close();
        return uname;
    }
}