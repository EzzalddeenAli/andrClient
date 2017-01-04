package ee.ttu.a103944.shopandr.ui.fragment;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.avast.android.dialogs.core.BaseDialogFragment;

import net.simonvt.numberpicker.NumberPicker;

import ee.ttu.a103944.shopandr.R;


public class AmountDialogFragment extends BaseDialogFragment {

    public static String TAG = "AmountDialogFragment";
    private NumberPicker numberPicker;
    private int prodid;
    private int value;
    private IAmountSelectedLstener listener;

    public static void show(FragmentManager fragmentManager, int id, int value, IAmountSelectedLstener listener) {
        AmountDialogFragment amountDialogFragment = new AmountDialogFragment();
        amountDialogFragment.listener = listener;
        amountDialogFragment.prodid = id;
        amountDialogFragment.value = value;
        amountDialogFragment.setCancelable(true);
        amountDialogFragment.show(fragmentManager, TAG);
    }

    @Override
    protected Builder build(Builder initialBuilder) {
        initialBuilder.setTitle("select amount");
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_amount, null);
        numberPicker = (NumberPicker) view.findViewById(R.id.amount);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(99);
        numberPicker.setValue(value);
        initialBuilder.setView(view);
        initialBuilder.setPositiveButton(R.string.select, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSelect(prodid, numberPicker.getValue(), getActivity());
                dismiss();
            }
        });
        initialBuilder.setNegativeButton(R.string.cancel, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return initialBuilder;
    }

    public interface IAmountSelectedLstener {
        void onSelect(int id, int paramInt, Context context);
    }


}
