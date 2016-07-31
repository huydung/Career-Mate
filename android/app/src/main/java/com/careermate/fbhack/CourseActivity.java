package com.careermate.fbhack;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.careermate.ACTIVITY.MenuActivity;
import com.careermate.ADAPTERS.CourseItemPickAdapter;
import com.careermate.CORE_LIBS.VIEWS.DefaultFontButton;
import com.careermate.CORE_LIBS.VIEWS.DefaultFontTextView;
import com.careermate.KEYS.V;
import com.careermate.MODELS.CourseSeverSideObject;
import com.careermate.MODELS.SkillNameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TuAnh on 7/31/2016.
 */
public class CourseActivity extends MenuActivity implements View.OnClickListener,CourseItemPickAdapter.OnNeedUpdate {
    private ImageView ivToogle;

    private DefaultFontButton btnAvailable,btnRegisted,btnFinish,btnClear,btnLearn;
    private DefaultFontTextView tvSkillFocused;
    private List<CourseSeverSideObject>  ListCourseAvailable=new ArrayList<>();
    private List<CourseSeverSideObject>  listRegisted=new ArrayList<>();
    private List<CourseSeverSideObject>  listFinish=new ArrayList<>();
    private List<CourseSeverSideObject>  listShow= new ArrayList<>();

    private RecyclerView rvListCourse;
    private CourseItemPickAdapter adapter;
    private int TT=0;


