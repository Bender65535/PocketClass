package com.example.pocketclass.managerOperation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import com.example.pocketclass.Manager;
import com.example.pocketclass.R;
import com.sql.SQLiteOperation;

public class DeleteStudent extends AppCompatActivity {

    Button btYes;
    Button btNo;

    Spinner studentSpinner;
    ArrayAdapter<String> studentAdapt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_student);

        btYes=(Button)findViewById(R.id.yes);
        btNo=(Button)findViewById(R.id.no);
        studentSpinner =(Spinner)findViewById(R.id.student);

        setSpinner();


        btYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //对话框
                AlertDialog.Builder builder=new AlertDialog.Builder(DeleteStudent.this);
                builder.setTitle("提示")
                        .setMessage("确认删除?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String studentName=studentSpinner.getSelectedItem().toString();
                                //分割spinner的字符串
                                int id=Integer.parseInt(studentName.split(" ")[0]);

                                int result =SQLiteOperation.deleteStudentById(getBaseContext(),id);
                                if(result>0){
                                    Toast.makeText(DeleteStudent.this,"删除成功",Toast.LENGTH_SHORT);
                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    Intent intent=new Intent(DeleteStudent.this, Manager.class);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(DeleteStudent.this,"删除失败",Toast.LENGTH_SHORT);

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
                Intent intent=new Intent(DeleteStudent.this, Manager.class);
                startActivity(intent);

            }
        });
    }

    private void setSpinner() {
        studentSpinner =(Spinner)findViewById(R.id.student);
        int[] studentId = SQLiteOperation.queryAllStudentId(getBaseContext());
        String[] strings=new String[studentId.length];
        for(int i =0;i<studentId.length;i++){
            String name=SQLiteOperation.queryStudentNameById(getBaseContext(),studentId[i]);
            strings[i]=studentId[i]+" "+name;
        }
        studentAdapt =new ArrayAdapter(DeleteStudent.this,R.layout.support_simple_spinner_dropdown_item,strings);
        studentSpinner.setAdapter(studentAdapt);
    }
}
