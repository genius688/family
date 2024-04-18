package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.AlertDialog;

public class family extends AppCompatActivity {

    public static List<String> test_information = new ArrayList<>(16);  //用于点击各个 “日” 显示的文本
    public List<String> day3 = new ArrayList<>(16);  //用于记录日历中每一块要显示的“日”
    public List<String> week3 = new ArrayList<>(16);//用于记录日历中每一块要显示的“星期”
    public List<String> month3 = new ArrayList<>(16);//用于记录日历中每一块要显示的“月份”
    private LinearLayout containerLayout;
    public static List<Task> taskList = new ArrayList<>();//任务列表
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //****设置大月份字体
        //*****设置大月份字体
        super.onCreate(savedInstanceState);
        setContentView(R.layout.family);
        //设置月份
        TextView monthTextView = findViewById(R.id.month);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/演示镇魂行楷.TTF");
        monthTextView.setTypeface(typeface);
        Date currentDate = new Date();// 获取当前日期
        Calendar calendar = Calendar.getInstance(Locale.CHINA);// 使用Calendar获取月份
        calendar.setTime(currentDate);
        int month = calendar.get(Calendar.MONTH) + 1; // Calendar的月份是从0开始的，所以需要+1
        int day =calendar.get(Calendar.DAY_OF_WEEK);
        // 汉字月份数组
        String[] chineseMonths = {"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};
        String[] chineseDays ={"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
        // 根据月份获取汉字月份
        if (month <= 12) {
            String chineseMonth = chineseMonths[month - 1]; // 数组索引从0开始，所以需要-1
            monthTextView.setText(chineseMonth);
        } else {
            monthTextView.setText("月份错误");
        }
        monthTextView.setTextSize(40);
        String days =chineseDays[day-2];
        //设置日期
        TextView date=findViewById(R.id.date);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy年MM月dd日");
        Date currentDate2=new Date();
        String formattedDate=dateFormat.format(currentDate);
        date.setText(formattedDate);
        date.setTextColor(0xff2F979C);
        date.setTextSize(18);
        //设置星期几
        TextView day2=findViewById(R.id.day);
        day2.setText(days);
        day2.setTextColor(0xff2F979C);
        day2.setTextSize(15);

        //****创建日历*****
        //获取过去一周内的出入库记录
//        test_information.add("Click 1");
//        test_information.add("Click 2");
//        test_information.add("Click 3");
//        test_information.add("Click 4");
//        test_information.add("Click 5");
//        test_information.add("Click 6");
//        test_information.add("Click 7");
//        test_information.add("Click 8");
//        test_information.add("Click 9");
//        test_information.add("Click 10");
//        test_information.add("Click 11");
//        test_information.add("Click 12");
//        test_information.add("Click 13");
//        test_information.add("Click 14");
//        test_information.add("Click 15");
//        test_information.add("Click 16");//用于点击各个 “日” 显示的文本

        get_days();  //函数定义在下面，用于将今天以前的七天对应的星期和日存入上面的day和month链表中

        //at是一个类，里面有一种函数add_date_item，可以动态地在日历中添加每一天
        //findViewById(R.id.date_time_bar表示要添加入的位置，findViewById(R.id.test）表示点击要显示的位置
        add_time1 at = new add_time1(this,findViewById(R.id.date_time_bar),findViewById(R.id.test));
        at.add_date_item(0, day3,week3);//触发添加函数
//****创建日历*****

        //*****动态添加任务*****
        TextView inputTextView = findViewById(R.id.add_task);
        inputTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        //*****动态添加任务*****


         //*****任务清单的线性布局******
        containerLayout = findViewById(R.id.containerLayout);
        //*****任务清单的线性布局******

        //*****清空任务*****
        Button clearButton = (Button) findViewById(R.id.clear_task);

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(family.this);
                builder.setTitle("清空任务");
                builder.setMessage("是否要清空所有任务？");

                // 设置确定按钮的点击事件
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击确定后，清空所有任务
                        containerLayout.removeAllViews();
                    }
                });

                // 设置取消按钮的点击事件
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击取消后，不执行任何操作，对话框自动关闭
                    }
                });

                // 显示对话框
                builder.show();
            }
        });
        //*****清空任务*****
    }


   //********函数********
    //****获取十六天的数据***
    public void get_days() {
        day3.clear();
        week3.clear();
        month3.clear();
        // 创建一个SimpleDateFormat对象用于格式化日期
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // 创建一个Calendar实例，并设置为当前日期和时间
        Calendar calendar = Calendar.getInstance();

        // 设置日历为今天
        calendar.add(Calendar.DATE, 0);

        // 遍历未来十六天的每一天
        for (int i = 0; i < 16; i++) {
            int formatday = calendar.get(Calendar.DAY_OF_MONTH);
            day3.add(Integer.toString(formatday));
            // 获取当天的月份（注意：月份是从0开始的）
            int formatweek = calendar.get(Calendar.DAY_OF_WEEK);
            week3.add(getWEEK(formatweek));
            // 将日历向前推一天，准备下一次循环
            calendar.add(Calendar.DATE, 1);
        }
    }
