package kr.co.project.service;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Calendar;

import kr.co.project.R;
import kr.co.project.config.RetrofitConfig;
import kr.co.project.main.MainActivity;
import kr.co.project.view.sign.util.PreferenceUtils;

import kr.co.project.vo.FCMBody;
import kr.co.project.vo.FCMTo;
import kr.co.project.vo.SignLiveModel;
import kr.co.project.vo.TokenModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String tokens;
    private FCMTo fcmTo;
    private FCMTo.FCMData fcmData;
    private TokenModel tokenModel;
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        if (message.getData().size() > 0) {
            showNotification(message.getData().get("title"), message.getData().get("body"));
        }
    }
    public void SendFCM(String title, String cont){
        fcmTo = new FCMTo();
        fcmData = new FCMTo.FCMData();
        fcmData.setBody(cont);
        fcmData.setTitle(title);
        fcmData.setDate(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)+":"+Calendar.getInstance().get(Calendar.MINUTE));
        Log.e(TAG, "SendFCM: "+ PreferenceUtils.getToken() );
        fcmTo.setTo(PreferenceUtils.getToken());
        fcmTo.setData(fcmData);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        firebaseDatabase= FirebaseDatabase.getInstance("https://project2-c3089-default-rtdb.firebaseio.com/");
        databaseReference = firebaseDatabase.getReference("users");
        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                RetrofitConfig retrofitConfig = RetrofitConfig.getInstance();
                retrofitConfig.fcmToken().sendFCM(fcmTo).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful()){
                            Log.e(TAG, "onFailure: "+response.body() );
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e(TAG, "onFailure: "+t.getMessage() );
                        Log.e(TAG, "onFailure: "+t.getLocalizedMessage() );
                        Log.e(TAG, "onFailure: "+t );

                    }
                });
            }
        });


    }

    public MyFirebaseMessagingService() {
        super();
        Task<String> token = FirebaseMessaging.getInstance().getToken();
        token.addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(task.isSuccessful()) {
                    Log.d("FCM Token", "여기서 부터는 MY TOKEN" + task.getResult());
                    onNewToken(task.getResult());


                    PreferenceUtils.setToken(task.getResult());
                    Log.d("FCM Token", "여기서 부터는 MY TOKEN" + PreferenceUtils.getToken());

                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://project2-c3089-default-rtdb.firebaseio.com/");
                    if (!TextUtils.isEmpty(PreferenceUtils.getUserId())) {
                        DatabaseReference databaseReference = firebaseDatabase.getReference("users").child(PreferenceUtils.getUserId());
                        databaseReference.setValue(task.getResult()).addOnCompleteListener(task1 -> {
                                Log.e("tatat", "DADSdadsad");
                        });
                    }
                }
            }
        });

    }
    public MyFirebaseMessagingService(String id) {

        super();

        Task<String> token = FirebaseMessaging.getInstance().getToken();
        token.addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(task.isSuccessful()) {
                    Log.d("FCM Token", "여기서 부터는 MY TOKEN" + task.getResult());
                    Log.d("FCM Token", "여기서 부터는 MY TOKEN" + id);

                    onNewToken(task.getResult());
                    Log.d("FCM Token", "여기서 부터는 MY TOKEN" + PreferenceUtils.getToken());

//                    PreferenceUtils.setToken(task.getResult());
                    tokens = task.getResult();
                    Log.i(TAG, "MyFirebaseMessagingService: "+PreferenceUtils.getToken());
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://project2-c3089-default-rtdb.firebaseio.com/");
                    DatabaseReference databaseReference = firebaseDatabase.getReference("users").child(id);
                    databaseReference.setValue(task.getResult()).addOnCompleteListener(task1 -> {
                            Log.e("tatat", "DADSdadsad");
                        });

                }
            }
        });

    }

    @SuppressLint("WrongThread")
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        PreferenceUtils.setToken(token);

    }

    RemoteViews getCustomDesign(String title, String message) {
        RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.notication);
        remoteViews.setTextViewText(R.id.noti_title, title);
        remoteViews.setTextViewText(R.id.noti_message, message);
        remoteViews.setImageViewResource(R.id.logo, R.drawable.open_graph_logo);
        return remoteViews;
    }
    public void showNotification(String title, String message) {

        //팝업 터치시 이동할 액티비티를 지정합니다.
        Intent intent = new Intent(this, MainActivity.class);
        //알림 채널 아이디 : 본인 하고싶으신대로...
        String channel_id = "CHN_ID";
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
//        Log.i(TAG, "showNotification: "+R.string.API_KEY);
        Log.i(TAG, "showNotification: "+String.valueOf(R.string.API_KEY));


        //기본 사운드로 알림음 설정. 커스텀하려면 소리 파일의 uri 입력
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channel_id)
                .setSmallIcon(R.drawable.open_graph_logo)
                .setSound(uri)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000}) //알림시 진동 설정 : 1초 진동, 1초 쉬고, 1초 진동
                .setOnlyAlertOnce(true) //동일한 알림은 한번만.. : 확인 하면 다시 울림
                .setContentIntent(pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) { //안드로이드 버전이 커스텀 알림을 불러올 수 있는 버전이면
            //커스텀 레이아웃 호출
            builder = builder.setContent(getCustomDesign(title, message));

            //  PendingIntent pendingIntent = PendingIntent.getActivity(this, alarmID, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        } else { //아니면 기본 레이아웃 호출
            builder = builder.setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.open_graph_logo); //커스텀 레이아웃에 사용된 로고 파일과 동일하게..
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //알림 채널이 필요한 안드로이드 버전을 위한 코드
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channel_id, "CHN_NAME", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setSound(uri, null);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        //알림 표시 !
        notificationManager.notify(0, builder.build());

    }
}
