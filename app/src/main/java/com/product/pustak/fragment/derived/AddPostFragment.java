package com.product.pustak.fragment.derived;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.product.pustak.R;
import com.product.pustak.adapter.WorkSpinnerAdapter;
import com.product.pustak.fragment.base.BaseFragment;
import com.product.pustak.model.Post;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddPostFragment extends BaseFragment implements View.OnClickListener {

    public static AddPostFragment getInstance() {

        AddPostFragment fragment = new AddPostFragment();
        return fragment;
    }

    private TextView mTxtName = null;
    private TextView mTxtAuthor = null;
    private TextView mTxtPublication = null;
    private TextView mTxtEdition = null;
    private TextView mTxtDescription = null;
    private TextView mTxtSubject = null;
    private TextView mTxtMarkedPrice = null;
    private TextView mTxtSellingPrice = null;
    private TextView mTxtRent = null;
    private TextView mTxtDays = null;
    private CheckBox mChkStatus = null;
    private Spinner mSpType = null;
    private Button mBtnDone = null;
    private RadioButton mRadioRent = null;
    private RadioButton mRadioSell = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_add_post, null);

        mTxtName = (TextView) view.findViewById(R.id.name);
        mTxtAuthor = (TextView) view.findViewById(R.id.author);
        mTxtPublication = (TextView) view.findViewById(R.id.publication);
        mTxtEdition = (TextView) view.findViewById(R.id.edition);
        mTxtDescription = (TextView) view.findViewById(R.id.description);
        mTxtSubject = (TextView) view.findViewById(R.id.subject);
        mTxtMarkedPrice = (TextView) view.findViewById(R.id.marked_price);
        mTxtSellingPrice = (TextView) view.findViewById(R.id.selling_price);
        mTxtRent = (TextView) view.findViewById(R.id.rent_per_day);
        mTxtDays = (TextView) view.findViewById(R.id.available_days);

        mBtnDone = (Button) view.findViewById(R.id.button_done);
        mChkStatus = (CheckBox) view.findViewById(R.id.checkbox_visibility);

        mRadioRent = (RadioButton) view.findViewById(R.id.radio_rent);
        mRadioSell = (RadioButton) view.findViewById(R.id.radio_sell);

        mSpType = (Spinner) view.findViewById(R.id.spinner_book_type);
        mSpType.setAdapter(new WorkSpinnerAdapter(getActivity(), R.layout.item_spinner_textview, R.drawable.icon_book_type, getResources().getStringArray(R.array.booktype)));

        mBtnDone.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.button_done:

                save(view);
                break;
        }
    }

    /**
     * Callback method on UI Save Button clicked.
     *
     * @param view
     */
    public void save(View view) {

        if (!checkValidation()) {

            Toast.makeText(getActivity(), "Input validation failed", Toast.LENGTH_SHORT).show();
            return;
        }

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.DATE, 7);
        try {
            Post post = new Post();
            post.setName(mTxtName.getText().toString().trim());
            post.setAuthor(mTxtAuthor.getText().toString().trim());
            post.setPub(mTxtPublication.getText().toString().trim());
            post.setType(((TextView) mSpType.getSelectedView()).getText().toString().trim());
            post.setEdition(mTxtEdition.getText().toString().trim());
            post.setDesc(mTxtDescription.getText().toString().trim());
            post.setSub(mTxtSubject.getText().toString().trim());
            post.setMrp(Float.parseFloat(mTxtMarkedPrice.getText().toString().trim()));
            post.setPrice(Float.parseFloat(mTxtSellingPrice.getText().toString().trim()));
            post.setRent(Float.parseFloat(mTxtRent.getText().toString().trim()));
            post.setDays(Integer.parseInt(mTxtDays.getText().toString().trim()));
            post.setAvail(mRadioRent.isChecked() ? "Rent" : "Sell");
            post.setActive(mChkStatus.isChecked());
            post.setDate(dateFormat.format(currentDate));
            post.setExpiry(dateFormat.format(cal.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Exception:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        int i = 0;


    }

    /**
     * Method to check the validation of all UI field(s). Also set error message if invalid input.
     *
     * @return boolean true = validation successful, false = validation failed.
     */
    private boolean checkValidation() {

        return true;
    }


}
