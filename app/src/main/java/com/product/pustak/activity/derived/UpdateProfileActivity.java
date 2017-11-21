package com.product.pustak.activity.derived;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
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
import com.product.pustak.model.User;

public class UpdateProfileActivity extends BaseActivity {

    public static final String TAG = "UpdateProfileActivity";

    /**
     * Class private UI component(s).
     */
    private Spinner spWork = null;
    private TextView etName = null;
    private TextView etEmail = null;
    private TextView etMobile = null;
    private TextView etArea = null;
    private TextView etCity = null;
    private TextView etState = null;
    private TextView etCountry = null;
    private TextView etPostalCode = null;

    /**
     * Class private data member(s).
     */
    private FirebaseFirestore db = null;
    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        db = FirebaseFirestore.getInstance();
        etName = (TextView) findViewById(R.id.txt_name);
        etEmail = (TextView) findViewById(R.id.txt_email);
        etMobile = (TextView) findViewById(R.id.txt_mobile);
        etArea = (TextView) findViewById(R.id.txt_area);
        etCity = (TextView) findViewById(R.id.txt_city);
        etState = (TextView) findViewById(R.id.txt_state);
        etCountry = (TextView) findViewById(R.id.txt_country);
        etPostalCode = (TextView) findViewById(R.id.txt_postal_code);
        spWork = (Spinner) findViewById(R.id.spinner_work);
        spWork.setAdapter(new WorkSpinnerAdapter(this, R.layout.item_spinner_textview, R.drawable.icon_work, getResources().getStringArray(R.array.work)));

        etMobile.setText(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
        etMobile.setEnabled(false);
    }

    public void save(final View view) {

        if (!validate()) {

            return;
        }

        view.setEnabled(false);
        user = new User();
        user.setName(etName.getText().toString().trim());
        user.setEmail(etEmail.getText().toString().trim());
        user.setMobile(etMobile.getText().toString().trim());
        user.setArea(etArea.getText().toString().trim());
        user.setCity(etCity.getText().toString().trim());
        user.setState(etState.getText().toString().trim());
        user.setCountry(etCountry.getText().toString().trim());
        user.setPostal(etPostalCode.getText().toString().trim());
        user.setWork(((TextView) spWork.getSelectedView()).getText().toString());
        user.setGeo("");
        user.setPic("");
        user.setRate(0.0f);
        user.setRateCount(0);

        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(UpdateProfileActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateProfileActivity.this, DashboardActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        view.setEnabled(true);
                        Toast.makeText(UpdateProfileActivity.this, "Failed update", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    /**
     * Method to check the validation for all data field(s).
     *
     * @return boolean true = valid, false = validation fail.
     */
    private boolean validate() {

        boolean isValid = true;

        return isValid;
    }

}
