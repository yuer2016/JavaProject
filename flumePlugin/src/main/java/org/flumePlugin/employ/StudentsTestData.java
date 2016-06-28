package org.flumePlugin.employ;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.conf.ComponentConfiguration;
import org.apache.flume.sink.hbase.AsyncHbaseEventSerializer;
import org.apache.log4j.Logger;
import org.flumePlugin.entity.EmployRowKey;
import org.flumePlugin.util.RowKey;
import org.flumePlugin.util.Tools;
import org.hbase.async.AtomicIncrementRequest;
import org.hbase.async.PutRequest;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
/**
 * 学员考试数据插件
 * @author yuer
 * */
public class StudentsTestData implements AsyncHbaseEventSerializer {

	private static Logger logger = Logger.getLogger(StudentsTestData.class);

	//表名
	private byte[] table;
	//列族名
	private byte[] columnFamily;
	//当前事件
	private Event currentEvent;
	//生成从业人员主键
	private EmployRowKey employRowKey;
	//区域编码
	private static final String AREA = "area";
	//从业人员身份证号
	private static final String IDCARD = "idCard";
	//考试行业
	private static final String PROFESSIONNAME = "professionName";
	//学员考试开始时间
	private static final String CREATEDATE ="createDate";
	//学员考试结束时间
	private static final String ENDEXAMTIME="endExamTime";

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
		employRowKey = new EmployRowKey();
		employRowKey.setArea(headers.get(AREA));
		employRowKey.setIdCard(headers.get(IDCARD));
		employRowKey.setProfessionName(headers.get(PROFESSIONNAME));
		employRowKey.setCreateDate(headers.get(CREATEDATE));
		employRowKey.setEndExamTime(headers.get(ENDEXAMTIME));
	}
	@Override
	public List<PutRequest> getActions() {
		putRequests.clear();
		if (isNotNull(employRowKey)) {
			putRequests.add(new PutRequest(table, RowKey.createRowKeys(employRowKey), columnFamily,createQualifier(employRowKey),currentEvent.getBody()));
		}else{
			logger.error("flumePluginShowNullData："+JSON.toJSONString(employRowKey));
		}
		return putRequests;

	}

	@Override
	public List<AtomicIncrementRequest> getIncrements() {
		incrementRequests.clear();
		incrementRequests.add(new AtomicIncrementRequest(table, "studentstestdata".getBytes(), columnFamily, "m".getBytes()) );
		return incrementRequests;
	}

	@Override
	public void cleanUp() {
		putRequests.clear();
		incrementRequests.clear();
		table = null;
		columnFamily = null;
		currentEvent = null;
		employRowKey = null;
	}

	/**
	 * 生成学员考试数据列限定符，规则：开始时间＋结束时间+考试行业
	 * */
	private byte[] createQualifier(EmployRowKey employRowKey){
		StringBuilder sb = new StringBuilder();
		sb.append(Tools.formatDateTime(employRowKey.getCreateDate()));
		sb.append(Tools.formatDateTime(employRowKey.getEndExamTime()));
		sb.append(Tools.base64Encode(employRowKey.getProfessionName().getBytes()));
		return sb.toString().getBytes();
	}

	@Override
	public void configure(Context arg0) {

	}
	@Override
	public void configure(ComponentConfiguration arg0) {

	}
	/**
	 * 判断headers是否有空值,非空为true,空为false
	 * @param companyRowKey
	 * @return
	 */
	public static boolean isNotNull(EmployRowKey employRowKey){
		boolean isCorr = true;
		if(employRowKey != null){
			if(StringUtils.isBlank(employRowKey.getArea()) 
					|| StringUtils.isBlank(employRowKey.getIdCard())){
				isCorr = false;
			}
		}
		return isCorr;
	}
}
