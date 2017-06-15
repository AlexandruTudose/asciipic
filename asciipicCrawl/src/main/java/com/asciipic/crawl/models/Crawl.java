package com.asciipic.crawl.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "crawls")
public class Crawl implements Serializable{

    @Id
    @GeneratedValue(generator = "CrawlsSeq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "CrawlsSeq", sequenceName = "CRAWLS_ID_SEQ", allocationSize = 1)
    private long id;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="job_id")
    private Job job;

    @Column(name = "is_done")
    private boolean isDone;

    @ManyToMany
    @JoinTable(name="crawls_response_urls",
            joinColumns=
            @JoinColumn(name="crawl_id", referencedColumnName="id"),
            inverseJoinColumns=
            @JoinColumn(name="url_id", referencedColumnName="id")
    )
    private List<Url> urls;

    @NotNull
    @Column(name = "site")
    private String site;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "post_date")
    private Date postDate;

    @Column(name = "image_size")
    private String imageSize;

    @Column(name = "number_of_images")
    private long numberOfImages;

    @ManyToMany
    @JoinTable(name="crawls_tags",
            joinColumns=
            @JoinColumn(name="crawl_id", referencedColumnName="id"),
            inverseJoinColumns=
            @JoinColumn(name="tag_id", referencedColumnName="id")
    )
    private List<Tag> tags;

    public Crawl() {
    }

    public Crawl(Job job, boolean isDone, List<Url> urls, String site, Date postDate, String imageSize, long numberOfImages, List<Tag> tags) {
        this.job = job;
        this.isDone = isDone;
        this.urls = urls;
        this.site = site;
        this.postDate = postDate;
        this.imageSize = imageSize;
        this.numberOfImages = numberOfImages;
        this.tags = tags;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public List<Url> getUrls() {
        return urls;
    }

    public void setUrls(List<Url> urls) {
        this.urls = urls;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getImageSize() {
        return imageSize;
    }

    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }

    public long getNumberOfImages() {
        return numberOfImages;
    }

    public void setNumberOfImages(long numberOfImages) {
        this.numberOfImages = numberOfImages;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
