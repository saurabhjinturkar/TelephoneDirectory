package in.saurabhjinturkar.telephonedirectory.gcm;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.stefanomunarini.telephonedirectory.R;
import in.saurabhjinturkar.telephonedirectory.database.services.NewsService;

//import in.saurabhjinturkar.telephonedirectory.bean.ContactUpdateResponse;

/**
 * Created by Saurabh on 8/20/2015.
 */
public class GCMMessageHandler extends GcmListenerService {
    public static final int MESSAGE_NOTIFICATION_ID = 435345;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        Log.i("GCMMESSAGEHANDLER", String.valueOf(data));
        String message = data.getString("data");
        Log.i("GCMMESSAGEHANDLER", data.toString());
        Log.i("GCMMessageHandler", "===" + message);
//        Log.i("GCMMessageHandler", o.getAsString());

        if (message == null) return;
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(message);

        JsonObject obj;
        if (jsonElement.isJsonArray()) {
            obj = (JsonObject) ((JsonArray) jsonElement).get(0);
        } else {
            obj = (JsonObject) jsonElement;
        }

        JsonElement title = obj.get("title");
        JsonElement body = obj.get("body");
        JsonElement time = obj.get("time");

        createNotification("Telephone Directory", title.getAsString());
        NewsService service = new NewsService(getBaseContext());
        service.insertNews(title.getAsString(), body.getAsString(), time.getAsString());
    }

    // Creates notification based on title and body received
    private void createNotification(String title, String body) {
        Context context = getBaseContext();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher).setContentTitle(title)
                .setContentText(body).setVibrate(new long[]{1000, 1000, 1000});
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(MESSAGE_NOTIFICATION_ID, mBuilder.build());
    }

}
