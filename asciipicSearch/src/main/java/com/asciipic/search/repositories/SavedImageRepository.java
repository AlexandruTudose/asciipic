package com.asciipic.search.repositories;

import com.asciipic.search.models.SavedImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface SavedImageRepository extends JpaRepository<SavedImage, Long> {
    @Transactional
    @Modifying
    @Query("update com.asciipic.search.models.SavedImage set data = ?1 where image_id = ?2")
    void updateBlob(byte[] blobImage,Long id);
}
