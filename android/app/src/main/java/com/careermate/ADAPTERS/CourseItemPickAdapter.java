package com.careermate.ADAPTERS;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.careermate.CORE_LIBS.HELPER.TinyDB;
import com.careermate.CORE_LIBS.VIEWS.DefaultFontTextView;
import com.careermate.KEYS.KFB;
import com.careermate.KEYS.V;
import com.careermate.MODELS.CourseSeverSideObject;
import com.careermate.fbhack.R;

import java.util.List;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class CourseItemPickAdapter extends RecyclerView.Adapter<CourseItemPickAdapter.MyViewHolder> {
    private TinyDB myDB;
    private Context mContext;
    private List<CourseSeverSideObject> list;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivCourses;
        public DefaultFontTextView tvStatus,tvTag,tvCoursesName,tvTotalLesson;
        public RelativeLayout relBackGround;
        public MyViewHolder(View view) {
            super(view);
            ivCourses= (ImageView) view.findViewById(R.id.ivCourses);
            tvStatus= (DefaultFontTextView) view.findViewById(R.id.tvStatus);
            tvStatus.setAlpha(V.ALPHA_TEXT_VIEW);
            tvCoursesName= (DefaultFontTextView) view.findViewById(R.id.tvCoursesName);
            tvTotalLesson= (DefaultFontTextView) view.findViewById(R.id.tvTotalLessons);
            relBackGround= (RelativeLayout) view.findViewById(R.id.relBackGround);
            tvTag= (DefaultFontTextView) view.findViewById(R.id.tvTag);
        }
    }


    public CourseItemPickAdapter(Context mContext, List<CourseSeverSideObject> list) {
        this.mContext = mContext;
        this.list = list;
        this.myDB= new TinyDB(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_pick_courses_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final CourseSeverSideObject obj = list.get(position);
        String content="";
        for(int i=0;i<obj.getSkills().size();i++) content+=" #"+obj.getSkills().get(i).getName();
        holder.tvTag.setText(content);
        holder.tvCoursesName.setText(obj.getName());
        Glide.with(mContext).load(KFB.PRE_PATH_ICON+obj.getThumbnail()).into(holder.ivCourses);
        holder.tvTotalLesson.setText(obj.getLessons().size()+" lessons ("+obj.getEstimated()+" minutes)");
        int stt=myDB.getInt(V.USER.getFbId()+V.SAVE_COURSE_STATUS+obj.getName(),-1);
        holder.tvStatus.setText(V.COURSE_STATUS[stt+1]);
        holder.relBackGround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(holder.tvTag,obj.getName());
            }
        });
    }

    private void showPopupMenu(View view, final String key) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int stt=myDB.getInt(V.USER.getFbId()+V.SAVE_COURSE_STATUS+key,-1);
                switch (item.getItemId()) {
                    case R.id.select_deselect:
                        switch (stt){
                            case 0:myDB.putInt(V.USER.getFbId()+V.SAVE_COURSE_STATUS+key,1);break;
                            case 1:myDB.putInt(V.USER.getFbId()+V.SAVE_COURSE_STATUS+key,0);break;
                            case 2:
                                myDB.putInt(V.USER.getFbId()+V.SAVE_COURSE_STATUS+key,0);
                                myDB.putString(V.USER.getFbId()+V.SAVE_ACTIVE_KEY,"");
                                break;
                            default:break;
                        }
                        ((OnNeedUpdate)mContext).update();
                        return true;
                    case R.id.active:
                        String key_active=myDB.getString(V.USER.getFbId()+V.SAVE_ACTIVE_KEY);
                        if(key_active!=""){
                            myDB.putInt(V.USER.getFbId()+V.SAVE_COURSE_STATUS+key_active,1);
                        }
                        switch (stt){
                            case 0:
                            case 1:
                            case 2:myDB.putInt(V.USER.getFbId()+V.SAVE_COURSE_STATUS+key,2);
                                myDB.putString(V.USER.getFbId() + V.SAVE_ACTIVE_KEY,key);
                                break;
                            default:break;
                        }
                        ((OnNeedUpdate)mContext).update();
                        return true;
                    default:
                }
                return false;
            }
        });
        popup.show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public interface OnNeedUpdate{
        void update();
    }
}
