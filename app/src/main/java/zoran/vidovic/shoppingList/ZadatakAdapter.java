package zoran.vidovic.shoppingList;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ZadatakAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Items> mList;
    private DBHelper dbHelper;

    public ZadatakAdapter(Context context) {
        mContext = context;
        mList = new ArrayList<Items>();
        dbHelper = new DBHelper(mContext,DBHelper.DbName,null,1);
    }

    private class ViewHolder {
        TextView itemName;
        CheckBox box;

    }
    public void update (Items[] lists) {
        mList.clear();
        if(lists != null) {
            for(Items list : lists) {
                mList.add(list);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //add item
    public void addItem(Items item) {
        mList.add(item);
        notifyDataSetChanged();
    }

    // remove item
    public void removeItem(int position) {
        mList.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_in_list_view, null);
            viewHolder = new ViewHolder();
            viewHolder.itemName = (TextView) convertView.findViewById(R.id.itemId);
            viewHolder.box = (CheckBox) convertView.findViewById(R.id.box);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /* Get data Object on position from list/database */

        Items i = mList.get(position);
        viewHolder.itemName.setText(i.getItems());



        viewHolder.box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    viewHolder.itemName.setPaintFlags(viewHolder.itemName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    viewHolder.itemName.setPaintFlags(viewHolder.itemName.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }
        });

        convertView.setOnLongClickListener(view -> {

         removeItem(position);

            return true; });


        return convertView;

    }
}
