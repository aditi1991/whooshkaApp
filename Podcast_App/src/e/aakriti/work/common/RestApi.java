package e.aakriti.work.common;import java.io.IOException;import org.apache.http.HttpEntity;import org.apache.http.HttpResponse;import org.apache.http.client.HttpClient;import org.apache.http.client.methods.HttpPost;import org.apache.http.impl.client.DefaultHttpClient;import org.apache.http.util.EntityUtils;import android.net.Uri;import android.util.Log;public class RestApi {    /* phpMyAdmin New Link :       http://67.231.30.13/~fantsy/webservice/test.php       https://67.231.30.13:2083/cpsess4195244892/3rdparty/phpMyAdmin/index.php */    //public static String URL_IMAGE = "http://d6419895.u93.c12.ixinstant.com/webservice/image/";    public static String URL_IMAGE = "http://67.231.30.13/~fantsy/webservice/image/";    //public static String URL_PLAYER_IMAGE = "http://d6419895.u93.c12.ixinstant.com/webservice/Player_Image/";    public static String URL_PLAYER_IMAGE = "http://67.231.30.13/~fantsy/webservice/Player_Image/";        //http://www.whooshkaa.com/index.php?r=api/LoginDevice&user_name=abc1234&password=123456    //public static String URL = "http://d6419895.u93.c12.ixinstant.com/webservice/";    public static String URL = "http://www.whooshkaa.com/";            // PARAMETERS    public static String PARAM_REQUEST = "r";       //---------------------------- REGISTER ---------------------------------    public static String PARAM_USER_ID = "uid";    public static String PARAM_USER_TYPE = "user_type";    public static String PARAM_FIRST_NAME = "fname";    //---------------------------- LOGIN ---------------------------------    public static String PARAM_LOGIN_EMAIL = "user_name";    public static String PARAM_USER_PASSWORD = "password";    //---------------------------- LOGOUT ---------------------------------    public static String PARAM_LOGOUT_EMAIL = "email";    public static String PARAM_LOGOUT_PUSHKEY = "push_key";        //------------------------------METHODS------------------------        public static String Login_WS = "api/LoginDevice";    public static String Login_WS_FB = "api/SocialLogIn";    public static String Login_WS_GP = "api/SocialLogIn";    public static String Register_WS = "api/register";    public static String Register_WS_FB = "api/SocialLogIn";    public static String Register_WS_GP = "api/SocialLogIn";    public static String ForgotPassword_WS = "api/forgetPassword";    public static String QuestionsList_WS = "api/Questionaries";    public static String SubmitQueAnswers_WS = "api/QueAns";    public static String GetAllCategories_WS = "api/Categories";    public static String GetAllFeaturedShows_WS = "api/featuredShow";    public static String GetAllPopularShows_WS = "api/populer";    public static String GetAllRecentShows_WS = "api/recent";    public static String GetAllExplore_episodes_WS = "api/explore";    public static String GetAllExplore_shows_WS = "api/explore";    public static String GetAllFollowing_shows_WS = "api/ListFollowings";    public static String Follow_UnFollow_shows_WS = "api/Followings";        //http://www.whooshkaa.com/index.php?r=api/QueAns&que_id=%@&list_id=%@&ans=%@",questionId,listener_id,rowNum   /* public static String getDataFromURL(String uri, List<NameValuePair> nameValuePairs) throws IOException {                   Log.w("URL", "dataURL=> " + uri);        Log.w("URL", "nameValuePairs=> " + nameValuePairs.toString());        String PARAM_API_KEY = "apikey";        String API_KEY = "349637602372cacf082412e8e2a1b3c8";        HttpClient httpclient = new DefaultHttpClient();        HttpPost httppost = new HttpPost(uri);        //httppost.addHeader(PARAM_API_KEY, API_KEY);        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));        HttpResponse res = httpclient.execute(httppost);        HttpEntity ent = res.getEntity();        String result = EntityUtils.toString(ent);            Log.w("URL", "RESULT=> " + result);        return result;    }*/    public static String getDataFromURLWithoutParam(String uri) throws IOException {                  Log.w("URL", "dataURL=> " + uri);       /* String PARAM_API_KEY = "apikey";        String API_KEY = "349637602372cacf082412e8e2a1b3c8";*/        HttpClient httpclient = new DefaultHttpClient();        uri = uri.replaceAll(" ", "%20");        HttpPost httppost = new HttpPost(uri);        //httppost.addHeader(PARAM_API_KEY, API_KEY);        HttpResponse res = httpclient.execute(httppost);        HttpEntity ent = res.getEntity();        String result = EntityUtils.toString(ent);                    Log.w("URL", "RESULT=> " + result);        return result;    }    public static String createURI(String methodName) {        Uri.Builder urlBuilder = Uri.parse(URL + "index.php/"+methodName).buildUpon();        //.appendQueryParameter(PARAM_REQUEST, methodName);        return urlBuilder.build().toString();    }    }