package com.example.prog3projekt.HelperClasses;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;

import com.example.prog3projekt.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Date;

public class GraphViewHelper {
    /** <i>Tom Sattler</i>
     * Hilfsmethode um den Graphen mit der richtigen größe(x- und y-Achse) und dem richtigen UI zu bauen */
    public static void setUpGraphView(GraphView graphView, int maxX, int maxY) {
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(0);
        graphView.getViewport().setMaxY(maxY);
        graphView.getViewport().setMinX(1);
        graphView.getViewport().setMaxX(maxX);
        graphView.setTitleColor(R.color.main_color);
        graphView.setTitleTextSize(18);
        graphView.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        graphView.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
        graphView.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
    }
    /** <i>Tom Sattler</i>
     *  Hilfsmethode die den Grapgen mit der richtigen größe und dem richtigem Ui baut, hier mit
     *  Daten auf der x-Achse. */
    public static void setUpGraphViewWithDate(GraphView graphView, Date minX, Date maxX, int maxY, Activity context) {
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(0);
        graphView.getViewport().setMaxY(maxY);
        graphView.getViewport().setMinX(minX.getTime());
        graphView.getViewport().setMaxX(maxX.getTime());
        graphView.setTitleColor(R.color.main_color);
        graphView.setTitleTextSize(10);
        graphView.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        graphView.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
        graphView.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
        graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(context));
        graphView.getGridLabelRenderer().setNumHorizontalLabels(3);
    }

    /** <i>Tom Sattler</i>
     *  Hilsmethoden um bei upgedatetem DataSet den Graphen neu zu zeichnen */
    public static void updateGraphView(GraphView graphView, LineGraphSeries<DataPoint> s) {
        graphView.removeAllSeries();
        s.setThickness(10);
        graphView.addSeries(s);
    }
}
