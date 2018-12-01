package com.github.brandonbai.jfreechart_demo;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import com.github.brandonbai.jfreechart_demo.pojo.SensorInfo;
import com.github.brandonbai.jfreechart_demo.util.JFreeChartUtil;
import org.jfree.chart.JFreeChart;

/**
 * Hello world!
 *
 */
public class App {

	static List<SensorInfo> data = null;
	static String baseDir = System.getProperty("user.home");

	public static void main(String[] args) {
		before();
		drawImage1();

	}

	public static void before() {
		int size = 10;
    	Random random = new Random();
    	Calendar c = Calendar.getInstance();
    	data = new ArrayList<>();
    	for (int i = 0; i < size; i++) {
    		c.add(Calendar.DAY_OF_MONTH, 1);
    		SensorInfo sensorInfo = new SensorInfo();
    		sensorInfo.setDateTime(c.getTime());
    		sensorInfo.setTemp(random.nextInt(20)+10);
    		sensorInfo.setHumi(random.nextInt(20) + 30);
    		sensorInfo.setIllu(random.nextInt(20) + 50);
    		sensorInfo.setRain(random.nextInt(20) + 70);
    		data.add(sensorInfo);
		}
	}
	
    public static void drawImage1() {
		try {
			JFreeChart chart = JFreeChartUtil.createBarChart("数据监测", JFreeChartUtil.createCategoryDataSet(data, SensorInfo.class));
			BufferedImage image = chart.createBufferedImage(800, 450);
			File dest = new File(baseDir+"/Desktop/abc.png");
			ImageIO.write(image, "png", dest);
			Desktop.getDesktop().open(dest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    }
    
    public static void drawImage2() {
		try {
			JFreeChart chart = JFreeChartUtil.createLineChart("数据监测", JFreeChartUtil.createCategoryDataSet(data, SensorInfo.class));
			BufferedImage image = chart.createBufferedImage(800, 450);
			File dest = new File(baseDir+"/Desktop/abc.png");
			ImageIO.write(image, "png", dest);
			Desktop.getDesktop().open(dest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    }

	public static void drawImage3() {
		try {
			JFreeChart chart = JFreeChartUtil.createTimeSeriesChart("数据监测", JFreeChartUtil.createDataSet(data, SensorInfo.class));
			BufferedImage image = chart.createBufferedImage(800, 450);
			File dest = new File(baseDir+"/Desktop/abc.png");
			ImageIO.write(image, "png", dest);
			Desktop.getDesktop().open(dest);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
    
}
