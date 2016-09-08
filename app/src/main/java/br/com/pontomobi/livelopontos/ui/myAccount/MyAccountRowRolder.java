package br.com.pontomobi.livelopontos.ui.myAccount;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.rey.material.widget.Switch;

import br.com.pontomobi.livelopontos.R;

/**
 * Created by selemafonso on 03/11/15.
 */
public class MyAccountRowRolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    private TextView myAccountTitle;
    private ImageView myAccountIcon;
    private Switch fingerprintSwitch;
    private RelativeLayout myAccountContent;
    private MyAccountFragment.OnMyAccountClickListener onMyAccountClickListener;


    public MyAccountRowRolder(Context context, View view, final MyAccountFragment.OnMyAccountClickListener onMyAccountClickListener) {
        super(view);

        this.myAccountContent = (RelativeLayout) view.findViewById(R.id.my_account_content);
        this.myAccountIcon = (ImageView) view.findViewById(R.id.my_account_icon);
        this.myAccountTitle = (TextView) view.findViewById(R.id.my_account_title);
        this.fingerprintSwitch = (Switch) view.findViewById(R.id.switch_fingerprint);

        fingerprintSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(Switch view, boolean checked) {
                onMyAccountClickListener.onSwitchStatusChange(checked);
            }
        });

        this.onMyAccountClickListener = onMyAccountClickListener;
        myAccountContent.setOnClickListener(this);
    }

    public TextView getMyAccountTitle() {
        return myAccountTitle;
    }

    public void setMyAccountTitle(TextView myAccountTitle) {
        this.myAccountTitle = myAccountTitle;
    }

    public ImageView getMyAccountIcon() {
        return myAccountIcon;
    }

    public void setMyAccountIcon(ImageView myAccountIcon) {
        this.myAccountIcon = myAccountIcon;
    }

    public RelativeLayout getMyAccountContent() {
        return myAccountContent;
    }

    public void setMyAccountContent(RelativeLayout myAccountContent) {
        this.myAccountContent = myAccountContent;
    }

    public Switch getFingerprintSwitch() {
        return fingerprintSwitch;
    }

    public void setFingerprintSwitch(Switch fingerprintSwitch) {
        this.fingerprintSwitch = fingerprintSwitch;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_account_content:
                onMyAccountClickListener.onItemClick(getAdapterPosition());
                break;
        }
    }
}
