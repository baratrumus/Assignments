package videocamera.ver1;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.apache.http.client.HttpClient;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

@ThreadSafe
public class Camera {
    @GuardedBy("queue")
    private static Queue<JSONObject> queue = new LinkedList<>();
    private final static String API_URL = "http://www.mocky.io/v2/5c51b9dd3400003252129fb5";

    private CopyOnWriteArrayList<JSONObject> result = new CopyOnWriteArrayList<>();

    public CopyOnWriteArrayList<JSONObject> getResult() {
        return result;
    }


    public static void main(String[] args) throws InterruptedException {
        Camera camera = new Camera();
        Thread producer = new Thread(camera.new Loader());
        producer.start();

        for (int i = 1; i <= 3; i++) {
            Thread consumer = new Thread(camera.new CamWorkConsumer());
            consumer.start();
            consumer.sleep(3000);
            System.out.println(String.format("c %s", consumer.getState()));
            consumer.interrupt();
            consumer.join();
        }
        producer.join();

        System.out.println(String.format("p %s", producer.getState()));
    }


    public class Loader implements Runnable {
        @Override
        public void run() {
            JSONArray cameras = new JSONArray(loadJson(API_URL));

            for (int i = 0; i < cameras.length(); i++) {
                JSONObject cam = cameras.getJSONObject(i);
                queue.offer(cam);
                System.out.format("%s%n", cam.getInt("id"));
                System.out.format("%s%n", cam.getString("sourceDataUrl"));
                System.out.format("%s%n", cam.getString("tokenDataUrl"));
            }
            System.out.println("Original Json loaded");
        }
    }

    public class CamWorkConsumer implements Runnable {
        JSONObject originalJson;
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted() || (queue.size() > 0)) {
                synchronized (queue) {
                    if (queue.size() <= 0) {
                        System.out.println(String.format("queue is empty, %s wait ", Thread.currentThread().getName()));
                        try {
                            queue.wait();
                        } catch (InterruptedException ie) {
                            ie.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                    originalJson = queue.poll();
                    queue.notify();
                }
                parseOneCam(originalJson);
            }
        }
    }


    private void parseOneCam(JSONObject jo) {
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
        if (resultCam != null) {
            result.add(resultCam);
        }
        System.out.println(resultCam.toString());
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
