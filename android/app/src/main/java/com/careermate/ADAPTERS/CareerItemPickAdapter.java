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
import com.careermate.MODELS.CareerObject;
import com.careermate.fbhack.R;

import java.util.List;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class CareerItemPickAdapter extends RecyclerView.Adapter<CareerItemPickAdapter.MyViewHolder> {
    private TinyDB myDB;
    private Context mContext;
    private List<CareerObject> careerObjectList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivCareerThumbs;
        public DefaultFontTextView tvName;
        public RelativeLayout relBackGround;
        public MyViewHolder(View view) {
            super(view);
            ivCareerThumbs= (ImageView) view.findViewById(R.id.ivCareerThumbs);
            tvName= (DefaultFontTextView) view.findViewById(R.id.tvName);
            relBackGround= (RelativeLayout) view.findViewById(R.id.relBackGround);
            tvName.setAlpha(V.ALPHA_TEXT_VIEW);
        }
    }


    public CareerItemPickAdapter(Context mContext, List<CareerObject> careerObjectList) {
        this.mContext = mContext;
        this.careerObjectList = careerObjectList;
        this.myDB= new TinyDB(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_pick_career_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final CareerObject careerObject = careerObjectList.get(position);

        holder.tvName.setText(careerObject.getName());
        // loading album cover using Glide library
        Glide.with(mContext).load(KFB.PRE_PATH_ICON+careerObject.getIcon()).into(holder.ivCareerThumbs);

        if(myDB.getBoolean(V.USER.getFbId()+V.SAVE_CAREER_PICK+careerObject.getKey(),false)){
            holder.relBackGround.setBackgroundResource(R.drawable.bg_selected);
        }else{
            holder.relBackGround.setBackgroundResource(R.drawable.bg_noselected);
        }
        holder.relBackGround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ok=myDB.getBoolean(V.USER.getFbId()+V.SAVE_CAREER_PICK+careerObject.getKey(),false);
                ok=!ok;
                myDB.putBoolean(V.USER.getFbId()+V.SAVE_CAREER_PICK+careerObject.getKey(),ok);
                if(ok){
                    doActiveSkillForCareer(careerObject);
                    holder.relBackGround.setBackgroundResource(R.drawable.bg_selected);
                }else{
                    holder.relBackGround.setBackgroundResource(R.drawable.bg_noselected);
                }
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void doActiveSkillForCareer(CareerObject obj){
        for(int i=0;i<obj.getSkills().size();i++){

            myDB.putBoolean(V.USER.getFbId()+V.SAVE_SKILL_PICK+obj.getSkills().get(i).getKey(),true);
        }
        ((OnSkillNeedUpdate) mContext).SkillUpdate();
    }
    @Override
    public int getItemCount() {
        return careerObjectList.size();
    }

    public interface OnSkillNeedUpdate{
        void SkillUpdate();
    }
}
