package zhaoq.com.myxmlkeyboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * android  自定义键盘
 * com.zhaoq.mykeyboard
 * @Email zhaoq_hero@163.com
 * @author zhaoQiang : 2016-4-13
 */
public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private EditText editText1,editText2;

    private View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = LayoutInflater.from(this).inflate(R.layout.activity_main,null);

        setContentView(view);

        editText1 = (EditText) findViewById(R.id.Etext_1);
        editText2 = (EditText) findViewById(R.id.Etext_2);

        //添加 触摸事件：
        editText2.setOnTouchListener(this);
        editText1.setOnTouchListener(this);
    }

    // 添加  触摸显示键盘的事件
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_UP){
            editText1.setInputType(InputType.TYPE_NULL);
            editText2.setInputType(InputType.TYPE_NULL);
            switch (v.getId()){
                case R.id.Etext_1:

                    new XMlKeysUtils(this,editText1).showKeyBoard();

                    break;
                case R.id.Etext_2:

                    new XMlKeysUtils(getApplicationContext(),view,editText2).showKeyBoard();

                    break;
                default:
                    break;
            }
            return true;
        }
        return false;
    }
}
