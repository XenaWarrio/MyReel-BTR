package dx.queen.myreeltest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;

public class ChooseSex extends AppCompatActivity {

    public static String sex;


    RadioButton rbMan;
    ImageButton ibFurther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_sex);
        rbMan = findViewById(R.id.rb_man);
       ibFurther = findViewById(R.id.ib_further);

        whatsex();
        ibFurther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

