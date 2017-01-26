package amiao.alarm;

import android.content.Intent;
import android.util.Log;

import java.util.Random;

/**
 * Created by Amiao on 1/24/2017.
 */

public class Math {
    private static int num1;
    private static int num2;
    private static int sum;
    private int max = 200;
    private int min = 0;

    Math(){
        Random rand = new Random(System.currentTimeMillis());
        num1 = rand.nextInt(rand.nextInt(max - min + 1) + min);
        num2 = rand.nextInt(rand.nextInt(max - min + 1) + min);
    }


    public static void computeSum(){

        sum = num1 + num2;
        Log.e("num1 is", num1+"");
        Log.e("num2 is", num2+"");
        Log.e("answer is",sum+"");
    }


    public static int getNum1(){
        return num1;
    }

    public static int getNum2(){
        return num2;
    }

    public static int getSum(){
        return sum;
    }


}
