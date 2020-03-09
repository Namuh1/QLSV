package com.namuh.assignment.Activity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.namuh.assignment.Activity.Model.Studentxemds;
import com.namuh.assignment.R;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter {
   private Context context;
   private  int layout;
   private List<Studentxemds> spinnerlist;
   TextView txtchonlop;

    public SpinnerAdapter(Context context, int layout, List<Studentxemds> studentxemds) {
        this.context = context;
        this.layout = layout;
        this.spinnerlist = studentxemds;
    }

    @Override
    public int getCount() {
        return spinnerlist.size();
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
        LayoutInflater inflater=LayoutInflater.from(context);
        view=inflater.inflate(layout,null);
        // ánh xạ
        txtchonlop=view.findViewById(R.id.txtchonlop);

        Studentxemds studentxemds=spinnerlist.get(position);
        txtchonlop.setText(studentxemds.getTenlop());



        return view;
    }
}
