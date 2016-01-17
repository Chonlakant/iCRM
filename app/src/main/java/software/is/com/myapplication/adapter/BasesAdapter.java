package software.is.com.myapplication.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import software.is.com.myapplication.R;
import software.is.com.myapplication.model.Post;


public class BasesAdapter extends android.widget.BaseAdapter implements AdapterView.OnClickListener {

    private Context context;
    private OnItemClickListener mItemClickListener;

    public ArrayList<Post> list = new ArrayList<Post>();

    public BasesAdapter(Context context, ArrayList<Post> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {


        TextView title;


        public ViewHolder(View row) {
            title = (TextView) row.findViewById(R.id.title);

        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder mViewHolder = null;

        if (convertView == null) {

            LayoutInflater mInflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = mInflater.inflate(R.layout.item_listview, parent, false);

            mViewHolder = new ViewHolder(convertView);

            Post item = list.get(position);


            mViewHolder.title.setText(item.getPosts().get(position).getName());


//            Picasso.with(context)
//                    .load(item.getpAvatar())
//                    .transform(new RoundedTransformation(50, 4))
//                    .resize(100, 100)
//                    .into(mViewHolder.avatar);


        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }


    @Override
    public void onClick(View view) {
//
//        switch (view.getId()) {
//            case R.id.video_thumb:
//                if (mItemClickListener != null) {
//                    mItemClickListener.onItemClick(view);
//                }
//                break;
//
//        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


}

