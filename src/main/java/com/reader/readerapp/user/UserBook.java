package com.reader.readerapp.user;

import java.time.LocalDate;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "book_by_userid_bookid")
public class UserBook {

	@PrimaryKey
	private UserBookPrimaryKey key;

	@Column("start_date")
	@CassandraType(type = Name.DATE)
	private LocalDate startDate;

	@Column("end_date")
	@CassandraType(type = Name.DATE)
	private LocalDate endDate;

	@Column("reading_status")
	@CassandraType(type = Name.TEXT)
	private String readingStatus;

	@Column("rating")
	@CassandraType(type = Name.INT)
	private int rating;

	public UserBookPrimaryKey getKey() {
		return key;
	}

	public void setKey(UserBookPrimaryKey key) {
		this.key = key;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getReadingStatus() {
		return readingStatus;
	}

	public void setReadingStatus(String readingStatus) {
		this.readingStatus = readingStatus;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

}
