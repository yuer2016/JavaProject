package org.flumePlugin.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.conf.ComponentConfiguration;
import org.apache.flume.sink.hbase.AsyncHbaseEventSerializer;
import org.apache.log4j.Logger;
import org.flumePlugin.entity.CompanyRowKey;
import org.flumePlugin.util.RowKey;
import org.hbase.async.AtomicIncrementRequest;
import org.hbase.async.PutRequest;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
/**
 * 业务公司插件
 * */
public class BusinessCompany implements AsyncHbaseEventSerializer {
	
	private static Logger logger = Logger.getLogger(BusinessCompany.class);

	//表名
	private byte[] table;
	//列族名
	private byte[] columnFamily;
	//当前事件
	private Event currentEvent;
	//业务公司主键生成类
	private CompanyRowKey companyRowKey;
	//区域编码
	private static final String AREA = "area";
	//业户公司ID
	private static final String ID = "id";
	
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
		companyRowKey = new CompanyRowKey();
		companyRowKey.setArea(headers.get(AREA));
		companyRowKey.setId(headers.get(ID));		
	}
	@Override
	public List<PutRequest> getActions() {
		putRequests.clear();
		if (isNotNull(companyRowKey)) {
			putRequests.add(new PutRequest(table, RowKey.createRowKeys(companyRowKey), columnFamily,"businessCompany".getBytes(),currentEvent.getBody()));
		}else{
			logger.error("flumePluginShowNullData："+JSON.toJSONString(companyRowKey));
		}
		return putRequests;
	}
	@Override
	public List<AtomicIncrementRequest> getIncrements() {
		incrementRequests.clear();
		incrementRequests.add(new AtomicIncrementRequest(table, "businesscompany".getBytes(), columnFamily, "m".getBytes()) );
		return incrementRequests;
	}
	@Override
	public void cleanUp() {
		putRequests.clear();
		incrementRequests.clear();
		table = null;
		columnFamily = null;
		currentEvent = null;
		companyRowKey = null;
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
	public static boolean isNotNull(CompanyRowKey companyRowKey){
		boolean isCorr = true;
		if(companyRowKey != null){
			if(StringUtils.isBlank(companyRowKey.getArea()) 
					|| StringUtils.isBlank(companyRowKey.getId())){
				isCorr = false;
			}
		}
		return isCorr;
	}
}
