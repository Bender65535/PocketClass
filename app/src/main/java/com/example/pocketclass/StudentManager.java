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
import com.example.pocketclass.teacherOperation.ClassAppraise;
import com.example.pocketclass.teacherOperation.ManagerAttendance;
import com.example.pocketclass.teacherOperation.ManagerHomeWork;

public class StudentManager extends Fragment {
    private ImageButton attendance;
    private ImageButton appraise;
    private ImageButton homework;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_student_manager, container, false);

        attendance=view.findViewById(R.id.attendance);
        appraise=view.findViewById(R.id.appraise);
        homework=view.findViewById(R.id.homework);

        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ManagerAttendance.class);
                startActivity(intent);
            }
        });

        appraise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ClassAppraise.class);
                startActivity(intent);
            }
        });

        homework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ManagerHomeWork.class);
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
