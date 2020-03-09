package com.namuh.assignment.Activity.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.namuh.assignment.R;

public class MenuActivity extends AppCompatActivity {
    Button btnthemlop, btnxemds, btnquanlysv, btnxemquanly;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    int REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        anhXa();
        // Khởi tao nagivation
        actionBarDrawerToggle = new ActionBarDrawerToggle(MenuActivity.this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.themds:
                        dialogcustom();
                        break;
                    case R.id.xemds:
                        Intent intent = new Intent(MenuActivity.this, XemDsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.quanlysinhvien:
                        Intent intent1 = new Intent(MenuActivity.this, QuanlySvActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.aboutapp:
                        diaglog();
                        break;
                    case R.id.lienhe:
                        dialoglienhe();
                        break;
                }
                return false;
            }
        });
        // tạo sự kiện button
        btnthemlop.setOnClickListener(new View.OnClickListener() {
                                          public void onClick(View view) {
                                              dialogcustom();
                                          }
                                      }
        );
        btnxemds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, XemDsActivity.class);
                startActivity(intent);
            }
        });
        btnquanlysv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, QuanlySvActivity.class);
                startActivity(intent);

            }
        });
        btnxemquanly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, XemQLActivity.class);
                startActivity(intent);
            }
        });
    }
    // đóng hoặc mở navigation
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // ràng buộc nhập tên lớp ãm lớp
    // thêm dữ liệu tên lớp mã lơp
    private void dialogcustom() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_themlop);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        //ánh xạ
        final EditText edtmalop = dialog.findViewById(R.id.edtmalop);
        final EditText edttenlop = dialog.findViewById(R.id.edttenlop);
        Button btnluu = dialog.findViewById(R.id.btnluu);
        Button btnxoa = dialog.findViewById(R.id.btnxoa);
        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String malop = edtmalop.getText().toString().trim();
                String tenlop = edttenlop.getText().toString().trim();
                if (edtmalop.length() == 0) {
                    Toast.makeText(MenuActivity.this, "Vui Lòng Nhập Mã Lớp!!", Toast.LENGTH_SHORT).show();
                    edtmalop.requestFocus();
                    return;
                }
                if (edttenlop.length() == 0) {
                    Toast.makeText(MenuActivity.this, "Vui Lòng Nhập Tên! ", Toast.LENGTH_SHORT).show();
                    edttenlop.requestFocus();
                    return;
                }
                if (malop.equals("")) {
                    Toast.makeText(MenuActivity.this, "Vui Lòng Nhập Mã Lớp!", Toast.LENGTH_SHORT).show();
                    edtmalop.requestFocus();
                } else {
                    MainActivity.dataBase.QueryData("INSERT INTO Class VALUES(null,'" + malop + "','" + tenlop + "')");
                    Toast.makeText(MenuActivity.this, "Đã Thêm!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
        btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtmalop.setText("");
                edttenlop.setText("");
            }
        });
    }
    // dialog giới thiệu app
    private void diaglog() {
        Dialog dialog = new Dialog(MenuActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.diag_gioithieu);
        dialog.show();

    }

    // diaglog liên hệ
    private void dialoglienhe() {
        Dialog dialog = new Dialog(MenuActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.diag_lienhe);
        dialog.show();
        ImageView imgcall = dialog.findViewById(R.id.call);
        ImageView imgfb= dialog.findViewById(R.id.iconfb);
        ImageView imggmail=dialog.findViewById(R.id.gmail);

        // sự kiện call,fb,gmail
        imgcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(MenuActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE);

            }
        });
        imgfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.facebook.com/red.moon.3745"));
                startActivity(intent);

            }
        });
        imggmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                String[] recipients={"namuhn01@gmail.com"};
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.putExtra(Intent.EXTRA_SUBJECT,"Subject text here...");
                intent.putExtra(Intent.EXTRA_TEXT,"Body of the content here...");
                intent.putExtra(Intent.EXTRA_CC,"namuhn01@gmail.com");
                intent.setType("text/html");
                intent.setPackage("com.google.android.gm");
                startActivity(Intent.createChooser(intent, "Send mail"));
            }
        });

    }
    // cấp quyền gọi
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:012345678"));
            startActivity(intent);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    // ánh xạ
    private void anhXa() {
        btnthemlop = findViewById(R.id.btnthem);
        btnxemds = findViewById(R.id.btnxem);
        btnquanlysv = findViewById(R.id.btnqualy);
        btnxemquanly = findViewById(R.id.btnxemquanlysv);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigation);

    }
}
