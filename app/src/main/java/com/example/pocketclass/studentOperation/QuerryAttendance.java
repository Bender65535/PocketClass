package com.example.pocketclass.studentOperation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import com.example.pocketclass.R;
import com.example.pocketclass.StudentActivity;
import com.sql.SQLiteOperation;

public class QuerryAttendance extends AppCompatActivity {

    private Spinner teacherSpinner;
    private TextView timesText;
    private TextView scoreText;

    ArrayAdapter<String> teacherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_querry_attendance);

        if(StudentActivity.classid==0){
            Intent intent=new Intent(QuerryAttendance.this,StudentActivity.class);
            Toast.makeText(QuerryAttendance.this,"请输入班级信息",Toast.LENGTH_SHORT);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startActivity(intent);
        }

        teacherSpinner=(Spinner)findViewById(R.id.teacher);
        timesText =(TextView)findViewById(R.id.times);
        scoreText =(TextView)findViewById(R.id.score);

        setSpinner();

        teacherSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //选择老师后显示缺勤次数
                String teachers=teacherSpinner.getSelectedItem().toString();

                //提取老师的id
                int tid=Integer.parseInt(teachers.split(" ")[0]);

                //查询缺勤次数
                int times=SQLiteOperation.queryTimesByStudentIdAndTeacherId(getBaseContext(),StudentActivity.studentid,tid);

                //返回缺勤次数
                timesText.setText(times);
                //出勤分=100-10*times
                scoreText.setText(100-10*times);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setSpinner(){
        teacherSpinner=(Spinner)findViewById(R.id.teacher);
        int[] teacherid=SQLiteOperation.queryTeacherByClassId(getBaseContext(),StudentActivity.classid);
        String[] teachers=new String[teacherid.length];
        for(int i=0;i<teacherid.length;i++){
            teachers[i]=teacherid[i]+" "+SQLiteOperation.queryTeacherNameByid(getBaseContext(),teacherid[i]);
        }
        teacherAdapter=new ArrayAdapter(QuerryAttendance.this,R.layout.support_simple_spinner_dropdown_item,teachers);
        teacherSpinner.setAdapter(teacherAdapter);
    }

}
