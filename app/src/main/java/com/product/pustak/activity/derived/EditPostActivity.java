package com.product.pustak.activity.derived;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.product.pustak.R;
import com.product.pustak.activity.base.BaseActivity;
import com.product.pustak.adapter.WorkSpinnerAdapter;
import com.product.pustak.model.Post;
import com.product.pustak.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Class to handle the edit post for the application.
 */
public class EditPostActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = "EditPostActivity";

    /**
     * Class private UI Object(s).
     */
    private EditText mEtBookName = null;
    private EditText mEtAuthorName = null;
    private EditText mEtPublication = null;
    private EditText mEtEdition = null;
    private EditText mEtDescription = null;
    private EditText mEtSubject = null;
    private EditText mEtMarkedPrice = null;
    private EditText mEtSellingPrice = null;
    private EditText mEtRent = null;
    private EditText mEtDays = null;

    private CheckBox mChkStatus = null;

    private Spinner mSpType = null;
    private Spinner mSpCondition = null;

    private Button mBtnDone = null;

    private RadioButton mRadioRent = null;
    private RadioButton mRadioSell = null;
    private RadioGroup mRadioGroup = null;

    /**
     * Class private data member(s).
     */
    private FirebaseFirestore db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        mEtBookName = findViewById(R.id.name);
        mEtAuthorName = findViewById(R.id.author);
        mEtPublication = findViewById(R.id.publication);
        mEtEdition = findViewById(R.id.edition);
        mEtDescription = findViewById(R.id.description);
        mEtSubject = findViewById(R.id.subject);
        mEtMarkedPrice = findViewById(R.id.marked_price);
        mEtSellingPrice = findViewById(R.id.selling_price);
        mEtRent = findViewById(R.id.rent_per_day);
        mEtDays = findViewById(R.id.available_days);

        mBtnDone = findViewById(R.id.button_done);
        mChkStatus = findViewById(R.id.checkbox_visibility);

        mRadioRent = findViewById(R.id.radio_rent);
        mRadioSell = findViewById(R.id.radio_sell);
        mRadioGroup = findViewById(R.id.radio_group);
        mSpType = findViewById(R.id.spinner_book_type);
        mSpType.setAdapter(new WorkSpinnerAdapter(this, R.layout.item_spinner_textview, R.drawable.icon_book_type, getResources().getStringArray(R.array.booktype)));

        mSpCondition = findViewById(R.id.spinner_book_condition);
        mSpCondition.setAdapter(new WorkSpinnerAdapter(this, R.layout.item_spinner_textview, R.drawable.icon_book_type, getResources().getStringArray(R.array.condition)));

        mBtnDone.setOnClickListener(this);

        mChkStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

                if (checked) {
                    mBtnDone.setText("Post");
                } else {
                    mBtnDone.setText("Save");
                }
            }
        });

        db = FirebaseFirestore.getInstance();

        publishFields();
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
     * Method to publish older data into UI fields. Called on activity start.
     */
    public void publishFields() {

        Post post = getIntent().getParcelableExtra("post");

        mEtBookName.setText(post.getName());
        mEtAuthorName.setText(post.getAuthor());
        mEtPublication.setText(post.getPub());
        mEtEdition.setText(post.getEdition());
        mEtDescription.setText(post.getDesc());
        mEtSubject.setText(post.getSub());
        mEtMarkedPrice.setText("" + post.getMrp());
        mEtSellingPrice.setText("" + post.getPrice());
        mEtRent.setText("" + post.getRent());
        mEtDays.setText("" + post.getDays());
        mChkStatus.setChecked(post.getActive());

        if (post.getAvail().contains("Rent")) {

            // Make Rent - Radio button selected.
            mRadioGroup.check(R.id.radio_rent);
        } else {

            // Make Sell - Radio button selected.
            mRadioGroup.check(R.id.radio_sell);
        }
    }

    /**
     * Callback method on UI Save Button clicked.
     *
     * @param view
     */
    public void save(View view) {

        /**
         * Get the referenceID of document which is to be updated.
         */
        final String documentReferenceId = getIntent().getStringExtra("documentReferenceId");

        if (!checkValidation()) {

            return;
        }

        /**
         * Updated the older post date with current date.
         */
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.DATE, 7);

        /**
         * Create the updated post request object.
         */
        try {
            Post post = new Post();
            post.setName(mEtBookName.getText().toString().trim());
            post.setAuthor(mEtAuthorName.getText().toString().trim());
            post.setPub(mEtPublication.getText().toString().trim());
            post.setType(((TextView) mSpType.getSelectedView()).getText().toString().trim());
            post.setEdition(mEtEdition.getText().toString().trim());
            post.setDesc(mEtDescription.getText().toString().trim());
            post.setSub(mEtSubject.getText().toString().trim());
            post.setMrp(Float.parseFloat(mEtMarkedPrice.getText().toString().trim()));
            post.setPrice(Float.parseFloat(mEtSellingPrice.getText().toString().trim()));
            post.setRent(Float.parseFloat(mEtRent.getText().toString().trim()));
            post.setDays(Integer.parseInt(mEtDays.getText().toString().trim()));
            post.setAvail(mRadioRent.isChecked() ? "Rent" : "Sell");
            post.setActive(mChkStatus.isChecked());
            post.setDate(dateFormat.format(currentDate));
            post.setExpiry(dateFormat.format(cal.getTime()));
            post.setCond(mSpCondition.getSelectedItemPosition());
            post.setMobile(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());

            showProgressDialog();

            /**
             * Update the post in {@link FirebaseFirestore} and wait for callbacks.
             */
            db.collection("posts")
                    .document(documentReferenceId)
                    .set(post)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            hideProgressDialog();
                            Toast.makeText(EditPostActivity.this, "Done", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "DocumentSnapshot written with ID: " + documentReferenceId);
                            finish();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            hideProgressDialog();
                            Toast.makeText(EditPostActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            Log.w(TAG, "Error adding document", e);
                        }
                    });

        } catch (Exception e) {

            e.printStackTrace();
            Toast.makeText(this, "Exception:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    /**
     * Method to check the validation of all UI field(s). Also set error message if invalid input.
     *
     * @return boolean true = validation successful, false = validation failed.
     */
    private boolean checkValidation() {

        String stBookName = mEtBookName.getText().toString();
        String strAuthorName = mEtAuthorName.getText().toString();
        String strPublication = mEtPublication.getText().toString();
        String strEdition = mEtEdition.getText().toString();
        String strDescription = mEtDescription.getText().toString();
        String strSubject = mEtSubject.getText().toString();
        String strMarkedPrice = mEtMarkedPrice.getText().toString();
        String strSellingPrice = mEtSellingPrice.getText().toString();
        String strRentPrice = mEtRent.getText().toString();
        String strRentDays = mEtDays.getText().toString();

        boolean isValid = false;

        if (!Pattern.compile(Constants.REGEX_TEXT_ONLY).matcher(strAuthorName).matches()) {

            isValid = false;
        }

        if (!Pattern.compile(Constants.REGEX_TEXT_ONLY).matcher(stBookName).matches()) {

            isValid = false;
        }

        if (!Pattern.compile(Constants.REGEX_TEXT_ONLY).matcher(strPublication).matches()) {

            isValid = false;
        }

        if (!Pattern.compile(Constants.REGEX_TEXT_ONLY).matcher(strEdition).matches()) {

            isValid = false;
        }

        if (!Pattern.compile(Constants.REGEX_TEXT_ONLY).matcher(strDescription).matches()) {

            isValid = false;
        }

        if (!Pattern.compile(Constants.REGEX_TEXT_ONLY).matcher(strSubject).matches()) {

            isValid = false;
        }

        if (!Pattern.compile(Constants.REGEX_TEXT_ONLY).matcher(strMarkedPrice).matches()) {

            isValid = false;
        }

        if (!Pattern.compile(Constants.REGEX_TEXT_ONLY).matcher(strSellingPrice).matches()) {

            isValid = false;
        }

        if (!Pattern.compile(Constants.REGEX_TEXT_ONLY).matcher(strRentPrice).matches()) {

            isValid = false;
        }

        if (!Pattern.compile(Constants.REGEX_TEXT_ONLY).matcher(strRentDays).matches()) {

            isValid = false;
        }

        return isValid;
    }


}
