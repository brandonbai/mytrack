package com.github.brandonbai.jfreechart_demo.util;

import java.awt.Color;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.github.brandonbai.jfreechart_demo.anno.ChartField;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;

/**
 * 
 * @author brandon
 * @since 2018/6/9
 */
public class JFreeChartUtil {

	private static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	/**
	 * 创建柱状图
	 * @param title 标题
	 * @param dataset 数据集
	 * @return JFreeChart
	 */
	public static JFreeChart createBarChart(String title, DefaultCategoryDataset dataset) {
		JFreeChart barChart = ChartFactory.createBarChart(title,
				"日期",
				"数值",
				dataset,
				PlotOrientation.VERTICAL,
				true,
				true,
				false);

		CategoryPlot plot = (CategoryPlot)barChart.getPlot();
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

		return barChart;

	}

	/**
	 * 创建时序图
	 * @param title 标题
	 * @param dataSet 数据
	 * @return JFreeChart
	 */
	public static JFreeChart createTimeSeriesChart(String title, XYDataset dataSet) {

		JFreeChart chart = ChartFactory.createTimeSeriesChart(title, // title
				"日期", // x-axis label
				"数值", // y-axis label
				dataSet, // data
				true, // create legend?
				true, // generate tooltips?
				false // generate URLs?
				);

		chart.setBackgroundPaint(Color.white);
		XYPlot plot = (XYPlot) chart.getPlot();
		// 背景颜色
		plot.setBackgroundPaint(Color.white);
		// 横纵网格线颜色
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		// xy轴偏移量
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		// 十字线可见
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);
		// 0线可见
		plot.setRangeZeroBaselineVisible(true);
		
		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			// 数据点可见及填充
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
			renderer.setDrawSeriesLineAsPath(true);
		}
		
		DateAxis axis = (DateAxis) plot.getDomainAxis();
		// 设置日期轴格式
		axis.setDateFormatOverride(SIMPLE_DATE_FORMAT);
		
		return chart;

	}

	/**
	 * 创建数据
	 * @param data 数据
	 * @param clz 数据类类型
	 * @param <T> 数据类型
	 * @return XYDataset
	 */
	public static <T> XYDataset createDataSet(List<T> data, Class<T> clz) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

		TimeSeriesCollection dataSet = new TimeSeriesCollection();
		Field timeField = clz.getDeclaredField("dateTime");
		timeField.setAccessible(true);
		Field[] declaredFields = clz.getDeclaredFields();
		for (Field field : declaredFields) {
			boolean annotationPresent = field.isAnnotationPresent(ChartField.class);
			if(annotationPresent) {
				ChartField annotation = field.getAnnotation(ChartField.class);
				String fName = field.getName();
				String name = annotation.name();
				TimeSeries timeSeries = new TimeSeries(name);
				for(T t : data) {
					Method m = clz.getDeclaredMethod("get"+fName.substring(0, 1).toUpperCase()+fName.substring(1));
					m.setAccessible(true);
					Object invoke = m.invoke(t);
					Object object = timeField.get(t);
					timeSeries.addOrUpdate(new Second((Date) object), (Integer)invoke);
				}
                dataSet.addSeries(timeSeries);
			}
		}
		return dataSet;

	}

	/**
	 * 创建折线图
	 * @param title 标题
	 * @param dataSet 数据集
	 * @return JFreeChart
	 */
	public static JFreeChart createLineChart(String title, DefaultCategoryDataset dataSet) {
		JFreeChart lineChart = ChartFactory.createLineChart(
				title,
		         "时间","数值",
		         dataSet,
		         PlotOrientation.VERTICAL,
		         true,true,false);
		CategoryPlot plot = (CategoryPlot)lineChart.getPlot();
		plot.setBackgroundPaint(Color.WHITE);
		plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
		plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
		plot.setDomainGridlinesVisible(true);
		
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		
		LineAndShapeRenderer renderer = (LineAndShapeRenderer)plot.getRenderer();
		
//		renderer.setSeriesPaint(0, Color.RED);
//		renderer.setSeriesPaint(1, new Color(0xEEBD6E));
//		renderer.setSeriesPaint(2, new Color(0x4E9AF1));
//		renderer.setSeriesPaint(3, new Color(0xC43EB6));
//		
//		renderer.setSeriesStroke(0, new BasicStroke(4));
//		renderer.setSeriesStroke(1, new BasicStroke(2));
//		renderer.setSeriesStroke(2, new BasicStroke(2));
//		renderer.setSeriesStroke(3, new BasicStroke(2f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND,
//				3.5f,new float[]{15,10,},0f));
//		
//		renderer.setSeriesShapesFilled(3, false);
		
		// 设置数据点的数值显示
		renderer.setShapesVisible(true);
		renderer.setDrawOutlines(true);
		renderer.setBaseItemLabelsVisible(true);
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.BASELINE_CENTER));
//		renderer.setBaseItemLabelFont(new Font("Dialog", 1, 7));
		renderer.setBaseItemLabelPaint(Color.BLACK);
		return lineChart;
	}

	/**
	 * 创建折线图数据集
	 * @param data 数据
	 * @param clz 数据类类型
	 * @param <T> 数据类型
	 * @return DefaultCategoryDataset
	 */
	public static <T> DefaultCategoryDataset createCategoryDataSet(List<T> data, Class<T> clz) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		Field timeField = clz.getDeclaredField("dateTime");
		timeField.setAccessible(true);
		Field[] declaredFields = clz.getDeclaredFields();
		for (Field field : declaredFields) {
			boolean annotationPresent = field.isAnnotationPresent(ChartField.class);
			if(annotationPresent) {
				ChartField annotation = field.getAnnotation(ChartField.class);
				String fName = field.getName();
				String name = annotation.name();
				for(T t : data) {
					Method m = clz.getDeclaredMethod("get"+fName.substring(0, 1).toUpperCase()+fName.substring(1));
					m.setAccessible(true);
					Object invoke = m.invoke(t);
					Object object = timeField.get(t);
                    dataSet.addValue((Integer)invoke , name , SIMPLE_DATE_FORMAT.format((Date)object));
				}
			}
		}
	    return dataSet;
	}
}
