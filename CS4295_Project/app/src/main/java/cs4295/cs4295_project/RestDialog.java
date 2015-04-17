package cs4295.cs4295_project;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


public class RestDialog extends Dialog {
    private ArrayList<String> mList;
    private Context mContext;
    private Spinner mSpinner;
    private DialogListener mReadyListener;
    private TextView title;

    public RestDialog() {
        super(null);
    }


    public RestDialog(Context context, DialogListener readyListener) {
        super(context);
        mReadyListener = readyListener;
        mContext = context;
        mList = new ArrayList<String>();
        mList.add("5");
        mList.add("10");
        mList.add("15");
        mList.add("20");


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog);

        title = (TextView)findViewById(R.id.dialog_title);
        title.setText("Break Time");

        mSpinner = (Spinner) findViewById(R.id.dialog_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, mList);
        mSpinner.setAdapter(adapter);

        Button buttonOK = (Button) findViewById(R.id.dialogOK);
        Button buttonCancel = (Button) findViewById(R.id.dialogCancel);
        buttonOK.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int n = mSpinner.getSelectedItemPosition();
                mReadyListener.ready(mList.get(n));
                RestDialog.this.dismiss();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mReadyListener.cancelled();
                RestDialog.this.dismiss();
            }
        });
    }

    public interface DialogListener {
        public void ready(String n);

        public void cancelled();
    }
}





