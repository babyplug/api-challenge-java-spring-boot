package com.babyplug.challenge.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

public class SqlUtils {
    
    public static StringBuffer genSqlPage(Pageable pageable, StringBuffer sql) {
        StringBuffer pageSql = new StringBuffer();
        if (pageable.getPageNumber() > 0) {

            int minRow = pageable.getPageNumber() * 25;
            int maxRow = (pageable.getPageNumber() * 25) + pageable.getPageSize();

            pageSql.append("select * from ( ");
            pageSql.append(" select row_.*, rownum rownum_ from( ").append(sql.toString()).append(" ) row_ ");
            pageSql.append(" where rownum <= ").append(maxRow);
            pageSql.append(" ) where rownum_ > ").append(minRow);
        } else {
            pageSql.append(" select * from( ").append(sql.toString()).append(" )");
            pageSql.append(" where rownum <= ").append(pageable.getPageSize());
        }
        return pageSql;
    }

	public static Predicate ignorecaseLikeString(CriteriaBuilder cb, Path<String> path, String value) {

		Predicate pre = null;
		if (StringUtils.isNotBlank(value)) {
			pre = cb.like(cb.upper(path), "%" + value.trim().toUpperCase() + "%");
		}

		return pre;
	}
    
}
