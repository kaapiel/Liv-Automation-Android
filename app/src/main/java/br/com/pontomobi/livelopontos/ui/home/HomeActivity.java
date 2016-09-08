package br.com.pontomobi.livelopontos.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.pontomobi.livelopontos.Constants;
import br.com.pontomobi.livelopontos.LiveloPontosActivity;
import br.com.pontomobi.livelopontos.LiveloPontosApp;
import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.listener.OnChangeFragmentListener;
import br.com.pontomobi.livelopontos.model.Alert;
import br.com.pontomobi.livelopontos.service.livelo.LiveloException;
import br.com.pontomobi.livelopontos.service.livelo.brandingbank.model.PartnerPartyEnrollment;
import br.com.pontomobi.livelopontos.service.livelo.userprofile.model.ProfileResponse;
import br.com.pontomobi.livelopontos.service.livelo.userprofile.model.UserProfileResponse;
import br.com.pontomobi.livelopontos.ui.activateDevice.ActivateDeviceBusiness;
import br.com.pontomobi.livelopontos.ui.cart.CartActivity;
import br.com.pontomobi.livelopontos.ui.dialog.DialogCustomAlert;
import br.com.pontomobi.livelopontos.ui.login.LoginActivity;
import br.com.pontomobi.livelopontos.ui.myInfo.MyInfoBusiness;
import br.com.pontomobi.livelopontos.ui.widget.BannerError;
import br.com.pontomobi.livelopontos.util.CustomTypefaceSpan;
import br.com.pontomobi.livelopontos.util.LoginUtil;
import br.com.pontomobi.livelopontos.util.gcm.GCMUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends LiveloPontosActivity implements OnChangeFragmentListener, OnChartGestureListener {

    public static final String GO_TO_CATALOG = "go_to_catalog";

    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    //@Bind(R.id.menu_header_name_user)
    TextView menuUserName;
    TextView menuHellow;
    ImageView logoBrandingBank;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.main_content)
    FrameLayout mainContent;
    @Bind(R.id.content_frame)
    FrameLayout contentFrame;
    @Bind(R.id.navigationview_home)
    NavigationView navigationviewHome;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.loading_content)
    RelativeLayout loadingContent;

    @Bind(R.id.cart_menu)
    LinearLayout cartMenu;
    @Bind(R.id.cart_menu_items)
    TextView cartMenuProducts;
    @Bind(R.id.cart_menu_image)
    ImageView cartMenuIcon;
    @Bind(R.id.banner_error)
    BannerError bannerError;

    private ActionBarDrawerToggle mDrawerToggle;
    private MyInfoBusiness myInfoBusiness;
    private boolean loadInfoFromCache = false;

    private ActivateDeviceBusiness mActivateDeviceBusiness;

    private int menuIdCurrent = -1;
    private boolean gotToCatalog;
    private BarChart mChart;
    private String lastIndex;
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("execucoes");
    private String pacote_regressivo, qtdTestesOK_regressivo, qtdTestesNOK_regressivo,
            pacote_smoke, qtdTestesOK_smoke, qtdTestesNOK_smoke,
            pacote_regressivoneg, qtdTestesOK_regressivoneg, qtdTestesNOK_regressivoneg,
            pacote_bvt, qtdTestesOK_bvt, qtdTestesNOK_bvt,
            pacote_servicos, qtdTestesOK_servicos, qtdTestesNOK_servicos,
            pacote_performance, qtdTestesOK_performance, qtdTestesNOK_performance;

    private ArrayList<Teste> testes_regressivo, testes_smoke, testes_bvt, testes_regressivoneg, testes_servicos, testes_performance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //checkIfUserLoged();
        ButterKnife.bind(this);

        loadingContent.setVisibility(View.VISIBLE);

        initViews();
        menuItemFonts();

        View drawerHeader = navigationviewHome.inflateHeaderView(R.layout.menu_header);
        menuUserName = (TextView) drawerHeader.findViewById(R.id.menu_header_name_user);
        menuHellow = (TextView) drawerHeader.findViewById(R.id.menu_header_helow);
        navigationviewHome.inflateMenu(R.menu.itens_menu_logged);

        //myInfoBusiness = new MyInfoBusiness(getBaseContext(), getOnMyInfoBusinessListener());
        //mActivateDeviceBusiness = new ActivateDeviceBusiness(getBaseContext(), getOnActiveDevice());

