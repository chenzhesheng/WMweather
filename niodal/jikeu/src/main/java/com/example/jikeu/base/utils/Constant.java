package com.example.jikeu.base.utils;

import android.content.Context;

/**
 * @author lujunjie
 * @時間：2015年12月10日 @類名：常量 @說明：
 */
public class Constant {
    //域名
    public final static String BASE_URL = "http://101.200.134.192/Api/";
    /**
     * 接口
     */
    //国家
    public final static String AREA_URL = "http://101.200.134.192/Api/school/area";
    //高校
    public final static String SCHOOLLIST_URL = "http://101.200.134.192/Api/school/schoollist";
    //录取率
    public final static String ENROLLRATE_URL = "http://101.200.134.192/Api/school/admit";
    //就业率
    public final static String EMPLOYMENT_URL = "http://101.200.134.192/Api/school/employment";
    //排行榜
    public final static String RANKING_URL = "http://101.200.134.192/Api/school/ranking";
    //图片url
    public final static String THUMB_URL = "http://101.200.134.192/Public/";
    //高校详细信息
    public final static String ECOLLEGE_DETAIL_URL = "http://101.200.134.192/Api/school/details";
    //高校简介信息
    public final static String INTRODUCTION_URL = "http://101.200.134.192/Api/school/oneschool";
    //高校选中
    public final static String UNDATASHCOOL_URL = "http://101.200.134.192/Api/setting/updataschool";
    //填写留学意向
    public final static String INTENTION_URL = "http://101.200.134.192/Api/account/country";
    //提交留学意向
    public final static String SUBMIT_SAVE_URL = "http://101.200.134.192/Api/account/program";
    //已选课程
    public final static String MY_COURSE_URL = "http://101.200.134.192/Api/course/myinfo";
    //课程列表 or 添加课程
    public final static String COURSE_LIST_URL = "http://101.200.134.192/Api/course/info";
    //课程详情
    public final static String COURSE_DERAIL_URL = "http://101.200.134.192/Api/course/detail";
    //课程购买or试听
    public final static String COURSE_BUY_OR_TRY_URL = "http://101.200.134.192/Api/course/useract";
    /**
     * 用户登录
     */
    public final static String USERLOGIN_URL = "http://101.200.134.192/Api/User/login";
    /**
     * 用户注册
     */
    public final static String USERREGISTER_URL = "http://101.200.134.192/Api/User/checkcode";
    /**
     * 注册第二步
     */
    public final static String USER_PSWD_REGISTER_URL = "http://101.200.134.192/Api/User/register";
    /**
     * 我的信息
     */
    public final static String USER_MY_MESSAGE_URL = "http://101.200.134.192/Api/Account/my";
    /**
     * 所有老师列表
     **/
    public final static String USER_MY_TEACHER_URL = "http://101.200.134.192/Api/Teacher/info";
    /**
     * 我的老师
     **/
    public final static String USER_MY_TEACHER_MESSAGE_URL = "http://101.200.134.192/Api/Teacher/myteacher";
    /**
     * 我的成绩
     **/
    public final static String USER_MY_SCORE_URL = "http://101.200.134.192/Api/Account/score";
    /**
     * 我的留学
     **/
    public final static String USER_MY_STUDY_INFO_URL = "http://101.200.134.192/Api/Setting/info";
    /**
     * 留学修改
     */
    public final static String USER_MY_STUDY_URL = "http://101.200.134.192/Api/Setting/uptime";
    /**
     * 老师详情
     **/
    public final static String USER_TEACHER_MESSAGE_URL = "http://101.200.134.192/Api/Teacher/detail";
    /**
     * 设置
     */
    public final static String USER_SETTING_URL = "http://101.200.134.192/Api/setting/account";
    /**
     * 修改密码
     */
    public final static String USER_ALTER_PSWD_URL = "http://101.200.134.192/Api/setting/updatapwd";
    /**
     * 消息中心
     */
    public final static String USER_MESSAGE_CENTRE_URL = "http://101.200.134.192/Api/setting/about";
    /**
     * 消息详情
     */
    public final static String USER_MESSAGE_DETAIL_URL = "http://101.200.134.192/Api/setting/detail";
    /**
     * 测评
     */
    public final static String TEST_TITLE_URL = "http://101.200.134.192/Api/test/question";
    /**
     * 返回答案
     */
    public final static String TEST_RESULT_ANSWER_URL = "http://101.200.134.192/Api/test/answer";
    /**
     * 录音文件
     */
    public final static String TEST_TONGUE_ORAL_URL = "http://101.200.134.192/Api/test/oralanswer";
    /**
     * 测评 - 听力 - MP3
     */

