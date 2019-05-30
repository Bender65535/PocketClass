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

public class CalculateDatetimeScore extends AppCompatActivity {
    private Spinner subjectSpinner;
    private TextView scoreText;

    ArrayAdapter<String> subjectAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_datetime_score);


        subjectSpinner=(Spinner)findViewById(R.id.subject);
        scoreText =(TextView)findViewById(R.id.score);

        setSpinner();

        subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String subjects=subjectSpinner.getSelectedItem().toString();
                int tid=Integer.parseInt(subjects.split(" ")[0]);

                //查询老师所给作业分数(40%)
                double score=0.4*(SQLiteOperation.queryHomeworkScoreByStudentIdAndTeacherId(getBaseContext(),StudentActivity.studentid,tid));
                //老师所给课堂评价分数(30%)
                score+=0.3*(SQLiteOperation.queryClassScoreByStudentIdAndTeacherId(getBaseContext(),StudentActivity.studentid,tid));

                //查询缺勤次数
                int times=SQLiteOperation.queryTimesByStudentIdAndTeacherId(getBaseContext(),StudentActivity.studentid,tid);
                //计算考勤分数(30%)
                int appendandceScore=100-10*times;
                score+=0.3*appendandceScore;

                scoreText.setText(score+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setSpinner(){
        subjectSpinner=(Spinner)findViewById(R.id.subject);

        int[] teacherid= SQLiteOperation.queryTeacherByClassId(getBaseContext(), StudentActivity.classid);

        String[] subjects=new String[teacherid.length];
        for(int i=0;i<teacherid.length;i++){
            String teacherName=SQLiteOperation.queryTeacherNameByid(getBaseContext(),teacherid[i]);
            //根据老师id查科目id
            int subjectId=SQLiteOperation.querySubjectIdByTeacherId(getBaseContext(),teacherid[i]);
            //根据科目id查科目名
            String subjectName=SQLiteOperation.querySubjectNameById(getBaseContext(),subjectId);
            //老师id  老师名字  科目名
            subjects[i]=teacherid[i]+" "+teacherName+subjectName;
        }
        subjectAdapter=new ArrayAdapter(CalculateDatetimeScore.this,R.layout.support_simple_spinner_dropdown_item,subjects);
        subjectSpinner.setAdapter(subjectAdapter);
    }
}
