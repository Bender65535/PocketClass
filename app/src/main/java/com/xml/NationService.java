package com.xml;

import android.util.Xml;
import com.entity.Area;
import com.entity.City;
import com.entity.Province;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class NationService {

    public static List<Province> getNationInfo(InputStream is) {


        XmlPullParser parser = Xml.newPullParser();
        InputStream i = is;
        try {
            parser.setInput(is, "utf-8");
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        List<Province> provinces = new ArrayList<Province>();
        Province province = null;
        String str = "123";
        List<City> cities = null;
        City city = null;
        str = "4";
        List<Area> areas = null;
        Area area = null;

        int type = 0;
        try {
            type = parser.getEventType();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        while (type != XmlPullParser.END_DOCUMENT) {
            switch (type) {
                case XmlPullParser.START_TAG:
//                    开始标签
                    if ("root".equals(parser.getName())) {

                    } else if ("province".equals(parser.getName())) {
                        province = new Province();
                        province.setNames(parser.getAttributeValue(0));
                        cities = new ArrayList<City>();
                    } else if ("city".equals(parser.getName())) {
                        city = new City();
                        city.setName(parser.getAttributeValue(0));
                        city.setIndex(Integer.parseInt(parser.getAttributeValue(1)));
                        areas = new ArrayList<Area>();
                    } else if ("area".equals(parser.getName())) {
                        area = new Area();
                        area.setName(parser.getAttributeValue(0));
                        area.setId(Integer.parseInt(parser.getAttributeValue(1)));
                        areas.add(area);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("city".equals(parser.getName())) {
                        city.setAres(areas);
                        cities.add(city);
                    } else if ("province".equals(parser.getName())) {
                        province.setCities(cities);
                        provinces.add(province);
                    }
            }

            try {
                type = parser.next();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
        }

        return provinces;
    }
}
