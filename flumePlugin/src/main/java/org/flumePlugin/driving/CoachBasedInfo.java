package org.flumePlugin.driving;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.conf.ComponentConfiguration;
import org.apache.flume.sink.hbase.AsyncHbaseEventSerializer;
import org.apache.log4j.Logger;
import org.flumePlugin.entity.DrivingRowKey;
import org.flumePlugin.util.RowKey;
import org.hbase.async.AtomicIncrementRequest;
import org.hbase.async.PutRequest;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

/**
 * 教练基础信息插件
 * @author yuer
 * */
public class CoachBasedInfo implements AsyncHbaseEventSerializer {
	
	private static Logger logger = Logger.getLogger(CoachBasedInfo.class);
	
	//表名
	private byte[] table;
	//列族名
	private byte[] columnFamily;
	//当前事件
	private Event currentEvent;
	//驾校主键实体类
	private DrivingRowKey drivingRowKey;

	private static final String AREA = "area";

//	private static final String NAME = "name";

	private static final String ID = "id";

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
		drivingRowKey = new DrivingRowKey();
		drivingRowKey.setArea(headers.get(AREA));
//		drivingRowKey.setName(headers.get(NAME));
		drivingRowKey.setId(headers.get(ID));
		drivingRowKey.setIdCard(headers.get(IDCARD));
	}

	@Override
	public List<PutRequest> getActions() {
		putRequests.clear();
		if (isNotNull(drivingRowKey)) {
			putRequests.add(new PutRequest(table, RowKey.createRowKeys(drivingRowKey), columnFamily,drivingRowKey.getIdCard().getBytes(),currentEvent.getBody()));
		}else{
			logger.error("flumePluginShowNullData："+JSON.toJSONString(drivingRowKey));
		}
		return putRequests;
	}

	@Override
	public List<AtomicIncrementRequest> getIncrements() {
		incrementRequests.clear();
		incrementRequests.add(new AtomicIncrementRequest(table, "coachbasedinfo".getBytes(), columnFamily, "m".getBytes()) );
		return incrementRequests;
	}

	@Override
	public void cleanUp() {
		putRequests.clear();
		incrementRequests.clear();
		table = null;
		columnFamily = null;
		currentEvent = null;
		drivingRowKey = null;
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
	public static boolean isNotNull(DrivingRowKey drivingRowKey){
		boolean isCorr = true;
		if(drivingRowKey != null){
			if(StringUtils.isBlank(drivingRowKey.getArea()) 
//					|| StringUtils.isBlank(drivingRowKey.getName()) 
					|| StringUtils.isBlank(drivingRowKey.getId()) 
					|| StringUtils.isBlank(drivingRowKey.getIdCard())){
				isCorr = false;
			}
		}
		return isCorr;
	}
}
