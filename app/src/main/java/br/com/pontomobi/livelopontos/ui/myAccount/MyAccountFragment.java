package br.com.pontomobi.livelopontos.ui.myAccount;

import android.app.KeyguardManager;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.pontomobi.livelopontos.Constants;
import br.com.pontomobi.livelopontos.LiveloPontoFragment;
import br.com.pontomobi.livelopontos.LiveloPontosApp;
import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.helper.SharedPreferencesHelper;
import br.com.pontomobi.livelopontos.model.MenuMyAccount;
import br.com.pontomobi.livelopontos.ui.changePassword.ChangePasswordActivity;
import br.com.pontomobi.livelopontos.ui.myInfo.MyInfoActivity;
import br.com.pontomobi.livelopontos.ui.myPoints.MyPointsActivity;
import br.com.pontomobi.livelopontos.ui.orderHistory.RescueHistoryActivity;
import br.com.pontomobi.livelopontos.util.Util;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by selemafonso on 03/11/15.
 */
public class MyAccountFragment extends LiveloPontoFragment {
    private static final int ENABLE_FINGERPRINT_REQUEST = 9999;

    @Bind(R.id.recycler_view_my_account)
    RecyclerView recyclerViewMyAccount;
    private List<MenuMyAccount> menuMyAccountList;

