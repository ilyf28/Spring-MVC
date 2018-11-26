package base.employee.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import base.employee.mapper.EmployeeMapper;
import common.model.DataMap;
import common.util.ParamUtil;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeMapper employeeMapper;

	public DataMap selectEmployeeList() {
		List<Map<String, Object>> listMap = employeeMapper.selectEmployeeList();
		DataMap dataMap = ParamUtil.setupDataMap(listMap);
		return dataMap;
	}
}
