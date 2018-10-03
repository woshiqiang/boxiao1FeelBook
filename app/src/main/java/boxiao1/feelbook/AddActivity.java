package boxiao1.feelbook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import boxiao1.feelbook.bean.Mood;
import boxiao1.feelbook.db.DBManager;

/**
 * add the mood
 */
public class AddActivity extends AppCompatActivity {
    private static final String TAG = "AddActivity";
    private TextView title;
    private Toolbar toolbar;
    private Spinner spinner;
    private EditText et_content;
    private Mood mood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initView();

        initData();
    }

    private void initData() {
        mood = (Mood) getIntent().getSerializableExtra("mood");
        if (mood != null) {//编辑
            title.setText("edit mood");
            //设置选中的心情
            String[] stringArray = getResources().getStringArray(R.array.types);
            int postion = 0;//下标
            for (int i = 0; i < stringArray.length; i++) {
                if (stringArray[i].equalsIgnoreCase(mood.getType())){
                    postion = i;
                    break;
                }

            }
            spinner.setSelection(postion);
            et_content.setText(mood.getContent());
        } else {
            //添加
            title.setText("add mood");
        }
    }

    /**
     * init
     */
    private void initView() {
        title = (TextView) findViewById(R.id.title);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_back);

        //set the back event
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.inflateMenu(R.menu.tool_bar_menu_save);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                save();
                return true;
            }
        });

        spinner = findViewById(R.id.spinner);
        et_content = findViewById(R.id.et_content);

    }

    /**
     * 添加或保存
     */
    private void save() {
        String content = et_content.getText().toString();
        String type = (String) spinner.getSelectedItem();
        if (mood == null) {
            mood = new Mood();
            mood.setAddtime(System.currentTimeMillis());
        }
        mood.setContent(content);
        mood.setType(type);
        DBManager.getInstance(this).save(mood);
        Toast.makeText(this, "save success!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
