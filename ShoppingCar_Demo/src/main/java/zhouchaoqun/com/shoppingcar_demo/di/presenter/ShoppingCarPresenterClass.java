package zhouchaoqun.com.shoppingcar_demo.di.presenter;

import java.lang.ref.SoftReference;

import zhouchaoqun.com.shoppingcar_demo.data.bean.GoodsBean;
import zhouchaoqun.com.shoppingcar_demo.di.contract.ShoppingCarContractMVP;
import zhouchaoqun.com.shoppingcar_demo.di.model.ShoppingCarModelClass;

/**
 * Create by ZCQ on 2019/1/15.
 */
public class ShoppingCarPresenterClass implements ShoppingCarContractMVP.ShoppingCarPresenter<ShoppingCarContractMVP.ShoppingCarView> {
    private ShoppingCarContractMVP.ShoppingCarView shoppingCarView;
    private SoftReference<ShoppingCarContractMVP.ShoppingCarView> reference;
    private ShoppingCarContractMVP.ShoppingCarModel shoppingCarModelClass;

    @Override
    public void attachView(ShoppingCarContractMVP.ShoppingCarView shoppingCarView) {
        this.shoppingCarView =  shoppingCarView;

        reference = new SoftReference<>(shoppingCarView);
        //创建M层对象
        shoppingCarModelClass = new ShoppingCarModelClass();
    }

    @Override
    public void disAttachView(ShoppingCarContractMVP.ShoppingCarView shoppingCarView) {

        reference.clear();
    }

    @Override
    public void requestData() {
        shoppingCarModelClass.containData(new ShoppingCarContractMVP.ShoppingCarModel.CallBack() {
            @Override
            public void callBack(GoodsBean goodsBean) {

                shoppingCarView.showData(goodsBean);
            }
        });
    }
}
