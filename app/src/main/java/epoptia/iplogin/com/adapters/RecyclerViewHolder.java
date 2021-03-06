package epoptia.iplogin.com.adapters;



import android.view.View;

import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;


import epoptia.iplogin.com.R;

/**
 * Created by giannis on 5/9/2017.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private ViewDataBinding mBinding;
//    private RecyclerCallback bindingInterface;

    public RecyclerViewHolder(ViewDataBinding viewDataBinding) {
        super(viewDataBinding.getRoot());
        this.mBinding = viewDataBinding;
//        this.bindingInterface = bindingInterface;
    }

    public void bind(Object obj, Object object, int position) {
        mBinding.setVariable(BR.obj, obj);
        mBinding.setVariable(BR.presenter, object);
        View baseView = mBinding.getRoot().findViewById(R.id.baseLlt);
        if (baseView != null) baseView.setTag(position);
        mBinding.executePendingBindings();
    }
}