//        if(getIntent().getExtras() != null) {
//            gotToCatalog = getIntent().getExtras().getBoolean(GO_TO_CATALOG, false);
//        }
//
//        loadingContent.setVisibility(View.VISIBLE);
//
//        if (LiveloPontosApp.getInstance().getProfileResponse() == null) {
//            loadInfoFromCache();
//        }

        //callServiceUserProfile();

    }

    private void loadInfoFromCache() {
        ProfileResponse profile = LiveloPontosApp.getInstance().loadInfoUserFromCache();

        if (profile == null) {
            loadInfoFromCache = false;
            loadingContent.setVisibility(View.VISIBLE);
        } else {
            loadInfoFromCache = true;
            LiveloPontosApp.getInstance().setProfileResponse(profile);
            setNickName();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        GCMUtil.checkPlayServices(this);
    }

    @OnClick(R.id.cart_menu)
    public void openCart() {
        startActivity(new Intent(this, CartActivity.class));
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            if (menuIdCurrent == R.id.menu_product_catalog) {
                replaceFragment(getSupportFragmentManager(), new HomeFragment(), "home_fragment", false, null);
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void checkIfUserLoged() {
        if (LoginUtil.loadInfoAboutLogin(getBaseContext()) == null) {
            finish();
        }
    }

    private void configureMenuNavigationView() {
        int menuRes;
        if (LiveloPontosApp.getInstance().getProfileResponse() == null) {
            menuRes = R.menu.itens_menu;
        } else {
            menuRes = R.menu.itens_menu_logged;
        }
        navigationviewHome.inflateMenu(menuRes);
    }

    private void callServiceUserProfile() {
        myInfoBusiness.callServiceGetUserProfile();
    }

    private void initViews() {
        navigationviewHome.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                changeFragment(menuItem.getItemId());
                drawerLayout.closeDrawers();
                return false;
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open_desc, R.string.drawer_close_desc) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mDrawerToggle.syncState();
                closeKeyboard();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                mDrawerToggle.syncState();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(false);

        toolbar.setNavigationIcon(R.drawable.ic_menu);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        drawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void menuItemFonts() {
        Menu m = navigationviewHome.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/MuseoSans_500.otf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());

//        if (mi.getTitle().equals(getBaseContext().getResources().getString(R.string.menu_item_my_account))) {
//            mNewTitle.setSpan(new TextAppearanceSpan(getBaseContext(), R.style.LiveloPontosTheme_NavigationView_TextAppearance_MyAccount), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//        }

        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    private float pxFromDp() {
        return 19 * getBaseContext().getResources().getDisplayMetrics().density;
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void setFragmentOnMenu(int menuId) {
        changeFragment(menuId);
    }

    @Override
    public void setMenuSelected(int menuId) {
        menuIdCurrent = menuId;

        switch (menuId) {

            case R.id.menu_relatorioGeral:
                customizeToolbarBlue();
                customizeToolbar(getString(R.string.app_name_upper));
                break;


            case R.id.menu_home:
                customizeToolbarBlue();
                customizeToolbar(getString(R.string.app_name_upper));
                break;

            case R.id.menu_my_account:
                customizeToolbarDrawable();
                customizeToolbar(getString(R.string.my_account_title));
                break;

            case R.id.menu_product_catalog:
                customizeToolbarBlue();
                customizeToolbar(getString(R.string.product_catalog_title));
                break;

            case R.id.menu_qr_code:
                customizeToolbarDrawable();
                customizeToolbar(getString(R.string.qr_code_title));
                break;

            case R.id.menu_rescue_code:
                customizeToolbarDrawable();
                customizeToolbar(getString(R.string.token_title));
                break;

            case R.id.menu_contact_us:
                customizeToolbarDrawable();
                customizeToolbar(getString(R.string.contact_use_title));
                break;

            case R.id.menu_servicos:
                customizeToolbarBlue();
                customizeToolbar("SERVIÇOS");
                break;
        }
    }

    private void changeFragment(int menuId) {

        //if (menuIdCurrent == menuId) return;

        final Bundle bGeral = new Bundle();
        bGeral.putString("percentual_regressivo", getPackagePercentual(qtdTestesNOK_regressivo, qtdTestesOK_regressivo));
        bGeral.putString("percentual_regressivoneg", getPackagePercentual(qtdTestesNOK_regressivoneg, qtdTestesOK_regressivoneg));
        bGeral.putString("percentual_smoke", getPackagePercentual(qtdTestesNOK_smoke, qtdTestesOK_smoke));
        bGeral.putString("percentual_bvt", getPackagePercentual(qtdTestesNOK_bvt, qtdTestesOK_bvt));
        bGeral.putString("percentual_servicos", getPackagePercentual(qtdTestesNOK_servicos, qtdTestesOK_servicos));
        bGeral.putString("percentual_performance", getPackagePercentual(qtdTestesNOK_performance, qtdTestesOK_performance));

        final Bundle bRegressivo = new Bundle();
        bRegressivo.putString("qtdTestesOK_regressivo", qtdTestesOK_regressivo);
        bRegressivo.putString("qtdTestesNOK_regressivo", qtdTestesNOK_regressivo);
        bRegressivo.putString("pacote_regressivo", pacote_regressivo);
        bRegressivo.putParcelableArrayList("testes_regressivo", testes_regressivo);

        final Bundle bRegressivoNeg = new Bundle();
        bRegressivoNeg.putString("qtdTestesOK_regressivoneg", qtdTestesOK_regressivoneg);
        bRegressivoNeg.putString("qtdTestesNOK_regressivoneg", qtdTestesNOK_regressivoneg);
        bRegressivoNeg.putString("pacote_regressivoneg", pacote_regressivoneg);
        bRegressivoNeg.putParcelableArrayList("testes_regressivoneg", testes_regressivoneg);

        final Bundle bBvt = new Bundle();
        bBvt.putString("qtdTestesOK_bvt", qtdTestesOK_bvt);
        bBvt.putString("qtdTestesNOK_bvt", qtdTestesNOK_bvt);
        bBvt.putString("pacote_bvt", pacote_bvt);
        bBvt.putParcelableArrayList("testes_bvt", testes_bvt);

        final Bundle bSmoke = new Bundle();
        bSmoke.putString("qtdTestesOK_smoke", qtdTestesOK_smoke);
        bSmoke.putString("qtdTestesNOK_smoke", qtdTestesNOK_smoke);
        bSmoke.putString("pacote_smoke", pacote_smoke);
        bSmoke.putParcelableArrayList("testes_smoke", testes_smoke);

        final Bundle bServicos = new Bundle();
        bServicos.putString("qtdTestesOK_servicos", qtdTestesOK_servicos);
        bServicos.putString("qtdTestesNOK_servicos", qtdTestesNOK_servicos);
        bServicos.putString("pacote_servicos", pacote_servicos);
        bServicos.putParcelableArrayList("testes_servicos", testes_servicos);

        final Bundle bPerformance = new Bundle();
        bPerformance.putString("qtdTestesOK_performance", qtdTestesOK_performance);
        bPerformance.putString("qtdTestesNOK_performance", qtdTestesNOK_performance);
        bPerformance.putString("pacote_performance", pacote_performance);
        bPerformance.putParcelableArrayList("testes_performance", testes_performance);

        menuIdCurrent = menuId;

        switch (menuId) {

            case R.id.menu_relatorioGeral:
                replaceFragment(getSupportFragmentManager(), new HomeFragment(), "relatório_geral", false, bGeral);
                break;

            case R.id.menu_home:
                replaceFragment(getSupportFragmentManager(), new ExecucaoTestesFragment(), "executar_testes", false, new Bundle());
                break;

            case R.id.menu_my_account:
                replaceFragment(getSupportFragmentManager(), new PerformanceFragment(), "performance_fragment", false, bPerformance);
                break;

            case R.id.menu_product_catalog:
                replaceFragment(getSupportFragmentManager(), new SuiteFragment(), "regressivo_fragment", false, bRegressivo);
                break;

            case R.id.menu_qr_code:
                replaceFragment(getSupportFragmentManager(), new SuiteFragment(), "regressivo_neg_fragment", false, bRegressivoNeg);
                break;

            case R.id.menu_rescue_code:
                replaceFragment(getSupportFragmentManager(), new SuiteFragment(), "bvt_fragment", false, bBvt);
                break;

            case R.id.menu_contact_us:
                replaceFragment(getSupportFragmentManager(), new SuiteFragment(), "smoke_fragment", false, bSmoke);
                break;

            case R.id.menu_servicos:
                replaceFragment(getSupportFragmentManager(), new SuiteFragment(), "servicos_fragment", false, bServicos);
                break;

            case R.id.menu_exit:
                menuIdCurrent = R.id.menu_home;
                showDialogLogout();
                return;

            default:
                return;
        }

    }

    private String getPackagePercentual(String negatives, String positives) {

        Float intNegative = Float.valueOf(negatives);
        Float intPositive = Float.valueOf(positives);
        float total = intNegative + intPositive;
        float negDivTot = intPositive / total;
        float percent = negDivTot * 100;
        return String.valueOf(percent); //percentual dos testes OK;
    }

    private void showDialogLogout() {
        Alert alert = new Alert();
        alert.setDescription(getString(R.string.logout_descrition));
        alert.setPositiveButton("Sair");
        alert.setNegativeButton("Cancelar");
        alert.setImage(getResources().getResourceEntryName(R.drawable.erro_generico));
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();

        if (isAlive()) {
            dialogCustom.showCustomDialog(HomeActivity.this, alert, false,
                    new DialogCustomAlert.AlertDialogClickListener() {
                        @Override
                        public void onPositiveClick() {
                            LiveloPontosApp.getInstance().sendTrackerEvent(
                                    Constants.GoogleAnalytisEvents.EVENT_SCREEN_ANY,
                                    Constants.GoogleAnalytisEvents.EVENT_CATEGORY_MENU,
                                    Constants.GoogleAnalytisEvents.EVENT_ACTION_EXIT,
                                    ""
                            );

                            LoginUtil.clearLogin(getBaseContext());
                            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onNegativeClick() {
                        }

                        @Override
                        public void onBackPressedInDialog() {
                            onBackPressed();
                        }
                    });
        }

    }

    private void customizeToolbar(String title) {
        toolbarTitle.setText(title);
    }

    private void customizeToolbarBlue() {
        toolbar.setBackgroundColor(getResources().getColor(R.color.my_points_toolbar_bg));
    }

    private void customizeToolbarDrawable() {
        toolbar.setBackgroundResource(R.drawable.bg_toolbar_1);
    }

    private void customizeToolbarBlueAlpha() {
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar_bg_alpha));
    }

    public void replaceFragment(FragmentManager fm, Fragment fragment, String label, boolean toBack, Bundle b) {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);

        fragment.setArguments(b);
        transaction.replace(R.id.content_frame, fragment, label);

        if (toBack) {
            transaction.addToBackStack(label);
        }

        transaction.commitAllowingStateLoss();
    }

    private OnMyInfoBusinessListener getOnMyInfoBusinessListener() {
        OnMyInfoBusinessListener onMyInfoBusinessListener = new OnMyInfoBusinessListener() {
            @Override
            public void onMyInfoBusinessSuccess(ProfileResponse userProfileResponse) {
                setNickName();
                checkActiveDevice();
            }

            @Override
            public void onMyInfoBusinessFail(LiveloException exception) {
                getUserProfileFail(exception);
            }
        };

        return onMyInfoBusinessListener;
    }

    private void getUserProfileFail(final LiveloException exception) {
        loadingContent.setVisibility(View.GONE);
        if (loadInfoFromCache) {
            checkActiveDevice();
            return;
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingContent.setVisibility(View.GONE);
                if (exception.getErrorCode() == LiveloException.EXCEPTION_REFRESH_TOKEN_ERROR) {
                    sessionExpired();
                } else {
                    showDialogNoConnection(exception.getAlertToShow(getBaseContext()));
                }
            }
        }, 500);
    }


    private void sessionExpired() {
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();

        if (isAlive()) {
            dialogCustom.showCustomDialog(HomeActivity.this, LiveloPontosApp.getInstance().getExpiredSession(), false,
                    new DialogCustomAlert.AlertDialogClickListener() {
                        @Override
                        public void onPositiveClick() {
                            LoginUtil.clearLogin(getBaseContext());
                            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onNegativeClick() {
                            Log.d("DIALOG", "NEGATIVE");
                        }

                        @Override
                        public void onBackPressedInDialog() {

                        }
                    });
        }
    }

    private void showDialogNoConnection(Alert alert) {
        final DialogCustomAlert dialogCustom = new DialogCustomAlert();

        if (isAlive()) {
            dialogCustom.showCustomDialog(HomeActivity.this, alert, false,
                    new DialogCustomAlert.AlertDialogClickListener() {
                        @Override
                        public void onPositiveClick() {
                            Log.d("DIALOG", "POSITIVE");
                            callServiceUserProfile();
                        }

                        @Override
                        public void onNegativeClick() {
                            Log.d("DIALOG", "NEGATIVE");
                        }

                        @Override
                        public void onBackPressedInDialog() {
                            callActivityLogin();
                        }
                    });
        }
    }

    public void showBannerErro(String msg) {
        bannerError.setBannerText(msg);
        bannerError.setVisibility(View.VISIBLE);
        bannerError.showAndAnimBannerError();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                bannerError.hideAndAnimBannerError();
            }
        }, 3000);
    }

    private void setNickName() {
        if (LiveloPontosApp.getInstance().getProfileResponse() == null) {
            return;
        }

        UserProfileResponse userProfileResponse = LiveloPontosApp.getInstance().getProfileResponse().getUserProfileResponse();

        if (!TextUtils.isEmpty(userProfileResponse.getNickName())) {
            menuUserName.setText(userProfileResponse.getNickName());
        } else {
            menuUserName.setText(userProfileResponse.getFirstName());
        }
        menuHellow.setVisibility(View.VISIBLE);
        menuHellow.setText(getResources().getString(R.string.menu_header_greet));
    }

    private void callActivityLogin() {
        LoginUtil.clearLogin(getBaseContext());
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void checkActiveDevice() {
        UserProfileResponse userProfileResponse = LiveloPontosApp.getInstance().getProfileResponse().getUserProfileResponse();
        mActivateDeviceBusiness.retrieveDevices(userProfileResponse.getCpf());
    }

    private ActivateDeviceBusiness.OnActiveDevice getOnActiveDevice() {
        return new ActivateDeviceBusiness.OnActiveDevice() {
            @Override
            public void onActiveSuccess(boolean hasActiveDevice) {
                Log.i("OnActiveDevice", "onActiveSuccess: hasActiveDevice - " + hasActiveDevice);
                if (!hasActiveDevice) {
                    callActivityLogin();
                }
            }

            @Override
            public void onActiveFail(LiveloException exception) {
                Log.e("OnActiveDevice", "onActiveFail: " + exception.getMessage());
            }
        };
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    public interface OnMyInfoBusinessListener {
        void onMyInfoBusinessSuccess(ProfileResponse userProfileResponse);

        void onMyInfoBusinessFail(LiveloException exception);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //REGRESSIVO---------------------------------------------------------------------------------

        myRef.orderByChild("pacote").equalTo("Regressivo").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot execucao : dataSnapshot.getChildren()) {
                    lastIndex = execucao.getKey();
                }

                Log.e("LAST INDEX OF REG", lastIndex);

                qtdTestesOK_regressivo = (String) dataSnapshot.child(lastIndex).child("qtdTestesOK").getValue();
                qtdTestesNOK_regressivo = (String) dataSnapshot.child(lastIndex).child("qtdTestesNOK").getValue();
                pacote_regressivo = (String) dataSnapshot.child(lastIndex).child("pacote").getValue();

                testes_regressivo = new ArrayList<>();
                for (DataSnapshot teste : dataSnapshot.child(lastIndex).child("testes").getChildren()) {
                    Teste t = new Teste();
                    t.setHoraFim(teste.child("horaFim").getValue().toString());
                    t.setHoraInicio(teste.child("horaInicio").getValue().toString());
                    t.setMensagem(teste.child("mensagem").getValue().toString());
                    t.setNome(teste.child("nome").getValue().toString());
                    t.setResultado(teste.child("resultado").getValue().toString());
                    t.setStatus(teste.child("status").getValue().toString());
                    testes_regressivo.add(t);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        //REGRESSIVO NEGATIVO-----------------------------------------------------------------------------------

        myRef.orderByChild("pacote").equalTo("Regressivo Negativo").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot execucao : dataSnapshot.getChildren()) {
                    lastIndex = execucao.getKey();
                }

                Log.e("LAST INDEX OF REG NEG", lastIndex);

                qtdTestesOK_regressivoneg = (String) dataSnapshot.child(lastIndex).child("qtdTestesOK").getValue();
                qtdTestesNOK_regressivoneg = (String) dataSnapshot.child(lastIndex).child("qtdTestesNOK").getValue();
                pacote_regressivoneg = (String) dataSnapshot.child(lastIndex).child("pacote").getValue();

                testes_regressivoneg = new ArrayList<>();
                for (DataSnapshot teste : dataSnapshot.child(lastIndex).child("testes").getChildren()) {
                    Teste t = new Teste();
                    t.setHoraFim(teste.child("horaFim").getValue().toString());
                    t.setHoraInicio(teste.child("horaInicio").getValue().toString());
                    t.setMensagem(teste.child("mensagem").getValue().toString());
                    t.setNome(teste.child("nome").getValue().toString());
                    t.setResultado(teste.child("resultado").getValue().toString());
                    t.setStatus(teste.child("status").getValue().toString());
                    testes_regressivoneg.add(t);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        //SMOKE-----------------------------------------------------------------------------------

        myRef.orderByChild("pacote").equalTo("Smoke").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot execucao : dataSnapshot.getChildren()) {
                    lastIndex = execucao.getKey();
                }

                Log.e("LAST INDEX OF SMK", lastIndex);

                qtdTestesOK_smoke = (String) dataSnapshot.child(lastIndex).child("qtdTestesOK").getValue();
                qtdTestesNOK_smoke = (String) dataSnapshot.child(lastIndex).child("qtdTestesNOK").getValue();
                pacote_smoke = (String) dataSnapshot.child(lastIndex).child("pacote").getValue();

                testes_smoke = new ArrayList<>();
                for (DataSnapshot teste : dataSnapshot.child(lastIndex).child("testes").getChildren()) {
                    Teste t = new Teste();
                    t.setHoraFim(teste.child("horaFim").getValue().toString());
                    t.setHoraInicio(teste.child("horaInicio").getValue().toString());
                    t.setMensagem(teste.child("mensagem").getValue().toString());
                    t.setNome(teste.child("nome").getValue().toString());
                    t.setResultado(teste.child("resultado").getValue().toString());
                    t.setStatus(teste.child("status").getValue().toString());
                    testes_smoke.add(t);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        //BVT-----------------------------------------------------------------------------------

        myRef.orderByChild("pacote").equalTo("BVT").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot execucao : dataSnapshot.getChildren()) {
                    lastIndex = execucao.getKey();
                }

                Log.e("LAST INDEX OF BVT", lastIndex);

                qtdTestesOK_bvt = (String) dataSnapshot.child(lastIndex).child("qtdTestesOK").getValue();
                qtdTestesNOK_bvt = (String) dataSnapshot.child(lastIndex).child("qtdTestesNOK").getValue();
                pacote_bvt = (String) dataSnapshot.child(lastIndex).child("pacote").getValue();

                testes_bvt = new ArrayList<>();
                for (DataSnapshot teste : dataSnapshot.child(lastIndex).child("testes").getChildren()) {
                    Teste t = new Teste();
                    t.setHoraFim(teste.child("horaFim").getValue().toString());
                    t.setHoraInicio(teste.child("horaInicio").getValue().toString());
                    t.setMensagem(teste.child("mensagem").getValue().toString());
                    t.setNome(teste.child("nome").getValue().toString());
                    t.setResultado(teste.child("resultado").getValue().toString());
                    t.setStatus(teste.child("status").getValue().toString());
                    testes_bvt.add(t);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        //SERVICOS-----------------------------------------------------------------------------------

        myRef.orderByChild("pacote").equalTo("Servicos").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot execucao : dataSnapshot.getChildren()) {
                    lastIndex = execucao.getKey();
                }

                Log.e("LAST INDEX OF SVC", lastIndex);

                qtdTestesOK_servicos = (String) dataSnapshot.child(lastIndex).child("qtdTestesOK").getValue();
                qtdTestesNOK_servicos = (String) dataSnapshot.child(lastIndex).child("qtdTestesNOK").getValue();
                pacote_servicos = (String) dataSnapshot.child(lastIndex).child("pacote").getValue();

                testes_servicos = new ArrayList<>();
                for (DataSnapshot teste : dataSnapshot.child(lastIndex).child("testes").getChildren()) {
                    Teste t = new Teste();
                    t.setHoraFim(teste.child("horaFim").getValue().toString());
                    t.setHoraInicio(teste.child("horaInicio").getValue().toString());
                    t.setMensagem(teste.child("mensagem").getValue().toString());
                    t.setNome(teste.child("nome").getValue().toString());
                    t.setResultado(teste.child("resultado").getValue().toString());
                    t.setStatus(teste.child("status").getValue().toString());
                    testes_servicos.add(t);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        //PERFORMANCE-----------------------------------------------------------------------------------

        myRef.orderByChild("pacote").equalTo("Performance").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot execucao : dataSnapshot.getChildren()) {
                    lastIndex = execucao.getKey();
                }

                Log.e("LAST INDEX OF PFC", lastIndex);

                qtdTestesOK_performance = (String) dataSnapshot.child(lastIndex).child("qtdTestesOK").getValue();
                qtdTestesNOK_performance = (String) dataSnapshot.child(lastIndex).child("qtdTestesNOK").getValue();
                pacote_performance = (String) dataSnapshot.child(lastIndex).child("pacote").getValue();

                testes_performance = new ArrayList<>();
                for (DataSnapshot teste : dataSnapshot.child(lastIndex).child("testes").getChildren()) {
                    Teste t = new Teste();
                    t.setHoraFim(teste.child("horaFim").getValue().toString());
                    t.setHoraInicio(teste.child("horaInicio").getValue().toString());
                    t.setMensagem(teste.child("mensagem").getValue().toString());
                    t.setNome(teste.child("nome").getValue().toString());
                    t.setResultado(teste.child("resultado").getValue().toString());
                    t.setStatus(teste.child("status").getValue().toString());
                    testes_performance.add(t);
                }

                loadingContent.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }
}
