package com.fiap.challenge.order.application.usecases.product;

import java.util.List;

import com.fiap.challenge.order.application.domain.models.Product;
import com.fiap.challenge.order.application.domain.models.enums.CategorieEnums;

public interface FindByCategorieUseCase {

	List<Product> findByCategorie(CategorieEnums categorie);
}
