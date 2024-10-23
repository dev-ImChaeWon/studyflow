package com.studyflow.response;

import java.time.LocalDateTime;
import java.util.List;

public class PageResponse<T> {
	private List<T> list; // 페이지에 포함될 실제 데이터 리스트
	private int currentPage;
	private int totalPages;
	private boolean hasNext;
	private boolean hasPrevieous;
	private long totalElements;
	private LocalDateTime date;

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public boolean isHasPrevieous() {
		return hasPrevieous;
	}

	public void setHasPrevieous(boolean hasPrevieous) {
		this.hasPrevieous = hasPrevieous;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

}
