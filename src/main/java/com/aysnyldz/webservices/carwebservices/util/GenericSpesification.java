package com.aysnyldz.webservices.carwebservices.util;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class GenericSpesification<T> implements Specification<T> {

	private static final long serialVersionUID = -7788478094229318910L;

	private List<SearchCriteria> list;

	public GenericSpesification() {
		this.list = new ArrayList<>();
	}

	public void add(SearchCriteria criteria) {
		list.add(criteria);
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

		// create a new predicate list
		List<Predicate> predicates = new ArrayList<>();

		boolean isAll = false;

		// add add criteria to predicates
		for (SearchCriteria criteria : list) {

			if (criteria.getOperation().equals(SearchOperation.ALL)) {
				isAll = true;
			}

			if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
				predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
			}
		}

		Predicate result = builder.and(predicates.toArray(new Predicate[0]));

		if (isAll) {
			result = builder.or(predicates.toArray(new Predicate[0]));
		}
		
		return result;
	}

}
