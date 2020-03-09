package com.namuh.assignment.Activity.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.namuh.assignment.Activity.Activity.XemDsActivity;
import com.namuh.assignment.Activity.Model.Studentxemds;
import com.namuh.assignment.R;

import java.util.ArrayList;
import java.util.List;

public class XemDsAdapter extends BaseAdapter {
    private XemDsActivity context;
    private int layout;
    private List<Studentxemds> studentxemdsList;
    ArrayList<Studentxemds> arrcoppyds;

    public XemDsAdapter(XemDsActivity context, int layout, List<Studentxemds> studentxemdsList) {
        this.context = context;
        this.layout = layout;
        this.studentxemdsList = studentxemdsList;
        arrcoppyds = new ArrayList<>();
        arrcoppyds.addAll(studentxemdsList);
    }

    @Override
    public int getCount() {
        return studentxemdsList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class ViewHolder {
        TextView txtstt, txtmalop, txttenlop;
        ImageView imgeedit;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(layout, null);

            // ánh xạ
            viewHolder.txtstt = view.findViewById(R.id.stt);
            viewHolder.txtmalop = view.findViewById(R.id.malop);
            viewHolder.txttenlop = view.findViewById(R.id.TenLop);
            viewHolder.imgeedit = view.findViewById(R.id.imgeditor);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        final Studentxemds studentxemds1 = studentxemdsList.get(i);// lấy phần từ trong danh sách của đối tượng
        viewHolder.txtstt.setText((i + 1) + "");
        viewHolder.txtmalop.setText(studentxemds1.getMalop());
        viewHolder.txttenlop.setText(studentxemds1.getTenlop());

        // bắt sự  kiện bằng icon chỉnh sửa cho người dùng
        // gán giá tri

        viewHolder.txttenlop.setText(studentxemds1.getTenlop());
        // tạo animation cho list
        Animation animation = null;
        switch (XemDsActivity.animationitem) {
            case R.id.menufadein:
                animation = AnimationUtils.loadAnimation(context, R.anim.animation_fadein);
                view.startAnimation(animation);
                break;
            case R.id.menuscale:
                animation = AnimationUtils.loadAnimation(context, R.anim.animation_scale);
                view.startAnimation(animation);
                break;
            case R.id.menushake:
                animation = AnimationUtils.loadAnimation(context, R.anim.animation_shake);
                view.startAnimation(animation);
                break;
            case R.id.menuslideinleft:
                animation = AnimationUtils.loadAnimation(context, R.anim.animation_slideleft);
                view.startAnimation(animation);
                break;
            case R.id.menuslideinup:
                animation = AnimationUtils.loadAnimation(context, R.anim.animation_slideup);
                view.startAnimation(animation);
                break;
        }

        animation = AnimationUtils.loadAnimation(context, R.anim.animation_fadein);
        view.startAnimation(animation);
        viewHolder.imgeedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.Dialogsuasv(studentxemds1.getTenlop(), studentxemds1.getMalop(), studentxemds1.getStt());
            }
        });

        return view;
    }
    // tìm kiếm sinh viên
    public void serachds(String text) {
        studentxemdsList.clear();
        if (text.length() == 0) {
            studentxemdsList.addAll(arrcoppyds);
        } else {
            for (Studentxemds st : arrcoppyds) {
                if (st.getTenlop().toLowerCase().contains(text.toLowerCase())) {
                    studentxemdsList.add(st);
                }
                notifyDataSetChanged();
            }
        }
    }


}
