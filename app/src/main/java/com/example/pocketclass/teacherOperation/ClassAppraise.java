package com.example.pocketclass.teacherOperation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import com.example.pocketclass.R;
import com.example.pocketclass.TeacherActivity;
import com.sql.SQLiteOperation;

public class ClassAppraise extends AppCompatActivity {
    private Spinner classesSpinner;
    private Spinner studentSpinner;

    Button btYes;
    Button btNo;

    ArrayAdapter<String> studentAdapt;
    ArrayAdapter<String> classAdapt;

    private EditText wordsEditText;
    private EditText scoreEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_appraise);

        btYes=(Button)findViewById(R.id.yes);
        btNo=(Button)findViewById(R.id.no);

        classesSpinner=(Spinner)findViewById(R.id.classes);
        studentSpinner=(Spinner)findViewById(R.id.student);

        wordsEditText =(EditText)findViewById(R.id.words);
        scoreEditText =(EditText)findViewById(R.id.score);



        setSpinner();

        btYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(ClassAppraise.this);
                builder.setTitle("提示")
                        .setMessage("确认提交?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //获得评语和成绩
                                String words=wordsEditText.getText().toString();
                                int score= Integer.parseInt(scoreEditText.getText().toString());
                                //获得学生id
                                String student=studentSpinner.getSelectedItem().toString();
                                int studentid=Integer.parseInt(student.split(" ")[0]);

                                //将学生id,教师id,评语,分数添加到课堂评价中
                                int teacherid=SQLiteOperation.queryTeacherIdByUname(getBaseContext(),TeacherActivity.uname);
                                long result =SQLiteOperation.addAppraise(getBaseContext(), teacherid,studentid, words, score);

                                if(result>0){
                                    Toast.makeText(ClassAppraise.this,"评价成功",Toast.LENGTH_SHORT);
                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    Intent intent=new Intent(ClassAppraise.this, TeacherActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("uname", TeacherActivity.uname);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(ClassAppraise.this,"评价失败",Toast.LENGTH_SHORT);

                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });

        btNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ClassAppraise.this, TeacherActivity.class);
                startActivity(intent);

            }
        });
    }


    private void setSpinner(){
        classesSpinner=(Spinner)findViewById(R.id.classes);
        studentSpinner=(Spinner)findViewById(R.id.student);

        final String[] className= SQLiteOperation.queryAllClass(getBaseContext());
        //适配班级
        classAdapt=new ArrayAdapter(ClassAppraise.this,R.layout.support_simple_spinner_dropdown_item,className);
        classesSpinner.setAdapter(classAdapt);

        classesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //按班级名查找所有学生id
                int[] studentId=SQLiteOperation.queryAllStudentIdByClassName(getBaseContext(),className[position]);
                String[] studentIdString=new String[studentId.length];
                for(int i=0;i<studentId.length;i++){
                    String name=SQLiteOperation.queryStudentNameById(getBaseContext(),studentId[i]);
                    studentIdString[i]=studentId[i]+" "+name;
                }
                //适配学生
                studentAdapt=new ArrayAdapter(ClassAppraise.this,R.layout.support_simple_spinner_dropdown_item,studentIdString);
                studentSpinner.setAdapter(studentAdapt);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
