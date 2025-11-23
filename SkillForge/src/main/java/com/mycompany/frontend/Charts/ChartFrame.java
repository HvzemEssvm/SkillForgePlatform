package com.mycompany.frontend.Charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
 * Reusable ChartFrame component for displaying various chart types
 * Supports Bar Charts, Line Charts, and Pie Charts
 * Can be embedded in any panel for analytics visualization
 * 
 * @author Zeyad
 */
public class ChartFrame {

    public enum ChartType {
        BAR, LINE, PIE
    }

    private JPanel chartPanel;
    private ChartType chartType;
    private String title;
    private String xAxisLabel;
    private String yAxisLabel;
    private Color primaryColor;
    private Color backgroundColor;

    /**
     * Create a new ChartFrame
     * 
     * @param chartType  Type of chart (BAR, LINE, PIE)
     * @param title      Chart title
     * @param xAxisLabel X-axis label (not used for PIE)
     * @param yAxisLabel Y-axis label (not used for PIE)
     */
    public ChartFrame(ChartType chartType, String title, String xAxisLabel, String yAxisLabel) {
        this.chartType = chartType;
        this.title = title;
        this.xAxisLabel = xAxisLabel;
        this.yAxisLabel = yAxisLabel;
        this.primaryColor = new Color(70, 130, 180); // Default blue
        this.backgroundColor = Color.WHITE;
        this.chartPanel = new JPanel(new BorderLayout());
    }

    /**
     * Create a bar chart with category dataset
     */
    public JPanel createBarChart(DefaultCategoryDataset dataset) {
        return createBarChart(dataset, 700, 400);
    }

    /**
     * Create a bar chart with custom dimensions
     */
    public JPanel createBarChart(DefaultCategoryDataset dataset, int width, int height) {
        JFreeChart chart = ChartFactory.createBarChart(
                title,
                xAxisLabel,
                yAxisLabel,
                dataset,
                PlotOrientation.VERTICAL,
                false, // No legend
                true, // Tooltips
                false // URLs
        );

        // Customize appearance
        CategoryPlot categoryPlot = chart.getCategoryPlot();
        categoryPlot.setBackgroundPaint(backgroundColor);
        categoryPlot.setRangeGridlinePaint(new Color(220, 220, 220));

        BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
        renderer.setSeriesPaint(0, primaryColor);

        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(width, height));

        chartPanel.removeAll();
        chartPanel.add(panel, BorderLayout.CENTER);
        chartPanel.revalidate();
        chartPanel.repaint();

        return chartPanel;
    }

    /**
     * Create a line chart with category dataset
     */
    public JPanel createLineChart(DefaultCategoryDataset dataset) {
        return createLineChart(dataset, 700, 400);
    }

    /**
     * Create a line chart with custom dimensions
     */
    public JPanel createLineChart(DefaultCategoryDataset dataset, int width, int height) {
        JFreeChart chart = ChartFactory.createLineChart(
                title,
                xAxisLabel,
                yAxisLabel,
                dataset,
                PlotOrientation.VERTICAL,
                false, // No legend
                true, // Tooltips
                false // URLs
        );

        // Customize appearance
        CategoryPlot lineCategoryPlot = chart.getCategoryPlot();
        lineCategoryPlot.setBackgroundPaint(backgroundColor);
        lineCategoryPlot.setRangeGridlinePaint(new Color(220, 220, 220));

        LineAndShapeRenderer lineRenderer = (LineAndShapeRenderer) lineCategoryPlot.getRenderer();
        lineRenderer.setSeriesPaint(0, primaryColor);
        lineRenderer.setSeriesShapesVisible(0, true); // Show data points

        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(width, height));

        chartPanel.removeAll();
        chartPanel.add(panel, BorderLayout.CENTER);
        chartPanel.revalidate();
        chartPanel.repaint();

        return chartPanel;
    }

    /**
     * Create a pie chart with pie dataset
     */
    public JPanel createPieChart(DefaultPieDataset dataset) {
        return createPieChart(dataset, 700, 400);
    }

    /**
     * Create a pie chart with custom dimensions
     */
    public JPanel createPieChart(DefaultPieDataset dataset, int width, int height) {
        JFreeChart chart = ChartFactory.createPieChart(
                title,
                dataset,
                true, // Legend
                true, // Tooltips
                false // URLs
        );

        // Customize appearance
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(backgroundColor);
        plot.setOutlineVisible(false);

        // Set section colors
        int sectionCount = dataset.getItemCount();
        for (int i = 0; i < sectionCount; i++) {
            plot.setSectionPaint(dataset.getKey(i), getSectionColor(i, sectionCount));
        }

        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(width, height));

        chartPanel.removeAll();
        chartPanel.add(panel, BorderLayout.CENTER);
        chartPanel.revalidate();
        chartPanel.repaint();

        return chartPanel;
    }

    /**
     * Create a multi-series bar chart (stacked or grouped)
     */
    public JPanel createMultiSeriesBarChart(DefaultCategoryDataset dataset, boolean stacked) {
        return createMultiSeriesBarChart(dataset, stacked, 700, 400);
    }

    /**
     * Create a multi-series bar chart with custom dimensions
     */
    public JPanel createMultiSeriesBarChart(DefaultCategoryDataset dataset, boolean stacked, int width, int height) {
        JFreeChart chart;

        if (stacked) {
            chart = ChartFactory.createStackedBarChart(
                    title, xAxisLabel, yAxisLabel, dataset,
                    PlotOrientation.VERTICAL, true, true, false);
        } else {
            chart = ChartFactory.createBarChart(
                    title, xAxisLabel, yAxisLabel, dataset,
                    PlotOrientation.VERTICAL, true, true, false);
        }

        CategoryPlot categoryPlot = chart.getCategoryPlot();
        categoryPlot.setBackgroundPaint(backgroundColor);
        categoryPlot.setRangeGridlinePaint(new Color(220, 220, 220));

        BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();

        // Set colors for multiple series
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

        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(width, height));

        chartPanel.removeAll();
        chartPanel.add(panel, BorderLayout.CENTER);
        chartPanel.revalidate();
        chartPanel.repaint();

        return chartPanel;
    }

    /**
     * Create a multi-series line chart
     */
    public JPanel createMultiSeriesLineChart(DefaultCategoryDataset dataset) {
        return createMultiSeriesLineChart(dataset, 700, 400);
    }

    /**
     * Create a multi-series line chart with custom dimensions
     */
    public JPanel createMultiSeriesLineChart(DefaultCategoryDataset dataset, int width, int height) {
        JFreeChart chart = ChartFactory.createLineChart(
                title, xAxisLabel, yAxisLabel, dataset,
                PlotOrientation.VERTICAL, true, true, false);

        CategoryPlot lineCategoryPlot = chart.getCategoryPlot();
        lineCategoryPlot.setBackgroundPaint(backgroundColor);
        lineCategoryPlot.setRangeGridlinePaint(new Color(220, 220, 220));

        LineAndShapeRenderer lineRenderer = (LineAndShapeRenderer) lineCategoryPlot.getRenderer();

        // Set colors for multiple series
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

        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(width, height));

        chartPanel.removeAll();
        chartPanel.add(panel, BorderLayout.CENTER);
        chartPanel.revalidate();
        chartPanel.repaint();

        return chartPanel;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setXAxisLabel(String label) {
        this.xAxisLabel = label;
    }

    public void setYAxisLabel(String label) {
        this.yAxisLabel = label;
    }

    public JPanel getChartPanel() {
        return chartPanel;
    }
}
