package com.namuh.assignment.Activity.Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.namuh.assignment.Activity.Adapter.XemDsAdapter;
import com.namuh.assignment.Activity.Model.Studentxemds;
import com.namuh.assignment.R;

import java.util.ArrayList;

public class XemDsActivity extends AppCompatActivity {
    public static ListView listViewxemDS;
    ArrayList<Studentxemds> studentxemdsArrayList = new ArrayList<>();
    XemDsAdapter xemDsAdapter;
    int stt;
    String malop;
    String tenlop;
    int Vitri = -1;
    public static int animationitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_ds);
        listViewxemDS = findViewById(R.id.listxemds);
        xemDsAdapter = new XemDsAdapter(XemDsActivity.this, R.layout.xemdongsv, studentxemdsArrayList);
        listViewxemDS.setAdapter(xemDsAdapter);
        getDATA();
        listViewxemDS.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            // tạo long-click
            // khởi tạo dialog
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(XemDsActivity.this);
                builder.setTitle("Thông Báo!");
                builder.setIcon(R.drawable.warning);
                builder.setMessage("Bạn Có Muốn Xóa "+ tenlop + " Không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // lấy stt get
                        Studentxemds studentxemds = studentxemdsArrayList.get(position);
                        final int idget = studentxemds.getStt();

                        MainActivity.dataBase.QueryData("DELETE FROM Class WHERE Id ='" + idget + "'");
                        Toast.makeText(XemDsActivity.this, "Đã Xóa " + tenlop+"_"+malop, Toast.LENGTH_SHORT).show();
                        getDATA();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.show();
                return false;
            }
        });

    }

    // lấy dữ liệu
    public void getDATA() {
        Cursor dataclas = MainActivity.dataBase.GetData("SELECT * FROM Class");
        studentxemdsArrayList.clear();
        while (dataclas.moveToNext()) {
            stt = dataclas.getInt(0);
            malop = dataclas.getString(1);
            tenlop = dataclas.getString(2);
            studentxemdsArrayList.add(new Studentxemds(stt, malop, tenlop   ));
        }
        xemDsAdapter = new XemDsAdapter(XemDsActivity.this, R.layout.xemdongsv, studentxemdsArrayList);
        listViewxemDS.setAdapter(xemDsAdapter);
        xemDsAdapter.notifyDataSetChanged();

    }

    // tạo dialg cập nhật tên sv
    public void Dialogsuasv(final String tenlop, final String malop, final int stt) {
        final Dialog dialog = new Dialog(XemDsActivity.this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_suads);
        //ánh xạ dilog
        final EditText edtnhap = dialog.findViewById(R.id.edtnhapten);
        final EditText edtnhaplop = dialog.findViewById(R.id.edtnhapmalop);
        Button btnxacnhands = dialog.findViewById(R.id.btnxacnhan);
        Button btnhuy = dialog.findViewById(R.id.btnhuy);
        edtnhap.setText(tenlop);
        edtnhaplop.setText(malop);
        // bắt sự kiện hủy và xác nhận
        btnxacnhands.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String ten = edtnhap.getText().toString();
                final String malop = edtnhaplop.getText().toString();
                if (edtnhap.length() == 0) {
                    Toast.makeText(XemDsActivity.this, "Vui Lòng Nhập Tên", Toast.LENGTH_SHORT).show();
                    edtnhap.requestFocus();
                    return;
                } else {
                    MainActivity.dataBase.QueryData("UPDATE Class SET TenLop = '" + ten + "' , MaLop = '" + malop + "' WHERE Id = '" + stt + "'");
                    Toast.makeText(XemDsActivity.this, "Đã Cập Nhật", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    getDATA();
                }
            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    // tìm kiếm danh sách sinh viên
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        SearchView  searchView = (SearchView) menu.findItem(R.id.search_sv123).getActionView();
       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String s) {
               return false;
           }
           @Override
           public boolean onQueryTextChange(String s) {
              xemDsAdapter.serachds(s);
              xemDsAdapter.notifyDataSetChanged();
               return false;
           }
       });
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        animationitem = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}
