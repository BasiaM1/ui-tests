package com.basia.pages;

public abstract class AbstractPageValidator <E extends AbstractPage> {

    protected E page;

    protected AbstractPageValidator(E page) {
        this.page = page;
    }
}
