package com.namuh.assignment.Activity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.namuh.assignment.Activity.Model.Studentquanly;
import com.namuh.assignment.R;

import java.util.ArrayList;
import java.util.List;

public class Xemsvadapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Studentquanly> studentquanlyList;
    ArrayList<Studentquanly> arrcoppyql;

    public Xemsvadapter(Context context, int layout, List<Studentquanly> studentquanlyList) {
        this.context = context;
        this.layout = layout;
        this.studentquanlyList = studentquanlyList;
        arrcoppyql = new ArrayList<>();
        arrcoppyql.addAll(studentquanlyList);
    }



    @Override
    public int getCount() {
        return studentquanlyList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        view=layoutInflater.inflate(layout,null);
        TextView txtdemo = view.findViewById(R.id.demo);
        TextView txtdemo1=view.findViewById(R.id.ngaysinh);

        Studentquanly studentquanly=studentquanlyList.get(position);
        txtdemo.setText(studentquanly.getTen());
        txtdemo1.setText(studentquanly.getNgay());

        Animation animation = AnimationUtils.loadAnimation(context,R.anim.animation_fadein);
        view.startAnimation(animation);

        return view;
    }


    public void serachds(String text){
        studentquanlyList.clear();
        if(text.length()==0){
            studentquanlyList.addAll(arrcoppyql);
        }else {
            for (Studentquanly st : arrcoppyql ){
                if(st.getTen().toLowerCase().contains(text.toLowerCase())){
                    studentquanlyList.add(st);
                }
                notifyDataSetChanged();
            }
        }

    }
}
