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



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder mViewHolder = null;

        if (convertView == null) {

            LayoutInflater mInflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = mInflater.inflate(R.layout.item_feed_text, parent, false);

            mViewHolder = new ViewHolder(convertView);

            Post item = list.get(position);


            mViewHolder.title.setText(item.getPost().get(position).getTitle());
            mViewHolder.count.setText("อ่าน "+item.getPost().get(position).getCount() + "คน");
//            if(item.getPost().get(position).getStatus_img() == 0){
//                mViewHolder.icon.setVisibility(View.VISIBLE);
//                mViewHolder.icon2.setVisibility(View.GONE);
//            }if(item.getPost().get(position).getStatus_img() == 1){
//                mViewHolder.icon.setVisibility(View.GONE);
//                mViewHolder.icon2.setVisibility(View.VISIBLE);
//            }
            mViewHolder.icon.setVisibility(View.VISIBLE);
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

    }

    public class ViewHolder {


        TextView title;
        TextView count;
        ImageView icon,icon2;

        public ViewHolder(View row) {
            title = (TextView) row.findViewById(R.id.title);
            count = (TextView) row.findViewById(R.id.count);
            icon = (ImageView) row.findViewById(R.id.icon);
            icon2 = (ImageView) row.findViewById(R.id.icon2);
        }
    }



}

