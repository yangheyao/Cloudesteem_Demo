package com.example.demo_study;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.SubMenuBuilder;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "com.example.demo_study";

    MqttAndroidClient mqttAndroidClient;

    final String serverUri = "tcp://abc.abc.abc.abc:1883";
    String clientId = "robot";
    //订阅的话题要和发布的话题一致
    final String subscriptionTopic = "emqtt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Data();
        Trsanfer(1);
        test_mqtt();
    }

    public void Data(){
        Log.e(TAG,"Open");
    }

    public int Trsanfer(int data){
        Log.e(TAG,"Trsanfer");
        return 0;
    }

    public void test_mqtt(){


        //新建client实例
        clientId = clientId + System.currentTimeMillis();
        mqttAndroidClient = new MqttAndroidClient(getApplicationContext(), serverUri, clientId);
        //设置回调函数
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                //打印订阅的信息
                Log.i("mouse",new String(message.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
        //连接设置,是否重连,是否清理session
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);

        try{
            //连接mqtt服务器
            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener(){
                @Override
                public void onSuccess(IMqttToken asyncActionToken){
                    makeToast("连接成功");
                    Log.i("mouse","连接成功");
                    //订阅topic
                    subscriptionTopic();
                }
                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception){
                    makeToast("连接失败");
                    Log.i("mouse","连接失败!!!" + exception.getMessage());
                }

            });

        }catch(MqttException ex){
            ex.printStackTrace();
        }

    }

    public void subscriptionTopic(){
        try{
            //开始订阅
            mqttAndroidClient.subscribe(subscriptionTopic, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    makeToast("成功订阅到" + subscriptionTopic);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    makeToast("订阅失败" + subscriptionTopic);
                }
            });
        }catch (MqttException ex){
            ex.printStackTrace();
        }
    }

    private void makeToast(String str){
        Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
    }
}
