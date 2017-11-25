package com.example.choiseungmin.test_expandable;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class GraphActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_graph);

        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6),
                new DataPoint(5,4),
                new DataPoint(6,9),
                new DataPoint(7,9),
                new DataPoint(8,9),
                new DataPoint(9,9),
                new DataPoint(10,9),
                new DataPoint(11,9),
                new DataPoint(12,9),
                new DataPoint(13,9),
                new DataPoint(14,9),
                new DataPoint(15,9),
        });
        graph.addSeries(series);
    }
}
