package zhaoq.com.myxmlkeyboard;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import java.util.List;

/**
 * PACKAGE_NAME:zhaoq.com.myxmlkeyboard
 * CREATE_BY:zhaoqiang
 * AUTHOR_EMAIL:zhaoq_hero@163.com
 * DATE: 2016/04/13  18:03
 * xml文件  定义的键盘
 */
public class XMlKeysUtils implements KeyboardView.OnKeyboardActionListener {

    private EditText editText; //当前  需要添加内容的EditText

    private Keyboard k1,k2;  //数字键盘 和字母键盘

    private KeyboardView keyboardView;//存放键盘的  布局

    //定义  构造方法一： 传入当前activity
    public XMlKeysUtils(Activity activity,EditText editText){
        this.editText = editText;

        //获取 数字键盘 和英文键盘：
        k1 = new Keyboard(activity.getApplicationContext(),R.xml.numskeys);
        k2 = new Keyboard(activity.getApplicationContext(),R.xml.wordskeys);
        keyboardView = (KeyboardView) activity.findViewById(R.id.my_keyboard_view);

        //填充布局:
        keyboardView.setKeyboard(k1);
        keyboardView.setEnabled(true);
        keyboardView.setPreviewEnabled(true);
        keyboardView.setOnKeyboardActionListener(this);  //添加监听事件键盘的
    }

    //定义  构造方法二： 传入当前activity
    public XMlKeysUtils(Context context,View view,EditText editText){
        this.editText = editText;

        //获取 数字键盘 和英文键盘：
        k1 = new Keyboard(context,R.xml.numskeys);
        k2 = new Keyboard(context,R.xml.wordskeys);
        keyboardView = (KeyboardView) view.findViewById(R.id.my_keyboard_view);

        //填充布局:
        keyboardView.setKeyboard(k1);
        keyboardView.setEnabled(true);
        keyboardView.setPreviewEnabled(true);
        keyboardView.setOnKeyboardActionListener(this);  //添加监听事件键盘的
    }


    //显示键盘
    public void showKeyBoard(){
        int visibility = keyboardView.getVisibility();
        if(visibility == View.GONE || visibility == View.INVISIBLE){
            keyboardView.setVisibility(View.VISIBLE);
        }
    }
    //隐藏键盘：
    public void hideKeyBoard(){
        int visibility = keyboardView.getVisibility();
        if(visibility == View.VISIBLE){
            keyboardView.setVisibility(View.GONE);
        }
    }

    private boolean isUpper = false;  //默认  为小写不是大写
    //大小写切换
    public void  changeKey(){

        List<Keyboard.Key> keyList = k2.getKeys(); //获取字母键盘   所有字母

        if(isUpper){ //是大写默认
            isUpper = false;
            for(Keyboard.Key key:keyList){
                if(key.label!=null && isword(key.label.toString())){
                    key.label = key.label.toString().toLowerCase();//转换为小写
                    key.codes[0] =  key.codes[0] + 32; //转换为小写
                }
            }
        }else{ //小写   转为大写
            isUpper = true;
            for(Keyboard.Key key:keyList){
                if(key.label !=null && isword(key.label.toString())){
                    key.label = key.label.toString().toUpperCase();//转换为大写
                    key.codes[0] = key.codes[0] - 32;//
                }
            }
        }
    }

    //判断  k2键盘中是否为英文：
    private boolean isword(String str){
        String wordstr = "abcdefghijklmnopqrstuvwxyz";
        if(wordstr.indexOf(str.toLowerCase())>-1){
            return  true;
        }
        return  false;
    }

    //------------------以下为键盘的监听事件--------------------
    @Override
    public void onPress(int primaryCode) {//按下事件
    }
    @Override
    public void onRelease(int primaryCode) {//释放事件
    }
    @Override
    public void onText(CharSequence text) {
    }
    @Override
    public void swipeLeft() {
    }
    @Override
    public void swipeRight() {
    }
    @Override
    public void swipeDown() {
    }
    @Override
    public void swipeUp() {
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        //添加监听事件：
        Editable edittable = editText.getText();//获取   当前EditText的可编辑对象
        int start = editText.getSelectionStart();//
        if(primaryCode == Keyboard.KEYCODE_CANCEL){//完成  按键
            hideKeyBoard();//隐藏键盘
        }else if (primaryCode == Keyboard.KEYCODE_DELETE){//回退
            if(edittable != null && edittable.length() > 0){
                if(start>0){
                    edittable.delete(start - 1,start);
                }
            }
        }else if(primaryCode == Keyboard.KEYCODE_SHIFT){  //大小写  切换
            changeKey();//切换大小写
            keyboardView.setKeyboard(k2);//设置  为字母键盘
        }else if (primaryCode == Keyboard.KEYCODE_MODE_CHANGE){//数字和英文键盘切换
            if(isNum){//当前为   数字键盘
                isNum = false;
                keyboardView.setKeyboard(k2);// 设置为  字母键盘
            }else{
                isNum = true;
                keyboardView.setKeyboard(k1);//设置为数字键盘
            }
        }else if(primaryCode == 57419){  //go left
            if (start > 0) {
                editText.setSelection(start - 1);
            }
        }else if(primaryCode == 57421){ //go right
            if (start < editText.length()) {
                editText.setSelection(start + 1);
            }
        }else{
            edittable.insert(start,Character.toString((char)primaryCode));
        }
    }

    private boolean isNum = false;//是否是数字键盘

}
