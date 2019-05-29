package com.example.pocketclass.managerOperation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.pocketclass.Manager;
import com.example.pocketclass.R;
import com.sql.SQLiteOperation;

public class AddTeacherToClass extends AppCompatActivity {

    private Spinner teacherSpinner;
    private Spinner classSpinner;
    ArrayAdapter<String> classAdapt;
    ArrayAdapter<String> teacherAdapt;

    Button btYes;
    Button btNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher_to_class);
        teacherSpinner=(Spinner)findViewById(R.id.teacher);
        classSpinner=(Spinner)findViewById(R.id.classes);

        btYes=(Button)findViewById(R.id.yes);
        btNo=(Button)findViewById(R.id.no);

        setSpinner();

        btYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //对话框
                AlertDialog.Builder builder=new AlertDialog.Builder(AddTeacherToClass.this);
                builder.setTitle("提示")
                        .setMessage("确认添加?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String studentName=teacherSpinner.getSelectedItem().toString();
                                //分割老师的spinner的字符串,得到id
                                int teacherid=Integer.parseInt(studentName.split(" ")[0]);

                                //得到班级名
                                String className=classSpinner.getSelectedItem().toString();

                                //根据班级名查找班级id
                                int classid=SQLiteOperation.queryClassIdByClassName(getBaseContext(),className);

                                long result =SQLiteOperation.addTeach(getBaseContext(),teacherid,classid);


                                if(result>0){
                                    Toast.makeText(AddTeacherToClass.this,"添加成功",Toast.LENGTH_SHORT);
                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    Intent intent=new Intent(AddTeacherToClass.this, Manager.class);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(AddTeacherToClass.this,"添加失败",Toast.LENGTH_SHORT);

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
                Intent intent=new Intent(AddTeacherToClass.this, Manager.class);
                startActivity(intent);

            }
        });
    }

    private void setSpinner(){
        teacherSpinner=(Spinner)findViewById(R.id.teacher);

        classSpinner=(Spinner)findViewById(R.id.classes);
        //适配班级
        String[] classes = SQLiteOperation.queryAllClass(getBaseContext());
        classAdapt=new ArrayAdapter(AddTeacherToClass.this,R.layout.support_simple_spinner_dropdown_item,classes);
        classSpinner.setAdapter(classAdapt);

        //适配老师
        int[] teacherid = SQLiteOperation.queryAllTeacherId(getBaseContext());
        String[] strings=new String[teacherid.length];
        for(int i =0;i<teacherid.length;i++){
            String name=SQLiteOperation.queryTeacherNameByid(getBaseContext(),teacherid[i]);
            strings[i]=teacherid[i]+" "+name;
        }
        teacherAdapt =new ArrayAdapter(AddTeacherToClass.this,R.layout.support_simple_spinner_dropdown_item,strings);
        teacherSpinner.setAdapter(teacherAdapt);
    }
}