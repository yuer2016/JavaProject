package org.flumePlugin.student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.conf.ComponentConfiguration;
import org.apache.flume.sink.hbase.AsyncHbaseEventSerializer;
import org.apache.log4j.Logger;
import org.flumePlugin.entity.StudentRowKey;
import org.flumePlugin.util.RowKey;
import org.flumePlugin.util.Tools;
import org.hbase.async.AtomicIncrementRequest;
import org.hbase.async.PutRequest;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

/**
 * 学习过程数据插件
 * @author yuer
 * */
public class LearningData implements AsyncHbaseEventSerializer {
	
	private static Logger logger = Logger.getLogger(LearningData.class);

	//表名
	private byte[] table;
	//列族名
	private byte[] columnFamily;
	//当前事件
	private Event currentEvent;
	//学员主键实体类
	private StudentRowKey studentRowKey;
	//区域编码
	private static final String AREA = "area";
	//驾校全称
//	private static final String NAME = "name";
	//驾校ID
//	private static final String ID = "id";
	//学习开始时间
	private static final String BEGINTIME = "beginTime";
	//学员身份证号
	private static final String IDCARD = "idCard";
	//所属教练Id
	private static final String TID = "tid";
	//所属教练车车牌号
	private static final String CARDNO = "cardNo";
	//学习过程id
	private static final String SRID = "srId";
	
	private final ArrayList<PutRequest> putRequests = Lists.newArrayList();

	private final ArrayList<AtomicIncrementRequest> incrementRequests = Lists.newArrayList();

	@Override
	public void initialize(byte[] table, byte[] cf) {
		this.table = table;
		this.columnFamily = cf;

	}
	@Override
	public void setEvent(Event event) {
		this.currentEvent = event;
		Map<String, String> headers = currentEvent.getHeaders();
		studentRowKey = new StudentRowKey();
		studentRowKey.setArea(headers.get(AREA));
//		studentRowKey.setName(headers.get(NAME));
//		studentRowKey.setId(headers.get(ID));
		studentRowKey.setIdCard(headers.get(IDCARD));
		studentRowKey.setBeginTime(headers.get(BEGINTIME));
		studentRowKey.setTid(headers.get(TID));
		studentRowKey.setCardNo(headers.get(CARDNO));
		studentRowKey.setSrId(headers.get(SRID));

	}
	@Override
	public List<PutRequest> getActions() {
		putRequests.clear();
		if (isNotNull(studentRowKey)) {
		    putRequests.add(new PutRequest(table, RowKey.createRowKeys(studentRowKey), columnFamily,createQualifier(studentRowKey),currentEvent.getBody()));
		}else{
			logger.error("flumePluginShowNullData："+JSON.toJSONString(studentRowKey));
		}
		return putRequests;
	}

	@Override
	public List<AtomicIncrementRequest> getIncrements() {
		incrementRequests.clear();
		incrementRequests.add(new AtomicIncrementRequest(table, "learningdata".getBytes(), columnFamily, "m".getBytes()) );
		return incrementRequests;
	}
	@Override
	public void cleanUp() {
		putRequests.clear();
		incrementRequests.clear();
		table = null;
		columnFamily = null;
		currentEvent = null;
		studentRowKey = null;

	}	
	@Override
	public void configure(Context arg0) {

	}
	@Override
	public void configure(ComponentConfiguration arg0) {

	}
	/**
	 * 生成学习过程数据列限定符,规则：开始时间+教练id＋车牌号＋唯一id
	 * */
	private byte[] createQualifier(StudentRowKey studentRowKey){
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
	 * 判断headers是否有空值,非空为true,空为false
	 * @param companyRowKey
	 * @return
	 */
	public static boolean isNotNull(StudentRowKey studentRowKey){
		boolean isCorr = true;
		if(studentRowKey != null){
			if(StringUtils.isBlank(studentRowKey.getArea()) 
//					|| StringUtils.isBlank(studentRowKey.getName()) 
//					|| StringUtils.isBlank(studentRowKey.getId()) 
					|| StringUtils.isBlank(studentRowKey.getBeginTime()) 
					|| StringUtils.isBlank(studentRowKey.getIdCard()) 
					|| StringUtils.isBlank(studentRowKey.getTid())
					|| StringUtils.isBlank(studentRowKey.getCardNo())
					|| StringUtils.isBlank(studentRowKey.getSrId())){
				isCorr = false;
			}
		}
		return isCorr;
	}
}
