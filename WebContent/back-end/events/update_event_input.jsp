<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.event.model.*"%>

<%
  EventVO eventVO = (EventVO) request.getAttribute("eventVO"); //EmpServlet.java (Concroller) �s�Jreq��empVO���� (�]�A�������X��empVO, �]�]�A��J��ƿ��~�ɪ�empVO����)
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>���ʸ�ƭק� - update_event_input.jsp</title>

<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

<style>
  table {
	width: 450px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #CCCCFF;
  }
  th, td {
    padding: 1px;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
	<tr><td>
		 <h3>���ʸ�ƭק� - update_emp_input.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">�^����</a></h4>
	</td></tr>
</table>

<h3>��ƭק�:</h3>

<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">�Эץ��H�U���~:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/event/EventServlet" name="form1" enctype="multipart/form-data">
<table>
	<tr>
		<td>���ʼ��D:</td>
		<td><input type="TEXT" name="event_title" size="45" 
			 value="<%= (eventVO==null)? "�п�J���ʼ��D" : eventVO.getEvent_title()%>" /></td>
	</tr>
	<tr>
		<td>�ֹνs��:</td>
		<td><input type="TEXT" name="band_id" size="45" 
			 value="<%= (eventVO==null)? "�п�J���ʼ��D" : eventVO.getBand_id()%>" /></td>
	</tr>
	<tr>
		<td>��������:</td>
		<td><select name="event_type" size="1" >
		<option value="0"<%=(eventVO==null)? "" : (eventVO.getEvent_type()== 0)?"selected":""%>>�@�묡��</option>
		<option value="1"<%=(eventVO==null)? "" : (eventVO.getEvent_type()== 1)?"selected":""%>>���P�n�h�B</option>
		<option value="2"<%=(eventVO==null)? "" : (eventVO.getEvent_type()== 2)?"selected":""%>>�S�⬡��</option>
		<option value="3"<%=(eventVO==null)? "" : (eventVO.getEvent_type()== 3)?"selected":""%>>�D������</option>
		<option value="4"<%=(eventVO==null)? "" : (eventVO.getEvent_type()== 4)?"selected":""%>>�̷s����</option>
		</select></td>
	</tr>
	<tr>
		<td>���ʶ}�l�ɶ�:</td>
		<td><input name="event_start_time" id="f_date1" type="text"></td>
	</tr>
	<tr>
		<td>���ʸԱ�:</td>
		<td><input type="TEXT" name="event_detail"  
			 value="<%= (eventVO==null)? "�п�J���ʼ��D" : eventVO.getEvent_detail()%>" /></td>
	</tr>
	<tr>
		<td>���ʱƧ�:</td>
		<td><input type="TEXT" name="event_sort" size="46"
			 value="<%= (eventVO==null)? "�п�J0-99���Ʀr" : eventVO.getEvent_sort()%>" /></td>
	</tr>
	<tr>
		<td>���ʮ���:</td>
		<td><input type="file" name="event_poster" id="event_poster"></td>
		<td>�w����</td>
     	<td><img id="event_poster_img" src="<%=(eventVO==null)?"":eventVO.getEvent_poster() %>"></td>
	</tr>
	 
	<tr>
		<td>���ʳ��a:</td>
		<td><input type="TEXT" name="event_place" size="45"
			 value="<%= (eventVO==null)? "0" : eventVO.getEvent_place()%>" /></td>
	</tr>
	<tr>
		<td>���ʰϰ�:</td>
		<td><select name="event_area" size="1" >
		<option value="0"<%=(eventVO==null)? "" : (eventVO.getEvent_area()== 0)?"selected":""%>>�_��</option>
		<option value="1"<%=(eventVO==null)? "" : (eventVO.getEvent_area()== 1)?"selected":""%>>����</option>
		<option value="2"<%=(eventVO==null)? "" : (eventVO.getEvent_area()== 2)?"selected":""%>>�n��</option>
		<option value="3"<%=(eventVO==null)? "" : (eventVO.getEvent_area()== 3)?"selected":""%>>�F��</option>
		<option value="4"<%=(eventVO==null)? "" : (eventVO.getEvent_area()== 4)?"selected":""%>>���q</option>
		</select></td>
	</tr>
	<tr>
		<td>���ʿ���:</td>
		<td><input type="TEXT" name="event_city" size="45"
			 value="<%= (eventVO==null)? "0" : eventVO.getEvent_city()%>" /></td>
	</tr>
	<tr>
		<td>���ʿ�������:</td>
		<td><input type="TEXT" name="event_cityarea" size="45"
			 value="<%= (eventVO==null)? "0" : eventVO.getEvent_cityarea()%>" /></td>
	</tr>
	<tr>
		<td>���ʦa�}:</td>
		<td><input type="TEXT" name="event_address" size="45"
			 value="<%= (eventVO==null)? "0" : eventVO.getEvent_address()%>" /></td>
	</tr>
	<tr>
		<td>���ʤW�[�ɶ�:</td>
		<td><input name="event_on_time" id="f_date2" type="text"></td>
	</tr>
	<tr>
		<td>���ʪ��A:</td>
		<td><select name="event_status" size="1" >
		<option value="0"<%=(eventVO==null)? "" : (eventVO.getEvent_status()== 0)?"selected":""%>>�U�[</option>
		<option value="1"<%=(eventVO==null)? "" : (eventVO.getEvent_status()== 1)?"selected":""%>>�W�[</option>
		</select></td>
	</tr>
	<tr>
		<td>�y���:</td>
		<td><input type="file" name="event_seat" size="45" id="event_seat"></td>
		<td>�w����</td>
     	<td><img id="event_seat_img" src="<%=(eventVO==null)?"":eventVO.getEvent_seat() %>"></td>
<%-- 			 value="<%= (eventVO==null)? "0" : eventVO.getEvent_seat()</td> --%>
	</tr>
	

<%-- 	<jsp:useBean id="deptSvc" scope="page" class="com.dept.model.DeptService" /> --%>
<!-- 	<tr> -->
<!-- 		<td>����:<font color=red><b>*</b></font></td> -->
<!-- 		<td><select size="1" name="deptno"> -->
<%-- 			<c:forEach var="deptVO" items="${deptSvc.all}"> --%>
<%-- 				<option value="${deptVO.deptno}" ${(empVO.deptno==deptVO.deptno)? 'selected':'' } >${deptVO.dname} --%>
<%-- 			</c:forEach> --%>
<!-- 		</select></td> -->
<!-- 	</tr> -->

</table>
<br>
<input type="hidden" name="event_last_edit_time" id ="event_last_edit_time">
<input type="hidden" name="event_last_editor" value="members00000">
<input type="hidden" name="event_id" value="${eventVO.event_id}">
<input type="hidden" name="action" value="update">
<input type="submit" value="�e�X�s�W" id="submit"></FORM>
</body>



<!-- =========================================�H�U�� datetimepicker �������]�w========================================== -->

<% 
  java.sql.Timestamp event_start_time = null;
  java.sql.Timestamp event_on_time = null;
  try {
	    event_start_time = eventVO.getEvent_start_time();
   } catch (Exception e) {
	    event_start_time = new java.sql.Timestamp(System.currentTimeMillis());
   }
  try {
	    event_on_time = eventVO.getEvent_on_time();
 } catch (Exception e) {
	    event_on_time = new java.sql.Timestamp(System.currentTimeMillis());
 }
%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/back-end/events/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/back-end/events/js/jquery3.5.1.min.js"></script>
<script src="<%=request.getContextPath()%>/back-end/events/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/back-end/events/datetimepicker/jquery.datetimepicker.full.js"></script>

<style>
  .xdsoft_datetimepicker .xdsoft_datepicker {
           width:  300px;   /* width:  300px; */
  }
  .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
           height: 151px;   /* height:  151px; */
  }
</style>

<script>
        $.datetimepicker.setLocale('zh');
        $('#f_date1').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:true,       //timepicker:true,
	       step: 10,                //step: 60 (�o�Otimepicker���w�]���j60����)
	       format:'Y-m-d H:i',         //format:'Y-m-d H:i:s',
		   value: '<%=event_start_time%>', // value:   new Date(),
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // �h���S�w���t
           //startDate:	            '2017/07/10',  // �_�l��
           //minDate:               '-1970-01-01', // �h������(���t)���e
           //maxDate:               '+1970-01-01'  // �h������(���t)����
        });
        
        $('#f_date2').datetimepicker({
 	       theme: '',              //theme: 'dark',
 	       timepicker:true,       //timepicker:true,
 	       step: 10,                //step: 60 (�o�Otimepicker���w�]���j60����)
 	       format:'Y-m-d H:i',         //format:'Y-m-d H:i:s',
 		   value: '<%=event_on_time%>', // value:   new Date(),
            //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // �h���S�w���t
            //startDate:	            '2017/07/10',  // �_�l��
            //minDate:               '-1970-01-01', // �h������(���t)���e
            //maxDate:               '+1970-01-01'  // �h������(���t)����
         });
   
        // ----------------------------------------------------------�H�U�ΨӱƩw�L�k��ܪ����-----------------------------------------------------------

        //      1.�H�U���Y�@�Ѥ��e������L�k���
        //      var somedate1 = new Date('2017-06-15');
        //      $('#f_date1').datetimepicker({
        //          beforeShowDay: function(date) {
        //        	  if (  date.getYear() <  somedate1.getYear() || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
        //              ) {
        //                   return [false, ""]
        //              }
        //              return [true, ""];
        //      }});

        
        //      2.�H�U���Y�@�Ѥ��᪺����L�k���
        //      var somedate2 = new Date('2017-06-15');
        //      $('#f_date1').datetimepicker({
        //          beforeShowDay: function(date) {
        //        	  if (  date.getYear() >  somedate2.getYear() || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
        //              ) {
        //                   return [false, ""]
        //              }
        //              return [true, ""];
        //      }});


        //      3.�H�U����Ӥ�����~������L�k��� (�]�i���ݭn������L���)
        //      var somedate1 = new Date('2017-06-15');
        //      var somedate2 = new Date('2017-06-25');
        //      $('#f_date1').datetimepicker({
        //          beforeShowDay: function(date) {
        //        	  if (  date.getYear() <  somedate1.getYear() || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
        //		             ||
        //		            date.getYear() >  somedate2.getYear() || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
        //              ) {
        //                   return [false, ""]
        //              }
        //              return [true, ""];
        //      }});
        $("#event_poster").on("change",function(e){
             let reader = new FileReader();
             console.log(this.files)
             reader.readAsDataURL(this.files[0]);
             reader.addEventListener("load",function(e){
                 $("#event_poster_img").attr("src",reader.result);
             })
         })
         $("#event_seat").on("change",function(e){
             let reader = new FileReader();
             console.log(this.files)
             reader.readAsDataURL(this.files[0]);
             reader.addEventListener("load",function(e){
                 $("#event_seat_img").attr("src",reader.result);
             })
         })
         
         $("#submit").on("click",function(e){
        	 $("#event_last_edit_time").attr("value",new Date().getTime());
         })
</script>
</html>