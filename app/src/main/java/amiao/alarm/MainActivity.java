package amiao.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringDef;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {


    Math math = new Math();
    Ringtone ringtone;
    AlarmManager alarmManager;
    TimePicker timePicker;
    TextView message;
    static TextView questionTV;
    static EditText answerTV;
    static boolean isCorrect = false;
    static int count = 0;
    static String givenAnswer;


    Context context;
    PendingIntent pendingIntent;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;


        ringtone = new Ringtone();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        message = (TextView) findViewById(R.id.message);
        questionTV = (TextView) findViewById(R.id.question);
        answerTV = (EditText) findViewById(R.id.answer);

        answerTV.addTextChangedListener(answerTextWatcher);

        final Calendar calendar = Calendar.getInstance();

        final Intent intent = new Intent(this.context, Command.class);


        Button alarmOn = (Button) findViewById(R.id.alarmOn);



        alarmOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                calendar.set(Calendar.MINUTE, timePicker.getMinute());

                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();


                String hourString = String.valueOf(hour);
                String minuteString = String.valueOf(minute);

                if (minute < 10){

                    minuteString = "0" + String.valueOf(minute);
                }



                setMessage("Alarm set to: " + hourString + ": " + minuteString);

                intent.putExtra("extra", "on");


                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);


                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        pendingIntent);


            }


        });


        Button alarmOff = (Button) findViewById(R.id.alarmOff);


        alarmOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Ringtone.isMusicOn){

                    setMessage("Alarm can't be canceled! ");
                    count++;

                } else {

                    setMessage("Alarm off!");
                }

                alarmManager.cancel(pendingIntent);

                intent.putExtra("extra", "off");

                sendBroadcast(intent);


            }
        });

        final Button submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Ringtone.isMusicOn && !isCorrect){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Your answer is wrong, please try again!");
                    builder.setCancelable(true);

                    builder.setPositiveButton(
                            "ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    answerTV.setText("");
                                }
                            });

                    AlertDialog alert11 = builder.create();
                    alert11.show();
                }

                alarmManager.cancel(pendingIntent);

                intent.putExtra("extra", "submit");

                sendBroadcast(intent);

            }
        });

    }

    private TextWatcher answerTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence s, int i, int i1, int i2) {

        }


        @Override
        public void afterTextChanged(Editable editable) {
            givenAnswer = answerTV.getText().toString();

            if(givenAnswer .equals(Math.getSum()+"")){
                isCorrect = true;
                Log.e("Given answer is ", givenAnswer+"");
                Log.e("isCorrect is", isCorrect+"");
            }
        }
    };



    private void setMessage(String s) {
        message.setText(s);
    }


    public static void setQuestionTV(String s){
        questionTV.setText(s);
    }

    public static void reset(){
        setQuestionTV("^o^ v");
        answerTV.setText("");
        count = 0;
        isCorrect = false;
    }

}
