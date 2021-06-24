package lb.edu.aust.ict355.greenhouse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class RemoteAPI {

    private OkHttpClient client;
    public RemoteAPI() {
        client = new OkHttpClient();
    }

    public List<States> listStates() throws IOException {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("192.168.1.110")
                .port(8080)
                .addPathSegments("CoapEdgeClientRest/api/greenhouse")
                .build();
        Request request = new Request.Builder()
                .get()
                .url(httpUrl)
                .build();

        try(Response response = client.newCall(request).execute()){
            try(ResponseBody body = response.body()){
                String jsonBody = body.string();
                List<States> states = new Gson().fromJson(jsonBody, new TypeToken<List<States>>(){}.getType());
                return states;
            }
        }
    }
}
