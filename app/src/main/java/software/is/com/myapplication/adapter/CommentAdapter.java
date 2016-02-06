package software.is.com.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import software.is.com.myapplication.R;
import software.is.com.myapplication.Data;

/**
 * Created by ELMTRIX on 5/2/2559.
 */
public class CommentAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    List<Data.PostEntity> mComments;
    private ViewHolder mViewHolder;

    private Data.PostEntity mPost;

    public CommentAdapter(Context activity, List<Data.PostEntity> posts) {
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        mComments = posts;
    }

    @Override
    public int getCount() {
        return mComments.size();
    }

    @Override
    public Object getItem(int position) {
        return mComments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_comment, parent, false);
            mViewHolder = new ViewHolder();
            mViewHolder.create_by = (TextView) convertView.findViewById(R.id.user_comment);
            mViewHolder.create_date = (TextView) convertView.findViewById(R.id.tv_showTime);
            mViewHolder.comment_content = (TextView) convertView.findViewById(R.id.tv_showComment);
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mPost = mComments.get(position);

        mViewHolder.create_by.setText(mPost.getCreate_by());
        mViewHolder.create_date.setText(mPost.getCreate_date());
        mViewHolder.comment_content.setText(mPost.getComment_content());

        return convertView;
    }

    private static class ViewHolder {

        TextView create_by;
        TextView create_date;
        TextView comment_content;
        TextView img_path;
    }
}
