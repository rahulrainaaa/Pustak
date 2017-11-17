package com.product.pustak;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateProfileActivity extends AppCompatActivity {

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
        spWork.setAdapter(new WorkSpinnerAdapter(this, R.layout.item_spinner_textview, getResources().getStringArray(R.array.work)));

        etMobile.setText(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());


    }

    public void save(View view) {

        if (!validate()) {

            return;
        }

        user = new User();
        user.setName(etName.getText().toString().trim());
        user.setEmail(etEmail.getText().toString().trim());
        user.setMobile(43);//Integer.parseInt(etMobile.getText().toString().trim()));
        user.setArea(etArea.getText().toString().trim());
        user.setCity(etCity.getText().toString().trim());
        user.setState(etState.getText().toString().trim());
        user.setCountry(etCountry.getText().toString().trim());
        user.setPostal(12);//Integer.parseInt(etPostalCode.getText().toString().trim()));
        user.setWork(((TextView) spWork.getSelectedView()).getText().toString());
        user.setGeo("");
        user.setPic("");
        user.setRate(0.0f);

        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        Toast.makeText(UpdateProfileActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

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
