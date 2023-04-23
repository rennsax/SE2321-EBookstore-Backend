package com.sjtu.rbj.bookstore.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnTransformer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Bojun Ren
 * @date 2023/04/08
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`book`")
public class Book {
    @Id
    @GeneratedValue(generator = "uuid")
    @Column(name = "uuid", columnDefinition = "BINARY(16)")
    @ColumnTransformer(write = "uuid_to_bin(?)")
    private UUID uuid;

    @Column(nullable = false)
    private String title;

    @Column(name = "pic_id", unique = true)
    private String picId;

    private BigDecimal price;

    private String author;
    private Date date;

    @Column(unique = true)
    private String isbn;

    @Lob
    private String description;
}
