package com.luqi.blog.dao;

import com.luqi.blog.po.Tag;
import com.luqi.blog.po.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String name);

    void delete(Long id);
}
