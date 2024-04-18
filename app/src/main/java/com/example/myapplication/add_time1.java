package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class add_time1 extends LinearLayout {
    private Context context;
    private LinearLayout dateTimeBar;
    private TextView test_info;
    public static  List<ImageView> date_index_list=new ArrayList<>();//是否点击的图标

    public add_time1(Context context, LinearLayout dateTimeBar, TextView test_info){
        super(context);
        this.context=context;
        this.dateTimeBar=dateTimeBar;
        this.test_info=test_info;
    }

    public  void add_date_item(Integer start_idx, List<String> day, List<String> week){

        for(int i=start_idx;i<day.size();i=i+1){
            View date_item= LayoutInflater.from(context)
                    .inflate(R.layout.date_item1,dateTimeBar,false);

            TextView rcd_day=date_item.findViewById(R.id.rcd_day);
            TextView rcd_wee=date_item.findViewById(R.id.rcd_week);
            View date_index=date_item.findViewById(R.id.date_index);
           if(i==start_idx){rcd_day.setText("Now");
                                    }
           else{ rcd_day.setText(day.get(i));}
            rcd_wee.setText(week.get(i));
            date_item.setTag(i);  //循环地设置每一天的数据，并为每一天设下一个tag，方便监听点击事件时知道是哪一天被点击

            dateTimeBar.addView(date_item);//把date_item放入日历中

            date_item.setOnClickListener(v ->{
                date_index.setVisibility(VISIBLE);

                for(int k= 0;k<date_index_list.size();k++) {
                    if ((int) v.getTag() != k)//{
                        //test_info.setText(family.test_information.get(k));
                        //}
                        //else
                        // {
                        date_index_list.get(k).setVisibility(INVISIBLE);
                }
            });
            date_index_list.add(date_item.findViewById(R.id.date_index));
        }
    }
}
