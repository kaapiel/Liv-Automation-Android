package br.com.pontomobi.livelopontos.ui.registerUser;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rey.material.widget.EditText;
import com.rey.material.widget.Spinner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import br.com.pontomobi.livelopontos.Constants;
import br.com.pontomobi.livelopontos.LiveloPontosActivity;
import br.com.pontomobi.livelopontos.LiveloPontosApp;
import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.model.Alert;
import br.com.pontomobi.livelopontos.model.Password;
import br.com.pontomobi.livelopontos.service.livelo.Address;
import br.com.pontomobi.livelopontos.service.livelo.LiveloException;
import br.com.pontomobi.livelopontos.service.livelo.activate.model.UserData;
import br.com.pontomobi.livelopontos.service.livelo.registration.model.RegisterUserRequest;
import br.com.pontomobi.livelopontos.ui.activateDevice.ActivateDeviceActivity;
import br.com.pontomobi.livelopontos.ui.dialog.DialogCustomAlert;
import br.com.pontomobi.livelopontos.ui.widget.BannerError;
import br.com.pontomobi.livelopontos.ui.widget.ExpandableList;
import br.com.pontomobi.livelopontos.ui.widget.SwitchCustom;
import br.com.pontomobi.livelopontos.util.DateUtil;
import br.com.pontomobi.livelopontos.util.Mask;
import br.com.pontomobi.livelopontos.util.StringUtil;
import br.com.pontomobi.livelopontos.util.Util;
import br.com.pontomobi.livelopontos.util.ValidateUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by selem.gomes on 10/09/15.
 */
public class RegisterUserActivity extends LiveloPontosActivity implements View.OnClickListener {

    public static final String BUNDLE_USER_DATA = "user_data";
    public static final String BUNDLE_COOKIE_JSESSIONID = "cookie_jsessionid";

