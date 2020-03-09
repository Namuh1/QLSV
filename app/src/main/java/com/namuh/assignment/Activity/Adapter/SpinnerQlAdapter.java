package com.namuh.assignment.Activity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.namuh.assignment.Activity.Model.Studentxemds;
import com.namuh.assignment.R;

import java.util.ArrayList;
import java.util.List;

public class SpinnerQlAdapter extends BaseAdapter {
    private Context context;
    private List<Studentxemds> studentxemdsList;
    private  int layout;
    TextView txtchonlopql;

    public SpinnerQlAdapter(Context context, ArrayList<Studentxemds> studentquanlyList, int layout) {
        this.context = context;
        this.studentxemdsList = studentquanlyList;
        this.layout = layout;

    }

    @Override
    public int getCount() {
        return studentxemdsList.size();
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

        txtchonlopql=view.findViewById(R.id.txtchonlopql);
        Studentxemds studentxemds=studentxemdsList.get(position);
        txtchonlopql.setText(studentxemds.getTenlop());

        return view;
    }
}
