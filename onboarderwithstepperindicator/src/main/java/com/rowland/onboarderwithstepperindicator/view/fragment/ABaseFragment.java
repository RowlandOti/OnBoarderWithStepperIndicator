package com.rowland.onboarderwithstepperindicator.view.fragment;

import android.support.v4.app.Fragment;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Base {@link Fragment} class for every fragment in this application.
 */
public abstract class ABaseFragment extends Fragment {

    // Class log identifier
    public final static String LOG_TAG = ABaseFragment.class.getSimpleName();

    /**
     * Shows a {@link Toast} message.
     *
     * @param message An string representing a message to be shown.
     */
    protected void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Shows a {@link EditText} message.
     *
     * @param etToBeValidated An EditText whose data is to be validated.
     */
    private boolean isValidEditTextData(EditText etToBeValidated) {
        if (etToBeValidated.getText().toString().trim().length() == 0) {
            etToBeValidated.setError("Required");
            return false;
        }
        return true;
    }

    /**
     * Shows a {@link EditText} message.
     *
     * @param etToBeValidated An array of EditText whose data are to be validated.
     */
    protected boolean isValidEditTextData(EditText... etToBeValidated) {
        boolean isEtTextDataValid = true;
        int i = 0;
        while (i < etToBeValidated.length && isEtTextDataValid) {
            EditText et = etToBeValidated[i];
            isEtTextDataValid = isValidEditTextData(et);
            i++;
        }
        return isEtTextDataValid;
    }
}
