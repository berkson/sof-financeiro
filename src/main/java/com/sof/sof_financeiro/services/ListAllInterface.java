package com.sof.sof_financeiro.services;

import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created By : Berkson Ximenes
 * Date : 28/07/2025
 **/

public interface ListAllInterface<T, ID> {
    @Transactional
    Collection<T> getAll();
}
