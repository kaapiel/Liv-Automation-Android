package br.com.aguido.livautomation.ui.dialog;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import br.com.aguido.livautomation.R;
import br.com.aguido.livautomation.ui.home.Issue;
import butterknife.BindView;
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

    @BindView(R.id.tv_summary)
    TextView summary;

    @BindView(R.id.tv_key)
    TextView key;

    @BindView(R.id.tv_priority)
    TextView priority;

    @BindView(R.id.tv_issueType)
    TextView issueType;

    @BindView(R.id.tv_component)
    TextView component;

    @BindView(R.id.tv_created)
    TextView created;

    @BindView(R.id.tv_assignee)
    TextView assignee;

    @BindView(R.id.tv_status)
    TextView status;

    @BindView(R.id.tv_sprint)
    TextView sprint;

    @BindView(R.id.tv_resolution)
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
