package com.sof.sof_financeiro.services;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created By : Berkson Ximenes
 * Date : 28/07/2025
 **/

public interface BaseService<T, ID> {
    T save(T entity);

    @Transactional
    Optional<T> getById(ID id);

    void delete(ID id);
}
