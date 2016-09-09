package br.com.pontomobi.livelopontos.ui.dialog;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatButton;
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

import br.com.pontomobi.livelopontos.Constants;
import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.ui.home.Issue;
import br.com.pontomobi.livelopontos.util.Util;
import butterknife.Bind;
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

    @Bind(R.id.cart_description_product)
    TextView descriptionProduct;
    @Bind(R.id.cart_confirm)
    AppCompatButton brnConfirm;
    @Bind(R.id.cart_continue)
    AppCompatButton btnContinue;
    @Bind(R.id.loading_content)
    RelativeLayout loading;

    private Dialog dialog;

    private String nameProduct;
    private OnDialogListener onDialogListener;
    private String PROJECT_NAME = "AUTOMATION_LIVELO";

    public static DialogCartConfirmation newInstance(String product) {
        DialogCartConfirmation d = new DialogCartConfirmation();

        Bundle args = new Bundle();
        args.putString(PRODUCT_BUNDLE, product);
        d.setArguments(args);

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

    @Bind(R.id.dr_img_dialog)
    ImageView imagem;

    @Bind(R.id.listBugs)
    ListView listBugs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_cart_confirmation, container, false);
        ButterKnife.bind(this, v);

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

//        //http://10.150.6.233:19120/Services1.0/services/tmplanning?wsdl
//        String url = "http://10.150.6.233:19120/Services1.0/services/";
//
//        sccService = new SystemServiceServiceLocator().getsccsystem(new URL(url + "sccsystem"));
//        planningService = new PlanningServiceServiceLocator().gettmplanning(new URL(url + "tmplanning"));
//        mainEntities = (MainEntities) (new MainEntitiesServiceLocator()).getsccentities(new URL(url + "sccentities"));
//        executionService = new ExecutionWebServiceServiceLocator().gettmexecution(new URL(url + "tmexecution"));
//
//        ((SccsystemSoapBindingStub) sccService).setTimeout(10000);
//
//        //LOGIN
//        sessionId = sccService.logonUser("admin", "admin");
//
//        //OBTEM OS PROJETOS
//        projects = mainEntities.getAllProjects(sessionId, PROJECT_NAME);
//
//        //DEFINE O PROJETO QUE IREMOS TRABALHAR
//        executionService.setCurrentProject(sessionId, projects[0].getId());
//
//        //OBTEM O NÓ RAIZ DO PROJETO
//        ExecutionNode root = executionService.getExecutionRootNode(sessionId, projects[0].getId());
//
//        //OBTEM OS CONTAINERS DO TESTE
//        NamedEntity testContainer = planningService.getTestContainers(sessionId, projects[0].getId())[0];
//
//
////		int nodeId = executionService.addNode(sessionId, getNewFolder("Folder1", "first folder"), root.getId());
////
////		 executionService.addNode(sessionId, getNewExecutionNode("ExecDef1.1", "child execDef", BUILD, VERSION,
////		           testContainer.getId()), nodeId);
////		 nodeId = executionService.addNode(sessionId, getNewExecutionNode("ExecDef1", "first execDef", BUILD, VERSION,
////		           testContainer.getId()), root.getId());
////
////		 assignTestDefs(nodeId);
//
//
//        Log.e("SESSIONID LOGIN", String.valueOf(sessionId));


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
        //con.setRequestProperty("Host", "10.150.197.3");
        //con.setRequestProperty("Host", "jira.pontoslivelo.com.br");
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
        dialog = new Dialog(getActivity(), R.style.LiveloPontosTheme_DialogConfirmation);

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
