package com.product.pustak.activity.derived;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
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
import com.product.pustak.utils.Constants;

import java.util.regex.Pattern;

/**
 * Activity class to update the profile information for user.
 */
public class UpdateProfileActivity extends BaseActivity implements UserProfileUpdatedListener {

    public static final String TAG = "UpdateProfileActivity";

    /**
     * Class private UI component(s).
     */
    private Spinner mSpWork = null;
    private EditText mEtName = null;
    private EditText mEtEmail = null;
    private EditText mEtMobile = null;
    private EditText mEtArea = null;
    private EditText mEtCity = null;
    private EditText mEtState = null;
    private EditText mEtCountry = null;
    private EditText mEtPostalCode = null;

    /**
     * Class private data member(s).
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        mEtName = findViewById(R.id.txt_name);
        mEtEmail = findViewById(R.id.txt_email);
        mEtMobile = findViewById(R.id.txt_mobile);
        mEtArea = findViewById(R.id.txt_area);
        mEtCity = findViewById(R.id.txt_city);
        mEtState = findViewById(R.id.txt_state);
        mEtCountry = findViewById(R.id.txt_country);
        mEtPostalCode = findViewById(R.id.txt_postal_code);
        mSpWork = findViewById(R.id.spinner_work);
        mSpWork.setAdapter(new WorkSpinnerAdapter(this, R.layout.item_spinner_textview, R.drawable.icon_work, getResources().getStringArray(R.array.work)));

        mEtMobile.setText(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
        mEtMobile.setEnabled(false);
        publishFields();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        User user = getIntent().getParcelableExtra("user");
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    /**
     * Method to publish UI fields with older data. Called on activity create.
     */
    private void publishFields() {
        User user = getIntent().getParcelableExtra("user");

        if (user == null) {

            Toast.makeText(this, "Error: No user session present", Toast.LENGTH_SHORT).show();
            return;
        }
        mEtName.setText("" + user.getName());
        mEtEmail.setText("" + user.getEmail());
        mEtMobile.setText("" + user.getMobile());
        mEtArea.setText("" + user.getArea());
        mEtCity.setText("" + user.getCity());
        mEtState.setText("" + user.getState());
        mEtCountry.setText("" + user.getCountry());
        mEtPostalCode.setText("" + user.getPostal());
    }

