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

public class AddSubjectActivity extends AppCompatActivity {
    Button btYes;
    Button btNo;
    EditText ed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        btYes=(Button)findViewById(R.id.add_yes);
        btNo=(Button)findViewById(R.id.add_no);
        ed=(EditText)findViewById(R.id.classname);

        btYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subjectName=ed.getText().toString();

                long result=SQLiteOperation.addSubject(getBaseContext(),subjectName);
                if(result>0){
                    Toast.makeText(AddSubjectActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddSubjectActivity.this, Manager.class);
                startActivity(intent);
            }
        });
    }
}
