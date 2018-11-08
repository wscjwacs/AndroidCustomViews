package ca.com.androidcustomviews.adapter;

import android.content.Context;
import android.support.annotation.Nullable;



import java.util.List;

import ca.com.androidcustomviews.R;
import ca.com.androidcustomviews.base.BaseRecyclerAdapter;
import ca.com.androidcustomviews.base.RecyclerViewHolder;
import ca.com.androidcustomviews.bean.TypeBean;


/**
 * <pre>
 *      @author : xiaoyao
 *      e-mail  : xiaoyao@51vest.com
 *      date    : 2018/05/14
 *      desc    :
 *      version : 1.0
 * </pre>
 */

public class MainAdapter extends BaseRecyclerAdapter<TypeBean> {


    public MainAdapter(Context context) {
        super(context);
    }

    @Override
    public void convert(RecyclerViewHolder holder, int position) {
        holder.setText(R.id.title_tv,getItemData(position).getTitle());
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_item_main;
    }
}
