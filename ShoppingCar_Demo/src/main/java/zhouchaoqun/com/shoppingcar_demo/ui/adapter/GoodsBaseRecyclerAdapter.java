package zhouchaoqun.com.shoppingcar_demo.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import zhouchaoqun.com.shoppingcar_demo.R;
import zhouchaoqun.com.shoppingcar_demo.data.bean.GoodsBean;
import zhouchaoqun.com.shoppingcar_demo.ui.widget.CalculatorView;

/**
 * Create by ZCQ on 2019/1/15.
 */
public class GoodsBaseRecyclerAdapter extends BaseQuickAdapter<GoodsBean.DataBean.ListBean,BaseViewHolder> {

    private ImageView goodsItem_img;
    private CheckBox goodsCheckbox;
    private GoodsCallBack goodsCallBack;

    //自定义方法,让外部调用实现接口
    public void getGoodsCallBack(GoodsCallBack goodsCallBack){
        this.goodsCallBack = goodsCallBack;
    }

    //自定义的接口
    public interface GoodsCallBack{
        public void goodsCall();
    }

    public GoodsBaseRecyclerAdapter(int layoutResId, @Nullable List<GoodsBean.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final GoodsBean.DataBean.ListBean item) {
        //加载组件
        goodsCheckbox = helper.getView(R.id.goodsCheckbox);
        goodsItem_img = helper.getView(R.id.goodsItem_Img);
        //避免
        goodsCheckbox.setOnCheckedChangeListener(null);
        //设置值
        helper.setText(R.id.goodsItem_name,item.getTitle());
        //设置是否选中
        goodsCheckbox.setChecked(item.isGoodsCheck());
        //设置数据
        helper.setText(R.id.goodsItem_price,"￥ "+item.getPrice());
        String images = item.getImages();
        String[] split = images.split("\\|");
        Glide.with(mContext).load(split[0]).into(goodsItem_img);

        goodsCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //选中属性发生改变,去商家进行处理
                item.setGoodsCheck(b);
                goodsCallBack.goodsCall();
            }
        });
        //获取加减器控件
        CalculatorView CalculatorView = helper.getView(R.id.CalculatorView);
        CalculatorView.getCalculatorCallBack(new CalculatorView.CalculatorCallBack() {
            @Override
            public void addCall(int num) {
                //对bean层赋值
                item.setDefaultNum(num);
                //数据发生改变,去执行activity,计算总价
                goodsCallBack.goodsCall();
            }

            @Override
            public void jianCall(int num) {

                //对bean层赋值
                item.setDefaultNum(num);
                //数据发生改变,去执行activity,计算总价
                goodsCallBack.goodsCall();
            }
        });
    }


}
