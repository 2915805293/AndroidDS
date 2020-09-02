package zhouchaoqun.com.shoppingcar_demo.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import zhouchaoqun.com.shoppingcar_demo.R;

/**
 * Create by ZCQ on 2019/1/16.
 */
public class CalculatorView extends LinearLayout implements View.OnClickListener {


    private Button addBtn;
    private TextView calculatorText;
    private Button jianBtn;
    private CalculatorCallBack calculatorCallBack;

    public CalculatorView(Context context) {
        super(context);

    }

    public CalculatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //加载布局
        View view = LayoutInflater.from(context).inflate(R.layout.calculator_view, this);
        //加载控件
        addBtn = view.findViewById(R.id.addBtn);
        calculatorText = view.findViewById(R.id.calculatorText);
        jianBtn = view.findViewById(R.id.jianBtn);

        //设置点击事件
        addBtn.setOnClickListener(this);
        jianBtn.setOnClickListener(this);
    }


    //暴露方法让外部调用
    public void getCalculatorCallBack(CalculatorCallBack calculatorCallBack){
        this.calculatorCallBack = calculatorCallBack;
    }

    //自定义接口,将数量传递
    public interface CalculatorCallBack{

        //加
        public void addCall(int num);
        //减
        public void jianCall(int num);
    }

    @Override
    public void onClick(View view) {
        //获取文本内容
        String numtext = calculatorText.getText().toString();
        //将文本内容转型成int
        int num = Integer.parseInt(numtext);
        switch (view.getId()){

            case R.id.addBtn: //加
                num++;
                //数量改变后重新设置文本中
                calculatorText.setText(String.valueOf(num));
                calculatorCallBack.addCall(num);
                break;
            case R.id.jianBtn: //减
                num--;
                if(num<0){     //当为0不能减
                    num=0;
                    //数量改变后重新设置文本中
                    calculatorText.setText(String.valueOf(num));
                    calculatorCallBack.jianCall(num);
                }
                //数量改变后重新设置文本中
                calculatorText.setText(String.valueOf(num));
                calculatorCallBack.jianCall(num);
                break;
        }
    }


    public void getTextContent(long num){
       
      
   }
