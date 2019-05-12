package regisrtation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RadioButton;

import dx.queen.myreeltest.R;

public class ChooseSex extends AppCompatActivity {

    public static String sex;


    RadioButton rbMan;
    ImageButton ibFurther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_choose_sex);
        rbMan = findViewById(R.id.rb_man);
       ibFurther = findViewById(R.id.ib_further);

        whatsex();
        ibFurther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(ChooseSex.this , AboutUser.class);
                startActivity(intent);
            }
    });
    }

    public String  whatsex() {

        if (rbMan.isChecked()) {
            sex = "man";
        }else{
            sex = "woman";
        }
        return sex;
  }


    }

