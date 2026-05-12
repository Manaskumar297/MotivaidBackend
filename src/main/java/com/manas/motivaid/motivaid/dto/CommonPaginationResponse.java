package com.manas.motivaid.motivaid.dto;

import java.util.List;

import lombok.Data;

@Data
public class CommonPaginationResponse<T> {

	private List<T>content;
	private int page;
	private int size;
	private long totalEiments;
	private int totalpages;
	private boolean last;
}
