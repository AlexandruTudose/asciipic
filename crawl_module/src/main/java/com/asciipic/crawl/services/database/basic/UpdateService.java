package com.asciipic.crawl.services.database.basic;

public interface UpdateService<T> {
    T save(T entity);
}