    private void doInitListCoursesAvailable(){
        ListCourseAvailable.clear();
        for(int i=0;i<V.LIST_ALL_COURSES.size();i++){
            int tt=myDB.getInt(V.USER.getFbId()+V.SAVE_COURSE_STATUS+V.LIST_ALL_COURSES.get(i).getName(),-1);
            if(tt>0) continue;
            if(tt==0) {
                ListCourseAvailable.add(V.LIST_ALL_COURSES.get(i));
            }else{
                List<SkillNameObject> list=V.LIST_ALL_COURSES.get(i).getSkills();
                for(int j=0;j<list.size();j++){
                    if(myDB.getBoolean(V.USER.getFbId()+V.SAVE_SKILL_PICK+list.get(j).getKey(),false)){
                        ListCourseAvailable.add(V.LIST_ALL_COURSES.get(i));
                        myDB.putInt(V.USER.getFbId() + V.SAVE_COURSE_STATUS + V.LIST_ALL_COURSES.get(i).getName(), 0);
                    }
                }
            }

        }
    }
    private void goMess1(){
        //good
        Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("fb://messaging/661794463969839"));
        try
        {
            startActivity(intent);
        }
        catch (ActivityNotFoundException ex)
        {
            Toast.makeText(this,
                    "Oups!Can't open Facebook messenger right now. Please try again later.",
                    Toast.LENGTH_SHORT).show();
        }
    }
    private void doStartLearn(){
        goMess1();
    }
    private void doInitListCoursesRegisted(){
        listRegisted.clear();
        for(int i=0;i<V.LIST_ALL_COURSES.size();i++){
            int tt=myDB.getInt(V.USER.getFbId()+V.SAVE_COURSE_STATUS+V.LIST_ALL_COURSES.get(i).getName(),-1);
            if(tt==1||tt==2) {
                listRegisted.add(V.LIST_ALL_COURSES.get(i));
            }
        }
    }
    private void doInitListFinish(){
        listFinish.clear();
        for(int i=0;i<V.LIST_ALL_COURSES.size();i++){
            int tt=myDB.getInt(V.USER.getFbId()+V.SAVE_COURSE_STATUS+V.LIST_ALL_COURSES.get(i).getName(),-1);
            if(tt==3) {
                listFinish.add(V.LIST_ALL_COURSES.get(i));
            }
        }
    }
    private void doBacktoProfile(){
        startActivity(new Intent(context,ProfileActivity.class));
        finish();
    }
    private void doAvailable(){
        TT=0;
        doInitListCoursesAvailable();
        listShow.clear();
        listShow.addAll(ListCourseAvailable);
        adapter.notifyDataSetChanged();
        btnAvailable.setBackgroundResource(R.drawable.bg_button);
        btnRegisted.setBackgroundResource(R.drawable.bg_deselected);
        btnFinish.setBackgroundResource(R.drawable.bg_deselected);
    }
    private void doRegisted(){
        TT=1;
        doInitListCoursesRegisted();
        listShow.clear();
        listShow.addAll(listRegisted);
        adapter.notifyDataSetChanged();
        btnAvailable.setBackgroundResource(R.drawable.bg_deselected);
        btnRegisted.setBackgroundResource(R.drawable.bg_button);
        btnFinish.setBackgroundResource(R.drawable.bg_deselected);
    }
    private void doFinish(){
        TT=2;
        doInitListFinish();
        listShow.clear();
        listShow.addAll(listFinish);
        adapter.notifyDataSetChanged();
        btnAvailable.setBackgroundResource(R.drawable.bg_deselected);
        btnRegisted.setBackgroundResource(R.drawable.bg_deselected);
        btnFinish.setBackgroundResource(R.drawable.bg_button);
    }

    //region CORE INIT
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_courses_pick);
        InitActivity();
        ConnectID();
        SetOnClick();
        DesignLayout();
        MainWork();
    }

    @Override
    public void InitActivity() {
        setBackActivity(ProfileActivity.class);
    }

    @Override
    public void ConnectID() {
        ivToogle= (ImageView) findViewById(R.id.ivToogle);
        btnAvailable= (DefaultFontButton) findViewById(R.id.btnAvailable);
        btnRegisted= (DefaultFontButton) findViewById(R.id.btnRegisted);
        btnClear= (DefaultFontButton) findViewById(R.id.btnClear);
        btnFinish= (DefaultFontButton) findViewById(R.id.btnFinish);
        tvSkillFocused= (DefaultFontTextView) findViewById(R.id.tvSkillFocus);
        rvListCourse= (RecyclerView) findViewById(R.id.rvListCourse);
        btnLearn= (DefaultFontButton) findViewById(R.id.btnStartLearn);
    }

    @Override
    public void SetOnClick() {
        ivToogle.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnRegisted.setOnClickListener(this);
        btnFinish.setOnClickListener(this);
        btnAvailable.setOnClickListener(this);
        btnLearn.setOnClickListener(this);
    }

    @Override
    public void DesignLayout() {
        String content="";
        for(int i=0;i< V.LIST_ALL_SKILL.size();i++)
            if(myDB.getBoolean(V.USER.getFbId()+V.SAVE_SKILL_PICK+V.LIST_ALL_SKILL.get(i).getKey(),false)){
                content+=V.LIST_ALL_SKILL.get(i).getName()+",";
            }
        tvSkillFocused.setText("Skills: " + content);
        RecyclerView.LayoutManager lp= new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        rvListCourse.setLayoutManager(lp);
        adapter= new CourseItemPickAdapter(context,listShow);
        rvListCourse.setAdapter(adapter);
    }
    private void doUpdateList(){
        doInitListCoursesAvailable();
        doInitListCoursesRegisted();
        doInitListFinish();
    }
    @Override
    public void MainWork() {
        doUpdateList();
        listShow.clear();
        TT=0;
        listShow.addAll(ListCourseAvailable);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivToogle: getSlidingMenu().toggle();break;
            case R.id.btnClear: tvSkillFocused.setText("Skills:");break;
            case R.id.btnAvailable: doAvailable();break;
            case R.id.btnRegisted: doRegisted();break;
            case R.id.btnFinish: doFinish();break;
            case R.id.btnStartLearn: doStartLearn();break;
        }
    }

    @Override
    public void update() {
        switch (TT){
            case 0:doAvailable();break;
            case 1:doRegisted();break;
            case 2:doFinish();break;
        }
    }
    //endregion
}
