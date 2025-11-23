package com.mycompany.frontend.Charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 * Standalone Chart Frame for displaying analytics visualizations
 * Opens charts in a separate JFrame window as required
 * 
 * @author SkillForge Team
 */
public class AnalyticsChartFrame extends JFrame {

    public enum ChartType {
        BAR, LINE, PIE, MULTI_BAR, MULTI_LINE
    }

    private ChartType chartType;
    private String chartTitle;
    private String xAxisLabel;
    private String yAxisLabel;
    private Color primaryColor;
    private Color backgroundColor;

    /**
     * Create a new Analytics Chart Frame
     * 
     * @param chartType  Type of chart to display
     * @param title      Frame and chart title
     * @param xAxisLabel X-axis label
     * @param yAxisLabel Y-axis label
     */
    public AnalyticsChartFrame(ChartType chartType, String title, String xAxisLabel, String yAxisLabel) {
        super(title);
        this.chartType = chartType;
        this.chartTitle = title;
        this.xAxisLabel = xAxisLabel;
        this.yAxisLabel = yAxisLabel;
        this.primaryColor = new Color(70, 130, 180);
        this.backgroundColor = Color.WHITE;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
    }

    /**
     * Display a bar chart
     */
    public void showBarChart(DefaultCategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                chartTitle,
                xAxisLabel,
                yAxisLabel,
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false);

        CategoryPlot categoryPlot = chart.getCategoryPlot();
        categoryPlot.setBackgroundPaint(backgroundColor);
        categoryPlot.setRangeGridlinePaint(new Color(220, 220, 220));

        BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
        renderer.setSeriesPaint(0, primaryColor);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(850, 550));

        getContentPane().removeAll();
        getContentPane().add(chartPanel, BorderLayout.CENTER);
        revalidate();
        repaint();

        setVisible(true);
    }

    /**
     * Display a line chart
     */
    public void showLineChart(DefaultCategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createLineChart(
                chartTitle,
                xAxisLabel,
                yAxisLabel,
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false);

        CategoryPlot lineCategoryPlot = chart.getCategoryPlot();
        lineCategoryPlot.setBackgroundPaint(backgroundColor);
        lineCategoryPlot.setRangeGridlinePaint(new Color(220, 220, 220));

        LineAndShapeRenderer lineRenderer = (LineAndShapeRenderer) lineCategoryPlot.getRenderer();
        lineRenderer.setSeriesPaint(0, primaryColor);
        lineRenderer.setSeriesShapesVisible(0, true);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(850, 550));

        getContentPane().removeAll();
        getContentPane().add(chartPanel, BorderLayout.CENTER);
        revalidate();
        repaint();

        setVisible(true);
    }

    /**
     * Display a pie chart
     */
    public void showPieChart(DefaultPieDataset<String> dataset) {
        JFreeChart chart = ChartFactory.createPieChart(
                chartTitle,
                dataset,
                true,
                true,
                false);

        @SuppressWarnings("unchecked")
        PiePlot<String> plot = (PiePlot<String>) chart.getPlot();
        plot.setBackgroundPaint(backgroundColor);
        plot.setOutlineVisible(false);

        // Set section colors
        int sectionCount = dataset.getItemCount();
        for (int i = 0; i < sectionCount; i++) {
            plot.setSectionPaint(dataset.getKey(i), getSectionColor(i, sectionCount));
        }

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(850, 550));

        getContentPane().removeAll();
        getContentPane().add(chartPanel, BorderLayout.CENTER);
        revalidate();
        repaint();

        setVisible(true);
    }

    /**
     * Display a multi-series bar chart
     */
    public void showMultiSeriesBarChart(DefaultCategoryDataset dataset, boolean stacked) {
        JFreeChart chart;

        if (stacked) {
            chart = ChartFactory.createStackedBarChart(
                    chartTitle, xAxisLabel, yAxisLabel, dataset,
                    PlotOrientation.VERTICAL, true, true, false);
        } else {
            chart = ChartFactory.createBarChart(
                    chartTitle, xAxisLabel, yAxisLabel, dataset,
                    PlotOrientation.VERTICAL, true, true, false);
        }

        CategoryPlot categoryPlot = chart.getCategoryPlot();
        categoryPlot.setBackgroundPaint(backgroundColor);
        categoryPlot.setRangeGridlinePaint(new Color(220, 220, 220));

        BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();

        Color[] colors = {
                new Color(70, 130, 180), // Blue
                new Color(40, 167, 69), // Green
                new Color(255, 193, 7), // Yellow
                new Color(220, 53, 69), // Red
                new Color(102, 16, 242), // Purple
                new Color(255, 140, 0) // Orange
        };

        for (int i = 0; i < dataset.getRowCount() && i < colors.length; i++) {
            renderer.setSeriesPaint(i, colors[i]);
        }

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(850, 550));

        getContentPane().removeAll();
        getContentPane().add(chartPanel, BorderLayout.CENTER);
        revalidate();
        repaint();

        setVisible(true);
    }

    /**
     * Display a multi-series line chart
     */
    public void showMultiSeriesLineChart(DefaultCategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createLineChart(
                chartTitle, xAxisLabel, yAxisLabel, dataset,
                PlotOrientation.VERTICAL, true, true, false);

        CategoryPlot lineCategoryPlot = chart.getCategoryPlot();
        lineCategoryPlot.setBackgroundPaint(backgroundColor);
        lineCategoryPlot.setRangeGridlinePaint(new Color(220, 220, 220));

        LineAndShapeRenderer lineRenderer = (LineAndShapeRenderer) lineCategoryPlot.getRenderer();

        Color[] colors = {
                new Color(70, 130, 180), // Blue
                new Color(40, 167, 69), // Green
                new Color(255, 193, 7), // Yellow
                new Color(220, 53, 69), // Red
                new Color(102, 16, 242), // Purple
                new Color(255, 140, 0) // Orange
        };

        for (int i = 0; i < dataset.getRowCount() && i < colors.length; i++) {
            lineRenderer.setSeriesPaint(i, colors[i]);
            lineRenderer.setSeriesShapesVisible(i, true);
        }

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(850, 550));

        getContentPane().removeAll();
        getContentPane().add(chartPanel, BorderLayout.CENTER);
        revalidate();
        repaint();

        setVisible(true);
    }

    /**
     * Get a color for pie chart sections
     */
    private Color getSectionColor(int index, int total) {
        Color[] colors = {
                new Color(70, 130, 180), // Blue
                new Color(40, 167, 69), // Green
                new Color(255, 193, 7), // Yellow
                new Color(220, 53, 69), // Red
                new Color(102, 16, 242), // Purple
                new Color(255, 140, 0), // Orange
                new Color(23, 162, 184), // Cyan
                new Color(108, 117, 125) // Gray
        };

        return colors[index % colors.length];
    }

    // Setters for customization

    public void setPrimaryColor(Color color) {
        this.primaryColor = color;
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
    }

    /**
     * Static factory method to quickly show a bar chart
     */
    public static void displayBarChart(String title, String xAxis, String yAxis, DefaultCategoryDataset dataset) {
        AnalyticsChartFrame frame = new AnalyticsChartFrame(ChartType.BAR, title, xAxis, yAxis);
        frame.showBarChart(dataset);
    }

    /**
     * Static factory method to quickly show a line chart
     */
    public static void displayLineChart(String title, String xAxis, String yAxis, DefaultCategoryDataset dataset) {
        AnalyticsChartFrame frame = new AnalyticsChartFrame(ChartType.LINE, title, xAxis, yAxis);
        frame.showLineChart(dataset);
    }

    /**
     * Static factory method to quickly show a pie chart
     */
    public static void displayPieChart(String title, DefaultPieDataset<String> dataset) {
        AnalyticsChartFrame frame = new AnalyticsChartFrame(ChartType.PIE, title, "", "");
        frame.showPieChart(dataset);
    }

    /**
     * Static factory method to quickly show a multi-series bar chart
     */
    public static void displayMultiBarChart(String title, String xAxis, String yAxis,
            DefaultCategoryDataset dataset, boolean stacked) {
        AnalyticsChartFrame frame = new AnalyticsChartFrame(ChartType.MULTI_BAR, title, xAxis, yAxis);
        frame.showMultiSeriesBarChart(dataset, stacked);
    }

    /**
     * Static factory method to quickly show a multi-series line chart
     */
    public static void displayMultiLineChart(String title, String xAxis, String yAxis, DefaultCategoryDataset dataset) {
        AnalyticsChartFrame frame = new AnalyticsChartFrame(ChartType.MULTI_LINE, title, xAxis, yAxis);
        frame.showMultiSeriesLineChart(dataset);
    }
}
