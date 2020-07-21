package videocamera.ver2;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

@ThreadSafe
public class Camera {
    @GuardedBy("queue")
    private static Queue<JSONObject> camQueue = new LinkedList<>();
    private final ExecutorService pool = Executors.newFixedThreadPool(3);
    private final static String API_URL = "http://www.mocky.io/v2/5c51b9dd3400003252129fb5";

    private List<JSONObject> result = new ArrayList<>();

    public List<JSONObject> getResult() {
        return result;
    }

    public static void main(String[] args) {
        Camera camera = new Camera();
        camera.start();
    }


    public void start() {
        loadData();
        while (!camQueue.isEmpty()) {
            try {
                Future<JSONObject> camRes = pool.submit(new CamWorkConsumer());
                result.add(camRes.get());
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            } catch (ExecutionException ee) {
                ee.printStackTrace();
            }
        }
        pool.shutdown();
    }


    public void loadData() {
        JSONArray cameras = new JSONArray(loadJson(API_URL));
        for (int i = 0; i < cameras.length(); i++) {
            JSONObject cam = cameras.getJSONObject(i);
            camQueue.offer(cam);
            System.out.format("%s%n", cam.getInt("id"));
            System.out.format("%s%n", cam.getString("sourceDataUrl"));
            System.out.format("%s%n", cam.getString("tokenDataUrl"));
        }
        System.out.println("Original Json loaded");
    }


    public class CamWorkConsumer implements Callable {
        @Override
        public JSONObject call() {
            JSONObject jo =  camQueue.poll();
            JSONObject resultCam = new JSONObject();

            String sourceDataUrl = loadJson(jo.getString("sourceDataUrl"));
            JSONObject jsonSourceDataUrl = new JSONObject(sourceDataUrl);
            String tokenDataUrl = loadJson(jo.getString("tokenDataUrl"));
            JSONObject jsonTokenDataUrl = new JSONObject(tokenDataUrl);

            resultCam.put("id", jo.getInt("id"));
            resultCam.put("urlType", jsonSourceDataUrl.getString("urlType"));
            resultCam.put("videoUrl", jsonSourceDataUrl.getString("videoUrl"));
            resultCam.put("value", jsonTokenDataUrl.getString("value"));
            resultCam.put("ttl", jsonTokenDataUrl.getInt("ttl"));

            System.out.println(resultCam.toString());
            return resultCam;
        }
    }


    private String loadJson(String url) {
        String result = "";
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet get = new HttpGet(url);
            HttpResponse response = client.execute(get);
            result = new BasicResponseHandler().handleResponse(response);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return result;
    }

}
