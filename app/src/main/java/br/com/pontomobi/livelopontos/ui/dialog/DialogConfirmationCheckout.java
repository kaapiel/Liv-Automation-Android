package br.com.pontomobi.livelopontos.ui.dialog;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import br.com.pontomobi.livelopontos.R;
import br.com.pontomobi.livelopontos.ui.home.Issue;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vilmar.filho on 5/17/16.
 */
public class DialogConfirmationCheckout extends DialogFragment {


    private Dialog dialog;

    private OnDialogListener onDialogListener;
    private final static String ISSUE_BUNDLE = "issue_bundle";
    private Issue issue;

    @Bind(R.id.tv_summary)
    TextView summary;

    @Bind(R.id.tv_key)
    TextView key;

    @Bind(R.id.tv_priority)
    TextView priority;

    @Bind(R.id.tv_issueType)
    TextView issueType;

    @Bind(R.id.tv_component)
    TextView component;

    @Bind(R.id.tv_created)
    TextView created;

    @Bind(R.id.tv_assignee)
    TextView assignee;

    @Bind(R.id.tv_status)
    TextView status;

    @Bind(R.id.tv_sprint)
    TextView sprint;

    @Bind(R.id.tv_resolution)
    TextView resolution;


    public static DialogConfirmationCheckout newInstance(Issue issue) {

        DialogConfirmationCheckout d = new DialogConfirmationCheckout();

        Bundle args = new Bundle();
        args.putParcelable(ISSUE_BUNDLE, issue);
        d.setArguments(args);

        return d;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_confirmation_checkout, container, false);
        ButterKnife.bind(this, v);

        key.setText(issue.getKey());
        summary.setText(issue.getSummary());
        priority.setText("Priority: "+issue.getPriority());
        issueType.setText("Issue Type: "+issue.getIssueType());
        component.setText("Component: "+issue.getComponent());
        created.setText("Created: "+issue.getCreated());
        assignee.setText("Assignee: "+issue.getAssignee());
        status.setText("Status: "+issue.getStatus());
        sprint.setText("Sprint: "+issue.getSprint());
        resolution.setText("Resolution: "+issue.getResolution());

        return v;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = new Dialog(getActivity());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setCancelable(false);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        return dialog;
    }

    @OnClick(R.id.checkout_confirm)
    public void checkoutConfirm() {
        dialog.dismiss();

        if (onDialogListener != null)
            onDialogListener.onContinue();
    }

    public void setOnDialogListener(OnDialogListener onDialogListener) {
        this.onDialogListener = onDialogListener;
    }

    public interface OnDialogListener {
        void onContinue();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        issue = getArguments().getParcelable(ISSUE_BUNDLE);

    }
}
