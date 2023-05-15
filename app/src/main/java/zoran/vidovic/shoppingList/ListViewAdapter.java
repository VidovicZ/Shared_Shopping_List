package zoran.vidovic.shoppingList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Lists> mList;

    public ListViewAdapter(Context context) {
        mContext = context;
        mList = new ArrayList<Lists>();
    }

    private class ViewHolder {
        TextView name;
        TextView shared;

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
    public void addItem(Lists item) {
        mList.add(item);
        notifyDataSetChanged();
    }

    // remove item
    public void removeItem(Lists item) {
        mList.remove(item);
        notifyDataSetChanged();
    }

    public void update (Lists[] lists) {
        mList.clear();
        if(lists != null) {
            for(Lists list : lists) {
                mList.add(list);
            }
        }
        notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_list_view, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.listName);
            viewHolder.shared = (TextView) convertView.findViewById(R.id.listShared1);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /* Get data Object on position from list/database */

        Lists listView = mList.get(position);
        viewHolder.name.setText(listView.getName());

        Lists ls = mList.get(position);
        viewHolder.shared.setText(listView.getShared());

        return convertView;



    }
}
