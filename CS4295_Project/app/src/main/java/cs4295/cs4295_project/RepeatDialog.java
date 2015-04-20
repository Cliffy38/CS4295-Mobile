package cs4295.cs4295_project;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;


public class RepeatDialog extends Dialog {
    private ArrayList<String> mList;
    private Context mContext;
    private Spinner mSpinner;
    private DialogListener mReadyListener;
    SharedPreferences settingsPrefs;
    private String selection;

    public RepeatDialog() {
        super(null);
    }


    public RepeatDialog(Context context, DialogListener readyListener,String selected) {
        super(context);
        mReadyListener = readyListener;
        mContext = context;
        selection = selected;
        mList = new ArrayList<String>();
        mList.add("0");
        mList.add("1");
        mList.add("2");
        mList.add("3");
        mList.add("4");
        mList.add("5");
        mList.add("6");
        mList.add("7");
        mList.add("8");
        mList.add("9");
        mList.add("10");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog);

        mSpinner = (Spinner) findViewById(R.id.dialog_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, mList);
        mSpinner.setAdapter(adapter);

        int spinnerPosition = adapter.getPosition(selection);

        //set the default according to value
        mSpinner.setSelection(spinnerPosition);

        Button buttonOK = (Button) findViewById(R.id.dialogOK);
        Button buttonCancel = (Button) findViewById(R.id.dialogCancel);
        buttonOK.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {
                int n = mSpinner.getSelectedItemPosition();
                mReadyListener.ready(mList.get(n));
                RepeatDialog.this.dismiss();
            }
        });
        buttonCancel.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {
                mReadyListener.cancelled();
                RepeatDialog.this.dismiss();
            }
        });
    }

    public interface DialogListener {
        public void ready(String n);

        public void cancelled();
    }
}