    /**
     * Callback method on save button {@link android.view.View.OnClickListener}.
     *
     * @param view reference
     */
    public void save(final View view) {

        if (!validate()) {

            return;
        }

        /**
         * Prompt with alert to pick map location.
         */
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

                // proceed without geo coordinates (empty).
                updateUserProfile("");
            }
        });
        alertBuilder.show();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        String geoLocation = "";
        if (requestCode == 12211) {

            /**
             * Picked the geo location and now proceed for update.
             */
            if (resultCode == RESULT_OK) {

                @SuppressWarnings("deprecation") Place place = PlacePicker.getPlace(data, this);
                geoLocation = place.getLatLng().latitude + "," + place.getLatLng().latitude;
                Toast.makeText(this, "Location Picked", Toast.LENGTH_LONG).show();
            }
            updateUserProfile(geoLocation);
        }
    }

    /**
     * Method to update user profile.
     *
     * @param geo String geo coordinates.
     */
    private void updateUserProfile(@NonNull String geo) {

        User user = new User();
        user.setName(mEtName.getText().toString().trim());
        user.setEmail(mEtEmail.getText().toString().trim());
        user.setMobile(mEtMobile.getText().toString().trim());
        user.setArea(mEtArea.getText().toString().trim());
        user.setCity(mEtCity.getText().toString().trim());
        user.setState(mEtState.getText().toString().trim());
        user.setCountry(mEtCountry.getText().toString().trim());
        user.setPostal(mEtPostalCode.getText().toString().trim());
        user.setWork(((TextView) mSpWork.getSelectedView()).getText().toString());
        user.setGeo(geo.trim());
        user.setPic("");
        user.setRate(0.0f);
        user.setRateCount(0);

        UserProfileHandler userProfileHandler = new UserProfileHandler(this);
        userProfileHandler.setUser(user, this, true);
    }

    @Override
    public void userProfileUpdatedCallback(User user, UserProfileHandler.CODE code, String message) {

        if (code == UserProfileHandler.CODE.SUCCESS) {

            /**
             * User profile updated successfully.
             */
            Toast.makeText(UpdateProfileActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UpdateProfileActivity.this, DashboardActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            finish();

        } else if (code == UserProfileHandler.CODE.Exception) {

            Toast.makeText(UpdateProfileActivity.this, "Failed update", Toast.LENGTH_SHORT).show();

        }
    }

    /**
     * Method to check the validation for all data field(s).
     *
     * @return boolean true = valid, false = validation fail.
     */
    private boolean validate() {

        boolean isValid = true;

        String strFullName = mEtName.getText().toString();
        String strEmail = mEtEmail.getText().toString();
        String strArea = mEtArea.getText().toString();
        String strCity = mEtCity.getText().toString();
        String strState = mEtState.getText().toString();
        String strCountry = mEtCountry.getText().toString();
        String strPostalCode = mEtPostalCode.getText().toString();

        // Full name field validation.
        if (strFullName.isEmpty()) {

            mEtName.setError(getString(R.string.cannot_be_empty));
            isValid = false;

        } else if (!Pattern.compile(Constants.REGEX_ALPHA_ONLY).matcher(strFullName).matches()) {

            mEtName.setError(getString(R.string.only_alpha_allowed));
            isValid = false;

        } else {

            mEtName.setError(null);
        }

        // Email field validation.
        if (strEmail.isEmpty()) {

            mEtEmail.setError(getString(R.string.cannot_be_empty));
            isValid = false;

        } else if (!Pattern.compile(Constants.REGEX_EMAIL).matcher(strEmail).matches()) {

            mEtEmail.setError(getString(R.string.enter_valid_email));
            isValid = false;

        } else {

            mEtEmail.setError(null);
        }

        // Area field validation.
        if (strArea.isEmpty()) {

            mEtArea.setError(getString(R.string.cannot_be_empty));
            isValid = false;

        } else if (!Pattern.compile(Constants.REGEX_ALNUM).matcher(strArea).matches()) {

            mEtArea.setError(getString(R.string.alpha_num_space_allowed));
            isValid = false;

        } else {

            mEtArea.setError(null);
        }

        // City field validation.
        if (strCity.isEmpty()) {

            mEtCity.setError(getString(R.string.cannot_be_empty));
            isValid = false;

        } else if (!Pattern.compile(Constants.REGEX_ALNUM).matcher(strCity).matches()) {

            mEtCity.setError(getString(R.string.alpha_num_space_allowed));
            isValid = false;

        } else {

            mEtCity.setError(null);
        }

        // State field validation.
        if (strState.isEmpty()) {

            mEtState.setError(getString(R.string.cannot_be_empty));
            isValid = false;

        } else if (!Pattern.compile(Constants.REGEX_ALNUM).matcher(strState).matches()) {

            mEtState.setError(getString(R.string.alpha_num_space_allowed));
            isValid = false;

        } else {

            mEtState.setError(null);
        }

        // Country field validation.
        if (strCountry.isEmpty()) {

            mEtCountry.setError(getString(R.string.cannot_be_empty));
            isValid = false;

        } else if (!Pattern.compile(Constants.REGEX_ALNUM).matcher(strCountry).matches()) {

            mEtCountry.setError(getString(R.string.alpha_num_space_allowed));
            isValid = false;

        } else {

            mEtCountry.setError(null);
        }

        // Postal code validation.
        if (strPostalCode.isEmpty()) {

            mEtPostalCode.setError(getString(R.string.cannot_be_empty));
            isValid = false;

        } else if (!Pattern.compile(Constants.REGEX_ALNUM).matcher(strPostalCode).matches()) {

            mEtPostalCode.setError(getString(R.string.enter_valid_postal_code));
            isValid = false;

        } else {

            mEtPostalCode.setError(null);
        }

        return isValid;
    }

}
