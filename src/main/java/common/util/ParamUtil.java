package common.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.model.DataMap;

public class ParamUtil {

	private static final Logger logger = LoggerFactory.getLogger(ParamUtil.class);
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getParameters(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		Map<String, String[]> requestParams = request.getParameterMap();
		
		for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
			String key = entry.getKey();
			String[] value = entry.getValue();
			String valueString = "";
			
			if (value.length > 1) {
				for (int i = 0, l = value.length; i < l; i++) {
					valueString += value[i] + ";";
				}
			} else {
				valueString = value[0];
			}
			
			map.put(key, valueString);
		}
		
		return map;
	}
	
	public static DataMap setupDataMap(List<Map<String, Object>> listMap) {
		DataMap dataMap = new DataMap();
		
		try {
			Map<String, Object> map = null;
			if (listMap.size() > 0) {
				String uppercaseKey = null;
				for (int i = 0, s = listMap.size(); i < s; i++) {
					map = listMap.get(i);
					if (!Objects.isNull(map)) {
						Set<String> keys = map.keySet();
						String[] key = new String[keys.size()];
						keys.toArray(key);
						for (int j = 0, l = key.length; j < l; j++) {
							uppercaseKey = key[j].toUpperCase();
							Object value = map.get(key[j]);
							
							if (Objects.isNull(value)) {
								dataMap.setString(uppercaseKey, i, "");
							} else {
								if (value instanceof String) {
									dataMap.setString(uppercaseKey, i, (String) value);
								} else if (value instanceof Integer) {
									dataMap.setInt(uppercaseKey, i, (Integer) value);
								} else {
									dataMap.setValue(uppercaseKey, i, value);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return dataMap;
	}
}