    private static final String PHONE_COUNTRY_CODE = "55";
    private static final String PHONE_TYPE = "M";

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
    @Bind(R.id.tv_label_address)
    TextView labelAddress;
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
    @Bind(R.id.register_user_activate)
    AppCompatButton registerUserActivate;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.loading_content)
    RelativeLayout loadingContent;
    @Bind(R.id.register_banner_error)
    BannerError registerBannerError;

    @Bind(R.id.cadeado_nickname)
    ImageView mCadeadoNickname;
    @Bind(R.id.cadeado_email)
    ImageView mCadeadoEmail;
    @Bind(R.id.cadeado_password)
    ImageView mCadeadoPassword;
    @Bind(R.id.cadeado_celphone)
    ImageView mCadeadoCelPhone;

    @Bind(R.id.cadeado_user_sex) ImageView mCadeadoSex;

    private List<Address> addressList;
    private RegisterUserAdapter registerUserAdapter;

    private UserData mUserDate;
    private String mJSessionId;
    public String renewToken;

    private RegisterUserBusiness mRegisterBusiness;

    private int posAddressSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        ButterKnife.bind(this);
        configureActionBar();
        customizeToolbarWithButtonBack();
        setTypefaceInEditText();
        disableEditTexts();

        if (getIntent().getExtras() != null) {
            mUserDate = (UserData) getIntent().getExtras().getSerializable(BUNDLE_USER_DATA);
            mJSessionId = getIntent().getExtras().getString(BUNDLE_COOKIE_JSESSIONID, "");
        }

        initButtonColor();
        configHintFielsRequired();
        configTextColor();

        configTerms();

        mCadeadoNickname.setVisibility(View.GONE);
        mCadeadoSex.setVisibility(View.GONE);
        mCadeadoEmail.setVisibility(View.GONE);
        mCadeadoPassword.setVisibility(View.GONE);
        mCadeadoCelPhone.setVisibility(View.GONE);

        labelAddress.setVisibility(View.VISIBLE);

        registerUserPhoneHome.addTextChangedListener(Mask.insert("(##)#########", registerUserPhoneHome));
        registerUserPhoneWork.addTextChangedListener(Mask.insert("(##)#########", registerUserPhoneWork));
        registerUserCelphone.addTextChangedListener(Mask.insert("(##)#########", registerUserCelphone));

        setupSwitch();
        fillUserData();

        registerUserSex.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                if (parent.getSelectedItem().toString().equalsIgnoreCase("Feminino"))
                    mUserDate.setGender("F");
                else
                    mUserDate.setGender("M");

            }
        });

        mRegisterBusiness = new RegisterUserBusiness(this, getOnServiceRegisterListener());
    }

    private void setupSpinerGender() {
        String[] items = new String[2];

        if (mUserDate.getGender().equalsIgnoreCase("M")) {
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

    private RegisterUserBusiness.OnServiceRegisterListener getOnServiceRegisterListener() {
        return new RegisterUserBusiness.OnServiceRegisterListener() {

            @Override
            public void onRegisterSuccess(String renewToken) {
                loadingContent.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(renewToken)) RegisterUserActivity.this.renewToken = renewToken;

                startActivity(new Intent(RegisterUserActivity.this, ActivateDeviceActivity.class));
                finish();
            }

            @Override
            public void onRegisterFail(final LiveloException exception, String renewToken) {
                loadingContent.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(renewToken)) RegisterUserActivity.this.renewToken = renewToken;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingContent.setVisibility(View.GONE);
                        if (exception.getErrorCode() == LiveloException.EXCEPTION_SERVICE_ERROR) {
                            showBannerErrorRegister();
                        } else {
                            showDialogNoConnection(exception.getAlertToShow(getBaseContext()));
                        }
                    }
                }, 500);
            }
        };
    }

    private void setupSwitch() {
        registerUserSwitchPush.getSwitchCustomSwitch().setChecked(true);
        registerUserSwitchSms.getSwitchCustomSwitch().setChecked(true);
        registerUserSwitchEmail.getSwitchCustomSwitch().setChecked(true);
    }

    private void showBannerErrorRegister() {
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();

        if (isAlive()) {
            dialogCustom.showCustomDialog(this, LiveloPontosApp.getInstance().getErrorGeneric(), false,
                    new DialogCustomAlert.AlertDialogClickListener() {
                        @Override
                        public void onPositiveClick() {
                            callServiceCreateUser();
                        }

                        @Override
                        public void onNegativeClick() {
                            Log.d("DIALOG", "NEGATIVE");
                        }

                        @Override
                        public void onBackPressedInDialog() { }
                    });
        }
    }

    private void showDialogNoConnection(Alert alert) {
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();

        if (isAlive()) {
            dialogCustom.showCustomDialog(RegisterUserActivity.this, alert, true,
                    new DialogCustomAlert.AlertDialogClickListener() {
                        @Override
                        public void onPositiveClick() {
                            callServiceCreateUser();
                        }

                        @Override
                        public void onNegativeClick() { }

                        @Override
                        public void onBackPressedInDialog() { }
                    });
        }
    }


    private void fillUserData() {
        registerUserCpf.setText(mUserDate.getCPF());

        String rg = (TextUtils.isEmpty(mUserDate.getRG())) ? "" : mUserDate.getRG();
        registerUserRg.setText(rg);

        String name = (TextUtils.isEmpty(mUserDate.getFullName())) ? "" : mUserDate.getFullName();
        registerUserName.setText(name);

        String nick = (TextUtils.isEmpty(mUserDate.getNickName())) ? "" : mUserDate.getNickName();
        registerUserNickname.setText(nick);

        setupSpinerGender();

        Timestamp timestamp = new Timestamp(mUserDate.getBirthDate().getTime());
        registerUserBirthDate.setText(DateUtil.getFormatDateToShowJustDate(timestamp));

        String email = (mUserDate.getEmailAddress() != null) ? mUserDate.getEmailAddress() : "";
        registerUserEmail.setText(email);

        loadAddress();
        loadPhones();

        Util.disableEditText(registerUserCpf);
        Util.disableEditText(registerUserRg);
        Util.disableEditText(registerUserName);
        Util.disableEditText(registerUserBirthDate);
    }

    private void initSpinnerSex() {
        String[] items = new String[2];

        if (mUserDate.getGender().equals("F")) {
            items[0] = "Feminino";
            items[1] = "Masculino";
        } else {
            items[0] = "Masculino";
            items[1] = "Feminino";
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row_spn, items);
        adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        registerUserSex.setAdapter(adapter);
    }

    private void loadAddress() {
        addressList = new ArrayList<>();
        /*addressList.add(new Address("Jeremias Albuquerque\n" +
                "Alameda Franca, 1562, \n" +
                "Jardim paulista - São Paulo / SP 04456-000", true));

        addressList.add(new Address("Jeremias Albuquerque\n" +
                "Alameda Franca, 1562, \n" +
                "Jardim paulista - São Paulo / SP 04456-000", false));*/

        if (mUserDate.getAddress() != null && mUserDate.getAddress().size() > 0) {

            for (int i = 0; i < mUserDate.getAddress().size(); i++) {
                boolean addressEnabled = false;

                if (i == 0)
                    addressEnabled = true;


                addressList.add(new Address(mUserDate.getAddress().get(i).getAddressLine1() + mUserDate.getAddress().get(i).getAddressLine2() + ",\n" +
                        mUserDate.getAddress().get(i).getDistrictName() + " - " +
                        mUserDate.getAddress().get(i).getCityName() + " / " +
                        mUserDate.getAddress().get(i).getStateName() + " " +
                        mUserDate.getAddress().get(i).getPostalCode().substring(0, 5) + "-" + mUserDate.getAddress().get(i).getPostalCode().substring(5), addressEnabled));
            }

            registerUserAddressList.setOnItemClickListener(getContentClickListener());
            registerUserAddressList.setExpanded(true);
            registerUserAdapter = new RegisterUserAdapter(getBaseContext(), addressList, R.layout.register_user_address_item_enable, R.color.register_user_title);
            registerUserAddressList.setAdapter(registerUserAdapter);

        }

    }

    private void loadPhones() {
        if (mUserDate.getPhoneList() != null && mUserDate.getPhoneList().size() > 0) {
            for (int i = 0; i < mUserDate.getPhoneList().size(); i++) {
                String phone = mUserDate.getPhoneList().get(i).getPhoneAreaCode() + mUserDate.getPhoneList().get(i).getPhoneNo();
                if (mUserDate.getPhoneList().get(i).getType().equals("Home") && TextUtils.isEmpty(registerUserPhoneHome.getText())) {
                    registerUserPhoneHome.setText(phone);

                } else if (mUserDate.getPhoneList().get(i).getType().equals("Mobile") && TextUtils.isEmpty(registerUserCelphone.getText())) {
                    registerUserCelphone.setText(phone);

                } else if (mUserDate.getPhoneList().get(i).getType().equals("Work") && TextUtils.isEmpty(registerUserPhoneWork.getText())) {
                    registerUserPhoneWork.setText(phone);

                }
            }
        }
    }

    private void configTextColor() {
        registerUserCpf.setTextColor(getResources().getColor(R.color.register_user_title));
        registerUserRg.setTextColor(getResources().getColor(R.color.register_user_title));
        registerUserName.setTextColor(getResources().getColor(R.color.register_user_title));
        registerUserNickname.setTextColor(getResources().getColor(R.color.register_user_title));
        registerUserBirthDate.setTextColor(getResources().getColor(R.color.register_user_title));
        registerUserEmail.setTextColor(getResources().getColor(R.color.register_user_title));
        registerUserConfirmEmail.setTextColor(getResources().getColor(R.color.register_user_title));
        registerUserPhoneHome.setTextColor(getResources().getColor(R.color.register_user_title));
        registerUserPhoneWork.setTextColor(getResources().getColor(R.color.register_user_title));
        registerUserCelphone.setTextColor(getResources().getColor(R.color.register_user_title));
        registerUserPassword.setTextColor(getResources().getColor(R.color.register_user_title));
        registerUserConfirmPassword.setTextColor(getResources().getColor(R.color.register_user_title));
    }

    private void initButtonColor() {
        registerUserActivate.setTextColor(getResources().getColor(R.color.activate_account_button_enable));
        registerUserActivate.setEnabled(true);
        registerUserActivate.setOnClickListener(this);
    }

    private void configHintFielsRequired() {
        registerUserEmail.setHint(getString(R.string.register_hint_email) + "*");
        registerUserConfirmEmail.setHint(getString(R.string.register_hint_confirm_email) + "*");
        registerUserConfirmPassword.setHint(getString(R.string.register_hint_confirm_password) + "*");
        registerUserCelphone.setHint(getString(R.string.register_hint_celphone) + "*");

    }

    private void configTerms() {
        registerUserTermsConditions.setText(Html.fromHtml(getString(R.string.register_terms_and_conditions)));
        registerUserTermsConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogCustomAlert dialogCustom = new DialogCustomAlert();

                if (isAlive()) {
                    dialogCustom.showCustomDialog(RegisterUserActivity.this, LiveloPontosApp.getInstance().getOpenUrl(), true,
                            new DialogCustomAlert.AlertDialogClickListener() {
                                @Override
                                public void onPositiveClick() {
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(Constants.URL_TERM));
                                    startActivity(i);
                                }

                                @Override
                                public void onNegativeClick() { }

                                @Override
                                public void onBackPressedInDialog() { }
                            });
                }
            }
        });
    }


    @Override
    protected void configureActionBar() {
        super.configureActionBar();
        setSupportActionBar(toolbar);
        toolbarTitle.setText(getString(R.string.register_toolbar_title));
    }

    public void customizeToolbarWithButtonBack() {
        if (getSupportActionBar() == null) return;

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setTypefaceInEditText() {
        Typeface MuseoSans300 = Typeface.createFromAsset(getAssets(), "fonts/MuseoSans_300.otf");
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

    private void disableEditTexts() {
        Util.disableEditText(registerUserCpf);
        Util.disableEditText(registerUserRg);
        Util.disableEditText(registerUserName);
        Util.disableEditText(registerUserNationality);
        Util.disableEditText(registerUserBirthDate);
        Util.disableEditText(registerUserPhoneHome);
        Util.disableEditText(registerUserPhoneWork);
    }


    private AdapterView.OnItemClickListener getContentClickListener() {
        return new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (Address address : addressList) {
                    address.setEnable(false);
                }
                addressList.get(position).setEnable(true);
                posAddressSelected = position;

                registerUserAdapter.notifyDataSetChanged();
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.register_user_activate:
                //startActivity(new Intent(RegisterUserActivity.this, RegisterConfirmation.class));
                callServiceCreateUser();
                break;

        }
    }


    private void callServiceCreateUser() {

        String token = !TextUtils.isEmpty(renewToken) ? renewToken : mUserDate.getAuthenticationToken();

        String cpf = StringUtil.removeCaractersCPF(registerUserCpf.getText().toString());
        String fullName = mUserDate.getFullName();
        String email = registerUserEmail.getText().toString();
        String emailConfirm = registerUserConfirmEmail.getText().toString();
        String password = registerUserPassword.getText().toString();
        String passwordConfirm = registerUserConfirmPassword.getText().toString();
        String gender = (mUserDate.getGender().equals("F")) ? "female" : "male";
        String phone = Mask.unmask(registerUserCelphone.getText().toString());
        String nickName = registerUserNickname.getText().toString();
        String dateOfBirth = registerUserBirthDate.getText().toString();

        boolean pushPermission = registerUserSwitchPush.getSwitchCustomSwitch().isChecked();
        boolean smsPermission = registerUserSwitchSms.getSwitchCustomSwitch().isChecked();
        boolean emailPermision = registerUserSwitchEmail.getSwitchCustomSwitch().isChecked();

        String addressId = (mUserDate.getAddress().get(posAddressSelected) != null) ? mUserDate.getAddress().get(posAddressSelected).getId() : "";

        if (isValidateFields()) {

            loadingContent.setVisibility(View.VISIBLE);

            String phoneAreaCode = phone.substring(0, 2);
            String phoneNo = phone.substring(2, phone.length());

            mRegisterBusiness.registerUser(new RegisterUserRequest(mJSessionId,
                    token,
                    cpf,
                    cpf,
                    email,
                    emailConfirm,
                    gender,
                    fullName,
                    nickName,
                    addressId,
                    pushPermission,
                    emailPermision,
                    smsPermission,
                    password,
                    passwordConfirm,
                    PHONE_COUNTRY_CODE,
                    phoneAreaCode,
                    phoneNo,
                    PHONE_TYPE,
                    dateOfBirth));
        }
    }

    private void showBannerTopError(String msg) {
        registerBannerError.setBannerText(msg);
        registerBannerError.setVisibility(View.VISIBLE);
        registerBannerError.showAndAnimBannerError();
    }

    private boolean isValidateFields() {
        clearErrors();

        String email = registerUserEmail.getText().toString();
        String emailConfirm = registerUserConfirmEmail.getText().toString();

        String password = registerUserPassword.getText().toString();
        String passwordConfirm = registerUserConfirmPassword.getText().toString();

        String phone = Mask.unmask(registerUserCelphone.getText().toString());

        String strValidate = "true";
        String msgReturn = "";

        Password passwordCheck = new Password(passwordConfirm, password, "");
        passwordCheck = passwordCheck.checkIfPasswordIsValid(getBaseContext());

        if (!passwordCheck.isValid()) {
            registerUserPassword.setError(getString(R.string.create_password));
            registerUserConfirmPassword.setError(getString(R.string.create_password));
            msgReturn = passwordCheck.getMsg();
            strValidate += "false";
        }

        if (!ValidateUtil.isValidEMail(email)) {
            registerUserEmail.setError(getString(R.string.register_error_field));
            strValidate += "false";
        }

        if (!ValidateUtil.isValidEMail(emailConfirm)) {
            registerUserConfirmEmail.setError(getString(R.string.register_error_field));
            strValidate += "false";
        }

        if (!email.equals(emailConfirm)) {
            registerUserEmail.setError(getString(R.string.register_error_field_email));
            registerUserConfirmEmail.setError(getString(R.string.register_error_field_email));
            strValidate += "false";
        }

        if (TextUtils.isEmpty(password)) {
            registerUserPassword.setError(getString(R.string.register_error_field));
            strValidate += "false";
        }

        if (TextUtils.isEmpty(passwordConfirm)) {
            registerUserConfirmPassword.setError(getString(R.string.register_error_field));
            strValidate += "false";
        }

        if (!password.equals(passwordConfirm)) {
            registerUserPassword.setError(getString(R.string.register_error_field_password));
            registerUserConfirmPassword.setError(getString(R.string.register_error_field_password));
            strValidate += "false";
        }

        if (password.length() < 6) {
            registerUserPassword.setError(getString(R.string.register_error_field));
            strValidate += "false";
        }

        if (passwordConfirm.length() < 6) {
            registerUserConfirmPassword.setError(getString(R.string.register_error_field));
            strValidate += "false";
        }

        if (phone.length() < 10) {
            registerUserCelphone.setError(getString(R.string.register_error_field));
            strValidate += "false";
        }

        if (strValidate.contains("false")) {
            showBannerTopError(!TextUtils.isEmpty(msgReturn) ? msgReturn : getResources().getString(R.string.register_error_required_fields));
            return false;
        }

        return true;
    }

    private void clearErrors() {
        registerUserEmail.setError(null);
        registerUserConfirmEmail.setError(null);

        registerUserPassword.setError(null);
        registerUserConfirmPassword.setError(null);

        registerUserCelphone.setError(null);
    }
}
