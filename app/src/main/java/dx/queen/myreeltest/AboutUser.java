package dx.queen.myreeltest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AboutUser extends AppCompatActivity {
//сделать иф , кнопка не может перейти на следующий экран, если поля пустые, джсон обьект в фаербазе стораже
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_user);
    }
}
