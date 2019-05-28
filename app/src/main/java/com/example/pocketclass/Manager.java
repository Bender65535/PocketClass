package com.example.pocketclass;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import com.example.pocketclass.managerOperation.AddClassActivity;
import com.example.pocketclass.managerOperation.DeleteClassActivity;

public class Manager extends AppCompatActivity {
    ImageButton addClass;
    ImageButton deleteClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        addClass=(ImageButton)findViewById(R.id.add_class);
        deleteClass=(ImageButton)findViewById(R.id.delete_class);




        addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Manager.this, AddClassActivity.class);
                startActivity(intent);
            }
        });
        deleteClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Manager.this, DeleteClassActivity.class);
                startActivity(intent);
            }
        });
    }
}
