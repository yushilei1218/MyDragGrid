package com.yushilei.mydraggrid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import com.yushilei.mydraggrid.adapter.DragAdapter;
import com.yushilei.mydraggrid.widget.DragParent;
import com.yushilei.mydraggrid.widget.DragGridView;

public class MainActivity extends AppCompatActivity {

    private DragParent parent;
    private DragGridView drag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parent = (DragParent) findViewById(R.id.parent);
        drag = (DragGridView) findViewById(R.id.drag);

        drag.setLayoutManager(new GridLayoutManager(this, 3));

        DragAdapter dragAdapter = new DragAdapter(this);

        dragAdapter.setListener(parent);

        parent.setListener(drag);

        drag.setAdapter(dragAdapter);
    }
}
