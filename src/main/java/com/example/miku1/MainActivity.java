package com.example.miku1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.miku1.frag.Frag01;
import com.example.miku1.frag.Frag02;
import com.example.miku1.frag.Frag03;
import com.example.miku1.frag.Frag04;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String imageUrl = "http://f.expoon.com//sub//news//2016//01//21//580828_230x162_0.jpg";
    private ImageLoader instance;
    private SharedPreferences sp;
    private LinearLayout line1;
    private DrawerLayout drawerLayout;
    private FrameLayout fragment;
    private ListView dreawlist;
    private List<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        instance = ImageLoader.getInstance();
        sp = getSharedPreferences("info",Context.MODE_PRIVATE);
        fragment = findViewById(R.id.fragment);
        dreawlist = findViewById(R.id.draewlistvw01);
        line1 = findViewById(R.id.line1);
        drawerLayout = findViewById(R.id.drawer);

        final FragmentManager manager = getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        final Frag01 frag01 = new Frag01();
        final Frag02 frag02 = new Frag02();
        final Frag03 frag03 = new Frag03();
        final Frag04 frag04 = new Frag04();
        transaction.add(R.id.fragment,frag01).show(frag01);
        transaction.add(R.id.fragment,frag02).hide(frag02);
        transaction.add(R.id.fragment,frag03).hide(frag03);
        transaction.add(R.id.fragment,frag04).hide(frag04);
        transaction.commit();


        arrayList = new ArrayList<>();
        for (int i=0;i<4;i++){
            switch (i) {
                case 0:
                    arrayList.add("跟我走吧");
                    break;
                case 1:
                    arrayList.add("我的收藏");
                    break;
                case 2:
                    arrayList.add("我的关注");
                    break;
                case 3:
                    arrayList.add("我的帖子");
                    break;

            }
        }
        DreaMyAdapter adapter = new DreaMyAdapter();
        dreawlist.setAdapter(adapter);
        dreawlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Frag01 frag01 = new Frag01();
                Bundle bundle = new Bundle();
                bundle.putString("key", arrayList.get(position));
                frag01.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,frag01).commit();
                drawerLayout.closeDrawer(line1);

            }
        });

        ArrayList<Fragment> list = new ArrayList<>();
        list.add(new Frag01());
        list.add(new Frag02());
        list.add(new Frag03());
        list.add(new Frag04());

        RadioGroup rg = findViewById(R.id.rg);
        rg.check(0);
        rg.check(rg.getChildAt(0).getId());
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction1 = manager.beginTransaction();
                switch (checkedId){
                    case 1:
                        transaction1.show(frag01).hide(frag02).hide(frag03).hide(frag04);
                        break;
                    case 2:
                        transaction1.show(frag02).hide(frag01).hide(frag03).hide(frag04);
                        break;
                    case 3:
                        transaction1.show(frag03).hide(frag02).hide(frag01).hide(frag04);
                        break;
                    case 4:
                        transaction1.show(frag04).hide(frag02).hide(frag03).hide(frag01);
                        break;
                }
                transaction1.commit();
            }
        });

        }

    private class DreaMyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(MainActivity.this, R.layout.draewitem, null);
                viewHolder.textView = convertView.findViewById(R.id.draewText);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.textView.setText(arrayList.get(position));
            return convertView;
        }
    }

    class ViewHolder {
        TextView textView;
    }
}
