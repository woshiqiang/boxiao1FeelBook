package boxiao1.feelbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import boxiao1.feelbook.adapter.MoodAdapter;
import boxiao1.feelbook.bean.Mood;
import boxiao1.feelbook.db.DBManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView title;
    private Toolbar toolbar;
    private ListView listView;
    private List<Mood> list = new ArrayList<>();
    private MoodAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        getData();
    }

    private void getData() {
        list.clear();
        list.addAll(DBManager.getInstance(this).query());
        adapter.notifyDataSetChanged();
    }


    private void initData() {
        adapter = new MoodAdapter(this, list);
        listView.setAdapter(adapter);
    }

    private void initView() {
        title = (TextView) findViewById(R.id.title);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //从菜单里加载右侧添加按钮
        toolbar.inflateMenu(R.menu.tool_bar_menu_add);
        //菜单点击事件
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                //点击添加，调整到添加页面
                startActivity(new Intent(MainActivity.this, AddActivity.class));
                return true;
            }
        });

        title.setText("boxiao1_feelsbook");

        listView = findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //点击 修改
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                intent.putExtra("mood", list.get(i));
                startActivity(intent);
            }
        });

        //长按删除
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                DBManager.getInstance(MainActivity.this).delete(list.get(i));
                getData();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }
}
