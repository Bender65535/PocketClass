package com.example.pocketclass;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import com.entity.Area;
import com.entity.City;
import com.entity.Province;
import com.sql.SQLiteOperation;
import com.xml.NationService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class TeacherInformationUpdate extends AppCompatActivity {
    private ImageButton bt1;
    private ImageButton bt_camera;


    private EditText ed_information;
    private EditText ed_date;
    private EditText ed_email;
    private EditText ed_phone;

    private RadioButton rd_man;
    private RadioButton rd_woman;

    Bitmap bitmap;

    private Spinner provinceSpinner;
    private Spinner citySpinner;
    private Spinner areaSpinner;

    private Spinner subjectSpinner;


    List<Province> provinces = null;
    List<City> cities = null;
    List<Area> areas = null;

    String[] provinceName = null;
    String[] cityName = null;
    String[] areaName = null;

    private ArrayAdapter<String> provinceAdapt;
    private ArrayAdapter<String> cityAdapt;
    private ArrayAdapter<String> areaAdapt;

    private ArrayAdapter<String> subjectAdapt;

    public static String uname=TeacherActivity.uname;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                bitmap = (Bitmap) data.getExtras().get("data");
                bt_camera.setImageBitmap(bitmap);

            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_information_update);

        ed_information = (EditText) findViewById(R.id.ed_base_information);
        ed_date = (EditText) findViewById(R.id.ed_date);

        rd_man = (RadioButton) findViewById(R.id.rd_man);
        rd_woman = (RadioButton) findViewById(R.id.rd_woman);

        ed_email = (EditText) findViewById(R.id.ed_email);
        ed_phone = (EditText) findViewById(R.id.ed_phone);

        bt_camera = (ImageButton) findViewById(R.id.bt_pic);
        bt_camera = (ImageButton) findViewById(R.id.bt_btcamera);

        provinceSpinner = (Spinner) findViewById(R.id.province);
        citySpinner = (Spinner) findViewById(R.id.city);
        areaSpinner = (Spinner) findViewById(R.id.area);
        subjectSpinner=(Spinner)findViewById(R.id.subject);

//      设置spinner控件
        setSpinner();


        bt_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);

            }
        });


        bt1 = (ImageButton) findViewById(R.id.bt_save);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name;
                int subjectid;
                String sex = "";
                if (rd_man.isChecked()) {
                    sex = "男";
                } else if (rd_woman.isChecked()) {
                    sex = "女";
                }
                String born;
                String email;
                String phone;
                String provinceString;
                String cityString;
                String areaString;
                String area;
                byte[] image;
                name = ed_information.getText().toString();

                if (subjectSpinner.getSelectedItem() != null)
                    subjectid =SQLiteOperation.querySubjectIdBySubjectName(getBaseContext(),subjectSpinner.getSelectedItem().toString());
                else
                    subjectid=0;

                born = ed_date.getText().toString();
                email = ed_email.getText().toString();
                phone = ed_phone.getText().toString();
                provinceString = provinceSpinner.getSelectedItem().toString();
                cityString = citySpinner.getSelectedItem().toString();
                areaString = areaSpinner.getSelectedItem().toString();
                area = provinceString + "," + cityString + "," + areaString;

                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                ContentValues values = new ContentValues();
                values.put("name", name);
                values.put("sex", sex);
                values.put("born", born);
                values.put("email", email);
                values.put("phone", phone);
                values.put("area", area);

                if(bitmap!=null){
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    image = baos.toByteArray();
                    values.put("image", image);
                }

                values.put("subjectid", subjectid);

                SQLiteOperation.updateTeacher(TeacherActivity.context, values, "uname=?", new String[]{uname});

                Intent intent = new Intent(TeacherInformationUpdate.this, TeacherActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("uname", uname);

                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

    }


    private void setSpinner() {
        provinceSpinner = (Spinner) findViewById(R.id.province);
        citySpinner = (Spinner) findViewById(R.id.city);
        areaSpinner = (Spinner) findViewById(R.id.area);


        //查询所有科目名,并适配到spinner中
        String[] subjects = SQLiteOperation.queryAllSubjects(getBaseContext());


        subjectSpinner = (Spinner) findViewById(R.id.subject);
        subjectAdapt = new ArrayAdapter(TeacherInformationUpdate.this, R.layout.support_simple_spinner_dropdown_item, subjects);
        subjectSpinner.setAdapter(subjectAdapt);
        try {
            provinces = NationService.getNationInfo(getAssets().open("nations.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }


//        绑定province适配器
        int a = provinces.size() - 1;
        provinceName = new String[provinces.size()];
        int i = 0;
        for (Province province : provinces) {
            provinceName[i] = province.getNames();
            i++;
        }

        provinceAdapt = new ArrayAdapter(TeacherInformationUpdate.this, R.layout.support_simple_spinner_dropdown_item, provinceName);
        provinceSpinner.setAdapter(provinceAdapt);


        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Province tempPronvince = null;
                for (Province province : provinces) {
                    if (provinceName[position].equals(province.getNames())) {
                        tempPronvince = province;
                        break;
                    }
                }

//                绑定city适配器
                cities = tempPronvince.getCities();
                cityName = new String[cities.size()];
                int i = 0;
                for (City city : cities) {
                    cityName[i] = city.getName();
                    i++;
                }
                cityAdapt = new ArrayAdapter(TeacherInformationUpdate.this, R.layout.support_simple_spinner_dropdown_item, cityName);
                citySpinner.setAdapter(cityAdapt);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                City tempCity = null;
                for (City city : cities) {
                    if (cityName[position].equals(city.getName())) {
                        tempCity = city;
                        break;
                    }
                }

//                绑定area适配器
                areas = tempCity.getAres();
                areaName = new String[areas.size()];
                int i = 0;
                for (Area area : areas) {
                    areaName[i] = area.getName();
                    i++;
                }
                areaAdapt = new ArrayAdapter(TeacherInformationUpdate.this, R.layout.support_simple_spinner_dropdown_item, areaName);
                areaSpinner.setAdapter(areaAdapt);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
