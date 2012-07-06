package cn.edu.hbcit.smms.servlet.gameapplyservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.edu.hbcit.smms.dao.gameapplydao.GetPlayerDAO;
import cn.edu.hbcit.smms.pojo.Player;
import cn.edu.hbcit.smms.pojo.PlayerNum;
import cn.edu.hbcit.smms.services.gameapplyservices.*;

/**
 * 2012, 河北工业职业技术学院计算机系2010软件专业.
 * 模块名称： 赛事报名
 * 子模块名称：教工组报名
 *
 *备注：
 *
 * 修改历史：
 * 时间			版本号	姓名		修改内容
 */
/**
 * @author 吕志瑶
 *
 */




public class GetPlayerServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public GetPlayerServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		GetPlayerService itemName  = new GetPlayerService();

		GetPlayerService spn = new GetPlayerService();
		String sportsname = spn.getSportsName();//获取当前运动会名称
		int grouptype = 0;//组别类型设为教工
		int sum = spn.getItemNumber(sportsname,grouptype);
		ArrayList list = new ArrayList();
		list = itemName.getItemName(sportsname,grouptype);
		
		session.setAttribute("mylist", list);//获取当前页面运动会所有项目
		session.setAttribute("num", Integer.valueOf(sum));//获取当前页面运动会所有项目总数
		//
		GetPlayerService player = new GetPlayerService();
		int flag = 0;
		String username = (String)session.getAttribute("username");//获取用户名
		flag = player.getDepartid(username);//根据用户名获取部门id
		int flag1 = player.getSp2dpid(flag);//获取当前组别id及运动会的id
		//
		GetPlayerDAO getPlayerDao = new GetPlayerDAO();
		ArrayList list1 = new ArrayList();
		list1 = getPlayerDao.getPlayerNum(flag1,0);//根据组别id及运动会id获取运动员号码布
		session.setAttribute("playernum",list1);
		//
		GetPlayerService playernum = new GetPlayerService(); 
		PlayerNum p = (PlayerNum)list1.get(0);
		int begin = Integer.parseInt(p.getBeginnum());//得到起始号码
		int end = Integer.parseInt(p.getEndnum());//得到终止号码
		String sql = "insert into t_player (sp2dpid,playernum) values ";//插入sql语句
		for(int i = begin;i <= end;i++){//循环插入到库中的起始到终止号码
			if(i > begin){
				sql = sql + ",";	
			}
			sql = sql + "(" + flag1 + "," + (i + "") + ")";
		}
		playernum.addPlayerBySql(sql);
		//
		
		ArrayList groupList = new ArrayList();
		GetPlayerService getGroupService = new GetPlayerService();
		groupList=getGroupService.selectAllGroupName();
		session.setAttribute("grouplist", groupList);//获取教工组的所有组别
		response.sendRedirect("../apply_teacher.jsp");
		
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}