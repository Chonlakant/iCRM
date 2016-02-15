package software.is.com.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import software.is.com.myapplication.R;
import software.is.com.myapplication.RoundedTransformation;
import software.is.com.myapplication.activity.NewsFullActivity;
import software.is.com.myapplication.model.Post;
import software.is.com.myapplication.viewholder.RecyclerViewSimpleTextViewHolder;
import software.is.com.myapplication.viewholder.ViewHolderPhoto;
import software.is.com.myapplication.viewholder.ViewHolderText;


public class RecyclerViewTimelineListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // The items to display in your RecyclerView
    ArrayList<Post> list = new ArrayList<>();
    Context context;
    private final int PHOTO = 0, IMAGE = 1, CLIP = 3;

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerViewTimelineListAdapter(Context context, ArrayList<Post> list) {
        this.list = list;
        this.context = context;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        int postType = list.get(position).getPost().get(position).getStatus_img();
        Log.e("getItemViewType", position + ":" + postType);
        switch (postType) {
            case 0:
                return 0;
            case 1:
                return 1;
            default:
                return 0;


        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case 0:
                View v1 = inflater.inflate(R.layout.item_feed_text, viewGroup, false);
                viewHolder = new ViewHolderText(v1);
                break;
            case 1:
                View v2 = inflater.inflate(R.layout.item_feed_photo, viewGroup, false);
                viewHolder = new ViewHolderPhoto(v2);
                break;

            default:
                View v = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
                viewHolder = new RecyclerViewSimpleTextViewHolder(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        //More to come
        switch (viewHolder.getItemViewType()) {
            case 0:
                ViewHolderText vh1 = (ViewHolderText) viewHolder;
                configureViewHolder1(vh1, position);
                break;
            case 1:
                ViewHolderPhoto vh2 = (ViewHolderPhoto) viewHolder;
                configureViewHolder2(vh2, position);
                break;
            default:
                RecyclerViewSimpleTextViewHolder vh = (RecyclerViewSimpleTextViewHolder) viewHolder;
                configureDefaultViewHolder(vh, position);
                break;
        }
    }

    private void configureDefaultViewHolder(RecyclerViewSimpleTextViewHolder vh, int position) {
        vh.getLabel().setText((CharSequence) list.get(position));
    }

    private void configureViewHolder1(ViewHolderText vh1, int position) {
        final Post item = list.get(position);
        if (item != null) {
            vh1.getLabel1().setText(item.getPost().get(position).getTitle());
            vh1.getLabel2().setText(item.getPost().get(position).getTitle());

            Log.e("ddd", item.getPost().get(position).getStatus_img() + "");

            if (item.getPost().get(position).getStatus_img() == 0) {
                Picasso.with(context)
                        .load(R.drawable.is_logo)
                        .centerCrop()
                        .resize(200, 200)
                        .transform(new RoundedTransformation(100, 4))
                        .into(vh1.getProfile_avatar());


//                Picasso.with(context)
//                        .load(item.media.getThumbUrl())
//                        .centerCrop()
//                        .resize(200, 200)
//                        .transform(new RoundedTransformation(100, 4))
//                        .into(vh1.getIvUserAvatar1());
            }

            vh1.SetOnItemClickListener(new ViewHolderText.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Toast.makeText(context, item.getPost().get(position).getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
            vh1.SetOnItemClickListenerView(new ViewHolderText.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Toast.makeText(context, position + "", Toast.LENGTH_SHORT).show();
                    String title = item.getPost().get(position).getTitle();
                    String detail = item.getPost().get(position).getDetails();
                    String image_url = item.getPost().get(position).getFile_img();
                    String code = item.getPost().get(position).getCode();
                    int type = item.getPost().get(position).getStatus_img();
                    Intent i = new Intent(context, NewsFullActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("title", title);
                    i.putExtra("detail", detail);
                    i.putExtra("image", image_url);
                    i.putExtra("type", type);
                    i.putExtra("code", code);
                    i.putExtra("vender", "is");
                    context.getApplicationContext().startActivity(i);
                }
            });
        }
    }

    private void configureViewHolder2(ViewHolderPhoto vh2, int position) {
        //vh2.getThumb().setImageResource(R.drawable.imge);

        Post item = list.get(position);
        Log.e("sss", item.getPost().get(position).getFile_img());
        String imagUrl = item.getPost().get(position).getFile_img();
        Picasso.with(context)
                .load(imagUrl)
                .centerCrop()
                .resize(200, 200)
                .transform(new RoundedTransformation(100, 4))
                .into(vh2.getImageView());

        Picasso.with(context)
                .load(imagUrl)
                .centerCrop()
                .resize(200, 200)
                .transform(new RoundedTransformation(100, 4))
                .into(vh2.getProfile_avatar());

        Picasso.with(context)
                .load(imagUrl)
                .fit().centerCrop()
                .into(vh2.getThumb());

//        vh2.getLn_comment().setVisibility(View.GONE);
    }

}