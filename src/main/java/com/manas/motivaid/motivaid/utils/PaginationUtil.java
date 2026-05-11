package com.manas.motivaid.motivaid.utils;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PaginationUtil {
private static final int DEFAULT_PAGE= 0;
private static final int DEFAULT_SIZE= 10;
private static final int MAX_SIZE =50;

public static Pageable createPageable(Integer page,Integer size) {
	
	int pageNumber = (page!=null && page>=0)
			?page:DEFAULT_PAGE;
	
	int pageSize = (size != null && size >=0)
			?Math.min(size, MAX_SIZE):DEFAULT_SIZE;
	
	return PageRequest.of(pageNumber, pageSize,Sort.by("createdDatetime").descending());
}
}
