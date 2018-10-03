package boxiao1.feelbook.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import boxiao1.feelbook.R;
import boxiao1.feelbook.bean.Mood;

/**
 * @Date 2018-10-03.
 */
public class MoodAdapter extends BaseAdapter {
    private Context context;
    private List<Mood> list;

    public MoodAdapter(Context context, List<Mood> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = View.inflate(context, R.layout.item_mood_list, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_type.setText(list.get(position).getType());
        holder.tv_content.setText(list.get(position).getContent());
        Long addtime = list.get(position).getAddtime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 'T' HH:mm:ss");
        holder.tv_time.setText(sdf.format(new Date(addtime)));
        return view;
    }

    static class ViewHolder {
        public TextView tv_type;
        public TextView tv_time;
        public TextView tv_content;

        public ViewHolder(View view) {
            this.tv_type = view.findViewById(R.id.tv_type);
            this.tv_time = view.findViewById(R.id.tv_time);
            this.tv_content = view.findViewById(R.id.tv_content);
        }
    }
}
