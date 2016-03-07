package ru.mishaignatov.touristquiz.ui.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.mishaignatov.touristquiz.R;

/***
 * Created by Mike on 26.02.2016.
 */
public abstract class BaseDialogFragment extends DialogFragment {

    private List<View> mViewList = new ArrayList<>();

    private LinearLayout mContainer;

    // return color resource
    public abstract int getTitleColor();
    // return string title resource
    public abstract int getTitleString();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        View v = inflater.inflate(R.layout.dialog_base, container, false);

        mContainer = (LinearLayout) v.findViewById(R.id.dialog_parent);
        mContainer.setBackgroundResource(R.drawable.dialog_content);

        TextView title = (TextView) v.findViewById(R.id.dialog_title);
        title.setText(getTitleString());
        title.setBackgroundResource(getTitleColor());

        setupDialog(mContainer);

        return v;
    }

    public void addView(final View v){
        mViewList.add(v);
    }

    private void setupDialog(final ViewGroup parent){

        for(int i = 0; i< mViewList.size(); i++){
            final View v = mViewList.get(i);
            parent.addView(v);
        }
    }


    public void addCancelButton(final View.OnClickListener listener){
        final Button cancel = new Button(getContext());
        cancel.setText(getResources().getString(R.string.cancel));
        cancel.setOnClickListener(listener);
        cancel.setTag("cancel");
        mViewList.add(cancel);
    }

    public void addNextButton(final View.OnClickListener listener){
        final Button next = new Button(getContext());
        next.setText(getString(R.string.dialog_success_next));
        next.setOnClickListener(listener);
        next.setTag("next");
        mViewList.add(next);
    }

    public void addCancelAndOkButtons(int sendResStringId, final View.OnClickListener listener){

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.item_ok_cancel_buttons, mContainer, false);

        final Button cancel = (Button) ll.findViewById(R.id.button_cancel);
        cancel.setOnClickListener(listener);
        cancel.setTag("cancel");

        final Button send = (Button) ll.findViewById(R.id.button_send);
        send.setOnClickListener(listener);
        send.setText(getResources().getString(sendResStringId));
        send.setTag("send");

        mViewList.add(ll);
    }

    public void addImageAndTextView(int resImageId, int resTextId, String value){

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.item_image_text, mContainer, false);

        final ImageView icon = (ImageView) ll.findViewById(R.id.dialog_icon);
        icon.setImageResource(resImageId);

        final TextView text = (TextView) ll.findViewById(R.id.dialog_value);
        text.setText(getString(resTextId, value));

        mViewList.add(ll);
    }
}
