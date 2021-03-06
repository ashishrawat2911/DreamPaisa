package com.rawat.ashish.game.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rawat.ashish.game.R;
import com.rawat.ashish.game.activities.WithDrawActivity;
import com.rawat.ashish.game.constants.MyConstants;
import com.rawat.ashish.game.model.UserDetails;
import com.rawat.ashish.game.networks.APIClient;
import com.rawat.ashish.game.networks.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WalletFragment extends Fragment {
    Button redeemButton;
    ProgressDialog progress;
    APIService apiService;
    SharedPreferences sharedPreferences;
    TextView referCash, selfIncome;

    public WalletFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_wallet, container, false);
        redeemButton = rootView.findViewById(R.id.redeemButton);
        redeemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redeemIntent = new Intent(getActivity(), WithDrawActivity.class);
                startActivity(redeemIntent);
            }
        });
        referCash = rootView.findViewById(R.id.referCashTextView);
        selfIncome = rootView.findViewById(R.id.selfIncomeTextView);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        redeemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redeemIntent = new Intent(getActivity(), WithDrawActivity.class);
                startActivity(redeemIntent);
            }
        });
        apiService = APIClient.getClient().create(APIService.class);
        loadProgressBar();
        loadData();
        return rootView;
    }

    private void loadData() {
        apiService.getUser(sharedPreferences.getString(MyConstants.USER_ID, null)).enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                referCash.setText(response.body().getResult().getReferCash());
                selfIncome.setText(response.body().getResult().getEarnedCash());
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadProgressBar() {
        progress = new ProgressDialog(getActivity());
        progress.setMessage("Loading data");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();
    }


}
