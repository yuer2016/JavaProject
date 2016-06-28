package flumePlugin;

import java.util.Date;

import org.flumePlugin.entity.CompanyRowKey;
import org.flumePlugin.entity.DrivingRowKey;
import org.flumePlugin.entity.EmployRowKey;
import org.flumePlugin.entity.StudentRowKey;
import org.flumePlugin.util.RowKey;
import org.flumePlugin.util.Tools;
import org.junit.Test;



public class RowKeyTest {
	
	@Test
	public void testCreateRowKey(){
		DrivingRowKey drivingRowKey = new DrivingRowKey();
		drivingRowKey = new DrivingRowKey();
		drivingRowKey.setArea("130100");
		drivingRowKey.setId("0000001");
		System.out.println("驾校rowkey："+new String(RowKey.createRowKeys(drivingRowKey)));
		
		StudentRowKey studentRowKey = new StudentRowKey();
		studentRowKey = new StudentRowKey();
		studentRowKey.setArea("130100");
		studentRowKey.setIdCard("621021199002200030");
		System.out.println("学员rowkey："+new String(RowKey.createRowKeys(studentRowKey)));
		
		CompanyRowKey companyRowKey = new CompanyRowKey();
		companyRowKey = new CompanyRowKey();
		companyRowKey.setArea("130100");
		companyRowKey.setId("234");	
		System.out.println("业户公司rowkey："+new String(RowKey.createRowKeys(companyRowKey)));
		
		EmployRowKey employRowKey = new EmployRowKey();
		employRowKey = new EmployRowKey();
		employRowKey.setArea("130100");
		employRowKey.setIdCard("621021199002200030");
		System.out.println("从业人员rowkey："+new String(RowKey.createRowKeys(employRowKey)));
		
		
	}
	
	@Test
	public void testQualifier(){
		StudentRowKey studentRowKey = new StudentRowKey();
		studentRowKey = new StudentRowKey();
		studentRowKey.setArea("130100");
		studentRowKey.setIdCard("621021199002200030");
		
		studentRowKey.setBeginTime("2016-01-19 16:02:39");
		studentRowKey.setTid("456");
		studentRowKey.setCardNo("冀A34567");
		studentRowKey.setSrId("23");
		
		studentRowKey.setApplyNote("2016-01-20 14:00:00");
		studentRowKey.setSubject("考试科目");
		System.out.println("学习过程Qualifier："+new String(createQualifier_ld(studentRowKey)));
		System.out.println("学员考试Qualifier："+new String(createQualifier_test(studentRowKey)));
		
		EmployRowKey employRowKey = new EmployRowKey();
		employRowKey = new EmployRowKey();
		employRowKey.setArea("130100");
		employRowKey.setIdCard("621021199002200030");
		
		employRowKey.setStartPlayTime("2016-01-19 16:02:39");
		employRowKey.setPlayTime("2016-01-19 18:08:23");
		
		employRowKey.setCertificate("621021199002200030");
		employRowKey.setProfessionType("从业资格证类型");
		
		employRowKey.setCreateDate("2016-01-14 13:02:39");
		employRowKey.setEndExamTime("2016-01-14 15:07:11");
		employRowKey.setProfessionName("考试行业");
		
		System.out.println("继续教育Qualifier："+new String(createQualifier_ce(employRowKey)));
		System.out.println("从业资格证Qualifier："+new String(createQualifier_job(employRowKey)));
		System.out.println("继续教育学员考试Qualifier："+new String(createQualifier_stu(employRowKey)));
		
	}
	
	/**
	 * 生成学习过程数据列限定符,规则：开始时间+教练id＋车牌号＋唯一id
	 * */
	private byte[] createQualifier_ld(StudentRowKey studentRowKey){
		StringBuilder sb = new StringBuilder();
		sb.append(Tools.formatDateTime(studentRowKey.getBeginTime()));
		sb.append("_");
		sb.append(studentRowKey.getTid());
		sb.append("_");
		sb.append(Tools.base64Encode(studentRowKey.getCardNo().getBytes()));
		sb.append("_");
		sb.append(studentRowKey.getSrId());
		return sb.toString().getBytes();
	}
	
	/**
	 *	生成学员考试数据列限定符，规则：申请时间+考试科目
	 * */
	private byte[] createQualifier_test(StudentRowKey studentRowKey){
		StringBuilder sb = new StringBuilder();
		sb.append(Tools.formatDateTime(studentRowKey.getApplyNote()));
		sb.append(Tools.base64Encode(studentRowKey.getSubject().getBytes()));
		return sb.toString().getBytes();
	}

	/**
	 * 继续教育过程数据列限定符，规则：开始时间＋结束时间
	 * */
	private byte[] createQualifier_ce(EmployRowKey employRowKey){
		StringBuilder sb = new StringBuilder();
		sb.append(Tools.formatDateTime(employRowKey.getStartPlayTime()));
		sb.append(Tools.formatDateTime(employRowKey.getPlayTime()));
		return sb.toString().getBytes();
	}
	
	/**
	 * 生成从业资格证信息列限定符，规则：从业资格证号＋从业资格证类型＋插入时间
	 * */
	private byte[] createQualifier_job(EmployRowKey employRowKey){
		StringBuilder sb = new StringBuilder();
		sb.append(employRowKey.getCertificate());
		sb.append(Tools.base64Encode(employRowKey.getProfessionType().getBytes()));
		sb.append(Tools.formatDateTime(new Date()));
		return sb.toString().getBytes();
	}
	
	/**
	 * 生成学员考试数据列限定符，规则：开始时间＋结束时间+考试行业
	 * */
	private byte[] createQualifier_stu(EmployRowKey employRowKey){
		StringBuilder sb = new StringBuilder();
		sb.append(Tools.formatDateTime(employRowKey.getCreateDate()));
		sb.append(Tools.formatDateTime(employRowKey.getEndExamTime()));
		sb.append(Tools.base64Encode(employRowKey.getProfessionName().getBytes()));
		return sb.toString().getBytes();
	}

}
