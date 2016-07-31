package com.careermate.KEYS;

import com.careermate.MODELS.CareerObject;
import com.careermate.MODELS.CourseSeverSideObject;
import com.careermate.MODELS.SkillObject;
import com.careermate.MODELS.UserObject;
import com.careermate.fbhack.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by TuAnh on 7/30/2016.
 */
public class V {
    public static UserObject USER= new UserObject();
    public static List<CareerObject> LIST_ALL_CAREER= new ArrayList<>();
    public static List<SkillObject> LIST_ALL_SKILL= new ArrayList<>();
    public static List<CourseSeverSideObject> LIST_ALL_COURSES= new ArrayList<>();
    public static HashMap<String,String> MM= new HashMap<>();


    public static final float ALPHA_TEXT_VIEW=0.8f;
    public static final int[] STAR_DRAWABLE={
            R.drawable.star_on,
            R.drawable.star_off
    };
    public static final int BONUS_TAG_STAR=10;


    public static final String PRE="ver1_";
    public static final String SAVE_USER=PRE+"save user object";

    public static final String SAVE_CAREER_PICK=PRE+"save career pick ";
    public static final String SAVE_SKILL_STAR=PRE+"save skill star ";
    public static final String SAVE_SKILL_PICK=PRE+"save skill pick ";
    public static final String SAVE_ACTIVE_KEY=PRE+"save active key ";
    public static final String SAVE_COURSE_STATUS=PRE+"save courses status ";
    public static final String[] COURSE_STATUS={"default","available","registed","active","finish"};
    /*
    * -1 - default
    * 0- available
    * 1- registed
    * 2- active
    * 3- finish
    * */


}
