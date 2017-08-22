package com.glorystudent.entity;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Created by billlamian on 17-8-4.
 */

public class ItemMenuEntity<T> extends SectionEntity<T> {
    private T data;
    public ItemMenuEntity(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public ItemMenuEntity(T t) {
        super(t);
        data=t;
    }

    public T getData() {
        return data;
    }
}
