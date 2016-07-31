package com.careermate.ADAPTERS;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.careermate.CORE_LIBS.HELPER.MyConvert;
import com.careermate.CORE_LIBS.UIMANAGERS.LogManager;
import com.careermate.CORE_LIBS.VIEWS.DefaultFontButton;
import com.careermate.CORE_LIBS.VIEWS.DefaultFontTextView;
import com.careermate.HELPER.FollowHelper;
import com.careermate.MODELS.FacebookUriObject;
import com.careermate.MODELS.FollowObject;
import com.careermate.fbhack.R;

import java.util.List;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class FollowPeopleAdapter extends RecyclerView.Adapter<FollowPeopleAdapter.MyViewHolder> {

    private Context mContext;
    private List<FollowObject> list;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public DefaultFontTextView tvName;
        public LinearLayout linContent;
        public MyViewHolder(View view) {
            super(view);
            tvName= (DefaultFontTextView) view.findViewById(R.id.tvName);
            linContent= (LinearLayout) view.findViewById(R.id.linContent);
        }
    }


    public FollowPeopleAdapter(Context mContext, List<FollowObject> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_follow_people_item, parent, false);

        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final FollowObject obj = list.get(position);
        holder.tvName.setText(obj.getName());
        holder.tvName.setTag(2);
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogManager.getINSTANCE().log(v.getTag().toString());
                if (Integer.parseInt(v.getTag().toString()) == 2) {
                    int hh= MyConvert.DpToPx(40,mContext);
                    holder.linContent.setVisibility(View.VISIBLE);
                    holder.tvName.setTag(1);
                    for(int i=0;i<obj.getList().size();i++){
                        final FacebookUriObject o= obj.getList().get(i);
                        DefaultFontButton but=new DefaultFontButton(mContext);
                        LinearLayout.LayoutParams lp= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,hh);
                        lp.gravity= Gravity.LEFT|Gravity.CENTER_VERTICAL;
                        lp.setMargins(0,5,0,5);
                        but.setText("Follow "+o.getFbName());
                        but.setBackgroundResource(R.drawable.bg_button);
                        but.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mContext.startActivity(FollowHelper.newFacebookIntent(mContext,o.getFbUrl()));
                            }
                        });
                        holder.linContent.addView(but,lp);
                    }
                } else {
                    holder.tvName.setTag(2);
                    holder.linContent.removeAllViewsInLayout();
                    holder.linContent.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
