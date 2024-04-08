package com.yoaceng.authapp.repositories;

import com.yoaceng.authapp.domain.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, String> {
}
