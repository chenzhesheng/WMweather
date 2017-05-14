package weather.wm.com.wmweather.common.units;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.Map;

import weather.wm.com.wmweather.WeatherApplication;

/**
 * Created by HelloKiki on 2017/3/28.
 */

public class RequestUtils {

    static RequestQueue mQueue = Volley.newRequestQueue(WeatherApplication.getContext());

    public static void get(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        StringRequest request = new StringRequest(url, listener, errorListener);
        mQueue.add(request);
    }

    public static void post(String url, final Map<String, String> header,final Map<String, String> body
            , Response.Listener<String> listener, Response.ErrorListener errorListener) {
        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if(header==null){
                    return super.getHeaders();
                }
                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if(body==null){
                    return super.getParams();
                }
                return body;
            }
        };
        mQueue.add(request);
    }

}
