package weather.wm.com.wmweather.common.units;

/**
 * Created by HelloKiki on 2017/3/27.
 */

public class UrlUtils {

    public static String TOKEN = "5d62d70f38dba07c128b1b9b93983f8a";
    private static String IP = "http://120.26.203.168:8080/weather";

    //公共
    public static String PROVINCE = IP + "/api/area/province";
    public static String CITY = IP + "/api/area/city";
    public static String AREA = IP + "/api/area/area";
    public static String TYPE = IP + "/api/instance/config";

    //注冊登陸
    public static String LOGIN = IP + "/api/auth/login";
    public static String GETCAPTCHA = IP + "/api/auth/getCaptcha";
    public static String REGISTOR = IP + "/api/auth/registor";
    public static String CHECK_GETCAPTCHA = IP + "/api/auth/checkoutCaptcha";
    public static String SET_PASSWORD = IP + "/api/auth/setPassWord";
    public static String LOGIN_FAST = IP + "/api/auth/loginFast";
    public static String CHANG_PHONE = IP + "/api/auth/changePhone";

    //首頁
    public static String MAIN = IP + "/api/index/main";
    public static String CITY_CALANDAR_RANK = IP + "/api/index/rankItem";
    public static String CALENDER = IP + "/api/index/listRangeTime";
    public static String CITY_RANK = IP + "/api/index/rankCity";

    //实时数据
    public static String REAL_TIME_DATA = IP + "/api/instance/day";
    public static String REAL_TIME_STATION_DESTAIL = IP + "/api/instance/data";

    //历史数据
    public static String HISTORY_DATA = IP + "/api/history/data";

    //用户数据
    public static String USER_INFO = IP + "/api/user/info";
    public static String USER_UPDATA = IP + "/api/user/update";
    public static String USER_UPDATA_AVATAR = IP + "/api/user/updateAvatar";
    public static String USER_UPDATA_PASSWORD = IP + "}/api/user/updatePassWord";
}
