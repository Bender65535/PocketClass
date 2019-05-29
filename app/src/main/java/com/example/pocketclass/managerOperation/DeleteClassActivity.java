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

public class DeleteClassActivity extends AppCompatActivity {
    Button btYes;
    Button btNo;


    Spinner classSpinner;
    ArrayAdapter<String> classAdapt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_class);
        btYes=(Button)findViewById(R.id.yes);
        btNo=(Button)findViewById(R.id.no);
        classSpinner=(Spinner)findViewById(R.id.classes);


        setSpinner();

        btYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //对话框
                AlertDialog.Builder builder=new AlertDialog.Builder(DeleteClassActivity.this);
                builder.setTitle("提示")
                        .setMessage("确认删除?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String classid=classSpinner.getSelectedItem().toString();
                                int result =SQLiteOperation.deleteClass(getBaseContext(),Integer.parseInt(classid));

                                if(result>0){
                                    Toast.makeText(DeleteClassActivity.this,"删除成功",Toast.LENGTH_SHORT);
                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    Intent intent=new Intent(DeleteClassActivity.this, Manager.class);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(DeleteClassActivity.this,"删除失败",Toast.LENGTH_SHORT);

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
                Intent intent=new Intent(DeleteClassActivity.this, Manager.class);
                startActivity(intent);

            }
        });
    }

    private void setSpinner() {
        classSpinner=(Spinner)findViewById(R.id.classes);
        String[] classes = SQLiteOperation.queryAllClass(getBaseContext());
        classAdapt=new ArrayAdapter(DeleteClassActivity.this,R.layout.support_simple_spinner_dropdown_item,classes);
        classSpinner.setAdapter(classAdapt);
    }
}
