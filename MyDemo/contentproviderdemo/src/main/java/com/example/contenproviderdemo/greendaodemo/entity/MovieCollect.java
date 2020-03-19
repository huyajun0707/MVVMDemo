package com.example.contenproviderdemo.greendaodemo.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * =========================================================
 *
 * @author :   HuYajun     <13426236872@163.com>
 * @version :
 * @date :   2019/4/18 14:56
 * @description :
 * =========================================================
 */

@Entity
public class MovieCollect {
    @Id(autoincrement = true)
    private Long id;
    private String movieImage;
    private String title;

    @Override
    public String toString() {
        return "MovieCollect{" +
                "id=" + id +
                ", movieImage='" + movieImage + '\'' +
                ", title='" + title + '\'' +
                ", year=" + year +
                '}';
    }

    private int year;
    @Generated(hash = 88218779)
    public MovieCollect(Long id, String movieImage, String title, int year) {
        this.id = id;
        this.movieImage = movieImage;
        this.title = title;
        this.year = year;
    }
    @Generated(hash = 432838481)
    public MovieCollect() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMovieImage() {
        return this.movieImage;
    }
    public void setMovieImage(String movieImage) {
        this.movieImage = movieImage;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getYear() {
        return this.year;
    }
    public void setYear(int year) {
        this.year = year;
    }
}
