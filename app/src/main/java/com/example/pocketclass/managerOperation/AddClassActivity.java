package com.example.pocketclass.managerOperation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.pocketclass.Manager;
import com.example.pocketclass.R;
import com.sql.SQLiteOperation;

public class AddClassActivity extends AppCompatActivity {
    Button btYes;
    Button btNo;
    EditText ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        btYes=(Button)findViewById(R.id.add_yes);
        btNo=(Button)findViewById(R.id.add_no);
        ed=(EditText)findViewById(R.id.classname);

        btYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String className=ed.getText().toString();
                if(SQLiteOperation.isClassNameExist(getBaseContext(),className)){
                    Toast.makeText(AddClassActivity.this,"该班名已存在,请重新输入",Toast.LENGTH_SHORT).show();
                    return;
                }
                long result=SQLiteOperation.addClass(getBaseContext(),className);
                if(result>0){
                    Toast.makeText(AddClassActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddClassActivity.this, Manager.class);
                startActivity(intent);
            }
        });
    }
}
