/*
 * Copyright 2016 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.pivotal.scdf.demo.task.jdbc.task.common;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;

public class JdbcColumnToPojoRowMapper implements RowMapper<Map<String, Object>> {

	@Override
	public Map<String, Object> mapRow(ResultSet rs, int arg1) throws SQLException {
		Map<String, Object> builder = new HashMap<>();
		Map<String, Object> processed = new HashMap<>();
		ColumnStandardMapper mapper = new ColumnStandardMapper();

		ResultSetMetaData metaData = rs.getMetaData();
		for (int i = 1; i <= metaData.getColumnCount(); i++) {
			builder.put(JdbcUtils.lookupColumnName(metaData, i), JdbcUtils.getResultSetValue(rs, i));
		}
		// send map to groovyColumnProcessor.process()
		processed = mapper.process(builder);

		return processed;
	}
}
