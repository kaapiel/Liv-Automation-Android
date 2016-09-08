package br.com.pontomobi.livelopontos.ui.myAccount;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.model.MenuMyAccount;
import br.com.pontomobi.livelopontos.util.Util;

/**
 * Created by selemafonso on 03/11/15.
 */
public class MyAccountAdapter extends RecyclerView.Adapter<MyAccountRowRolder> {

    private Context context;
    private List<MenuMyAccount> menuMyAccountList;
    private MyAccountFragment.OnMyAccountClickListener onMyAccountClickListener;

    public MyAccountAdapter(Context context, List<MenuMyAccount> menuMyAccountList, MyAccountFragment.OnMyAccountClickListener onMyAccountClickListener) {
        this.context = context;
        this.menuMyAccountList = menuMyAccountList;
        this.onMyAccountClickListener = onMyAccountClickListener;
    }

    @Override
    public MyAccountRowRolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_my_account, parent, false);

        return new MyAccountRowRolder(context, itemView, onMyAccountClickListener);
    }

    @Override
    public void onBindViewHolder(MyAccountRowRolder holder, int position) {
        MenuMyAccount menuMyAccount = menuMyAccountList.get(position);

        holder.getMyAccountTitle().setText(menuMyAccount.getTitle());
        holder.getMyAccountIcon().setImageDrawable(Util.getDrawableByName(context, menuMyAccount.getIcon()));

        holder.getFingerprintSwitch().setVisibility(menuMyAccount.isFingerPrint() ? View.VISIBLE : View.GONE);
        holder.getFingerprintSwitch().setChecked(menuMyAccount.isFingerEnable());

    }

    @Override
    public int getItemCount() {
        return (null != menuMyAccountList ? menuMyAccountList.size() : 0);
    }
}