    /**
     * 接口类型
     */
    public static final int INTENTION_TYPE = 11;
    public static final int SUBMIT_SAVE_TYPE = 12;
    public static final int MY_COURSE_TYPE = 13;
    public static final int COURSE_LIST_TYPE = 14;
    public static final int COURSE_ADD_TYPE = 15;
    public static final int COURSE_DERAIL_TYPE = 16;
    public static final int COURSE_BUY_OR_TRY_TYPE = 17;
    public static final int AREA_TYPE = 1001;
    public static final int SCHOOLLIST_TYPE = 1002;
    public static final int SCHOOLLIST_MORE_TYPE = 1003;
    public static final int ENROLLRATE_TYPE = 1004;
    public static final int EMPLOYMENT_TYPE = 1005;
    public static final int RANKING_TYPE = 1006;
    public static final int ENROLLRATE_MORE_TYPE = 1007;
    public static final int EMPLOYMENT_MORE_TYPE = 1008;
    public static final int RANKING_MORE_TYPE = 1009;
    public static final int OTHER_THREE_TYPE = 1010;
    public static final int ECOLLEGE_DETAIL_TYPE = 1011;
    public static final int INTRODUCTION_TYPE = 1012;
    public static final int LOGIN_TYPE = 100;
    public static final int REGISTER_TYPE = 101;
    public static final int REGISTER_PSWD_TYPE = 102;
    public static final int MY_MESSAGE_TYPE = 103;
    public static final int IMAGE_PORTRAIT_TYPE = 104;
    public static final int USER_MY_TEACHER_TYPE = 105;
    public static final int USER_MY_SCORE_TYPE = 106;
    public static final int USER_MY_STUDY_TYPE = 107;
    public static final int USER_TEACHER_TYPE = 108;
    public static final int USER_CELECT_TEACHER_TYPE = 109;
    public static final int USER_TEACHER_MESSAGE_TYPE = 110;
    public static final int USER_SETTING_TYPE = 111;
    public static final int USER_MY_STUDY_INFO_TYPE = 112;
    public static final int USER_MESSAGE_CENTRE_TYPE = 113;
    public static final int USER_MESSAGE_DETAIL_TYPE = 114;
    public static final int TEST_TITLE_TYPE = 115;
    public static final int TEST_RESULT_ANSWER_TYPE = 116;
    public static final int TEST_TONGUE_ORAL_TYPE = 117;
    public static final int UNDATASHCOOL_TYPE = 118;
    public static final String RECEICE_BROADCAST_COURSE = "course";
    public static final String RECEICE_BROADCAST_MESSAGE = "message";
    public static final String RECEICE_BROADCAST_SCHOOL = "school";
    public static final String RECEICE_BROADCAST_EAR = "ear";
    /**
     * 返回参数常量
     */
    public final static String ERROR = "error";
    public final static String MSG = "msg";
    public final static String REQUEST_TYPE = "requestType";
    public final static String TOKEN = "token";
    public final static String DATA = "data";
    /**
     * 密码判断：1.注册状态 2.修改密码 3.忘记密码
     */
    public final static String REGISTER_PASWD_ACTIVITY_ALTER_PASSWORD_STATE = "alter_password_state";

    /**
     * SMSSDK
     */
    public final static String APP_KEY = "102a37ca57afe";
    public final static String APP_SECRET = "20bfaad95f59bbb150fda47f461050ee";

    /**
     * 获取token
     */
    public static String getToken(Context context) {
        return (String) SharedPreferencesUtils.getParam(context, "token", "");
    }

}
