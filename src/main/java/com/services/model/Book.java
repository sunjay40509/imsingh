package com.services.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.IndexColumn;

@Entity
@Table(name = "Book")
public class Book {

	@Id
	private String isbn;
	private String title;
	private Integer bookPrice;
	private Integer noOfCopies;
	private Integer noOfIssueCopies;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinColumn(name = "users_id")
	@IndexColumn(name = "idx")
	private List<User> users;

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(Integer bookPrice) {
		this.bookPrice = bookPrice;
	}

	public Integer getNoOfCopies() {
		return noOfCopies;
	}

	public void setNoOfCopies(Integer noOfCopies) {
		this.noOfCopies = noOfCopies;
	}

	public Integer getNoOfIssueCopies() {
		return noOfIssueCopies;
	}

	public void setNoOfIssueCopies(Integer noOfIssueCopies) {
		this.noOfIssueCopies = noOfIssueCopies;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "Book [isbn=" + isbn + ", title=" + title + ", bookPrice="
				+ bookPrice + ", noOfCopies=" + noOfCopies
				+ ", noOfIssueCopies=" + noOfIssueCopies + ", users=" + users
				+ "]";
	}

}
