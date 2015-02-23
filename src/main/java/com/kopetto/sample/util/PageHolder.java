package com.kopetto.sample.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageHolder<T> {
    public static final int MAX_PAGE_ITEM_DISPLAY = 5;
    private Page<T> page;
    private List<PageItem> items;
    private int currentNumber;
	private int totalPages;
    
    public PageHolder(Page<T> page){
    	this (page, -1);
    }
    
    public PageHolder(Page<T> page, int maxPages){
        this.page = page;
        items = new ArrayList<PageItem>();
        
        if (maxPages > -1 && this.page.getTotalPages() > maxPages)
        	this.totalPages = maxPages;
        else
        	this.totalPages = page.getTotalPages();

        currentNumber = page.getNumber() + 1; //start from 1 to match page.page
        
        int start, size;
        if (this.totalPages <= MAX_PAGE_ITEM_DISPLAY){
            start = 1;
            size = this.totalPages;
        } else {
            if (currentNumber <= MAX_PAGE_ITEM_DISPLAY - MAX_PAGE_ITEM_DISPLAY/2){
                start = 1;
                size = MAX_PAGE_ITEM_DISPLAY;
            } else if (currentNumber >= this.totalPages - MAX_PAGE_ITEM_DISPLAY/2){
                start = this.totalPages - MAX_PAGE_ITEM_DISPLAY + 1;
                size = MAX_PAGE_ITEM_DISPLAY;
            } else {
                start = currentNumber - MAX_PAGE_ITEM_DISPLAY/2;
                size = MAX_PAGE_ITEM_DISPLAY;
            }
        }
        
        for (int i = 0; i<size; i++){
            items.add(new PageItem(start+i, (start+i)==currentNumber));
        }
        
    }
    
    public List<PageItem> getItems(){
        return items;
    }
    
    public int getNumber(){
        return currentNumber;
    }
    
    public List<T> getContent(){
        return page.getContent();
    }
    
    public int getSize(){
        return page.getSize();
    }
    
    public int getTotalPages(){
        return this.totalPages;
    }
    
    public long getTotalElements(){
        return page.getTotalElements();
    }
    
    public boolean isFirstPage(){
        return page.isFirstPage();
    }
    
    public boolean isLastPage(){
        return page.isLastPage();
    }
    
    public boolean isHasPreviousPage(){
        return page.hasPreviousPage();
    }
    
    public boolean isHasNextPage(){
        return page.hasNextPage();
    }
    
    public class PageItem {
        private int number;
        private boolean current;
        public PageItem(int number, boolean current){
            this.number = number;
            this.current = current;
        }
        
        public int getNumber(){
            return this.number;
        }
        
        public boolean isCurrent(){
            return this.current;
        }
    }
}
