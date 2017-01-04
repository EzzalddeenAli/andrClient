package ee.ttu.a103944.shopandr.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;

import ee.ttu.a103944.shopandr.R;
import ee.ttu.a103944.shopandr.event.UserEvents;
import ee.ttu.a103944.shopandr.network.service.ServiceCreator;
import ee.ttu.a103944.shopandr.network.service.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AbstractActivity {

    private String TAG = "LoginActivity";
    private EditText nick;
    private EditText pwd;
    private View.OnClickListener onLoginListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final String nick = LoginActivity.this.nick.getText().toString();
            String pwd = LoginActivity.this.pwd.getText().toString();

            if (validateInput(nick, pwd)) {
                UserService orderService = ServiceCreator.createService(UserService.class
                        , LoginActivity.this);
                Call<String> call = orderService.login(nick, pwd);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String resp = response.body();
                        Log.d(TAG, "resp is " + resp);
                        if (resp.equalsIgnoreCase("ok")) {
                            finish();
                            EventBus.getDefault().post(new UserEvents.LoginEvent());
                        } else if (resp.equalsIgnoreCase("fail")) {
                            TextInputLayout layout = findView(R.id.layout_login);
                            setLayoutError(layout, "wrong nick or pwd");
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        }
    };

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivityForResult(intent, 999);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Login");

        nick = (EditText) findViewById(R.id.nick);
        pwd = (EditText) findViewById(R.id.pwd);

        Button order = (Button) findViewById(R.id.login);
        order.setOnClickListener(onLoginListener);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validateInput(String nick, String pwd) {
        boolean isValid = true;
        TextInputLayout layout = findView(R.id.layout_login);
        if (nick.isEmpty()) {
            setLayoutError(layout, "enter nick");
            isValid = false;
        } else
            setLayoutError(layout, null);
        layout = findView(R.id.layout_pwd);
        if (pwd.isEmpty()) {
            setLayoutError(layout, "enter pwd");
            isValid = false;
        } else
            setLayoutError(layout, null);
        return isValid;
    }

    private void setLayoutError(TextInputLayout layout, String errorMsg) {
        layout.setError(errorMsg);
    }


}

