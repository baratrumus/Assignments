package videocamera.ver1;

import org.junit.Test;
import org.json.JSONObject;
import videocamera.ver1.Camera;

import java.util.concurrent.CopyOnWriteArrayList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CameraTest {

    @Test
    public void whenCameraLoadeditsOk() throws InterruptedException {
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
        CopyOnWriteArrayList result = camera.getResult();
        JSONObject jo = (JSONObject) result.get(0);

        assertThat(result.size(), is(4));
        assertThat(jo.getString("urlType"), is("LIVE"));
        assertThat(jo.getString("videoUrl"), is("rtsp://127.0.0.1/1"));
        assertThat(jo.getInt("id"), is(1));
        assertThat(jo.getString("value"), is("fa4b588e-249b-11e9-ab14-d663bd873d93"));
    }
}