package zhouchaoqun.com.shoppingcar_demo.di.contract;

import zhouchaoqun.com.shoppingcar_demo.data.bean.GoodsBean;

/**
 * Create by ZCQ on 2019/1/15.
 */
public interface ShoppingCarContractMVP {


    //V层
    public interface ShoppingCarView{

        public void showData(GoodsBean goodsBean);
    }

    //P层
    public interface ShoppingCarPresenter<ShoppingCarView>{

        public void attachView(ShoppingCarView view);
        public void disAttachView(ShoppingCarView view);
        public void requestData();
    }

    //M层
    public interface ShoppingCarModel{

        public void containData(CallBack callBack);

        public interface CallBack{

            public void callBack(GoodsBean goodsBean);
        }
    }
}
