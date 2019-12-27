package com.example.itime.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.itime.riqi;
import com.example.itime.shanchuxiugai;
import com.example.itime.tianjianew;
import com.example.itime.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.itime.shanchuxiugai.RESULT_DELETE;
import static com.example.itime.MainActivity.REQUEST_CODE;

public class homen extends Fragment {
    int nian123;
    long missnss;
    View v1;
    String sg;
    private ListView listView;
    private ImageView vbackground;
    private FloatingActionButton buttianjia;
    public DateAdapter pter;
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.home, container, false);
        dates.add(new riqi("Android itime","日期：2022/05/31","iTime开发",R.drawable.foreground,"只剩1550天"));
        pter=new DateAdapter(root.getContext(),R.layout.date_item,dates);
        listView=root.findViewById(R.id.list_view);
        listView.setAdapter(pter);
        vbackground=root.findViewById(R.id.view_background);
        vbackground.setImageResource(R.drawable.timg);
        buttianjia=root.findViewById(R.id.buttianjia);
        buttianjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent = new Intent(root.getContext(), tianjianew.class);
                 intent.putExtra("title","");
                 startActivityForResult(intent, REQUEST_CODE);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                nian123=i;
                v1=view;
                String str1=new String(sg);
                Intent intent = new Intent(root.getContext(), shanchuxiugai.class);
                String first=((TextView)view.findViewById(R.id.title)).getText().toString();
                intent.putExtra("position",i);
                intent.putExtra("title",((TextView)view.findViewById(R.id.title)).getText().toString());
                intent.putExtra("endtime",((TextView)view.findViewById(R.id.endtime)).getText().toString());
                intent.putExtra("sttr",str1);
                intent.putExtra("missnss",missnss);
                Toast.makeText(homen.this.getContext(),first,Toast.LENGTH_SHORT).show();
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        return root;
    }
    class DateAdapter extends ArrayAdapter<riqi> {
        private int resourceId;
        public DateAdapter(Context context, int resource, List<riqi> objects) {
            super(context, resource, objects);
            resourceId = resource;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            riqi date = getItem(position);
            View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            ((ImageView) view.findViewById(R.id.image_view)).setImageURI(date.getUri());
            ((TextView) view.findViewById(R.id.title)).setText(date.getTitle());
            ((TextView) view.findViewById(R.id.beizhu)).setText(date.getBeizhu());
            ((TextView) view.findViewById(R.id.endtime)).setText(date.getEndtime());
            ((TextView) view.findViewById(R.id.daoshu)).setText(date.getDaoshu());
            return view;
        }
    }
    public List<riqi> dates=new ArrayList<>();
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode)
        {
            case REQUEST_CODE:
                if(resultCode==RESULT_OK)
                {
                    String title=data.getStringExtra("title");
                    String endtime=data.getStringExtra("endtime");
                    String beizhu=data.getStringExtra("beizhu");
                    String daoshu=data.getStringExtra("daoshu");
                    String miaoshu=data.getStringExtra("miaoshu");
                    missnss=Long.valueOf(miaoshu);
                    String str=data.getStringExtra("uri");
                    Uri uri= Uri.parse((String) str);
                    sg=getFilePathFromURI(this.getContext(),uri);
                    dates.add(new riqi(title,endtime,beizhu,daoshu,uri));
                    pter.notifyDataSetChanged();
                }
               if(resultCode==RESULT_DELETE)
                {
                    int i=data.getIntExtra("position",0);
                    dates.remove(i);
                    pter.notifyDataSetChanged();
                }
                break;
        }
    }
    private String getFilePathFromURI(Context context, Uri contentUri) {
       File rootDataDir = context.getFilesDir();
       String fileName = getFileName(contentUri);
       if (!TextUtils.isEmpty(fileName)) {
              File copyFile = new File(rootDataDir + File.separator + fileName + ".jpg");
              copyFile(context, contentUri, copyFile);
              return copyFile.getAbsolutePath();
       }
       return null;
    }
    private String getFileName(Uri uri) {
       if (uri == null) return null;
       String fileName = null;
       String path = uri.getPath();
       int cut = path.lastIndexOf('/');
       if (cut != -1) {
           fileName = path.substring(cut + 1);
       }
       return System.currentTimeMillis() + fileName;
    }
    private void copyFile(Context context, Uri srcUri, File dstFile) {
        try {
              InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
              if (inputStream == null) return;
              OutputStream outputStream = new FileOutputStream(dstFile);
              copyStream(inputStream, outputStream);
              inputStream.close();
              outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private int copyStream(InputStream input, OutputStream output) throws Exception, IOException {
        final int BUFFER_SIZE = 1024 * 2;
        byte[] buffer = new byte[BUFFER_SIZE];
        BufferedInputStream in = new BufferedInputStream(input, BUFFER_SIZE);
        BufferedOutputStream out = new BufferedOutputStream(output, BUFFER_SIZE);
        int count = 0, n = 0;
        try {
                        while ((n = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
                        out.write(buffer, 0, n);
                        count += n;
                        }
                        out.flush();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
            }
            try {
                in.close();
            } catch (IOException e) {
            }
        }
        return count;
    }
}