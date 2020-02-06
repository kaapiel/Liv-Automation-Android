package br.com.aguido.livautomation.ui.dialog;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import br.com.aguido.livautomation.Constants;
import br.com.aguido.livautomation.R;
import br.com.aguido.livautomation.model.ExecutionNode;
import br.com.aguido.livautomation.model.NamedEntity;
import br.com.aguido.livautomation.service.silk.getAllProjects;
import br.com.aguido.livautomation.service.silk.getExecutionRootNode;
import br.com.aguido.livautomation.service.silk.getTestContainers;
import br.com.aguido.livautomation.service.silk.logonUser;
import br.com.aguido.livautomation.service.silk.startExecutionTMPLANNING;
import br.com.aguido.livautomation.ui.home.Issue;
import br.com.aguido.livautomation.util.Util;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by vilmar.filho on 5/17/16.
 */
public class DialogCartConfirmation extends DialogFragment {

    private final static String PRODUCT_BUNDLE = "product_bundle";
    private ArrayList<Issue> issuesFromJIRA = new ArrayList<>();
    private TextView textLoading;

    @BindView(R.id.cart_description_product)
    TextView descriptionProduct;
    @BindView(R.id.cart_confirm)
    AppCompatButton brnConfirm;
    @BindView(R.id.cart_continue)
    AppCompatButton btnContinue;
    @BindView(R.id.loading_content)
    RelativeLayout loading;

    private Dialog dialog;

    private String nameProduct;
    private OnDialogListener onDialogListener;

    private String PROJECT_NAME = "Demo Project";
    private long sessionId;
    private Integer projectId;
    private ExecutionNode rootNode;
    private NamedEntity testContainer;
    private static String host;

    public static DialogCartConfirmation newInstance(String maquina) {
        DialogCartConfirmation d = new DialogCartConfirmation();

        Bundle args = new Bundle();
        args.putString(PRODUCT_BUNDLE, maquina);
        d.setArguments(args);
        host = maquina;

        return d;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nameProduct = getArguments().getString(PRODUCT_BUNDLE, "");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fillDetails();

    }

    @BindView(R.id.dr_img_dialog)
    ImageView imagem;

    @BindView(R.id.listBugs)
    ListView listBugs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_cart_confirmation, container, false);
        ButterKnife.bind(this, v);

        brnConfirm.setEnabled(false);

        textLoading = (TextView) loading.findViewById(R.id.loading_progress_text);

        loading.setVisibility(View.VISIBLE);
        textLoading.setText("Obtendo bugs...");

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    issuesFromJIRA = getIssuesFromJIRA();

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loading.setVisibility(View.GONE);
                            brnConfirm.setEnabled(true);
                            ArrayAdapter<Issue> adapter = new ArrayAdapter<>(getContext(), R.layout.item_issues, issuesFromJIRA);
                            listBugs.setAdapter(adapter);
                        }
                    });

                } catch (IOException e) {
                    Log.e("ERROR: ", e.getMessage());
                }
            }
        });
        t.start();

        return v;
    }

    @OnClick(R.id.cart_confirm)
    public void onConfirmButtonClick() {

        //EXECUTAR TESTES NO SILK

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    sessionId = new logonUser().executar();
                    projectId = new getAllProjects().executar(sessionId, PROJECT_NAME);
                    rootNode = new getExecutionRootNode().executar(sessionId, projectId);
                    testContainer = new getTestContainers().executar(sessionId, projectId);
                    new startExecutionTMPLANNING().executar(sessionId, rootNode.getId(), "VERSION", "BUILD", host.split(" ")[1], "PORT");

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        t.start();

    }

    private ArrayList<Issue> getIssuesFromJIRA() throws IOException {
        return Util.parseIssues(getJsonIssues());
    }

    private String getJsonIssues() throws IOException {

        byte[] bytes = "gabriel.fraga:gabriel123".getBytes();
        String auth = "Basic " + Base64.encodeToString(bytes, Base64.DEFAULT);

        URL obj = new URL(Constants.JiraParser.JIRA_GET3);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        trustAllHosts();
        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", auth);
        con.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        //con.setRequestProperty("Host", "192.168.0.1");
        //con.setRequestProperty("Host", "jira.com.br");
        con.setHostnameVerifier(DO_NOT_VERIFY);

        con.setDoOutput(true);

        DataOutputStream wr = new DataOutputStream(
                con.getOutputStream());

        wr.writeBytes("{\"jql\":\"project=Livelo\",\"startAt\":2,\"maxResults\":5,\"fields\":[\"key\",\"summary\",\"issuetype\",\"created\",\"priority\",\"status\",\"customfield_10015\",\"components\",\"resolution\",\"reporter\",\"assignee\"]}");

        if (con.getResponseCode() != 200) {

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            Log.e("RESPONSE == " + con.getResponseCode(), response.toString());

        } else {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            Log.e("RESPONSE == 200", response.toString());

            return response.toString();

        }
        return null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = new Dialog(getActivity(), R.style.LivautomationTheme_DialogConfirmation);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setCancelable(false);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        return dialog;
    }

    private void fillDetails() {
        descriptionProduct.setText(nameProduct);
    }

    @OnClick(R.id.cart_confirm)
    public void cartFinish() {
        dialog.dismiss();

        if (onDialogListener != null)
            onDialogListener.onFinishCart();
    }

    @OnClick(R.id.cart_continue)
    public void cartContinue() {
        dialog.dismiss();

        if (onDialogListener != null)
            onDialogListener.onContinue();
    }

    public void setOnDialogListener(OnDialogListener onDialogListener) {
        this.onDialogListener = onDialogListener;
    }


    public interface OnDialogListener {
        void onFinishCart();

        void onContinue();
    }

    @OnItemClick(R.id.listBugs)
    public void onItemClickIssue(AdapterView<?> adapter, int position) {

        Issue issueSelecionado = (Issue) adapter.getItemAtPosition(position);
        DialogConfirmationCheckout dcc = new DialogConfirmationCheckout().newInstance(issueSelecionado);
        dcc.show(getFragmentManager(), "TESTE");

    }

    private static void trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }

            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }
        }};

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

}
