package cn.ms.gateway.common.area;

import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * 数据区
 * 
 * @author lry
 * @version v1.0
 */
public class DataArea {
	
    /**
     * 系统对象主键:session、cookie、localAddre....
     */
    public static final String SYSTEM = "sys";
    /**
     * 输入对象主键
     */
    public static final String INPUT = "input";
    /**
     * 输出对象主键
     */
    public static final String OUTPUT = "output";
    
    
    private final Map<String, Object> data;
    
    // 禁止外部调用者使用构造函数构造出数据区，必须使用build方法。
    private DataArea() {
        this.data = new LinkedHashMap<String, Object>();
    }
    
    private DataArea(Map<String, Object> data) {
        if(data==null)
            throw new RuntimeException("构建数据区的对象为null");
        this.data = data;
    }
    
    //put one
    public static DataArea buildWithSys(String key,Object value) {
    	DataArea dataArea=new DataArea();
    	MapListDataContext mdc=dataArea.getSys();
    	mdc.put(key, value);
    	dataArea.setSys(mdc);
        return dataArea;
    }
    public static DataArea buildWithInput(String key,Object value) {
    	DataArea dataArea=new DataArea();
    	MapListDataContext mdc=dataArea.getInput();
    	mdc.put(key, value);
    	dataArea.setInput(mdc);
        return dataArea;
    }
    public static DataArea buildWithOutput(String key,Object value) {
    	DataArea dataArea=new DataArea();
    	MapListDataContext mdc=dataArea.getOutput();
    	mdc.put(key, value);
    	dataArea.setOutput(mdc);
        return dataArea;
    }
    
    // build方法
    public static DataArea buildWithEmpty() {
        return new DataArea();
    }
    public static DataArea buildWithData(Map<String, Object> data) {
        return new DataArea(data);
    }
    public static DataArea buildWithSystem(Map<String, Object> system) {
        return new DataArea().setSys(system);
    }
    public static DataArea buildWithInput(Map<String, Object> input) {
        return new DataArea().setInput(input);
    }
    public static DataArea buildWithOutput(Map<String, Object> output) {
        return new DataArea().setOutput(output);
    }
    
    @SuppressWarnings("unchecked")
    private MapListDataContext getDataObj(String key) {
        Object value = data.get(key);
        if (value instanceof MapListDataContext)
            return (MapListDataContext)value;
        
        if (value != null && !(value instanceof Map))
            throw new IllegalArgumentException("报文的第一级只能为map对象, value=[" + value + "]");
        
        MapListDataContext ret;
        if (value != null) {
            ret = new MapListDataContext((Map<String, Object>)value);
        } else {
            ret = new MapListDataContext();
        }
        data.put(key, ret);
        return ret;
    }
    
    private DataArea putDataObj(String key, Map<String, Object> value) {
    	if(value==null)
    		throw new RuntimeException(key+"对象的Map是空对象");
        if (!(value instanceof MapListDataContext))
            value = new MapListDataContext(value);
        data.put(key, value);
        return this;
    }
    

    public MapListDataContext getSys() {
        return this.getDataObj(SYSTEM);
    }

    
    public MapListDataContext getInput() {
        return this.getDataObj(INPUT);
    }

    public DataArea setSys(Map<String, Object> system) {
        return this.putDataObj(SYSTEM, system);
    }
    

    
    public DataArea setInput(Map<String, Object> input) {
        return this.putDataObj(INPUT, input);
    }

    
    public MapListDataContext getOutput() {
        return this.getDataObj(OUTPUT);
    }
    
    
    public DataArea setOutput(Map<String, Object> data) {
        return this.putDataObj(OUTPUT, data);
    }
    

    
    //2016.04.20 修改getData为queryData,解决序列化为json串的结果字段
    public Map<String, Object> queryData() {
        return data;
    }
    
    /**
     * 获取空报文体
     * @return
     */
    public static String toBuildWithEmpty() {
        return buildWithEmpty().toJSONString();
    }
    
    /**
     * 转为json字符串
     * @return
     */
    public String toJSONString() {
        return JSON.toJSONString(this);
    }
    
    public String toString() {
        return data.toString();
    }
    
}
