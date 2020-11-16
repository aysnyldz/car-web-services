package com.aysnyldz.webservices.carwebservices.util;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class SearchSpecification<T> implements Specification<T> {

	private static final long serialVersionUID = -8116819816924672754L;

	private List<SearchCriteria> list;

	public SearchSpecification() {
		this.list = new ArrayList<>();
	}

	public void add(SearchCriteria criteria) {
		list.add(criteria);
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

		List<Predicate> predicates = new ArrayList<>();

		boolean isAll = false;

		for (SearchCriteria criteria : list) {

			if (criteria.getOperation().equals(SearchOperation.ALL)) {
				isAll = true;
			}

			if (criteria.getOperation().equals(SearchOperation.MATCH)) {
				Predicate predicateForGrade = builder.like(builder.lower(root.get(criteria.getKey())),
						"%" + criteria.getValue().toString().toLowerCase() + "%");
				predicates.add(predicateForGrade);
			}

		}

		Predicate result = builder.and(predicates.toArray(new Predicate[0]));

		if (isAll) {
			result = builder.or(predicates.toArray(new Predicate[0]));
		}
		
		return result;

	}
}
