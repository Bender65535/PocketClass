package com.example.pocketclass;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import com.example.pocketclass.managerOperation.*;

public class Manager extends AppCompatActivity {
    ImageButton addClass;
    ImageButton deleteClass;
    ImageButton addSubject;
    ImageButton deleteSubject;

    ImageButton deleteStudent;
    ImageButton deleteTeacher;

    ImageButton addTeacherToClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        addClass=(ImageButton)findViewById(R.id.add_class);
        deleteClass=(ImageButton)findViewById(R.id.delete_class);

        addSubject=(ImageButton)findViewById(R.id.add_subject);
        deleteSubject=(ImageButton)findViewById(R.id.delete_subject);

        deleteStudent=(ImageButton)findViewById(R.id.delete_student);
        deleteTeacher=(ImageButton)findViewById(R.id.delete_teacher);

        addTeacherToClass=(ImageButton)findViewById(R.id.add_teacher_to_class);

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

        addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Manager.this, AddSubjectActivity.class);
                startActivity(intent);
            }
        });

        deleteSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Manager.this, DeleteSubjectActivity.class);
                startActivity(intent);
            }
        });

        deleteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Manager.this, DeleteStudent.class);
                startActivity(intent);
            }
        });
        deleteTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Manager.this, DeleteTeacher.class);
                startActivity(intent);
            }
        });

        addTeacherToClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Manager.this, AddTeacherToClass.class);
                startActivity(intent);
            }
        });
    }
}
