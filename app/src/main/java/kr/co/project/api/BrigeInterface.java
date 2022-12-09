//package kr.co.project.api;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.webkit.JavascriptInterface;
//
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentActivity;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.lifecycle.ViewModelStore;
//import androidx.lifecycle.ViewModelStoreOwner;
//
//import kr.co.project.R;
//import kr.co.project.main.MainActivity;
//import kr.co.project.view.sign.signup.SignUpFragment1;
//import kr.co.project.view.sign.signup.address.AddressFragment;
//import kr.co.project.vo.AddressData;
//import kr.co.project.vo.SignDTO;
//
//public class BrigeInterface {
//    private static final String TAG = BrigeInterface.class.getSimpleName();
//    private  AddressData addressData;
//    private Bundle bundle;
//    private SignUpFragment1 signUpFragment1;
//    private AddressFragment addressFragment;
//
//    @JavascriptInterface
//    @SuppressWarnings("unused")
//    public void processDATA( String data) {
//
//        addressData = new ViewModelProvider().get(AddressData.class);
//
//        Log.i(TAG, "데이터 값 확인 "+data);
//        Log.i(TAG, "데이터 값 확인 "+ data.substring(data.lastIndexOf(",")+1));
//        Log.i(TAG, "데이터 값 확인 "+
//                data.substring(0, data.indexOf(",")));
//        addressData.fulladdress.setValue(data.substring(data.lastIndexOf(",")+1));
//        Log.i(TAG, "되는지 확인용"+addressData.fulladdress.getValue());
//                SignDTO signDTO = new SignDTO(data.substring(0, data.indexOf(",")), data.substring(data.lastIndexOf(",")+1));
//
//
//        FragmentTransaction fragmentTransaction = addressFragment.getActivity().getSupportFragmentManager().beginTransaction();
//
//
//        addressData = new ViewModelProvider(signUpFragment1.requireActivity()).get(AddressData.class);
//        addressData.zipCd.setValue(data.substring(0, data.indexOf(",")));
//        addressData.fulladdress.setValue(data.substring(data.lastIndexOf(",")+1));
//
//
//                navController = new NavHostController(navController.getContext());
//                navController.getPreviousBackStackEntry().getSavedStateHandle().set("d", data);
//
//           }
//
//
//
//    }
//
