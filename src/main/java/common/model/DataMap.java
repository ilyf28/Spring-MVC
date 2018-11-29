package common.model;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class DataMap extends ConcurrentHashMap<Object, Object> {

	/**
	 * Default Serial Version ID
	 */
	private static final long serialVersionUID = 1L;
	
	private int kIndex = 0;
	private int arraySize = 0;
	
	public DataMap() {
		super(50);
	}
	
	public DataMap(int i) {
		super(i);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setValue(String key, int index, Object object) {
		Vector valueVector = (Vector) get(key);
		if (valueVector == null) {
			valueVector = new Vector(50, 100);
			put(key, valueVector);
			
			Vector<String> keyVector = (Vector) get("FIELDS");
			if (keyVector == null) {
				keyVector = new Vector(50, 100);
				put("FIELDS", keyVector);
			}
			if (kIndex >= keyVector.size()) {
				keyVector.setSize(kIndex + 1);
			}
			
			keyVector.setElementAt(key, kIndex++);
		}
		if (index >= valueVector.size()) {
			valueVector.setSize(index + 1);
		}
		
		valueVector.setElementAt(object, index);
		
		if (arraySize < (index + 1)) {
			arraySize = index + 1;
		}
	}
	
	public void setValue(String s, Object object) {
		setValue(s, 0, object);
	}
	
	public void setInt(String s, int i, int value) {
		setValue(s, i, new Integer(value));
	}
	
	public void setInt(String s, int value) {
		setValue(s, 0, new Integer(value));
	}
	
	public void setString(String s, int i, String value) {
		setValue(s, i, value);
	}
	
	public void setString(String s, String value) {
		setValue(s, 0, value);
	}
	
	@SuppressWarnings("rawtypes")
	public Object getValue(String key, int index, Object object) {
		Vector vector = (Vector) super.get(key);
		if (vector == null) return object;
		try {
			return vector.elementAt(index);
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			return object;
		}
	}
	
	public Object getValue(String s, int i) {
		return getValue(s, i, null);
	}
	
	public Object getValue(String s) {
		return getValue(s, 0, null);
	}
	
	public Object getValue(String s, Object object) {
		return getValue(s, 0, object);
	}
	
	public int getInt(String s, int i) {
		int result;
		try {
			Object value = getValue(s, i, 0);
			if (value instanceof String) {
				result = Integer.parseInt((String) value);
			} else {
				result = ((Integer) value).intValue();
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			result = 0;
		} catch (Exception e) {
			e.printStackTrace();
			result = 0;
		}
		
		return result;
	}
	
	public int getInt(String s) {
		return getInt(s, 0);
	}
	
	public String getString(String s, int i) {
		return (String) getValue(s, i);
	}
	
	public String getString(String s) {
		return (String) getValue(s, 0);
	}
	
	public int getArraySize() {
		return arraySize;
	}

}
