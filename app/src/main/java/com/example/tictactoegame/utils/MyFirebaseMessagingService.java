package com.example.tictactoegame.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

import com.example.tictactoegame.R;
import com.example.tictactoegame.activities.ItemDetailsActivity;
import com.example.tictactoegame.api.TicTacToeApiClient;
import com.example.tictactoegame.entities.TicTacToe;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private List<TicTacToe> listOfTicTacToes;
    private String title;
    private String message;
    private String click_action;

    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            try {
                JSONObject data = new JSONObject(remoteMessage.getData());
                String jsonMessage = data.getString("extra_information");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            title = remoteMessage.getNotification().getTitle();
            message = remoteMessage.getNotification().getBody();
            click_action = remoteMessage.getNotification().getClickAction();

            makeNotification();
        }
    }

    @Override
    public void onDeletedMessages() {
    }

    public void makeNotification() {
        final TicTacToeApiClient apiService = getApplicationEx().getTicTacToeApiClient();
        final Call<List<TicTacToe>> call = apiService.getTicTacToes();

        call.enqueue(new Callback<List<TicTacToe>>() {
            @Override
            public void onResponse(final Call<List<TicTacToe>> call,
                                   final Response<List<TicTacToe>> response) {
                listOfTicTacToes = response.body();

                int ticTacToeIndex = Integer.parseInt(click_action);
                TicTacToe ticTacToe = listOfTicTacToes.get(ticTacToeIndex);

                sendNotification(ticTacToe);
            }

            @Override
            public void onFailure(Call<List<TicTacToe>> call, Throwable t) {
            }
        });
    }

    private void sendNotification(TicTacToe ticTacToe) {
        Intent intent = new Intent(this, ItemDetailsActivity.class);
        intent.putExtra("tic_tac_toe_name", ticTacToe.getName());
        intent.putExtra("tic_tac_toe_game_type", ticTacToe.getGameType());
        intent.putExtra("tic_tac_toe_description", ticTacToe.getDescription());
        intent.putExtra("tic_tac_toe_img_url", ticTacToe.getPicture());
        intent.putExtra("ticTacToe_err_mes", message);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "l")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationId = new Random().nextInt();
        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    private ApplicationEx getApplicationEx() {
        return ((ApplicationEx) getApplication());
    }
}
