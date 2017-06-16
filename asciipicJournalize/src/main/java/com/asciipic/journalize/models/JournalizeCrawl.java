package com.asciipic.journalize.models;

import com.asciipic.journalize.models.Journalize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "j_crawl")
public class JournalizeCrawl {
    @Id
    @Column(name="journalize_id")
    private long id;

    @MapsId
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "journalize_id", referencedColumnName = "id")
    private Journalize journalize;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @NotNull
    @Column(name = "job_id")
    private long jobId;

    @Column(name = "post_date")
    private Date postDate;

    @Column(name = "image_size")
    private String size;


    @Column(name = "tag", length = 32)
    private String tag;

    public JournalizeCrawl() {
    }

    public JournalizeCrawl(Journalize journalize) {
        this.journalize = journalize;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Journalize getJournalize() {
        return journalize;
    }

    public void setJournalize(Journalize journalize) {
        this.journalize = journalize;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
