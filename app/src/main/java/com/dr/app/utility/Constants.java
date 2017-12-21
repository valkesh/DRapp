/**
 * @author Valkesh patel
 */
package com.dr.app.utility;

public abstract class Constants {

    public static final boolean IS_ERROR_LOG = true;
    public static final boolean SHOW_URL_IN_LOG = true;


    //Chat
    public static String App_Name = "playerv";
    public static String domain = "@wampserver";
    // public static String domain = "ip-172-31-4-65";
   // public static String HOST = "demo.zealousys.com";
     public static String HOST = "ec2-34-208-87-200.us-west-2.compute.amazonaws.com";
    public static int PORT = 5222;

    public static String FILTER_DISTANCE = "FILTER_DISTANCE";
    public static String FILTER_SPORT = "FILTER_SPORT";
    public static String FILTER_SPORT_ID = "FILTER_SPORT_ID";
    public static String FILTER_DAYS = "FILTER_DAYS";
    public static String FILTER_START_DATES = "FILTER_START_DATES";
    public static String FILTER_END_DATES = "FILTER_END_DATES";
    public static String FILTER_LOCATION = "FILTER_LOCATION";
    public static String FILTER_LOCATION_LATITUDE = "FILTER_LOCATION_LATITUDE";
    public static String FILTER_LOCATION_LONGTITUDE = "FILTER_LOCATION_LONGLITUDE";
    public static String FILTER_TOTAL = "FILTER_TOTAL";
    public static String event_more = "";


    public static String PREFS_NAME = "justsport";
    public static String PREFS_NAME_bool = "justsport_bool";


    public static final String YOUTUBE_APIKEY = "AIzaSyBVO9AfC2yQTfKOQsWGDoP6Kn1xgqBkFII";

    public static final String BASE_URL_FOR_IMAGE = "http://ec2-34-208-87-200.us-west-2.compute.amazonaws.com/wp-content/themes/player_vacancy/webservices";
  //  public static final String BASE_URL_FOR_IMAGE = "http://opensource.zealousys.com/player_vacancy/wp-content/themes/player_vacancy/webservices";


//     public static final String BASE_URL = "http://app.parrisgoebel.com/";
//    public static final String BASE_URL = "http://opensource.zealousys.com/player_vacancy/wp-content/themes/player_vacancy/webservices/";
    public static final String BASE_URL = "http://ec2-34-208-87-200.us-west-2.compute.amazonaws.com/wp-content/themes/player_vacancy/webservices/";
    //        public static final String BASE_URL = "http://demo.zealousys.com:81/parrisdance/wp-content/themes/parrisdance/webservice/";
    public static final String AUTH_TOKEN_KEY = "Token";
    public static final String AUTH_TOKEN_TYPE = "Authorization";

    public static final String DEVICE_TYPE = "android";
    public static final String LOGIN_WITH_APP_TYPE = "1";
    public static final String LOGIN_WITH_FACEBOOK_TYPE = "2";


    public static final String TABLIST = "TAB_LIST";
    public static String cate_id = "0";
    public static String content_id = "0";
    public static String image_url_video = "";

    public static final String DEVICE_TOKEN_GOOGLE = "device_token_google";


    public static boolean NAVIGATION_STATE_FLAG = false;
    public static boolean NAVIGATION_FAVOURITE_FLAG = false;
    public static boolean NAVIGATION_NOTIFICATION_FLAG = false;

    public static int NAVIGATION_FOOTER_CLICKED = -1;
    public static boolean IS_APPLICATION_MODE = false;


    // Api Url with method
    public static final String HOME_VIDEO_LIST = "free_video_list_new.php";


    public static final String HOME_LOGIN = "login.php";
    public static final String UPDATE_PROFILE = "update_profile.php";
    public static final String GET_PROFILE = "profile_detail.php";


    public static final String FAVOURITE_ITEM = "favourite.php";
    public static final String CACHED_DATA_HOME_KEY = "CASHEDDATAHOME";


    //    Get SPort Name
    public static final String GETSPORTNAME = "game_list.php";
    public static final String CREATEEVENTPOST = "create_event.php";
    public static final String EDITEVENTPOST = "update_event.php";
    public static final String HOME_API = "home.php";

    //categories event  API
    public static final String EVENT_LIST = "sport_events.php";
    public static final String EVENT_DETAILS = "event_detail.php";
    public static final String SEARCH_EVENT = "search_event.php";

    //suggested sport
    public static final String SUGGESTED_SPORT = "request_new_game.php";

    //Apply and widhraw event
    public static final String WITHDRAW_EVENT = "withdraw_event.php";
    public static final String APPLY_EVENT = "apply_event.php";

    // common url settings
    public static final String PRIVACY_POLICY_SETTINGS = "privacy_policy.php";
    public static final String LOGOUT = "logout.php";

    // Notification list
    public static final String NOTIFICATION = "notification_list.php";
    public static final String NOTIFICATION_ACCEPT_REJECT = "Action.php";

    // params of this api user_id, event_id
    public static final String EVENT_USERS = "join_userlist.php";

    //
    public static final String PUSHNOTIFICATION_TOGGEL = "settings_push.php";


    public static boolean isForground = false;
    public static String is_push_notification = "is_push_notification";
    public static String USER_STATUS = "user_status.php";
    //    public static String APPLICATION_LINK = "<html> <body><a href=\"https://play.google.com/store\">Go to Google</a> </body> </html>";
    public static String APPLICATION_LINK = "https://play.google.com/store";
}