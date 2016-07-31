package com.careermate.fbhack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.careermate.ACTIVITY.MenuActivity;
import com.careermate.ADAPTERS.CareerItemPickAdapter;
import com.careermate.ADAPTERS.SkillItemPickAdapter;
import com.careermate.CORE_LIBS.UIMANAGERS.FirebaseManager;
import com.careermate.CORE_LIBS.VIEWS.DefaultFontButton;
import com.careermate.KEYS.V;
import com.careermate.MODELS.CareerObject;
import com.careermate.MODELS.SkillObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TuAnh on 7/30/2016.
 */
public class ProfileActivity extends MenuActivity implements CareerItemPickAdapter.OnSkillNeedUpdate, View.OnClickListener {
    private ImageView ivAvatar;
    private ImageView ivToogle;
    private DefaultFontButton btnFindCourse;
    private RecyclerView rvCareer,rvSkill;
    private CareerItemPickAdapter careerItemPickAdapter;
    private SkillItemPickAdapter skillItemPickAdapter;
    private List<SkillObject> skillObjectList=new ArrayList<>();
    private List<CareerObject> careerObjectList= new ArrayList<>();
    private FirebaseManager fb=FirebaseManager.getINSTANCE();

    /////////////
    private void doToogle(){
        getSlidingMenu().toggle();
    }
    private void doInitCareerList(){
        L.log("careers: " + V.LIST_ALL_CAREER.size());
        careerObjectList.addAll(V.LIST_ALL_CAREER);
        careerItemPickAdapter.notifyDataSetChanged();
    }
    private void doInitSkillList(){
        L.log("skills: " + V.LIST_ALL_SKILL.size());
        skillObjectList.addAll(V.LIST_ALL_SKILL);
        skillItemPickAdapter.notifyDataSetChanged();
    }
    //region CORE INIT
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_profile);
        InitActivity();
        ConnectID();
        SetOnClick();
        DesignLayout();
        MainWork();
    }

    @Override
    public void InitActivity() {
    }

    @Override
    public void ConnectID() {
        rvCareer= (RecyclerView) findViewById(R.id.rvInterestfulCareer);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        rvCareer.setLayoutManager(layoutManager);

        RecyclerView.LayoutManager layoutManager2= new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        rvSkill= (RecyclerView) findViewById(R.id.rvSkill);
        rvSkill.setLayoutManager(layoutManager2);

        ivAvatar= (ImageView) findViewById(R.id.ivAvatar);
        ivToogle= (ImageView) findViewById(R.id.ivToogle);
        btnFindCourse= (DefaultFontButton) findViewById(R.id.btnFindCourses);

    }

    @Override
    public void SetOnClick() {
        ivToogle.setOnClickListener(this);
        btnFindCourse.setOnClickListener(this);

    }

    @Override
    public void DesignLayout() {
        careerItemPickAdapter= new CareerItemPickAdapter(context,careerObjectList);
        rvCareer.setAdapter(careerItemPickAdapter);
        skillItemPickAdapter= new SkillItemPickAdapter(context,skillObjectList);
        rvSkill.setAdapter(skillItemPickAdapter);
        Glide.with(context).load("https://graph.facebook.com/"+ V.USER.getFbId()+"/picture?type=large").into(ivAvatar);
    }

    @Override
    public void MainWork() {
        doInitCareerList();
        doInitSkillList();
    }

    @Override
    public void SkillUpdate() {
        skillItemPickAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivToogle: doToogle();break;
            case R.id.btnFindCourses:
                startActivity(new Intent(context, CourseActivity.class));
                finish();break;
            default:break;
        }
    }
    //endregion
}
