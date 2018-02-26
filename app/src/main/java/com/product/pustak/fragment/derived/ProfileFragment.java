package com.product.pustak.fragment.derived;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.product.pustak.R;
import com.product.pustak.activity.derived.LoginActivity;
import com.product.pustak.activity.derived.UpdateProfileActivity;
import com.product.pustak.fragment.base.BaseFragment;
import com.product.pustak.model.User;

/**
 * Fragment class to show profile screen on {@link com.product.pustak.activity.derived.DashboardActivity}.
 */
public class ProfileFragment extends BaseFragment implements View.OnClickListener {

    private User mUser = null;

    public static ProfileFragment getInstance() {

        return new ProfileFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_my_profile, container, false);

        TextView txtName = view.findViewById(R.id.txt_user_name);
        TextView txtMobile = view.findViewById(R.id.txt_phone);
        TextView txtEmail = view.findViewById(R.id.txt_email);
        TextView txtArea = view.findViewById(R.id.txt_area);
        TextView txtCity = view.findViewById(R.id.txt_city);
        TextView txtState = view.findViewById(R.id.txt_state);
        TextView txtCountry = view.findViewById(R.id.txt_country);
        ImageButton iBtnEdit = view.findViewById(R.id.btn_edit_profile);
        ImageButton iBtnLogout = view.findViewById(R.id.btn_logout);

        iBtnEdit.setOnClickListener(this);
        iBtnLogout.setOnClickListener(this);

        mUser = getDashboardActivity().getUser();

        if (mUser != null) {

            txtName.setText(mUser.getName());
            txtMobile.setText(mUser.getMobile());
            txtEmail.setText(mUser.getEmail());
            txtArea.setText(mUser.getArea());
            txtCity.setText(mUser.getCity());
            txtState.setText(mUser.getState());
            txtCountry.setText(mUser.getCountry());
        }

        return view;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_edit_profile) {

            Intent intent = new Intent(getDashboardActivity(), UpdateProfileActivity.class);
            intent.putExtra("user", mUser);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            getDashboardActivity().finish();

        } else if (view.getId() == R.id.btn_logout) {

            logout();

        } else {

            Toast.makeText(getActivity(), "Unhandled OnClickListener()", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method to logout from Google {@link FirebaseAuth}.
     */
    private void logout() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(R.drawable.icon_exit);
        builder.setTitle(getString(R.string.sign_out));
        builder.setMessage(getString(R.string.sign_out_message));
        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                FirebaseAuth.getInstance().signOut();
                getActivity().finish();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        builder.setNegativeButton(getString(R.string.no), null);
        builder.show();
    }
}
