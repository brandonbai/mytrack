package com.github.brandonbai.jfreechart_demo.pojo;

import com.github.brandonbai.jfreechart_demo.anno.ChartField;

import java.util.Date;

public class SensorInfo {

	@ChartField(name="温度")
	private Integer temp;
	
	@ChartField(name="湿度")
	private Integer humi;
	
	@ChartField(name="光强")
	private Integer illu;
	
	@ChartField(name="雨露")
	private Integer rain;
	
	private Date dateTime;
	
	public Integer getTemp() {
		return temp;
	}

	public void setTemp(Integer temp) {
		this.temp = temp;
	}

	public Integer getHumi() {
		return humi;
	}

	public void setHumi(Integer humi) {
		this.humi = humi;
	}

	public Integer getIllu() {
		return illu;
	}

	public void setIllu(Integer illu) {
		this.illu = illu;
	}

	public Integer getRain() {
		return rain;
	}

	public void setRain(Integer rain) {
		this.rain = rain;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
	

}
