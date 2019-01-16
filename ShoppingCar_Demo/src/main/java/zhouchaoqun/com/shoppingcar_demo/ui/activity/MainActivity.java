package zhouchaoqun.com.shoppingcar_demo.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import zhouchaoqun.com.shoppingcar_demo.R;
import zhouchaoqun.com.shoppingcar_demo.data.bean.GoodsBean;
import zhouchaoqun.com.shoppingcar_demo.di.contract.ShoppingCarContractMVP;
import zhouchaoqun.com.shoppingcar_demo.di.presenter.ShoppingCarPresenterClass;
import zhouchaoqun.com.shoppingcar_demo.ui.adapter.UserBaseRecyclerAdapter;

public class MainActivity extends AppCompatActivity implements ShoppingCarContractMVP.ShoppingCarView {

    private RecyclerView userRecycler;
    private CheckBox qxCheckbox;
    private ShoppingCarContractMVP.ShoppingCarPresenter shoppingCarPresenterClass;
    private TextView goodsMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        userRecycler = findViewById(R.id.userRecycler);
        qxCheckbox = findViewById(R.id.qxCheckbox);
        goodsMoney = findViewById(R.id.goodsMoney);
        //创建P层对像
        shoppingCarPresenterClass = new ShoppingCarPresenterClass();
        shoppingCarPresenterClass.attachView(this);
        shoppingCarPresenterClass.requestData();


    }

    @Override
    public void showData(final GoodsBean goodsBean) {

        final List<GoodsBean.DataBean> userList = goodsBean.getData();
        userList.remove(0);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL, false);
        userRecycler.setLayoutManager(linearLayoutManager);
        final UserBaseRecyclerAdapter userBaseRecyclerAdapter = new UserBaseRecyclerAdapter(R.layout.user_recycler_item,userList);
        userRecycler.setAdapter(userBaseRecyclerAdapter);

        //接收回调方法,复选框状态改变
        userBaseRecyclerAdapter.getUserCallBack(new UserBaseRecyclerAdapter.UserCallBack() {
            @Override
            public void userCall() {
                boolean result = true;
                //商家遍历
                for (int i = 0; i <goodsBean.getData().size() ; i++) {

                    result = result&goodsBean.getData().get(i).isUserCheck();
                    //商家内部商品遍历
                    for (int j = 0; j < goodsBean.getData().get(i).getList().size(); j++) {

                        result = result&goodsBean.getData().get(i).getList().get(j).isGoodsCheck();
                    }
                }

                qxCheckbox.setChecked(result);
                //计算总价,goods每次复选框改变的状态的时候,就会回调接口,自动执行到这里----计算总价
                getTotal(userList);
            }
        });


        qxCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i <userList.size() ; i++) {

                    userList.get(i).setUserCheck(qxCheckbox.isChecked());
                    for (int j = 0; j <userList.get(i).getList().size() ; j++) {

                        userList.get(i).getList().get(j).setGoodsCheck(qxCheckbox.isChecked());
                    }
                }
                //刷新适配器
                userBaseRecyclerAdapter.notifyDataSetChanged();
                getTotal(userList);
            }
        });

    }
    //计算总价
    private void getTotal(List<GoodsBean.DataBean> userList){
        //应付金额
        double totalCount = 0;
        //商家条目
        for (int i = 0; i <userList.size() ; i++) {

            //商品
            for (int j = 0; j <userList.get(i).getList().size() ; j++) {
                //判断该商品是否被选中
                if(userList.get(i).getList().get(j).isGoodsCheck()){

                    //获取商品的价格*数量
                    int num = userList.get(i).getList().get(j).getDefaultNum();
                    double price = userList.get(i).getList().get(j).getPrice();
                    double goodsFinalPrice = num * price;
                    //累加每个选中的商品应付金额
                    totalCount+=goodsFinalPrice;
                }
            }
        }
        goodsMoney.setText("￥ "+totalCount);

    }

}
