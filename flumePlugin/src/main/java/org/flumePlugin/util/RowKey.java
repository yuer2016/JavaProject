package org.flumePlugin.util;

import org.flumePlugin.entity.CompanyRowKey;
import org.flumePlugin.entity.DrivingRowKey;
import org.flumePlugin.entity.EmployRowKey;
import org.flumePlugin.entity.StudentRowKey;

/**
 * 生成Hbase主键类
 * @author yuer
 * */
public class RowKey {

	public static byte[] createRowKeys(Object object){
		byte[] result = null;
		if(object instanceof DrivingRowKey){
			result = createDrivingRowKey((DrivingRowKey)object);
		}
		if(object instanceof StudentRowKey){
			result = createStudentRowKey((StudentRowKey)object);
		}
		if(object instanceof CompanyRowKey){
			result = createCompanyRowKey((CompanyRowKey)object);
		}
		if(object instanceof EmployRowKey){
			result = createEmployRowKey((EmployRowKey)object);
		}
		return result;
	}


	/**
	 * 驾校表rowkey生成,规则：区域＋ID（7位）
	 * @param DrivingRowKey
	 * @return 
	 * */
	private static byte[] createDrivingRowKey(DrivingRowKey drivingRowKey){
		StringBuilder sb = new StringBuilder();
		sb.append(drivingRowKey.getArea());
//		sb.append(drivingRowKey.getName());
		sb.append(Tools.getIdFormat(drivingRowKey.getId()));
		return sb.toString().getBytes();
	}
	/**
	 * 学员表rowkey生成，规则：区域＋身份证号
	 * */
	private static byte[] createStudentRowKey(StudentRowKey studentRowKey){
		StringBuilder sb = new StringBuilder();
		sb.append(studentRowKey.getArea());
//		sb.append(studentRowKey.getName());
//		sb.append(studentRowKey.getId());
		sb.append(studentRowKey.getIdCard());
		return sb.toString().getBytes();
	}
	/**
	 * 	业务公司rowkey生成，规则：区域 ＋id（7位）
	 * */
	private static byte[] createCompanyRowKey(CompanyRowKey companyRowKey){
		StringBuilder sb = new StringBuilder();
		sb.append(companyRowKey.getArea());
//		sb.append(companyRowKey.getName());
		sb.append(Tools.getIdFormat(companyRowKey.getId()));
		return sb.toString().getBytes();
	}
	/**
	 * 从业人员rowkey生成，规则：区域+身份证号码
	 * */
	private static byte[] createEmployRowKey(EmployRowKey employRowKey){
		StringBuilder sb = new StringBuilder();
		sb.append(employRowKey.getArea());
		sb.append(employRowKey.getIdCard());
		return sb.toString().getBytes();
	}

}
