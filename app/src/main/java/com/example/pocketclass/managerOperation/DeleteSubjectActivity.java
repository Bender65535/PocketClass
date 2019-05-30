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

public class DeleteSubjectActivity extends AppCompatActivity {

    Button btYes;
    Button btNo;


    Spinner subjectSpinner;
    ArrayAdapter<String> subjectAdapt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_subject);
        btYes=(Button)findViewById(R.id.yes);
        btNo=(Button)findViewById(R.id.no);
        subjectSpinner =(Spinner)findViewById(R.id.classes);


        setSpinner();

        btYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //对话框
                AlertDialog.Builder builder=new AlertDialog.Builder(DeleteSubjectActivity.this);
                builder.setTitle("提示")
                        .setMessage("确认删除?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String subjectName= subjectSpinner.getSelectedItem().toString();

                                //根据科目名找科目id
                                int subjectId=SQLiteOperation.querySubjectIdBySubjectName(getBaseContext(),subjectName);

                                int result =SQLiteOperation.deleteSubject(getBaseContext(),subjectId);
                                if(result>0){
                                    Toast.makeText(DeleteSubjectActivity.this,"删除成功",Toast.LENGTH_SHORT);
                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    Intent intent=new Intent(DeleteSubjectActivity.this, Manager.class);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(DeleteSubjectActivity.this,"删除失败",Toast.LENGTH_SHORT);

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
                Intent intent=new Intent(DeleteSubjectActivity.this, Manager.class);
                startActivity(intent);

            }
        });
    }


    private void setSpinner() {
        subjectSpinner =(Spinner)findViewById(R.id.classes);
        String[] classes = SQLiteOperation.queryAllSubjects(getBaseContext());
        subjectAdapt =new ArrayAdapter(DeleteSubjectActivity.this,R.layout.support_simple_spinner_dropdown_item,classes);
        subjectSpinner.setAdapter(subjectAdapt);
    }
}
