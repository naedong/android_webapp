package kr.co.project.view.sign.signup.address;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ClientCertRequest;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;

import kr.co.project.api.BrigeInterface;
import kr.co.project.databinding.FragmentAddressBinding;
import kr.co.project.databinding.FragmentFirstBinding;
import kr.co.project.view.sign.SignUpFragment;
import kr.co.project.vo.AddressData;
import kr.co.project.vo.SignDTO;

public class AddressFragment extends Fragment {
    private static final String TAG = AddressFragment.class.getSimpleName();
    private FragmentAddressBinding binding;
    private AddressData addressData;

    public static AddressFragment newInstance(){
        AddressFragment sf = new AddressFragment();
        return sf;
    }

    @Override
    public void onStart() {
        super.onStart();

    }



    public void onInitWebSetting(){

        binding.web.getSettings().setLoadWithOverviewMode(true);
        binding.web.getSettings().setJavaScriptEnabled(true);
        binding.web.addJavascriptInterface(new AddressBrige(), "Android");
        binding.web.setWebViewClient(new WebViewClient() {


            @Override
            public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
                super.onReceivedClientCertRequest(view, request);
                Log.i(TAG, request+"");
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                binding.webProgress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i(TAG, "확인용");

                binding.webProgress.setVisibility(View.GONE);

                binding.web.loadUrl("javascript:sample2_execDaumPostcode();");

            }
        });

        binding.web.loadUrl("https://project2-c3089.web.app/");


    }


    @Override
    public void onResume() {
        super.onResume();

        if(!TextUtils.isEmpty(addressData.fulladdress.getValue())){
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
            getActivity().getSupportFragmentManager().popBackStack();
        }

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddressBinding.inflate(inflater, container, false);
        onBack();
        onInitWebSetting();



//        if(!TextUtils.isEmpty(addressData.fulladdress.getValue())){
//            onBack();
//        }

        return binding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void onBack(){
        binding.btnSignAddressBack.setOnClickListener(view -> {
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
            getActivity().getSupportFragmentManager().popBackStack();
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addressData = new ViewModelProvider(requireActivity()).get(AddressData.class);
    }

    private class AddressBrige {

        @JavascriptInterface
    @SuppressWarnings("unused")
    public void processDATA( String data) {


        Log.i(TAG, "데이터 값 확인 "+data);
        Log.i(TAG, "데이터 값 확인 "+ data.substring(data.lastIndexOf(",")+1));
        Log.i(TAG, "데이터 값 확인 "+
                data.substring(0, data.indexOf(",")));
        addressData.fulladdress.postValue(data.substring(data.lastIndexOf(",")+1));
        addressData.zipCd.postValue(
                Integer.valueOf(data.substring(0, data.indexOf(",")))
        );

        Log.i(TAG, "되는지 확인용"+addressData.fulladdress.getValue());
        Log.i(TAG, addressData.fulladdress.getValue()+"");
            getActivity().getSupportFragmentManager().beginTransaction().remove(AddressFragment.newInstance()).commit();
            getActivity().getSupportFragmentManager().popBackStack();

          //  SignDTO signDTO = new SignDTO(data.substring(0, data.indexOf(",")), data.substring(data.lastIndexOf(",")+1));

        }
    }
}
