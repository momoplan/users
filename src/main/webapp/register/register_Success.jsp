<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>  
<%@ page import="net.sf.json.JSONObject,com.ruyicai.util.JSONReslutUtil,com.ruyicai.util.NameUtil" %>
<%
	JSONObject tuserinfo = JSONReslutUtil.getUserInfo(request);
	String username = NameUtil.getNameUtilJson(tuserinfo.getJSONObject("value"));
	
%> 
<script>getBalance();</script>
<div class="ZhuCe_titleBg">
	<span class="ZhuCe_titleLeft">快速注册</span>
</div>

<ul class="ZhuCe_end">
	<li><strong><%=username %></strong>，欢迎您！</li>
	<li>您的账户余额为<i id="final_money">￥0.00</i>元，请先充值，然后开始购买彩票。</li>	
</ul>
<table cellspacing="0" cellpadding="0" class="ZhuCe_endTable">
	<tr>
		<td width="126"><a href="http://www.ruyicai.com/rules/user.html?key=4" class="ZhuCe_btn" title="立即充值">立即充值</a></td>
		<th>充值免手续费</th>
	</tr>
</table>
<div class="ZhuCe_line"></div>

<div class="ZhuCe_guide">
<!--左下的表格-->
<table width="48%" cellspacing="0" cellpadding="0" class="ZhuCe_infor">
	<tr>
		<td colspan="3"><b>您还可以：</b></td>
	</tr>
	<tr>
		<td width="30%"><a href="http://www.ruyicai.com/rules/user.html?key=33" class="ZhuCe_greyBtn" title="完善购彩资料">完善购彩资料</a></td>
		<th width="20%">实名信息</th>
		<td width="50%">购彩、领奖的实名认证信息</td>
	</tr>
	<tr>
		<td><a href="http://www.ruyicai.com/fucaituijian/fucaituijian_shuangseqiu_1.html" class="ZhuCe_greyBtn" title="浏览专家推荐">浏览专家推荐</a></td>
		<th>专家说彩</th>
		<td>各彩种专家每期倾情奉献推荐号码</td>
	</tr>
	<tr>
		<td><a href="http://bbs.ruyicai.com" class="ZhuCe_greyBtn" title="到论坛逛逛">进入论坛逛逛</a></td>
		<th>彩民论坛</th>
		<td>广大彩民交流购彩经验的平台</td>
	</tr>
	
</table>
<!--左下的表格end-->

<!--右下的表格-->
<table width="50%" cellspacing="0" cellpadding="0" class="ZhuCe_buy">
	<tr>
		<td colspan="3"><em>进入<a href="http://www.ruyicai.com/include/goucaidating.html" title="购彩大厅">购彩大厅>></a></em></td>
	</tr>
	<tr><td colspan="3">&nbsp;</td></tr>
	<tr>
		<td width="50%">
			<table cellspacing="0" cellpadding="0" class="ZhuCe_kinds">
				<tr>
					<th rowspan="2"><img src="../images/logo_ssq_b.gif" width="54" height="54" /></th>
					<td><i>双色球</i></td>
				</tr>
				<tr>
					<td>每周二、四、日晚开奖，单注最高奖金1000万元！</td>
				</tr>
				<tr>
					<th><a href="http://www.ruyicai.com/shuangseqiu.html" class="ZhuCe_greyBtnS" title="立即投注">立即投注</a></th>
					<th><a href="http://www.ruyicai.com/hemai/shuanseqiuhemai.html" class="ZhuCe_greyBtnS" title="参与合买">参与合买</a></th>
				</tr>

			</table>

		</td>
		<td width="1%">&#160;</td>
		<td width="49%">
			<table cellspacing="0" cellpadding="0" class="ZhuCe_kinds">
				<tr>
					<th rowspan="2"><img src="../images/logo_dlt_b.gif" width="54" height="54" /></th>
					<td><i>大乐透</i></td>
				</tr>
				<tr>
					<td>每周一、三、六晚开奖，3元可赢取1600万元！</td>
				</tr>
				<tr>
					<th><a href="http://www.ruyicai.com/daletou.html" class="ZhuCe_greyBtnS" title="立即投注">立即投注</a></th>
					<th><a href="http://www.ruyicai.com/hemai/daletouhemai.html" class="ZhuCe_greyBtnS" title="参与合买">参与合买</a></th>
				</tr>
			</table>
		</td>
	</tr>
</table>

</div>
