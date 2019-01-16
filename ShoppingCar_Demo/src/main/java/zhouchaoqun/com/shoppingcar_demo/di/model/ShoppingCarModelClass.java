package zhouchaoqun.com.shoppingcar_demo.di.model;

import android.util.Log;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import zhouchaoqun.com.shoppingcar_demo.data.bean.GoodsBean;
import zhouchaoqun.com.shoppingcar_demo.data.urls.Urls;
import zhouchaoqun.com.shoppingcar_demo.di.contract.ShoppingCarContractMVP;

/**
 * Create by ZCQ on 2019/1/15.
 */
public class ShoppingCarModelClass implements ShoppingCarContractMVP.ShoppingCarModel {
    @Override
    public void containData(final CallBack callBack) {
        OkGo.<String>get(Urls.GOODS_URL).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {

                String s = response.body().toString();
                Log.i("zcq", "商品接口解析数据: "+s);
                //解析数据
                Gson gson = new Gson();
                GoodsBean goodsBean = gson.fromJson(s, GoodsBean.class);
                callBack.callBack(goodsBean);
            }
        });
    }
}
