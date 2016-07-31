package com.careermate.fbhack;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.careermate.ACTIVITY.MenuActivity;
import com.careermate.ADAPTERS.FollowPeopleAdapter;
import com.careermate.CORE_LIBS.VIEWS.DefaultFontButton;
import com.careermate.HELPER.FollowHelper;
import com.careermate.KEYS.V;
import com.careermate.MODELS.FacebookUriObject;
import com.careermate.MODELS.FollowObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TuAnh on 7/30/2016.
 */
public class ComunityActivity extends MenuActivity implements View.OnClickListener {

    private RecyclerView rvPeople;
    private ImageView ivToogle;
    private DefaultFontButton btnPeople, btnGroup;
    private FollowPeopleAdapter adapter;
    private List<FollowObject> listPeople = new ArrayList<>();
    private List<FollowObject> listGroup = new ArrayList<>();
    private List<FollowObject> listShow = new ArrayList<>();
    private int TT=0;

    private void doInitListPeople() {
        listPeople.clear();
        for(int i=0;i<V.LIST_ALL_CAREER.size();i++){
            if(myDB.getBoolean(V.USER.getFbId()+V.SAVE_CAREER_PICK+V.LIST_ALL_CAREER.get(i).getKey(),false)){
                List<FacebookUriObject> list= new ArrayList<>();
                for(int j=0;j<FollowHelper.URL_FOLLOW[i].length;j++)
                    list.add(new FacebookUriObject("",FollowHelper.URL_FOLLOW[i][j],FollowHelper.NAME_FOLLOW[i][j]));
                listPeople.add(new FollowObject(V.LIST_ALL_CAREER.get(i).getName(),list));
            }
        }
    }
    private void doInitListGroup(){
        listGroup.clear();

        for(int i=0;i< V.LIST_ALL_SKILL.size();i++){
            if(myDB.getBoolean(V.USER.getFbId()+V.SAVE_SKILL_PICK+V.LIST_ALL_SKILL.get(i).getKey(),false)){
                List<FacebookUriObject> list= new ArrayList<>();
                FacebookUriObject obj= new FacebookUriObject("fb://groups/"+ FollowHelper.URI_FOLLOW_GROUP[i],FollowHelper.URL_FOLLOW_GROUP[i],"CM "+FollowHelper.NAME_FOLLOW_GROUP[i]+" Group");
                list.add(obj);
                list.add(obj);
                listGroup.add(new FollowObject(V.LIST_ALL_SKILL.get(i).getName(),list));
            }
        }
        L.log("size "+listGroup.size());
    }

    //region CORE INIT
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_follow);
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
        rvPeople = (RecyclerView) findViewById(R.id.rvPeople);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvPeople.setLayoutManager(layoutManager);
        adapter = new FollowPeopleAdapter(context, listShow);
        rvPeople.setAdapter(adapter);
        ivToogle= (ImageView) findViewById(R.id.ivToogle);
        btnPeople = (DefaultFontButton) findViewById(R.id.btnPeople);
        btnGroup = (DefaultFontButton) findViewById(R.id.btnGroup);
    }

    @Override
    public void SetOnClick() {
        ivToogle.setOnClickListener(this);
        //btnPeople.setOnClickListener(this);
        btnGroup.setOnClickListener(this);
        btnPeople.setOnClickListener(this);
    }

    @Override
    public void DesignLayout() {

    }
    private void doUpdate(){
        switch (TT){
            case 1: listShow.clear();listShow.addAll(listGroup);
                btnGroup.setBackgroundResource(R.drawable.bg_button);
                btnPeople.setBackgroundResource(R.drawable.bg_deselected);
                adapter.notifyDataSetChanged();
                break;
            case 0: listShow.clear();listShow.addAll(listPeople);
                btnGroup.setBackgroundResource(R.drawable.bg_deselected);
                btnPeople.setBackgroundResource(R.drawable.bg_button);
                adapter.notifyDataSetChanged();
                break;
            default:break;
        }
    }
    @Override
    public void MainWork() {
        doInitListPeople();
        doInitListGroup();
        doUpdate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivToogle:getSlidingMenu().toggle();break;
            case R.id.btnPeople:TT=0;doUpdate();break;
            case R.id.btnGroup:TT=1;doUpdate();break;
        }
    }
    //endregion
}
