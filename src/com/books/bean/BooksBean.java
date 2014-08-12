package com.books.bean;


public class BooksBean {

	private String bookImg;
	private String bookName;
	private String bookDesc;
	private String bookBuy;
	private int bookCat;
	public int getBookCat() {
		return bookCat;
	}
	public void setBookCat(int bookCat) {
		this.bookCat = bookCat;
	}
	@Override
	public String toString() {
		return "BooksBean [bookImg=" + bookImg + ", bookName=" + bookName
				+ ", bookDesc=" + bookDesc + ", bookBuy=" + bookBuy + "]";
	}
	public String getBookImg() {
		return bookImg;
	}
	public void setBookImg(String bookImg) {
		this.bookImg = bookImg;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getBookDesc() {
		return bookDesc;
	}
	public void setBookDesc(String bookDesc) {
		this.bookDesc = bookDesc;
	}
	public String getBookBuy() {
		return bookBuy;
	}
	public void setBookBuy(String bookBuy) {
		this.bookBuy = bookBuy;
	}
	
	
}
