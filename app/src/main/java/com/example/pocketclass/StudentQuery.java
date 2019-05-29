package com.example.pocketclass;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.example.pocketclass.studentOperation.CalculateDatetimeScore;
import com.example.pocketclass.studentOperation.QuerryAttendance;
import com.example.pocketclass.studentOperation.QueryAppraise;
import com.example.pocketclass.studentOperation.QueryHomeWork;

public class StudentQuery extends Fragment {

    private ImageButton attendance;
    private ImageButton appraise;
    private ImageButton homework;
    private ImageButton calculate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_student_query,container,false);

        attendance=view.findViewById(R.id.attendance);
        appraise=view.findViewById(R.id.appraise);
        homework=view.findViewById(R.id.homework);
        calculate=view.findViewById(R.id.calculate);

        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QuerryAttendance.class);
                startActivity(intent);
            }
        });

        appraise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QueryAppraise.class);
                startActivity(intent);
            }
        });

        homework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QueryHomeWork.class);
                startActivity(intent);
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CalculateDatetimeScore.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