    private boolean fingerPrintAvaliable = false;
    private boolean fingerPrintEnable = false;
    private MyAccountAdapter adapter;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_my_account, container, false);
        ButterKnife.bind(this, view);

        initFingerPrintManager();
        checkFingerPrint();
        checkFingerPrintEnable();
        generateListMenuMyAccount();
        initRecyclerView();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setMenuSelected(R.id.menu_my_account);
    }

    private void checkFingerPrint() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        fingerPrintAvaliable = Util.isFingerprintAuthAvailable(getContext().getSystemService(FingerprintManager.class));
    }

    private void initFingerPrintManager() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        fingerprintManager = getContext().getSystemService(FingerprintManager.class);
        keyguardManager = getContext().getSystemService(KeyguardManager.class);
    }

    private void generateListMenuMyAccount() {
        menuMyAccountList = new ArrayList<>();
        menuMyAccountList.add(new MenuMyAccount(getResources().getResourceEntryName(R.drawable.ic_conta), getString(R.string.my_account_item_my_data)));
        menuMyAccountList.add(new MenuMyAccount(getResources().getResourceEntryName(R.drawable.ic_pontos), getString(R.string.my_account_item_my_points)));
        menuMyAccountList.add(new MenuMyAccount(getResources().getResourceEntryName(R.drawable.ic_historico), getString(R.string.my_account_item_rescue_history)));
        menuMyAccountList.add(new MenuMyAccount(getResources().getResourceEntryName(R.drawable.ic_configuracoes), getString(R.string.my_account_item_change_password)));

        if (fingerPrintAvaliable) {
            menuMyAccountList.add(new MenuMyAccount(getResources().getResourceEntryName(R.drawable.ic_fingerprint), getString(R.string.my_account_item_fingerprint),
                    true, fingerPrintEnable));
        }
    }

    private void initRecyclerView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMyAccount.setLayoutManager(layoutManager);
        adapter = new MyAccountAdapter(getActivity(), menuMyAccountList, getOnMyAccountClickListener());
        recyclerViewMyAccount.setAdapter(adapter);
    }

    private OnMyAccountClickListener getOnMyAccountClickListener() {
        OnMyAccountClickListener onMyAccountClickListener = new OnMyAccountClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 0:
                        LiveloPontosApp.getInstance().sendTrackerEvent(
                                Constants.GoogleAnalytisEvents.EVENT_SCREEN_MY_ACCOUNT,
                                Constants.GoogleAnalytisEvents.EVENT_CATEGORY_MY_ACCOUNT,
                                Constants.GoogleAnalytisEvents.EVENT_ACTION_MY_DATA,
                                ""
                        );
                        Intent intent = new Intent(getContext(), MyInfoActivity.class);
                        startActivity(intent);
                        break;

                    case 1:
                        //((HomeActivity) getActivity()).setHome();
                        LiveloPontosApp.getInstance().sendTrackerEvent(
                                Constants.GoogleAnalytisEvents.EVENT_SCREEN_MY_ACCOUNT,
                                Constants.GoogleAnalytisEvents.EVENT_CATEGORY_MY_ACCOUNT,
                                Constants.GoogleAnalytisEvents.EVENT_ACTION_MY_POINTS,
                                ""
                        );
                        startActivity(new Intent(getContext(), MyPointsActivity.class));
                        break;

                    case 2:
                        LiveloPontosApp.getInstance().sendTrackerEvent(
                                Constants.GoogleAnalytisEvents.EVENT_SCREEN_MY_ACCOUNT,
                                Constants.GoogleAnalytisEvents.EVENT_CATEGORY_MY_ACCOUNT,
                                Constants.GoogleAnalytisEvents.EVENT_ACTION_HISTORY_RESCUE,
                                ""
                        );
                        startActivity(new Intent(getContext(), RescueHistoryActivity.class));
                        break;

                    case 3:
                        LiveloPontosApp.getInstance().sendTrackerEvent(
                                Constants.GoogleAnalytisEvents.EVENT_SCREEN_MY_ACCOUNT,
                                Constants.GoogleAnalytisEvents.EVENT_CATEGORY_MY_ACCOUNT,
                                Constants.GoogleAnalytisEvents.EVENT_ACTION_CHANGE_PASSWORD,
                                ""
                        );
                        startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
                        break;
                }
            }

            @Override
            public void onSwitchStatusChange(boolean enalbe) {
                if (!checkFingerprintIsConfigurateInDevice()) {
                    return;
                }

                if (enalbe && fingerPrintEnable) {
                    return;
                }

                if (!enalbe) {
                    disableFingerPrint();
                } else {
                    enableOrDisableFinger();
                }

                LiveloPontosApp.getInstance().sendTrackerEvent(
                        Constants.GoogleAnalytisEvents.EVENT_SCREEN_MY_ACCOUNT,
                        Constants.GoogleAnalytisEvents.EVENT_CATEGORY_MY_ACCOUNT,
                        Constants.GoogleAnalytisEvents.EVENT_ACTION_FINGERPRINT,
                        (!enalbe + "")
                );
            }
        };

        return onMyAccountClickListener;
    }

    private boolean checkFingerprintIsConfigurateInDevice() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false;
        }

        if (!fingerprintManager.hasEnrolledFingerprints()
                || !keyguardManager.isKeyguardSecure()) {

            Snackbar snackbar = Snackbar
                    .make(recyclerViewMyAccount, "Para utilizar esse recurso você deve cadastrar uma digital nas configurações.", Snackbar.LENGTH_LONG);
            snackbar.show();

            disableFingerPrint();
            return false;
        }

        return true;
    }


    private void enableOrDisableFinger() {
        Intent intent = new Intent(getActivity(), PasswordFingerPrintActivity.class);
        startActivityForResult(intent, ENABLE_FINGERPRINT_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ENABLE_FINGERPRINT_REQUEST) {
            if (resultCode == getActivity().RESULT_OK) {
                fingerPrintEnable = true;
            } else {
                disableFingerPrint();
            }
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void disableFingerPrint() {
        fingerPrintEnable = false;
        menuMyAccountList.get(4).setFingerEnable(false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.notifyItemChanged(4);
            }
        }, 100);


        SharedPreferencesHelper.remove(getContext(), Constants.SharedPrefsKeys.SHARED_PREF_NAME, Constants.SharedPrefsKeys.FINGERPRINT_ENABLE);
        SharedPreferencesHelper.remove(getContext(), Constants.SharedPrefsKeys.SHARED_PREF_NAME, Constants.SharedPrefsKeys.FINGERPRINT_PASS);
    }
    private void checkFingerPrintEnable() {
        fingerPrintEnable = Util.checkFingerPrintIsEnalbe(getContext());
    }

    public interface OnMyAccountClickListener {
        void onItemClick(int position);

        void onSwitchStatusChange(boolean enalbe);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
