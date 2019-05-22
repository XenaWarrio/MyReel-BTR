package search.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import dx.queen.myreeltest.R;
import search.Model.Person;

public class MyAdapter extends PagerAdapter {

    Context context;
    List<Person>personList;
    LayoutInflater layoutInflater;

    public MyAdapter(Context context, List<Person> personList) {
        this.context = context;
        this.personList = personList;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return personList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager)container).removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //inflate view
        View v  = layoutInflater.inflate(R. layout.view_pager_item, container, false);
        //view
        ImageView personPhoto = v.findViewById(R.id.profile_pic);
        TextView personName = v.findViewById(R.id.profile_name);
        TextView personDescription = v.findViewById(R.id.profile_description);
        FloatingActionButton favorite = v.findViewById(R.id.fav);
        //set Data

        Picasso.get().load(personList.get(position).getImage()).into(personPhoto);

        personName.setText(personList.get(position).getName());
        personDescription.setText(personList.get(position).getDescription());

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context , "Добавленно к любимчикам", Toast.LENGTH_SHORT ).show();
            }
        });

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context , "Смотри хоть до умопомрачения. Действуй.", Toast.LENGTH_SHORT ).show();

            }
        });

        container.addView(v);
        return v;
    }
}
