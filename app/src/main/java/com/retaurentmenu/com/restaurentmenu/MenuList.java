package com.retaurentmenu.com.restaurentmenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MenuList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
        Toast.makeText(MenuList.this, "menu list", Toast.LENGTH_SHORT).show();
    }
    public void click(View v)
    {
        Intent i  =new Intent(MenuList.this,Login.class);
        startActivity(i);
    }
}
