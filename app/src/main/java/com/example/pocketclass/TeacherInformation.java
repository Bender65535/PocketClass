package com.example.pocketclass;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.entity.Teacher;
import com.sql.SQLiteOperation;

public class TeacherInformation extends Fragment {

    Button bt;
    private TextView tx_name;
    private TextView tx_subject;
    private TextView tx_date;
    private TextView tx_sex;
    private TextView tx_email;
    private TextView tx_phone;
    private TextView tx_address;

    private ImageButton bt_pic;
    private String uname;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_teacher_information, container, false);
        uname = TeacherActivity.uname;
        Context context = TeacherActivity.context;

        tx_name = (TextView) view.findViewById(R.id.tx_name);
        tx_subject = (TextView) view.findViewById(R.id.tx_subject);
        tx_date = (TextView) view.findViewById(R.id.tx_date);
        tx_sex = (TextView) view.findViewById(R.id.tx_sex);
        tx_email = (TextView) view.findViewById(R.id.tx_email);
        tx_phone = (TextView) view.findViewById(R.id.tx_phone);
        tx_address = (TextView) view.findViewById(R.id.tx_address);

        bt_pic = (ImageButton) view.findViewById(R.id.bt_pic);
        String name ;
        int subjectid=0;
        String sex ;
        String born ;
        String area ;
        String phone ;
        String email ;
        byte[] image;



        Teacher teacher = SQLiteOperation.queryTeacher(context, uname);

        name = teacher.getName();
        subjectid = teacher.getSubjectid();
        sex = teacher.getSex();
        born = teacher.getBorn();
        area = teacher.getArea();
        phone = teacher.getPhone();
        email = teacher.getEmail();
        if (teacher.getImage() == null)
            image = new byte[2];
        else
            image = teacher.getImage();

        tx_name.setText("姓名: " + name);
        if(subjectid==0)
            tx_subject.setText("科目: ");
        else
            tx_subject.setText("科目: "+SQLiteOperation.querySubjectNameBySubjectId(TeacherActivity.context,subjectid));
        tx_sex.setText("性别: "+sex);
        tx_date.setText("出生日期: "+born);
        tx_address.setText("地区: "+area);
        tx_phone.setText("电话号码: "+phone);
        tx_email.setText("邮箱: "+email);

        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        bt_pic.setImageBitmap(bitmap);


        bt = (Button) view.findViewById(R.id.update);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TeacherInformationUpdate.class);
                Bundle bundle = new Bundle();
                bundle.putString("uname", uname);
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
