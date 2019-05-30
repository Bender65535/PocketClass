package com.example.pocketclass.studentOperation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.pocketclass.R;
import com.example.pocketclass.StudentActivity;
import com.sql.SQLiteOperation;

public class QueryAppraise extends AppCompatActivity {

    private Spinner teacherSpinner;
    private TextView wordsText;
    private TextView scoreText;

    ArrayAdapter<String> teacherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_appraise);

        teacherSpinner=(Spinner)findViewById(R.id.teacher);
        wordsText =(TextView)findViewById(R.id.words);
        scoreText =(TextView)findViewById(R.id.score);

        setSpinner();

        teacherSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String teachers=teacherSpinner.getSelectedItem().toString();
                int tid=Integer.parseInt(teachers.split(" ")[0]);

                //查询老师所给评语
                String words=SQLiteOperation.queryWordsByStudentIdAndTeacherId(getBaseContext(),StudentActivity.studentid,tid);
                //查询老师所给评分
                int score=SQLiteOperation.queryClassScoreByStudentIdAndTeacherId(getBaseContext(),StudentActivity.studentid,tid);

                wordsText.setText(words);
                scoreText.setText(score+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setSpinner(){
        teacherSpinner=(Spinner)findViewById(R.id.teacher);
        int[] teacherid= SQLiteOperation.queryTeacherByClassId(getBaseContext(), StudentActivity.classid);
        String[] teachers=new String[teacherid.length];
        for(int i=0;i<teacherid.length;i++){
            teachers[i]=teacherid[i]+" "+SQLiteOperation.queryTeacherNameByid(getBaseContext(),teacherid[i]);
        }
        teacherAdapter=new ArrayAdapter(QueryAppraise.this,R.layout.support_simple_spinner_dropdown_item,teachers);
        teacherSpinner.setAdapter(teacherAdapter);
    }
}
