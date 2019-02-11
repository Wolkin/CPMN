package com.db.access.operate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.db.access.DBConnection;

public class BettingRecord {
	private int record = 0;
	//定义投注记录字段
	private String expect = "";     //期数
	private String user = "";       //投注人
	private int redball1 = 0;       //红色球
	private int redball2 = 0;       //红色球
	private int redball3 = 0;       //红色球
	private int redball4 = 0;       //红色球
	private int redball5 = 0;       //红色球
	private int redball6 = 0;       //红色球
	private int blueball = 0;       //蓝色球
	private String isuse = "";      //是否中奖
	private String profit = "";     //收益
	private String inputdate = "";  //投注时间
	
	/**
	 * 
	 * @param data[][]
	 */
	public void insertRecord(String expect,String user,String[][] data,String rand) {
		String[] temp = new String[7];
		this.record = data.length;
		
		for(int count = 0 ; count < data.length ; count++) {
			for(int n = 0 ; n < data[count].length ; n++) {
				temp[n] = data[count][n];
			}
			insertRecord(expect,user,temp,rand);
		}
	}
	
	/**
	 * 
	 * @param data[]
	 */
	public void insertRecord(String expect,String user,String[] data,String rand) {
		if(data.length == 7) {
			insertRecord(expect,user,data[0],data[1],data[2],data[3],data[4],data[5],data[6],rand);
		}else {
			System.out.println("传入参数异常，字段长度错误!");
		}
	}
	
	/**
	 * 插入投注记录
	 * @param expect
	 * @param user
	 * @param redball1
	 * @param redball2
	 * @param redball3
	 * @param redball4
	 * @param redball5
	 * @param redball6
	 * @param blueball
	 */
	public void insertRecord(String expect,String user,String redball1,String redball2,String redball3,String redball4,String redball5,String redball6,
			String blueball,String rand) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String guessDate = df.format(new Date());
		String sSql = " insert into betting_record(expect,user,redball1,redball2,redball3,redball4,redball5,redball6,blueball,isuse,investment,inputdate,random) " + 
					  " values('" + expect + "'," + 
					  " '" + user + "'," + 
					  " '" + redball1 + "'," + 
					  " '" + redball2 + "'," + 
					  " '" + redball3 + "'," + 
					  " '" + redball4 + "'," + 
					  " '" + redball5 + "'," + 
					  " '" + redball6 + "'," + 
					  " '" + blueball + "'," + 
					  " 'N'," + 
					  " 1," + 
					  " '" + guessDate + "'," + 
					  " '" + rand + "')";
		
		String isSql = " select count(1) as num from betting_record where random = '" + rand + "' ";
		
		Connection conn = new DBConnection().getConnection();
		Statement stat = null;
		Statement stat1 = null;
		
		try {
			stat = conn.createStatement();
			stat1 = conn.createStatement();
			ResultSet rs = stat1.executeQuery(isSql);
			if(rs.next()) {
				if(rs.getInt("num") <= this.record) {
					stat.execute(sSql);
				}else {
					System.out.println("已经存在该期记录！");
				}
			}
			rs.getStatement().close();
			System.out.println(sSql);
			
			stat.close();
			conn.close();
		} catch (SQLException e) {
			if (stat != null) {
				try {
					stat.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String getExpect() {
		return expect;
	}
	public void setExpect(String expect) {
		this.expect = expect;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public int getRedball1() {
		return redball1;
	}
	public void setRedball1(int redball1) {
		this.redball1 = redball1;
	}
	public int getRedball2() {
		return redball2;
	}
	public void setRedball2(int redball2) {
		this.redball2 = redball2;
	}
	public int getRedball3() {
		return redball3;
	}
	public void setRedball3(int redball3) {
		this.redball3 = redball3;
	}
	public int getRedball4() {
		return redball4;
	}
	public void setRedball4(int redball4) {
		this.redball4 = redball4;
	}
	public int getRedball5() {
		return redball5;
	}
	public void setRedball5(int redball5) {
		this.redball5 = redball5;
	}
	public int getRedball6() {
		return redball6;
	}
	public void setRedball6(int redball6) {
		this.redball6 = redball6;
	}
	public int getBlueball() {
		return blueball;
	}
	public void setBlueball(int blueball) {
		this.blueball = blueball;
	}
	public String getIsuse() {
		return isuse;
	}
	public void setIsuse(String isuse) {
		this.isuse = isuse;
	}
	public String getProfit() {
		return profit;
	}
	public void setProfit(String profit) {
		this.profit = profit;
	}
	public String getInputdate() {
		return inputdate;
	}
	public void setInputdate(String inputdate) {
		this.inputdate = inputdate;
	}
}
