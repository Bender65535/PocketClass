package com.example.pocketclass;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import com.sql.SQLiteOperation;

public class Register extends AppCompatActivity {

    private Button bt;
    private EditText et_uname;
    private EditText et_password;
    private RadioButton rb_student;
    private RadioButton rb_teacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        bt = (Button) findViewById(R.id.bt);
        et_uname = (EditText) findViewById(R.id.uname);
        et_password = (EditText) findViewById(R.id.password);
        rb_student=(RadioButton) findViewById(R.id.student);
        rb_teacher=(RadioButton)findViewById(R.id.teacher);
        //注册
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = et_uname.getText().toString().trim();
                String pwd = et_password.getText().toString().trim();
                String position="";
                if(rb_student.isChecked()){
                    position="student";
                }else if(rb_teacher.isChecked()){
                    position="teacher";
                }
                if (uname.isEmpty()) {
                    et_uname.setError("用户名不能为空");
                    return;
                }
                if (pwd.isEmpty()) {
                    et_password.setError("密码不能为空");
                    return;
                }
                if(position.isEmpty()){
                    et_password.setError("请选择您的身份");
                    return;
                }

                //判断账号是否重复
                boolean result = SQLiteOperation.isAccountExit(getBaseContext(),uname);
                if(result){
                    Toast.makeText(Register.this, "账号重复,请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                //录入账号
                long unameResult=SQLiteOperation.addUser(getBaseContext(),uname,pwd,position);

                if(unameResult>0){
                    //录入到教师或学生表中
                    if(rb_student.isChecked()){
                        SQLiteOperation.addStudent(getBaseContext(),uname);
                    }else if(rb_teacher.isChecked()){
                        SQLiteOperation.addTeacher(getBaseContext(),uname);
                    }
                    Toast.makeText(Register.this, "注册成功", Toast.LENGTH_SHORT).show();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent intent=new Intent(Register.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(Register.this, "注册失败", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
