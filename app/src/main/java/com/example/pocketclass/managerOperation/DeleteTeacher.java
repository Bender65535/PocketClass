
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

public class DeleteTeacher extends AppCompatActivity {
    Button btYes;
    Button btNo;

    Spinner teacherSpinner;
    ArrayAdapter<String> teacherAdapt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_teacher);

        btYes=(Button)findViewById(R.id.yes);
        btNo=(Button)findViewById(R.id.no);
        teacherSpinner =(Spinner)findViewById(R.id.teacher);

        setSpinner();
        btYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //对话框
                AlertDialog.Builder builder=new AlertDialog.Builder(DeleteTeacher.this);
                builder.setTitle("提示")
                        .setMessage("确认删除?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String teacherName=teacherSpinner.getSelectedItem().toString();
                                //分割spinner的字符串
                                int id=Integer.parseInt(teacherName.split(" ")[1]);

                                int result =SQLiteOperation.deleteTeacherById(getBaseContext(),id);
                                if(result>0){
                                    Toast.makeText(DeleteTeacher.this,"删除成功",Toast.LENGTH_SHORT);
                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    Intent intent=new Intent(DeleteTeacher.this, Manager.class);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(DeleteTeacher.this,"删除失败",Toast.LENGTH_SHORT);

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
                Intent intent=new Intent(DeleteTeacher.this, Manager.class);
                startActivity(intent);

            }
        });
    }



    private void setSpinner() {
        teacherSpinner =(Spinner)findViewById(R.id.teacher);
        int[] teacherId = SQLiteOperation.queryAllTeacherId(getBaseContext());
        String[] strings=new String[teacherId.length];
        for(int i =0;i<teacherId.length;i++){
            String name=SQLiteOperation.queryTeacherNameByid(getBaseContext(),teacherId[i]);
            strings[i]=teacherId[i]+" "+name;
        }
        teacherAdapt =new ArrayAdapter(DeleteTeacher.this,R.layout.support_simple_spinner_dropdown_item,strings);
        teacherSpinner.setAdapter(teacherAdapt);
    }
}
