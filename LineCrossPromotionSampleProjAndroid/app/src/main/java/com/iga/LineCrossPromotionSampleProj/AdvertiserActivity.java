package com.iga.LineCrossPromotionSampleProj;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.line.crosspromotion.LineCrossPromotion;

/**
 * Created by MikeHan on 2016-04-16.
 */
public class AdvertiserActivity extends Activity {

    private Button signInBtn, signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advertiser_layout);

        makeUiComponents();

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LineCrossPromotion.unlock(LineCrossPromotion.Event.SIGN_IN);
                Toast.makeText(AdvertiserActivity.this, "SignIn Unlock!", Toast.LENGTH_SHORT).show();
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LineCrossPromotion.unlock(LineCrossPromotion.Event.SIGN_UP);
                Toast.makeText(AdvertiserActivity.this, "SignUn Unlock!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void makeUiComponents() {
        signInBtn = (Button)findViewById(R.id.signInBtnOnAdvertiser);
        signUpBtn = (Button)findViewById(R.id.signUpBtnOnAdvertiser);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
