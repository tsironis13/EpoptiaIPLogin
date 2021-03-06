package epoptia.iplogin.com.adapters;




import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by giannis on 5/9/2017.
 */

public abstract class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private int layoutid;
    private ViewDataBinding mBinding;

    public RecyclerViewAdapter(int layoutId) {
        this.layoutid = layoutId;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        mBinding = DataBindingUtil.inflate(layoutInflater, layoutid, parent, false);

        return new RecyclerViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Object item = getObjForPosition(position, mBinding);
        holder.bind(item, getClickListenerObject(), position);
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    @Override
    public int getItemCount() {
        return getTotalItems();
    }

//    protected abstract int getsItemCount();

    protected abstract Object getObjForPosition(int position, ViewDataBinding mBinding);

    protected abstract Object getClickListenerObject();

    protected abstract int getLayoutIdForPosition(int position);

    protected abstract int getTotalItems();

}
