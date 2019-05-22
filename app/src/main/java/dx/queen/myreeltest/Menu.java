package dx.queen.myreeltest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import search.Search;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.profile:
                intentFunc(ProfileActivity.class);
                return true;
            case R.id.messenger:

                return true;
            case R.id.search:
                intentFunc(Search.class);
                return true;
            case R.id.favorite:

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void  intentFunc (Class classy){
        Intent intent  =  new Intent(Menu.this,classy);
        startActivity(intent);
    }
}

