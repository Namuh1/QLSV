package com.namuh.assignment.Activity.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.namuh.assignment.Activity.Activity.QuanlySvActivity;
import com.namuh.assignment.Activity.Model.Studentquanly;
import com.namuh.assignment.R;

import java.util.List;

public class QuanlySvAdapter extends BaseAdapter {
    private QuanlySvActivity context;
    private int layout;
    private List<Studentquanly>studentquanlyList;

    public QuanlySvAdapter(QuanlySvActivity context, int layout, List<Studentquanly>studentquanlyList ) {
        this.context = context;
        this.layout = layout;
        this.studentquanlyList = studentquanlyList;
    }


    @Override
    public int getCount() {
        return studentquanlyList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class Viewholder{
        TextView txtmssv,txtten,txtngay;
        ImageView imgedit;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       Viewholder viewholder;
        if(view==null){
            viewholder = new Viewholder();
            LayoutInflater inflater= LayoutInflater.from(context);
            view=inflater.inflate(layout,null);

            //ánh xạ
            viewholder.txtmssv=view.findViewById(R.id.mssv);
            viewholder.txtten=view.findViewById(R.id.tenqlsv);
            viewholder.txtngay=view.findViewById(R.id.ngaythangnam);
            viewholder.imgedit=view.findViewById(R.id.imgedit);
            view.setTag(viewholder);
        }else {
           viewholder = (Viewholder) view.getTag();


        }
        // gán giá trị
        final Studentquanly studentquanly=studentquanlyList.get(i);
        viewholder.txtmssv.setText((i+1)+"");
        viewholder.txtten.setText(studentquanly.getTen());
        viewholder.txtngay.setText(studentquanly.getNgay());
        // tạo click sự kiện edit
        viewholder.imgedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.Dialogedit(studentquanly.getMsvv(),studentquanly.getTen(),studentquanly.getNgay());
            }
        });

        Animation animation = (Animation) AnimationUtils.loadAnimation(context,R.anim.animation_fadein);
        view.startAnimation(animation);

        return view;
    }
}
