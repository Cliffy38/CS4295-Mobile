package cs4295.cs4295_project;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;


public class Pause extends ActionBarActivity implements OnClickListener {

    private ImageButton next , play , previous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pause);

        next = (ImageButton) findViewById(R.id.btnNext);
        play = (ImageButton) findViewById(R.id.btnPlay);
        previous = (ImageButton) findViewById(R.id.btnPrevious);

        next.setOnClickListener(this);
        play.setOnClickListener(this);
        previous.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNext:
                Toast.makeText(Pause.this, "Next is clicked!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnPlay:
                Toast.makeText(Pause.this, "Play is clicked!", Toast.LENGTH_LONG).show();
                break;
            case R.id.btnPrevious:
                Toast.makeText(Pause.this, "Previous is clicked!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pause, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
