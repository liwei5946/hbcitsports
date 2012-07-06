/**
 * Copyright(C) 2012, 河北工业职业技术学院.
 *
 * 模块名称：	赛前管理
 * 子模块名称：	运动会管理
 *
 * 备注：
 *
 * 修改历史：
 * 时间			版本号		姓名			修改内容
 * 2012-6-19		V1.0		李玮 		新建
*/
package cn.edu.hbcit.smms.dao.gamesetdao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import cn.edu.hbcit.smms.dao.databasedao.DBConn;
import cn.edu.hbcit.smms.pojo.Sports;

/**
 * 运动会管理类
 *
 * 本类的简要描述：
 * 负责运动会赛前管理
 *
 * @author 李玮
 * @version 1.00  2012-6-19 新建类
 */
public class SportsDAO {
	private ResultSet rs = null;
	private Connection conn = null;
	private PreparedStatement pStatement = null;
	protected final Logger log = Logger.getLogger(SportsDAO.class.getName());
	DBConn db = new DBConn();
	
	/**
	 * 获取所有运动会信息
	 * @return
	 */
	public ArrayList selectSportsInfo(){
		ArrayList list = new ArrayList();
		String sql = "SELECT id,sportsname,sportsbegin,sportsend,registend,address,current FROM t_sports ORDER BY sportsbegin DESC";
		conn = db.getConn();
		try{
			pStatement = conn.prepareStatement(sql);
			rs = pStatement.executeQuery();
			while(rs.next()){
				Sports sp = new Sports();
				sp.setId(rs.getInt(1));
				sp.setSportsname(rs.getString(2));
				sp.setSportsbegin(rs.getString(3));
				sp.setSportsend(rs.getString(4));
				sp.setRegistend(rs.getString(5));
				sp.setAddress(rs.getString(6));
				sp.setCurrent(rs.getInt(7));
				list.add(sp);
			}
			rs.close();
			pStatement.close();
			db.freeConnection(conn);
		}catch(Exception e){
			log.error("获取运动会信息失败！");
			log.error(e.getMessage());
		}
		return list;
	}
	/**
	 * 获取指定ID的运动会信息
	 * @param int sportsId
	 * @return
	 */
	public ArrayList selectSportsInfoById(int sportsId){
		ArrayList list = new ArrayList();
		String sql = "SELECT id,sportsname,sportsbegin,sportsend,registend,address,current FROM t_sports WHERE id=?";
		conn = db.getConn();
		try{
			pStatement = conn.prepareStatement(sql);
			pStatement.setInt(1, sportsId);
			rs = pStatement.executeQuery();
			while(rs.next()){
				Sports sp = new Sports();
				sp.setId(rs.getInt(1));
				sp.setSportsname(rs.getString(2));
				sp.setSportsbegin(rs.getString(3));
				sp.setSportsend(rs.getString(4));
				sp.setRegistend(rs.getString(5));
				sp.setAddress(rs.getString(6));
				sp.setCurrent(rs.getInt(7));
				list.add(sp);
			}
			rs.close();
			pStatement.close();
			db.freeConnection(conn);
		}catch(Exception e){
			log.error("获取运动会信息By ID失败！");
			log.error(e.getMessage());
		}
		return list;
	}
	/**
	 * 设置当前运动会
	 * @param userId
	 * @return
	 */
	public boolean setCurrSports(int spId){
		boolean rst = false;
		int flag_sql_1 = 0;
		int flag_sql_2 = 0;
		conn = db.getConn();
		String sql_1 = "UPDATE t_sports SET current=0 WHERE current=1"; //将运动会为“当前”的全部设为“非当前”
		String sql_2 = "UPDATE t_sports SET current=1 WHERE id=?";	//将指定ID的运动会设为“当前”
		try{
			//将所有current为1的重置为0
			pStatement = conn.prepareStatement(sql_1);
			flag_sql_1 = pStatement.executeUpdate();
			pStatement.close();
			//将指定ID的记录设为1
			pStatement = conn.prepareStatement(sql_2);
			pStatement.setInt(1, spId);
			flag_sql_2 = pStatement.executeUpdate();
			//
			log.debug("flag_sql_1:"+flag_sql_1+"\t flag_sql_2:"+flag_sql_2);
			if( flag_sql_1>0 && flag_sql_2>0){
				rst = true;
			}
			pStatement.close();
			db.freeConnection(conn);
		}catch(Exception e){
			log.error("设置当前运动会失败！");
			log.error(e.getMessage());
		}
		return rst;
	}
	
	/**
	 * 新增运动会
	 * @param sportsName
	 * @param begin
	 * @param end
	 * @param registEnd
	 * @param address
	 * @return
	 */
	public boolean addSports(String sportsName, String begin, String end, String registEnd, String address){
		boolean flag = false;
		int rst = 0;
		conn = db.getConn();
		String sql = "INSERT INTO t_sports (sportsname,sportsbegin,sportsend,registend,address,current) VALUES(?,?,?,?,?,?)"; 
		try{
			pStatement = conn.prepareStatement(sql);
			pStatement.setString(1, sportsName);
			pStatement.setString(2, begin);
			pStatement.setString(3, end);
			pStatement.setString(4, registEnd);
			pStatement.setString(5, address);
			pStatement.setInt(6, 0);
			rst = pStatement.executeUpdate();
			//
			if( rst>0 ){
				flag = true;
			}
			pStatement.close();
			db.freeConnection(conn);
		}catch(Exception e){
			log.error("添加运动会失败！");
			log.error(e.getMessage());
		}
		return flag;
	}
	
	/**
	 * 修改运动会By id
	 * @param id
	 * @param sportsName
	 * @param begin
	 * @param end
	 * @param registEnd
	 * @param address
	 * @return
	 */
	public boolean updateSports(int id,String sportsName, String begin, String end, String registEnd, String address){
		boolean flag = false;
		int rst = 0;
		conn = db.getConn();
		String sql = "UPDATE t_sports SET sportsname=?,sportsbegin=?,sportsend=?,registend=?,address=? WHERE id=?"; 
		try{
			pStatement = conn.prepareStatement(sql);
			pStatement.setString(1, sportsName);
			pStatement.setString(2, begin);
			pStatement.setString(3, end);
			pStatement.setString(4, registEnd);
			pStatement.setString(5, address);
			pStatement.setInt(6, id);
			rst = pStatement.executeUpdate();
			//
			if( rst>0 ){
				flag = true;
			}
			pStatement.close();
			db.freeConnection(conn);
		}catch(Exception e){
			log.error("添加运动会失败！");
			log.error(e.getMessage());
		}
		return flag;
	}

	/**
	 * 删除指定ID运动会
	 * @param id
	 * @return
	 */
	public boolean removeSports(int id){
		boolean flag = false;
		int rst = 0;
		conn = db.getConn();
		String sql = "DELETE FROM t_sports WHERE id=?"; 
		try{
			pStatement = conn.prepareStatement(sql);
			pStatement.setInt(1, id);
			rst = pStatement.executeUpdate();
			//
			if( rst>0 ){
				flag = true;
			}
			pStatement.close();
			db.freeConnection(conn);
		}catch(Exception e){
			log.error("删除运动会失败！");
			log.error(e.getMessage());
		}
		return flag;
	}
	
}