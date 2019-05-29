package com.example.pocketclass;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import com.sql.SQLiteOperation;
import com.xml.Utils;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button bt;
    private EditText et_uname;
    private EditText et_password;
    private TextView tv_register;
    private CheckBox save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bt = (Button) findViewById(R.id.bt);
        et_uname = (EditText) findViewById(R.id.uname);
        et_password = (EditText) findViewById(R.id.password);
        tv_register=(TextView) findViewById(R.id.register);
        save=(CheckBox)findViewById(R.id.save) ;

        Map<String, String> userInfo = Utils.getUserInfo(this);
        if (userInfo != null) {
            et_uname.setText(userInfo.get("number"));
            et_password.setText(userInfo.get("password"));
        }

        //登录按钮
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = et_uname.getText().toString().trim();
                String pwd = et_password.getText().toString().trim();

                int a = 0;
                if (uname.isEmpty()) {
                    et_uname.setError("用户名不能为空");
                    return;
                }
                if (pwd.isEmpty()) {
                    et_password.setError("密码不能为空");
                    return;
                }

                //判断账号密码是否正确
                boolean result = SQLiteOperation.isAccountRight(getBaseContext(),uname,pwd);

                //跳到管理员界面
                if ("admin".equals(uname)&&"123".equals(pwd)) {
                    Intent intent = new Intent(MainActivity.this, Manager.class);
                    startActivity(intent);
                    return;
                }

                Intent intent;
                if (result) {
                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    //                    如果选择记住密码
                    if(save.isChecked()){
                        boolean isSaveSuccess=Utils.saveUserInfo(MainActivity.this,uname,pwd);
                        if(isSaveSuccess){
                            Toast.makeText(MainActivity.this,"保存成功",Toast.LENGTH_SHORT).show();

                        }
                        else{
                            Toast.makeText(MainActivity.this,"保存失败",Toast.LENGTH_SHORT);
                        }
                    }
                    if("student".equals(SQLiteOperation.unameToPosition(getBaseContext(),uname))) {

                        intent = new Intent(MainActivity.this, StudentActivity.class);

                        Bundle bundle = new Bundle();
                        bundle.putString("uname", uname);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    if("teacher".equals(SQLiteOperation.unameToPosition(getBaseContext(),uname))) {

                        intent = new Intent(MainActivity.this, TeacherActivity.class);

                        Bundle bundle = new Bundle();
                        bundle.putString("uname", uname);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        //注册按钮
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
    }
}
