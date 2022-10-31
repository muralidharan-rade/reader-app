package com.reader.readerapp.user;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBookRepository extends CassandraRepository<UserBook, UserBookPrimaryKey> {

}
