package br.com.pontomobi.livelopontos.ui.activateDevice;

import android.content.Context;
import android.text.TextUtils;

import java.util.List;

import br.com.pontomobi.livelopontos.Constants;
import br.com.pontomobi.livelopontos.helper.SharedPreferencesHelper;
import br.com.pontomobi.livelopontos.service.livelo.LiveloException;
import br.com.pontomobi.livelopontos.service.livelo.LiveloRepository;
import br.com.pontomobi.livelopontos.service.livelo.activatedevice.model.retrievemobile.MobileDevice;
import br.com.pontomobi.livelopontos.service.livelo.activatedevice.model.retrievemobile.RetrieveDeviceResponse;
import br.com.pontomobi.livelopontos.util.DeviceManager;

/**
 * Created by vilmar.filho on 1/25/16.
 */
public class ActivateDeviceBusiness {

    private Context mContext;
    private OnActiveDevice mOnActiveDevice;

    private String mLogin;

    public ActivateDeviceBusiness(Context mContext, OnActiveDevice onActiveDevice) {
        this.mContext = mContext;
        this.mOnActiveDevice = onActiveDevice;
    }

    public void retrieveDevices(String login) {
        mLogin = TextUtils.isEmpty(login) ? "111" : login;
        LiveloRepository.with(mContext).retrieveDevices(mContext, getOnRetrieveDevice());
    }

    private OnRetrieveDevice getOnRetrieveDevice() {
        return new OnRetrieveDevice() {

            @Override
            public void onRetrieveSuccess(RetrieveDeviceResponse response) {
                //mOnRetrieveDevice.onRetrieveSuccess(response);

                boolean activeDevice = false;

                if(response != null && response.getQueryMobileDevicesResponse().getMobileDevicesList() != null && response.getQueryMobileDevicesResponse().getMobileDevicesList().getMobileDevice().size() > 0) {
                    List<MobileDevice> list = response.getQueryMobileDevicesResponse().getMobileDevicesList().getMobileDevice();

                    for(int i = 0; i < list.size(); i++){
                        if(DeviceManager.with(mContext).isDeviceActive(mLogin, list.get(i).getDeviceID())){
                            SharedPreferencesHelper.write(mContext, Constants.SharedPrefsKeys.SHARED_PREF_NAME, Constants.SharedPrefsKeys.DEVICE_ACTIVE, true);
                            activeDevice = true;
                            break;
                        }
                    }

                    mOnActiveDevice.onActiveSuccess(activeDevice);

                } else {
                    mOnActiveDevice.onActiveSuccess(activeDevice);
                }
            }

            @Override
            public void onRetrieveFail(LiveloException exception) {
                mOnActiveDevice.onActiveFail(exception);
            }
        };
    }

    public interface OnRetrieveDevice {
        void onRetrieveSuccess(RetrieveDeviceResponse response);

        void onRetrieveFail(LiveloException exception);
    }

    public interface OnActiveDevice {
        void onActiveSuccess(boolean hasActiveDevice);

        void onActiveFail(LiveloException exception);
    }
}
