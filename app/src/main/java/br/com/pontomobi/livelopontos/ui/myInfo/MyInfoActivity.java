package br.com.pontomobi.livelopontos.ui.myInfo;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rey.material.widget.EditText;
import com.rey.material.widget.Spinner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.pontomobi.livelopontos.Constants;
import br.com.pontomobi.livelopontos.LiveloPontosActivity;
import br.com.pontomobi.livelopontos.LiveloPontosApp;
import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.service.livelo.Address;
import br.com.pontomobi.livelopontos.service.livelo.userprofile.model.UserAddressResponse;
import br.com.pontomobi.livelopontos.service.livelo.userprofile.model.UserProfileResponse;
import br.com.pontomobi.livelopontos.ui.dialog.DialogCustomAlert;
import br.com.pontomobi.livelopontos.ui.registerUser.RegisterUserAdapter;
import br.com.pontomobi.livelopontos.ui.widget.ExpandableList;
import br.com.pontomobi.livelopontos.ui.widget.LastUpdate;
import br.com.pontomobi.livelopontos.ui.widget.SwitchCustom;
import br.com.pontomobi.livelopontos.util.DateUtil;
import br.com.pontomobi.livelopontos.util.Mask;
import br.com.pontomobi.livelopontos.util.Util;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.http.HEAD;

/**
 * Created by selemafonso on 19/09/15.
 */
public class MyInfoActivity extends LiveloPontosActivity {


