package cn.ms.gateway.common.area;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 数据上下文
 * @author lry
 */
public class MapListDataContext implements Map<String, Object> {
	
	private static String DEFAULT_NAME_OF_RECORDS = "records";
	private final Map<String, Object> data;
	
	public MapListDataContext() {
		data = new LinkedHashMap<String, Object>();
	}

	public MapListDataContext(Map<String, Object> data) {
		this.data = data;
	}

	public Map<String, Object> getMap() {
		return data;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getMap(String key) {
		Map<String, Object> ret = (Map<String, Object>) data.get(key);
		if (ret == null) {
			ret = new LinkedHashMap<String, Object>();
			data.put(key, ret);
		}
		return ret;
	}

	public MapListDataContext getMapListDataContext(String key) {
		MapListDataContext mdc=new MapListDataContext();
		@SuppressWarnings("unchecked")
		Map<String,Object> map=(Map<String,Object>)data.get(key);
		for (Map.Entry<String,Object> entry:map.entrySet()){
			mdc.put(entry.getKey(),entry.getValue());
		}
		return mdc;
	}

	public Object get(String name, int index) {
		return ((index >= 0 && index < this.getRecordCount()) ? this.getRecord(index).get(name) : null);
	}
	
	@SuppressWarnings("unchecked")
	private List<MapListDataContext> getDefaultRecords() {
		List<MapListDataContext> records = null;
		
		if (!this.containsKey(DEFAULT_NAME_OF_RECORDS)) {
			records = new ArrayList<MapListDataContext>();
			put(DEFAULT_NAME_OF_RECORDS, records);
		} else {
			records = (List<MapListDataContext>)get(DEFAULT_NAME_OF_RECORDS);
		}
		
		return records;
	}
	
	public boolean addRecord(MapListDataContext record) {
		getDefaultRecords().add(record);
		return true;
	}
	
	public void setRecord(int index, MapListDataContext record) {
		getDefaultRecords().set(index, record);
	}
	
	public int getRecordCount() {
		return getDefaultRecords().size();
	}

	public List<MapListDataContext> getRecords() {
		return getDefaultRecords();
	}

	public MapListDataContext getRecord(int index) {
		if (index >= 0 && index < this.getRecordCount())
			return (MapListDataContext)(getDefaultRecords().get(index));
		return null;
	}
	
	public MapListDataContext add(String name, Object value) {
		this.put(name, value);
		return this;
	}

	public void copyFrom(MapListDataContext dataContext) {
		if (dataContext == null)
			return;
		this.clear();
		for (String name : dataContext.keySet()) {
			this.put(name, dataContext.get(name));
		}
		this.copyRecordsFrom(dataContext);
	}

	protected void copyRecordsFrom(MapListDataContext dataContext) {
		if (dataContext.getRecords() != null) {
			for (MapListDataContext r : dataContext.getRecords())
				this.addRecord(r);
		}
	}

	public String getString(String name) {
		return this.get(name) != null ? this.get(name).toString() : null;
	}
	
	public void setString(String name, String value) {
		this.put(name, value);
	}

	public int getInt(String name, int defaultValue) {
		Object ret = this.get(name);
		if(ret==null){
			return defaultValue;
		}else{
			try {
				return Integer.valueOf(ret+"");
			} catch (Throwable t) {
				return  defaultValue;
			}
		}
	}

	public String getString(String name, int index) {
		Object ret = this.get(name, index);
		return ret == null ? null : ret.toString();
	}

	@Override
	public int size() {
		return data.size();
	}

	@Override
	public boolean isEmpty() {
		return data.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return data.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return data.containsValue(value);
	}

	@Override
	public Object get(Object key) {
		return data.get(key);
	}

	@Override
	public Object put(String key, Object value) {
		return data.put(key, value);
	}

	@Override
	public Object remove(Object key) {
		return data.remove(key);
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		data.putAll(m);
	}

	@Override
	public void clear() {
		data.clear();
	}

	@Override
	public Set<String> keySet() {
		return data.keySet();
	}

	@Override
	public Collection<Object> values() {
		return data.values();
	}

	@Override
	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		return data.entrySet();
	}

    @Override
    public String toString(){
        return data.toString();
    }
}
