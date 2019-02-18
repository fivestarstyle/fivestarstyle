package com.example.fivestarstyle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ChooseOutfit extends AppCompatActivity {
    private ListView lstView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_outfit);

        lstView = (ListView) findViewById(R.id.category_lst);

        //get data and initialize list view adapter
        ChooseOutfitAdapter catLstAdapter = new ChooseOutfitAdapter((getCatSelection()), this);
        lstView.setAdapter(catLstAdapter);
    }

    private List<Model> getCatSelection(){
        List<Model> modelList = new ArrayList<Model>();
        modelList.add(new Model("Casual", false));
        modelList.add(new Model("Cocktail", false));
        modelList.add(new Model("Formal", false));
        modelList.add(new Model("Work", false));
        modelList.add(new Model("Gym", false));
        modelList.add(new Model("Bar", false));

        return modelList;
    }
}
