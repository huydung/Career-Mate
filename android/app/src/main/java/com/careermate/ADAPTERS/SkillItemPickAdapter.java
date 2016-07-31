package com.careermate.ADAPTERS;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.careermate.CORE_LIBS.HELPER.TinyDB;
import com.careermate.CORE_LIBS.VIEWS.DefaultFontTextView;
import com.careermate.KEYS.KFB;
import com.careermate.KEYS.V;
import com.careermate.MODELS.SkillObject;
import com.careermate.fbhack.R;

import java.util.List;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class SkillItemPickAdapter extends RecyclerView.Adapter<SkillItemPickAdapter.MyViewHolder> {

    private Context mContext;
    private TinyDB myDB;
    private List<SkillObject> skillObjectsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivSkillThumbs;
        public ImageView ivStar[]= new ImageView[5];
        public DefaultFontTextView tvName;
        public RelativeLayout relBackGround;
        public MyViewHolder(View view) {
            super(view);
            ivSkillThumbs= (ImageView) view.findViewById(R.id.ivSkillThumbs);
            tvName= (DefaultFontTextView) view.findViewById(R.id.tvName);
            relBackGround= (RelativeLayout) view.findViewById(R.id.relBackGround);
            ivStar[0]= (ImageView) view.findViewById(R.id.ivStar0);
            ivStar[1]= (ImageView) view.findViewById(R.id.ivStar1);
            ivStar[2]= (ImageView) view.findViewById(R.id.ivStar2);
            ivStar[3]= (ImageView) view.findViewById(R.id.ivStar3);
            ivStar[4]= (ImageView) view.findViewById(R.id.ivStar4);
            for(int i=0;i<5;i++) ivStar[i].setTag(String.valueOf(V.BONUS_TAG_STAR+i));
        }
    }


    public SkillItemPickAdapter(Context mContext, List<SkillObject> careerObjectList) {
        this.mContext = mContext;
        myDB= new TinyDB(mContext);
        this.skillObjectsList = careerObjectList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_pick_skill_item, parent, false);

        return new MyViewHolder(itemView);
    }
    private void updateStar(final MyViewHolder holder,SkillObject skillObject){
        for(int i=0;i<5;i++)
            holder.ivStar[i].setImageResource(V.STAR_DRAWABLE[1]);
        int num= myDB.getInt(V.USER.getFbId()+V.SAVE_SKILL_STAR+skillObject.getKey(),3);
        for(int i=0;i<num;i++)
            holder.ivStar[i].setImageResource(V.STAR_DRAWABLE[0]);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final SkillObject skillObject = skillObjectsList.get(position);
        for(int i=0;i<5;i++)
            holder.ivStar[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int s=Integer.parseInt(v.getTag().toString())-V.BONUS_TAG_STAR;
                    myDB.putInt(V.USER.getFbId() + V.SAVE_SKILL_STAR + skillObject.getKey(), s + 1);
                    updateStar(holder,skillObject);
                }
            });
        updateStar(holder, skillObject);
        if(myDB.getBoolean(V.USER.getFbId()+V.SAVE_SKILL_PICK+skillObject.getKey(),false)){
            holder.relBackGround.setBackgroundResource(R.drawable.bg_selected);
        }else{
            holder.relBackGround.setBackgroundResource(R.drawable.bg_noselected);
        }
        holder.tvName.setText(skillObject.getName());
        // loading album cover using Glide library
        Glide.with(mContext).load(KFB.PRE_PATH_ICON+skillObject.getIcon()).into(holder.ivSkillThumbs);
        holder.relBackGround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ok=myDB.getBoolean(V.USER.getFbId()+V.SAVE_SKILL_PICK+skillObject.getKey(),false);
                ok=!ok;
                myDB.putBoolean(V.USER.getFbId()+V.SAVE_SKILL_PICK+skillObject.getKey(),ok);
                if(ok){
                    holder.relBackGround.setBackgroundResource(R.drawable.bg_selected);
                }else{
                    holder.relBackGround.setBackgroundResource(R.drawable.bg_noselected);
                }
            }
        });
        //holder.ivSkillThumbs.setImageResource(R.drawable.career_thumbs_default);
    }


    @Override
    public int getItemCount() {
        return skillObjectsList.size();
    }
}