    @Bind(R.id.register_user_cpf)
    EditText registerUserCpf;
    @Bind(R.id.register_user_rg)
    EditText registerUserRg;
    @Bind(R.id.register_user_name)
    EditText registerUserName;
    @Bind(R.id.register_user_nickname)
    EditText registerUserNickname;
    @Bind(R.id.register_user_nationality)
    EditText registerUserNationality;
    @Bind(R.id.register_user_sex)
    Spinner registerUserSex;
    @Bind(R.id.register_user_birth_date)
    EditText registerUserBirthDate;
    @Bind(R.id.register_user_email)
    EditText registerUserEmail;
    @Bind(R.id.register_user_confirm_email)
    EditText registerUserConfirmEmail;
    @Bind(R.id.register_user_address_list)
    ExpandableList registerUserAddressList;
    @Bind(R.id.register_user_phone_home)
    EditText registerUserPhoneHome;
    @Bind(R.id.register_user_phone_work)
    EditText registerUserPhoneWork;
    @Bind(R.id.register_user_celphone)
    EditText registerUserCelphone;
    @Bind(R.id.register_user_password)
    EditText registerUserPassword;
    @Bind(R.id.register_user_confirm_password)
    EditText registerUserConfirmPassword;
    @Bind(R.id.register_user_switch_push)
    SwitchCustom registerUserSwitchPush;
    @Bind(R.id.register_user_switch_sms)
    SwitchCustom registerUserSwitchSms;
    @Bind(R.id.register_user_switch_email)
    SwitchCustom registerUserSwitchEmail;
    @Bind(R.id.register_user_terms_conditions)
    TextView registerUserTermsConditions;
    @Bind(R.id.my_account_change)
    AppCompatButton myAccountChange;
    @Bind(R.id.my_account_banner)
    LinearLayout myAccountBanner;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.my_info_last_update)
    LastUpdate myInfoLastUpdate;
    private List<Address> addressList;
    private UserProfileResponse userProfileResponse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        ButterKnife.bind(this);

        configureActionBar();
        userProfileResponse = LiveloPontosApp.getInstance().getProfileResponse().getUserProfileResponse();
        setInfoScreen();
        listAddress();
        setTypefaceInEditText();
        initButtonColor();
        disableEditTexts();

        myInfoLastUpdate.getRefreshImage().setVisibility(View.GONE);
    }

    @Override
    protected void configureActionBar() {
        setSupportActionBar(toolbar);
        super.configureActionBar();
        toolbarTitle.setText(getString(R.string.my_data_title_toolbar));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @OnClick({R.id.my_account_change, R.id.my_account_banner})
    public void openEditaMyAccountClick() {
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();

        if (isAlive()) {
            dialogCustom.showCustomDialog(MyInfoActivity.this, LiveloPontosApp.getInstance().getOpenUrl(), true,
                    new DialogCustomAlert.AlertDialogClickListener() {
                        @Override
                        public void onPositiveClick() {
                            LiveloPontosApp.getInstance().sendTrackerEvent(
                                    Constants.GoogleAnalytisEvents.EVENT_SCREEN_MY_DATA,
                                    Constants.GoogleAnalytisEvents.EVENT_CATEGORY_MY_DATA,
                                    Constants.GoogleAnalytisEvents.EVENT_ACTION_EDIT_DATA,
                                    ""
                            );

                            String url = Constants.URL_EDIT_ACCOUNT_PROD;
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }

                        @Override
                        public void onNegativeClick() {

                        }

                        @Override
                        public void onBackPressedInDialog() {

                        }
                    });
        }
    }

    private void setInfoScreen() {
        myInfoLastUpdate.setLastUpdateDateHour(DateUtil.getFormatDateToShow(userProfileResponse.getLastUpdateData()));

        registerUserCpf.addTextChangedListener(Mask.insert("###.###.###-##", registerUserCpf));

        registerUserCpf.setText(userProfileResponse.getCpf());
        registerUserRg.setText(userProfileResponse.getRg());
        registerUserName.setText(userProfileResponse.getFullName());
        registerUserNickname.setText(userProfileResponse.getNickName());
        registerUserEmail.setText(userProfileResponse.getEmail());

        Timestamp timestamp = new Timestamp(userProfileResponse.getDateOfBirth().getDateTime());
        registerUserBirthDate.setText(DateUtil.getFormatDateToShowJustDate(timestamp));
        registerUserPassword.setText("******");

        String phoneHome = userProfileResponse.getPhoneNumberByType("H");
        if (phoneHome.length() < 11) {
            registerUserPhoneHome.addTextChangedListener(Mask.insert("(##) ####-####", registerUserPhoneHome));
        } else {
            registerUserPhoneHome.addTextChangedListener(Mask.insert("(##) #####-####", registerUserPhoneHome));
        }

        String phoneWork = userProfileResponse.getPhoneNumberByType("W");
        if (phoneHome.length() < 11) {
            registerUserPhoneWork.addTextChangedListener(Mask.insert("(##) ####-####", registerUserPhoneWork));
        } else {
            registerUserPhoneWork.addTextChangedListener(Mask.insert("(##) #####-####", registerUserPhoneWork));
        }


        String celphone = userProfileResponse.getPhoneNumberByType("M");
        if (celphone.length() < 11) {
            registerUserCelphone.addTextChangedListener(Mask.insert("(##) ####-####", registerUserCelphone));
        } else {
            registerUserCelphone.addTextChangedListener(Mask.insert("(##) #####-####", registerUserCelphone));
        }


        registerUserPhoneHome.setText(phoneHome);
        registerUserPhoneWork.setText(phoneWork);
        registerUserCelphone.setText(celphone);

        registerUserSwitchPush.getSwitchCustomSwitch().setChecked(userProfileResponse.isReceivePush());
        registerUserSwitchEmail.getSwitchCustomSwitch().setChecked(userProfileResponse.isReceivePromoEmail());
        registerUserSwitchSms.getSwitchCustomSwitch().setChecked(userProfileResponse.isReceivePromoSMS());

        registerUserSwitchPush.getSwitchCustomSwitch().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //Your code
                }
                return true;
            }
        });

        registerUserSwitchSms.getSwitchCustomSwitch().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //Your code
                }
                return true;
            }
        });

        registerUserSwitchEmail.getSwitchCustomSwitch().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //Your code
                }
                return true;
            }
        });

        registerUserSwitchSms.getSwitchCustomSwitch().setClickable(false);

        setupSpinerGender();

    }


    private void setupSpinerGender() {
        String[] items = new String[2];

        if (userProfileResponse.getGender().equalsIgnoreCase("male")) {
            items[0] = "Masculino";
            items[1] = "Feminino";
        } else {
            items[0] = "Feminino";
            items[1] = "Masculino";
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row_spn, items);
        adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        registerUserSex.setAdapter(adapter);
    }

    private void listAddress() {
        addressList = new ArrayList<>();
        boolean isEnable = true;

        if (userProfileResponse.getHomeAddress().getAddress1() != null) {
            addressList.add(new Address(userProfileResponse.getHomeAddress().formatAddressToShow(), isEnable));
            isEnable = false;
        }

        for (UserAddressResponse address : userProfileResponse.getSecondaryAddresses()) {
            if (address.getAddress1() != null) {
                addressList.add(new Address(address.formatAddressToShow(), isEnable));
            }
        }

        List<Address> uniqueAddressList = new ArrayList<>();
        Set<String> uniqueAddress = new HashSet<>();

        for (Address address : addressList) {
            if (uniqueAddress.add(address.getAddress())) {
                uniqueAddressList.add(address);
            }
        }

        addressList = uniqueAddressList;

        registerUserAddressList.setExpanded(true);
        registerUserAddressList.setAdapter(new RegisterUserAdapter(getBaseContext(), addressList));
    }

    private void initButtonColor() {
        myAccountChange.setTextColor(getResources().getColor(R.color.activate_account_button_enable));
        myAccountChange.setEnabled(true);
    }

    private void disableEditTexts() {
        registerUserConfirmEmail.setVisibility(View.GONE);
        registerUserConfirmPassword.setVisibility(View.GONE);
        registerUserTermsConditions.setVisibility(View.GONE);

        Util.disableEditText(registerUserCpf);
        Util.disableEditText(registerUserRg);
        Util.disableEditText(registerUserName);
        Util.disableEditText(registerUserNickname);
        registerUserSex.setClickable(false);
        Util.disableEditText(registerUserNationality);
        Util.disableEditText(registerUserBirthDate);
        Util.disableEditText(registerUserEmail);
        Util.disableEditText(registerUserPhoneHome);
        Util.disableEditText(registerUserPhoneWork);
        Util.disableEditText(registerUserCelphone);
        Util.disableEditText(registerUserPassword);

    }

    private void setTypefaceInEditText() {
        Typeface MuseoSans300 = Typeface.createFromAsset(getBaseContext().getAssets(), "fonts/MuseoSans_300.otf");
        registerUserCpf.setTypeface(MuseoSans300);
        registerUserRg.setTypeface(MuseoSans300);
        registerUserName.setTypeface(MuseoSans300);
        registerUserNationality.setTypeface(MuseoSans300);
        registerUserBirthDate.setTypeface(MuseoSans300);
        registerUserEmail.setTypeface(MuseoSans300);
        registerUserNickname.setTypeface(MuseoSans300);
        registerUserConfirmEmail.setTypeface(MuseoSans300);
        registerUserPhoneHome.setTypeface(MuseoSans300);
        registerUserPhoneWork.setTypeface(MuseoSans300);
        registerUserCelphone.setTypeface(MuseoSans300);
        registerUserPassword.setTypeface(MuseoSans300);
        registerUserConfirmPassword.setTypeface(MuseoSans300);
    }
}
