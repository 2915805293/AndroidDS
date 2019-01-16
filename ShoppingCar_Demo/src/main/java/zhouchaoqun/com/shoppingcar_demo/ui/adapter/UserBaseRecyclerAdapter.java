package zhouchaoqun.com.shoppingcar_demo.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import zhouchaoqun.com.shoppingcar_demo.R;
import zhouchaoqun.com.shoppingcar_demo.data.bean.GoodsBean;

/**
 * Create by ZCQ on 2019/1/15.
 */
public class UserBaseRecyclerAdapter extends BaseQuickAdapter<GoodsBean.DataBean,BaseViewHolder> {

    private RecyclerView goodsRecycler;
    private UserCallBack userCallBack;

    //暴露的接口回调方法
    public void getUserCallBack(UserCallBack userCallBack){
        this.userCallBack = userCallBack;
    }

    public interface UserCallBack{

        public void userCall();
    }


    public UserBaseRecyclerAdapter(int layoutResId, @Nullable List<GoodsBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final GoodsBean.DataBean item) {

        //设置数据
        helper.setText(R.id.sellerName,item.getSellerName());
        //获取组件
        goodsRecycler = helper.getView(R.id.goodsRecycler);
        final CheckBox userCheckbox = helper.getView(R.id.userCheckbox);
        //避免焦点抢占
        userCheckbox.setOnCheckedChangeListener(null);

        //设置是否选中
        userCheckbox.setChecked(item.isUserCheck());

        //数据源
        List<GoodsBean.DataBean.ListBean> list = item.getList();
        //布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL, false);
        goodsRecycler.setLayoutManager(linearLayoutManager);
        final GoodsBaseRecyclerAdapter goodsBaseRecyclerAdapter = new GoodsBaseRecyclerAdapter(R.layout.goods_recycler_item,list);
        goodsRecycler.setAdapter(goodsBaseRecyclerAdapter);
        //接收回调
        goodsBaseRecyclerAdapter.getGoodsCallBack(new GoodsBaseRecyclerAdapter.GoodsCallBack() {
            @Override
            public void goodsCall() {
                boolean resule = true;

                //遍历所有商品复选框
                for (int i = 0; i <item.getList().size() ; i++) {
                    //判断每一个复选框状态,一个商品复选框没选中,商家复选框就不选
                    resule = resule & item.getList().get(i).isGoodsCheck();
                }
                userCheckbox.setChecked(resule);
                //刷新商品子适配器
                goodsBaseRecyclerAdapter.notifyDataSetChanged();
                //一旦数据改变,通知activity的全选机制,判断是否有一个复选框没有选中
                userCallBack.userCall();
            }
        });
        //商家控制商品
        userCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i <item.getList().size() ; i++) {
                     //让每一个商品复选框选中
                    item.getList().get(i).setGoodsCheck(userCheckbox.isChecked());
                }
                //商品复选框的状态值
                item.setUserCheck(userCheckbox.isChecked());
                //刷新本适配器
               notifyDataSetChanged();
                //一旦数据改变,通知activity的全选机制,判断是否有一个复选框没有选中
                userCallBack.userCall();
            }
        });



    }
}
