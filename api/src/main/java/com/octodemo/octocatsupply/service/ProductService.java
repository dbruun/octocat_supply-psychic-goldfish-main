package com.octodemo.octocatsupply.service;

import com.octodemo.octocatsupply.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<Product> searchProductsByName(String name) {
		String sql = "SELECT * FROM products WHERE name LIKE '%" + name + "%'";
		Query query = entityManager.createNativeQuery(sql, Product.class);
		return query.getResultList();
	}
}
