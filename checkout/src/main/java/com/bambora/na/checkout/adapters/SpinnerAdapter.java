package com.bambora.na.checkout.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

/**
 * Created by dlight on 2016-09-19.
 */

public class SpinnerAdapter extends ArrayAdapter<String> {

    private String hint;

    public SpinnerAdapter(Context context, int layoutResource, List<String> list, String hint) {
        super(context, layoutResource, list);
        this.hint = hint;
    }

    public static void selectItem(Spinner spinner, String item) {
        int position = 0;
        SpinnerAdapter adapter = (SpinnerAdapter) spinner.getAdapter();
        if (adapter != null) {
            position = adapter.getPosition(item);
        }
        spinner.setSelection(position);
    }

    @Override
    public int getCount() {
        return super.getCount() + 1;
    }

    @Override
    public int getPosition(String item) {
        return super.getPosition(item) + 1;
    }

    @Override
    public String getItem(int position) {
        if (position == 0) {
            return hint;
        }
        return super.getItem(position - 1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean isEnabled(int position) {
        return position != 0;
    }
}
