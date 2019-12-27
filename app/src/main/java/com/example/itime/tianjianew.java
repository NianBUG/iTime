package com.example.itime;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;


public class tianjianew extends AppCompatActivity {

    public static final int RESULT_DONE = 222;
    ListView new_setup;
    ImageView picture;
    FloatingActionButton okbutton;
    String []setup={"日期","重复","图片","添加标签"};
    TextView new_titlecon,new_describecon;
    Uri uri;
    String daoshu;
    int hourOfDay1,minute1,year1,monthOfYear1,dayOfMonth1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitytianjia);
        new_setup=this.findViewById(R.id.new_setup);
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                tianjianew.this,android.R.layout.simple_list_item_1,setup);
        new_setup.setAdapter(adapter);
        new_titlecon=this.findViewById(R.id.new_titlecon);
        new_describecon=this.findViewById(R.id.new_describecon);
        picture=this.findViewById(R.id.picture);

        final String title=getIntent().getStringExtra("title");
        String beizhu=getIntent().getStringExtra("beizhu");
        String endtime=getIntent().getStringExtra("endtime");
        String string=getIntent().getStringExtra("string");

        final String a="";
        if(!title.equals(a))
        {
            Uri uri=Uri.parse(string);
            picture.setImageURI(uri);
            new_titlecon.setText(title);
            new_describecon.setText(beizhu);
            setup[0]=endtime;
            adapter.notifyDataSetChanged();
        }

        new_setup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0) {
                    Calendar c = Calendar.getInstance();
                    new TimePickerDialog(tianjianew.this,
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view,
                                                      int hourOfDay, int minute) {
                                    setup[0]=setup[0]+"  时间："+hourOfDay+"："+minute;
                                    hourOfDay1=hourOfDay;
                                    minute1=minute;
                                    adapter.notifyDataSetChanged();
                                }
                            }
                            , c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
                            true).show();

                        final Calendar calendar;
                        DatePickerDialog dialog;
                        calendar = Calendar.getInstance();
                        dialog = new DatePickerDialog(tianjianew.this,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        System.out.println("年-->" + year + "月-->"
                                                + monthOfYear + "日-->" + dayOfMonth);
                                        monthOfYear++;
                                        setup[0]="已选日期："+year + "/" + monthOfYear + "/"
                                                + dayOfMonth;
                                        monthOfYear--;
                                        year1=year;
                                        monthOfYear1=monthOfYear;
                                        dayOfMonth1=dayOfMonth;
                                        adapter.notifyDataSetChanged();
                                    }
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH));
                        dialog.show();

                }
                else if(i==1)
                {
                    Toast.makeText(tianjianew.this, "1", Toast.LENGTH_SHORT).show();
                    final String[] items = {"自定义","每周", "每月", "每年", "无"};
                    AlertDialog.Builder dialog = new AlertDialog.Builder(tianjianew.this);
                    dialog.setTitle("周期")
                            .setItems(items, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(which==0)
                                    {
                                        final EditText inputServer = new EditText(tianjianew.this);
                                        String digists = "0123456789";
                                        inputServer.setKeyListener(DigitsKeyListener.getInstance(digists));
                                        inputServer.setHint("输入周期（天）");

                                        AlertDialog.Builder builder = new AlertDialog.Builder(tianjianew.this);
                                        builder.setTitle("周期").setView(inputServer)
                                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                String mMeetName = inputServer.getText().toString();
                                                String a="";
                                                if(!a.equals(mMeetName))
                                                {
                                                    setup[1]="重复   "+mMeetName+"天";
                                                    adapter.notifyDataSetChanged();
                                                }
                                            }
                                        });
                                        builder.show();
                                    }
                                    else
                                    {
                                        setup[1]="重复   "+items[which];
                                        adapter.notifyDataSetChanged();
                                        Toast.makeText(tianjianew.this,"选择: " + items[which],Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    dialog.show();
                }
                else if(i==2)
                {
                    Toast.makeText(tianjianew.this, "2", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, 2);

                }
                else if(i==3)
                {
                    Toast.makeText(tianjianew.this,"3",Toast.LENGTH_SHORT).show();
                    final EditText inputServer = new EditText(tianjianew.this);
                    inputServer.setHint("请输入标签");
                    AlertDialog.Builder builder = new AlertDialog.Builder(tianjianew.this);
                    builder.setTitle("添加标签").setView(inputServer)
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            String mMeetName = inputServer.getText().toString();
                            String a="";
                            if(!a.equals(mMeetName))
                            {
                                setup[3]="添加标签   已添加："+mMeetName;
                                adapter.notifyDataSetChanged();
                            }
                            else {
                                setup[3]="添加标签   "+mMeetName;
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                    builder.show();
                }
            }
        });

        okbutton=this.findViewById(R.id.OkButton);
        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(title.equals(a))
                {
                    Calendar cc= Calendar.getInstance();
                    cc.set(year1,monthOfYear1,dayOfMonth1,hourOfDay1,minute1);
                    long tt=cc.getTimeInMillis();
                    Calendar calendar1= Calendar.getInstance();
                    long Time1=calendar1.getTimeInMillis();
                    long daoshu1=Math.abs(tt-Time1);
                    long miaoshu=daoshu1;
                    long daoshu2=daoshu1/(1000*60*60*24);
                    if(tt>Time1)
                        daoshu="只剩"+daoshu2+"天";
                    else
                        daoshu="已过"+daoshu2+"天";
                    Toast.makeText(tianjianew.this,"dianji",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("title",new_titlecon.getText().toString().trim());
                    intent.putExtra("uri",uri.toString());
                    intent.putExtra("beizhu", new_describecon.getText().toString().trim());
                    intent.putExtra("endtime", setup[0]);
                    intent.putExtra("daoshu",daoshu);
                    intent.putExtra("miaoshu",String.valueOf(miaoshu));
                    setResult(RESULT_OK, intent);
                    tianjianew.this.finish();
                }
                else
                {
                    Intent intent = new Intent();
                    intent.putExtra("title",new_titlecon.getText().toString().trim());
                    intent.putExtra("beizhu", new_describecon.getText().toString().trim());
                    intent.putExtra("endtime", setup[0]);
                    intent.putExtra("uri",uri.toString());
                    setResult(RESULT_DONE, intent);
                    tianjianew.this.finish();
                }


            }
        }) ;

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 2)
        {
             if (data != null) {
             uri = data.getData();
             picture=this.findViewById(R.id.picture);
             picture.setImageURI(uri);
             }
        }
    }


}
