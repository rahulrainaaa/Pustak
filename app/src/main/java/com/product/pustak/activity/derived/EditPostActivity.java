package com.product.pustak.activity.derived;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditPostActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = "EditPostActivity";

    /**
     * Class private UI Object(s).
     */
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

        mTxtName = (TextView) findViewById(R.id.name);
        mTxtAuthor = (TextView) findViewById(R.id.author);
        mTxtPublication = (TextView) findViewById(R.id.publication);
        mTxtEdition = (TextView) findViewById(R.id.edition);
        mTxtDescription = (TextView) findViewById(R.id.description);
        mTxtSubject = (TextView) findViewById(R.id.subject);
        mTxtMarkedPrice = (TextView) findViewById(R.id.marked_price);
        mTxtSellingPrice = (TextView) findViewById(R.id.selling_price);
        mTxtRent = (TextView) findViewById(R.id.rent_per_day);
        mTxtDays = (TextView) findViewById(R.id.available_days);

        mBtnDone = (Button) findViewById(R.id.button_done);
        mChkStatus = (CheckBox) findViewById(R.id.checkbox_visibility);

        mRadioRent = (RadioButton) findViewById(R.id.radio_rent);
        mRadioSell = (RadioButton) findViewById(R.id.radio_sell);
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group);
        mSpType = (Spinner) findViewById(R.id.spinner_book_type);
        mSpType.setAdapter(new WorkSpinnerAdapter(this, R.layout.item_spinner_textview, R.drawable.icon_book_type, getResources().getStringArray(R.array.booktype)));

        mSpCondition = (Spinner) findViewById(R.id.spinner_book_condition);
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

    public void publishFields() {

        Post post = getIntent().getParcelableExtra("post");

        mTxtName.setText(post.getName());
        mTxtAuthor.setText(post.getAuthor());
        mTxtPublication.setText(post.getPub());
        mTxtEdition.setText(post.getEdition());
        mTxtDescription.setText(post.getDesc());
        mTxtSubject.setText(post.getSub());
        mTxtMarkedPrice.setText("" + post.getMrp());
        mTxtSellingPrice.setText("" + post.getPrice());
        mTxtRent.setText("" + post.getRent());
        mTxtDays.setText("" + post.getDays());
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

        final String documentReferenceId = getIntent().getStringExtra("documentReferenceId");

        if (!checkValidation()) {

            Toast.makeText(this, "Input validation failed", Toast.LENGTH_SHORT).show();
            return;
        }

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
            post.setCond(mSpCondition.getSelectedItemPosition());
            post.setMobile(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());

            showProgressDialog();

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

        return true;
    }


}
