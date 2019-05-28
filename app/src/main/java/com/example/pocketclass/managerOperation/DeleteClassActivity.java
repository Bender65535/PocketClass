package com.example.pocketclass.managerOperation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import com.example.pocketclass.MainActivity;
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
        setSpinner();
        btYes=(Button)findViewById(R.id.yes);
        btNo=(Button)findViewById(R.id.no);
        classSpinner=(Spinner)findViewById(R.id.classes);


        setSpinner();
        btYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String classid=classSpinner.getSelectedItem().toString();
                SQLiteOperation.deleteClass(getBaseContext(),"id=?",new String[]{classid});
            }
        });

        btNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DeleteClassActivity.this, MainActivity.class);
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