//****获取十六天的数据***

    //****将星期几转化为“周几”***
    public String getWEEK(Integer m){
        switch(m){
            case (1): return "周日";
            case (2): return "周一";
            case (3): return "周二";
            case (4): return "周三";
            case (5): return "周四";
            case (6): return "周五";
            case (7): return "周六";
        }
        return "";
    }
//****将星期几转化为“周几”***

    //*****动态添加任务*****
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog, null);

        final EditText editText1 = dialogView.findViewById(R.id.editText1);
        final EditText editText2 = dialogView.findViewById(R.id.editText2);
        final EditText editText3 = dialogView.findViewById(R.id.editText3);
        final TextView taskDateTextView = dialogView.findViewById(R.id.select_date);
        // 设置日期选择监听器
        taskDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        family.this, // 将此处的ShowDialogActivity替换为你的Activity名
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // 设置日期，注意月份是从0开始的，所以+1
                                String date = (monthOfYear + 1) + "月" + dayOfMonth + "日";
                                taskDateTextView.setText(date);

                                // 如果需要，可以将选择的日期保存在某个变量中，供后续使用
                            }
                        }, year, month, day
                );
                // 设置DatePickerDialog的最小日期为今天
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                // 设置最大可选日期为今天起15天后
                calendar.add(Calendar.DAY_OF_MONTH, 15);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());

                datePickerDialog.show();
            }
        });

        builder.setView(dialogView)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String input1 = editText1.getText().toString();
                        String input2 = editText2.getText().toString();
                        String input3 = editText3.getText().toString();
                        String date = taskDateTextView.getText().toString(); // 获取用户选择的日期

                        // 创建自定义布局的实例
                        View customView = getLayoutInflater().inflate(R.layout.task, null);
                        TextView textView1 = customView.findViewById(R.id.textView1);
                        TextView textView2 = customView.findViewById(R.id.textView2);
                        TextView textView3 = customView.findViewById(R.id.textView3);
                        TextView textViewDate = customView.findViewById(R.id.task_date);
                        Button actionbutton=customView.findViewById(R.id.task_op);
                        // 设置TextView的文本
                        textView1.setText(input1);
                        textView2.setText(input2);
                        textView3.setText(input3);
                        textViewDate.setText(date); // 设置日期TextView的文本

                        // 将自定义布局的实例添加到containerLayout中
                        containerLayout.addView(customView);
                        //将这个任务添加到任务类储存
                        addNewTask(date,input1,input2,input3);
                        // 设置按钮的点击事件监听器
                        actionbutton.setOnClickListener(new Button.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showTaskOptionsDialog(customView); // 显示任务选项弹窗
                            }
                        });
                    }
                })
                .setNegativeButton("取消", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    //*****动态添加任务*****
    //******添加到任务类
    public void addNewTask(String date, String title,String content,String person) {
        Task newTask = new Task(date, title,content,person);
        taskList.add(newTask);
    }
    //******添加到任务类
    // ******显示任务选项弹窗
    private void showTaskOptionsDialog(View customView) {
        //*****编辑任务弹窗
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        builder2.setTitle("编辑任务");
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog, null);
        //*****获取该任务的信息
        TextView title = customView.findViewById(R.id.textView1);
        TextView content= customView.findViewById(R.id.textView2);
        TextView person = customView.findViewById(R.id.textView3);
        //*****任务功能弹窗
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("任务操作");
        builder.setItems(new String[]{"启动任务", "暂缓任务", "编辑任务", "删除任务"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ImageView startTaskButton = customView.findViewById(R.id.start_task);
                ImageView stopTaskButton = customView.findViewById(R.id.stop_task);
                switch (which) {
                    case 0://启动任务逻辑
                        startTaskButton.setVisibility(View.VISIBLE);
                        stopTaskButton.setVisibility(View.INVISIBLE);
                        break;
                    case 1:// 暂缓任务逻辑
                        startTaskButton.setVisibility(View.INVISIBLE);
                        stopTaskButton.setVisibility(View.VISIBLE);
                        break;
                    case 2:// 编辑任务逻辑
                        final EditText editText1 = dialogView.findViewById(R.id.editText1);
                        final EditText editText2 = dialogView.findViewById(R.id.editText2);
                        final EditText editText3 = dialogView.findViewById(R.id.editText3);
                        final TextView taskDateTextView = dialogView.findViewById(R.id.select_date);
                        // 预先填充任务信息（如果需要）
                        editText1.setText(title.getText());
                        editText2.setText(content.getText());
                        editText3.setText(person.getText());
                        // 设置日期选择监听器
                        taskDateTextView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final Calendar calendar = Calendar.getInstance();
                                int year = calendar.get(Calendar.YEAR);
                                int month = calendar.get(Calendar.MONTH);
                                int day = calendar.get(Calendar.DAY_OF_MONTH);

                                DatePickerDialog datePickerDialog = new DatePickerDialog(
                                        family.this, // 将此处的ShowDialogActivity替换为你的Activity名
                                        new DatePickerDialog.OnDateSetListener() {
                                            @Override
                                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                                // 设置日期，注意月份是从0开始的，所以+1
                                                String date = (monthOfYear + 1) + "月" + dayOfMonth + "日";
                                                taskDateTextView.setText(date);

                                                // 如果需要，可以将选择的日期保存在某个变量中，供后续使用
                                            }
                                        }, year, month, day
                                );
                                // 设置DatePickerDialog的最小日期为今天
                                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                                // 设置最大可选日期为今天起15天后
                                calendar.add(Calendar.DAY_OF_MONTH, 15);
                                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());

                                datePickerDialog.show();
                            }
                        });
                        builder2.setView(dialogView)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String input1 = editText1.getText().toString();
                                        String input2 = editText2.getText().toString();
                                        String input3 = editText3.getText().toString();
                                        String date = taskDateTextView.getText().toString(); // 获取用户选择的日期

                                        // 创建自定义布局的实例
                                        View customView = getLayoutInflater().inflate(R.layout.task, null);
                                        TextView textView1 = customView.findViewById(R.id.textView1);
                                        TextView textView2 = customView.findViewById(R.id.textView2);
                                        TextView textView3 = customView.findViewById(R.id.textView3);
                                        TextView textViewDate = customView.findViewById(R.id.task_date);
                                        Button actionbutton=customView.findViewById(R.id.task_op);
                                        // 设置TextView的文本
                                        textView1.setText(input1);
                                        textView2.setText(input2);
                                        textView3.setText(input3);
                                        textViewDate.setText(date); // 设置日期TextView的文本

                                        // 将自定义布局的实例添加到containerLayout中
                                        containerLayout.addView(customView);
                                        //将这个任务添加到任务类储存
                                        addNewTask(date,input1,input2,input3);
                                        // 设置按钮的点击事件监听器
                                        actionbutton.setOnClickListener(new Button.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                showTaskOptionsDialog(customView); // 显示任务选项弹窗
                                            }
                                        });
                                    }
                                })
                                .setNegativeButton("取消", null);

                        AlertDialog dialog2 = builder2.create();
                        dialog2.show();
                        break;
                    case 3:// 删除任务逻辑，从LinearLayout中移除视图等
                        containerLayout.removeView(customView);
                        break;
                }
            }
        });
        builder.show();
    }
    // ******显示任务选项弹窗
}
