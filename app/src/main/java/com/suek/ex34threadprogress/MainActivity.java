package com.suek.ex34threadprogress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    // 1)
    ProgressDialog dialog;

    int gauge= 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    // 2)
    public void clickBtn1(View view) {
        //Wheel type progress dialog
        dialog= new ProgressDialog(this);
        dialog.setTitle("Wheel dialog");
        dialog.setMessage("Downloading...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        //dialog.setCancelable(false);   //뒤로가기버튼 누르면 꺼지지 않게 하는법
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();    //dialog.show(); 만 쓰면 밖에를 터치하면 꺼짐

        //3초뒤에 다이어로그 종료 시키기 (원래는 다운로드 끝나면 종료해야함)
        //Handler 객체를 이용
        handler.sendEmptyMessageDelayed(0,3000);
        //3000ms 후에 Handler 객체의 handleMessage()가 발동함

    }

    //Handler 객체 생성
    Handler handler= new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            dialog.dismiss();
        }
    };

    public void clickBtn2(View view) {
        //Bar type progress dialog
        dialog= new ProgressDialog(this);
        dialog.setTitle("Bar dialog");
        dialog.setMessage("Downloading...");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        //dialog.setMax(10);  //0/10   --> 안쓰면 자동으로 100
        dialog.setMax(100);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog.setProgress(gauge);

        //별도 스레드가 게이지 증가시키도록..    버튼안에 반복문쓴ㄴ 대신에 Thread 이용하기
        //new Thread().start();
        new Thread(){
            @Override
            public void run() {
                while(gauge<100){
                    gauge++;
                    dialog.setProgress(gauge);

                    try {
                        Thread.sleep(50);   //0.05초 대기
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }// while 이 끝나면 dialog 끝남--> dismiss 시키기
                dialog.dismiss();
            }
        }.start();
    }
}
