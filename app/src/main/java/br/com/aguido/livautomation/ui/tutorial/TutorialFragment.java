package br.com.aguido.livautomation.ui.tutorial;


import android.content.Context;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import br.com.aguido.livautomation.R;
import br.com.aguido.livautomation.model.Tutorial;
import br.com.aguido.livautomation.util.Util;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by selem.gomes on 02/09/15.
 */
public class TutorialFragment extends Fragment {
    static final private String KEY_TUTORIAL = "keyTutorial";
    @BindView(R.id.tutorial_title_header)
    TextView tutorialTitleHeader;
    @BindView(R.id.tutorial_description_header)
    TextView tutorialDescriptionHeader;
    @BindView(R.id.tutorial_title_footer)
    TextView tutorialTitleFooter;
    @BindView(R.id.tutorial_description_footer)
    TextView tutorialDescriptionFooter;
    @BindView(R.id.tutorial_background)
    RelativeLayout tutorialBackground;
    @BindView(R.id.tutorial_image_header)
    ImageView tutorialImageHeader;
    @BindView(R.id.tutorial_image_footer)
    ImageView tutorialImageFooter;

    private Tutorial tutorial;

    public static Fragment newInstance(Context context, Tutorial tutorial) {
        Bundle params = new Bundle();
        params.putSerializable(KEY_TUTORIAL, tutorial);
        return Fragment.instantiate(context, TutorialFragment.class.getName(), params);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tutorial = (Tutorial) getArguments().getSerializable(KEY_TUTORIAL);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_tutorial, container, false);
        ButterKnife.bind(this, view);
        setInfo();
        return view;
    }

    private void setInfo() {
        tutorialTitleHeader.setText(tutorial.getTitleHeader());
        tutorialDescriptionHeader.setText(tutorial.getDescriptionHeader());
        tutorialTitleFooter.setText(tutorial.getTitleFooter());
        tutorialDescriptionFooter.setText(tutorial.getDescriptionFooter());
        tutorialImageHeader.setImageDrawable(Util.getDrawableByName(getContext(), tutorial.getImageHeader()));
        tutorialImageFooter.setImageDrawable(Util.getDrawableByName(getContext(), tutorial.getImageFooter()));
        tutorialBackground.setBackgroundDrawable(Util.getDrawableByName(getContext(), tutorial.getBackground()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
