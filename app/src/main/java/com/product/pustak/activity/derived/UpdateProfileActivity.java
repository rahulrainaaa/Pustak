package com.product.pustak.activity.derived;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.product.pustak.R;
import com.product.pustak.activity.base.BaseActivity;
import com.product.pustak.adapter.WorkSpinnerAdapter;
import com.product.pustak.handler.UserProfileHandler.UserProfileHandler;
import com.product.pustak.handler.UserProfileListener.UserProfileUpdatedListener;
import com.product.pustak.model.User;

public class UpdateProfileActivity extends BaseActivity implements UserProfileUpdatedListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

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

        User user = getIntent().getParcelableExtra("user");

        if (user != null) {

            publishFields(user);
        }
    }

    private void publishFields(User user) {

        etName.setText("" + user.getName());
        etEmail.setText("" + user.getEmail());
        etMobile.setText("" + user.getMobile());
        etArea.setText("" + user.getArea());
        etCity.setText("" + user.getCity());
        etState.setText("" + user.getState());
        etCountry.setText("" + user.getCountry());
        etPostalCode.setText("" + user.getPostal());
    }

    public void save(final View view) {

        if (!validate()) {

            return;
        }

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Pick Location");
        alertBuilder.setIcon(R.drawable.icon_locate_black);
        alertBuilder.setMessage("Would you like to add the map location too?");
        alertBuilder.setCancelable(false);
        alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                // Proceed to pick map location point and then proceed.
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(UpdateProfileActivity.this), 12211);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                    Toast.makeText(UpdateProfileActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertBuilder.setNegativeButton("No Thanks", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                // proceed without updating geo coordinates.
                updateUserProfile("");
            }
        });
        alertBuilder.show();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        String geoLocation = "";
        if (requestCode == 12211) {

            // Picked geo location. Now proceed to update user profile.
            if (resultCode == RESULT_OK) {

                Place place = PlacePicker.getPlace(data, this);
                geoLocation = place.getLatLng().latitude + "," + place.getLatLng().latitude;
                Toast.makeText(this, "Location Picked", Toast.LENGTH_LONG).show();
            }
            updateUserProfile(geoLocation);
        }
    }

    private void updateUserProfile(String geo) {

        User user = new User();
        user.setName(etName.getText().toString().trim());
        user.setEmail(etEmail.getText().toString().trim());
        user.setMobile(etMobile.getText().toString().trim());
        user.setArea(etArea.getText().toString().trim());
        user.setCity(etCity.getText().toString().trim());
        user.setState(etState.getText().toString().trim());
        user.setCountry(etCountry.getText().toString().trim());
        user.setPostal(etPostalCode.getText().toString().trim());
        user.setWork(((TextView) spWork.getSelectedView()).getText().toString());
        user.setGeo(geo.trim());
        user.setPic("");
        user.setRate(0.0f);
        user.setRateCount(0);

        UserProfileHandler userProfileHandler = new UserProfileHandler(this);
        userProfileHandler.setUser(user, this, true);
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

    @Override
    public void userProfileUpdatedCallback(User user, UserProfileHandler.CODE code, String message) {

        if (code == UserProfileHandler.CODE.SUCCESS) {

            Toast.makeText(UpdateProfileActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UpdateProfileActivity.this, DashboardActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            finish();

        } else if (code == UserProfileHandler.CODE.Exception) {

            Toast.makeText(UpdateProfileActivity.this, "Failed update", Toast.LENGTH_SHORT).show();

        }
    }
}
