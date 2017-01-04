package ee.ttu.a103944.shopandr.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import ee.ttu.a103944.shopandr.R;
import ee.ttu.a103944.shopandr.model.RegDTO;
import ee.ttu.a103944.shopandr.network.service.ServiceCreator;
import ee.ttu.a103944.shopandr.network.service.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterFragment extends AbstractFragment {

    private String TAG = "RegisterFragment";
    private EditText nick;
    private EditText email;
    private EditText pwd;
    private EditText repPwd;
    private RegDTO regDTO;
    private View.OnClickListener onRegisterListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            UserService orderService = ServiceCreator.createService(UserService.class
                    , getActivity());
            regDTO = new RegDTO();
            regDTO.setEmail(email.getText().toString());
            regDTO.setNick(nick.getText().toString());
            regDTO.setPassword(pwd.getText().toString());
            regDTO.setPassword2(repPwd.getText().toString());
            Call<RegDTO> call = orderService.register(regDTO);
            call.enqueue(new Callback<RegDTO>() {
                @Override
                public void onResponse(Call<RegDTO> call, Response<RegDTO> response) {
                    RegDTO regDTO = response.body();
                    if (validateAnswer(regDTO)) {
                        Log.d(TAG, "valid ok");
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content,
                                new AccCreateSuccFragment(),
                                AccCreateSuccFragment.class.toString()).commit();
                    } else
                        Log.d(TAG, "valid not ok");
                }

                @Override
                public void onFailure(Call<RegDTO> call, Throwable t) {

                }
            });
        }

    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        nick = (EditText) view.findViewById(R.id.nick);
        email = (EditText) view.findViewById(R.id.email);
        pwd = (EditText) view.findViewById(R.id.pwd);
        repPwd = (EditText) view.findViewById(R.id.repPwd);

        Button register = (Button) view.findViewById(R.id.register);
        register.setOnClickListener(onRegisterListener);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private boolean validateAnswer(RegDTO regDTO) {
        boolean isValid = true;
        TextInputLayout layout = findView(R.id.layout_nick);
        if (regDTO.getNickError() != null) {
            setLayoutError(layout, regDTO.getNickError());
            isValid = false;
        } else
            setLayoutError(layout, null);
        layout = findView(R.id.layout_email);
        if (regDTO.getEmailError() != null) {
            setLayoutError(layout, regDTO.getEmailError());
            isValid = false;
        } else
            setLayoutError(layout, null);
        layout = findView(R.id.layout_pwd);
        if (regDTO.getPwdError() != null) {
            setLayoutError(layout, regDTO.getPwdError());
            isValid = false;
        } else
            setLayoutError(layout, null);
        layout = findView(R.id.layout_repPwd);
        if (regDTO.getPwd2Error() != null) {
            setLayoutError(layout, regDTO.getPwd2Error());
            isValid = false;
        } else
            setLayoutError(layout, null);
        return isValid;
    }

    private void setLayoutError(TextInputLayout layout, String errorMsg) {
        layout.setError(errorMsg);
    }
}
