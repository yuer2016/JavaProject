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
import org.hbase.async.AtomicIncrementRequest;
import org.hbase.async.PutRequest;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

/**
 * 从业人员基础信息列限定符
 * @author yuer
 * */
public class PersonnelBasicInfo implements AsyncHbaseEventSerializer {
	
	private static Logger logger = Logger.getLogger(PersonnelBasicInfo.class);
	
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
	}
	@Override
	public List<PutRequest> getActions() {
		putRequests.clear();
		if (isNotNull(employRowKey)) {
		putRequests.add(new PutRequest(table, RowKey.createRowKeys(employRowKey), columnFamily,"personnelBasicInfo".getBytes(),currentEvent.getBody()));
		}else{
			logger.error("flumePluginShowNullData："+JSON.toJSONString(employRowKey));
		}
		return putRequests;
	}

	@Override
	public List<AtomicIncrementRequest> getIncrements() {
		incrementRequests.clear();
		incrementRequests.add(new AtomicIncrementRequest(table, "personnelbasicinfo".getBytes(), columnFamily, "m".getBytes()) );
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
